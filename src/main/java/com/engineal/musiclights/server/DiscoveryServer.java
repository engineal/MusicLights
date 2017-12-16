/*
 * Copyright 2017 Aaron Lucia.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.engineal.musiclights.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Aaron Lucia
 */
public class DiscoveryServer {

    private static final Logger log = LogManager.getLogger(DiscoveryServer.class);
    private static final String BROADCAST_REQUEST = "MUSICLIGHTS_DISCOVERY_REQUEST";
    private static final String BROADCAST_RESPONSE = "MUSICLIGHTS_DISCOVERY_RESPONSE";

    private final Thread requestThread;
    private final Thread responseThread;

    private final Set<DiscoveryServerListener> listeners;

    public DiscoveryServer(int port) {
        requestThread = new Thread(new RequestThread());
        responseThread = new Thread(new ResponseThread(port));
        listeners = Collections.synchronizedSet(new HashSet<>());
    }

    public boolean addDiscoveryServerListener(DiscoveryServerListener listener) {
        return listeners.add(listener);
    }

    public boolean removeDiscoveryServerListener(DiscoveryServerListener listener) {
        return listeners.remove(listener);
    }

    private void notifyListeners(InetAddress address) {
        listeners.stream().forEach(listener -> listener.peerFound(address));
    }

    public void start() {
        requestThread.start();
        responseThread.start();
    }

    public void stop() {
        requestThread.interrupt();
        responseThread.interrupt();
    }

    private class RequestThread implements Runnable {

        @Override
        public void run() {
            //Open a random port to send the package
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setBroadcast(true);

                byte[] sendData = BROADCAST_REQUEST.getBytes();

                //Try the 255.255.255.255 first
                try {
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
                    socket.send(sendPacket);
                    log.debug("Request packet sent to: 255.255.255.255 (DEFAULT)");
                } catch (IOException ex) {
                    log.error("Exception while trying to send request", ex);
                }

                // Broadcast the message over all the network interfaces
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = interfaces.nextElement();

                    if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                        continue; // Don't want to broadcast to the loopback interface
                    }

                    for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                        InetAddress broadcast = interfaceAddress.getBroadcast();
                        if (broadcast == null) {
                            continue;
                        }

                        // Send the broadcast package!
                        try {
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
                            socket.send(sendPacket);
                        } catch (IOException ex) {
                            log.error("Exception while trying to send request", ex);
                        }

                        log.debug("Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
                    }
                }

                log.info("Done looping over all network interfaces. Now waiting for replies!");

                while (!Thread.interrupted()) {
                    //Wait for a response
                    byte[] recvBuf = new byte[128];
                    DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
                    socket.receive(receivePacket);

                    //Check if the message is correct
                    String message = new String(receivePacket.getData()).trim();
                    if (message.equals(BROADCAST_RESPONSE)) {
                        //We have a response
                        log.debug("Broadcast response from server: " + receivePacket.getAddress().getHostAddress());
                        notifyListeners(receivePacket.getAddress());
                    }
                }
            } catch (IOException ex) {
                log.error("Exception in request thread", ex);
            }
        }
    }

    private class ResponseThread implements Runnable {

        private final int port;

        ResponseThread(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            //Keep a socket open to listen to all the UDP trafic that is destined for this port
            try (DatagramSocket socket = new DatagramSocket(port)) {
                socket.setBroadcast(true);
                log.info("Ready to receive broadcast packets!");

                while (!Thread.interrupted()) {
                    //Receive a packet
                    byte[] recvBuf = new byte[128];
                    DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
                    socket.receive(receivePacket);

                    //Packet received
                    String message = new String(receivePacket.getData()).trim();
                    if (message.equals(BROADCAST_REQUEST)) {
                        log.debug("Broadcast request received from: " + receivePacket.getAddress().getHostAddress());
                        notifyListeners(receivePacket.getAddress());

                        byte[] sendData = BROADCAST_RESPONSE.getBytes();

                        //Send a response
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                        socket.send(sendPacket);

                        log.debug("Sent packet to: " + sendPacket.getAddress().getHostAddress());
                    }
                }
            } catch (IOException ex) {
                log.error("Exception in response thread", ex);
            }
        }
    }
}
