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
package com.engineal.musiclights.display;

import com.engineal.musiclights.display.io.DotStar;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aaron Lucia
 */
public class DotStarDisplay {

    private static final Logger LOG = Logger.getLogger(DotStarDisplay.class.getName());

    private final DotStar strip;

    /**
     * Create a new DotStarDisplay
     *
     * @throws java.io.IOException
     */
    public DotStarDisplay() throws IOException {
        strip = new DotStar(150);
    }

    public void run() {
        for (int i = 0; i < 100; i += 3) {
            strip.setPixelColor(i, Color.RED);
            strip.setPixelColor(i + 1, Color.GREEN);
            strip.setPixelColor(i + 2, Color.BLUE);
        }
        try {
            strip.show();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Return true
     *
     * @return
     */
    public boolean test() {
        return true;
    }
}
