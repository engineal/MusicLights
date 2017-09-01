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

import org.webbitserver.WebSocketConnection;
import org.webbitserver.WebSocketHandler;

/**
 *
 * @author Aaron Lucia
 */
public class WebSocketServer implements WebSocketHandler {

    private int connectionCount;

    @Override
    public void onOpen(WebSocketConnection connection) {
        connection.send("Hello! There are " + connectionCount + " other connections active");
        connectionCount++;
    }

    @Override
    public void onClose(WebSocketConnection connection) {
        connectionCount--;
    }

    @Override
    public void onMessage(WebSocketConnection connection, String message) {
        connection.send(message.toUpperCase());
    }

    @Override
    public void onMessage(WebSocketConnection connection, byte[] msg) {
        connection.send(msg);
    }

    @Override
    public void onPing(WebSocketConnection connection, byte[] msg) {
        connection.send("Ping");
    }

    @Override
    public void onPong(WebSocketConnection connection, byte[] msg) {
        connection.send("Pong");
    }
}
