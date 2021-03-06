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
package com.engineal.musiclights;

import com.engineal.musiclights.server.DiscoveryServer;
import com.engineal.musiclights.server.WebSocketServer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.handler.StaticFileHandler;

/**
 *
 * @author Aaron Lucia
 */
public class MusicLights {

    private static final Logger LOG = Logger.getLogger(MusicLights.class.getName());

    /**
     * Begin the program
     *
     * @param args
     */
    public static void main(String[] args) {
        LOG.log(Level.INFO, "Hello World!");
        WebServer webServer = WebServers.createWebServer(8080)
                .add("/hellowebsocket", new WebSocketServer())
                .add(new StaticFileHandler("web"));
        webServer.start();
        LOG.log(Level.INFO, "Server running at {0}", webServer.getUri());
        
        DiscoveryServer discoveryServer = new DiscoveryServer(8888);
        discoveryServer.start();
        
        /*try {
            DotStarDisplay display = new DotStarDisplay(new SPIDotStar(150));
            display.runEffect(new RainbowEffect(30), 5000);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }*/
    }
}
