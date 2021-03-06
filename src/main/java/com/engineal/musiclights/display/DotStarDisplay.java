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
import com.engineal.musiclights.effects.Effect;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aaron Lucia
 */
public class DotStarDisplay extends Display {

    private static final Logger LOG = Logger.getLogger(DotStarDisplay.class.getName());

    private final DotStar strip;

    /**
     * Create a new DotStarDisplay
     *
     * @param strip
     */
    public DotStarDisplay(DotStar strip) {
        this.strip = strip;
    }

    public void runEffect(Effect effect, long duration) {
        for (int i = 0; i < duration; i++) {
            strip.clear();

            effect.getChanges().entrySet().forEach((pair) -> {
                strip.setPixelColor(pair.getKey(), pair.getValue());
            });

            try {
                strip.show();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }

            effect.advance();
        }
    }

    private void run() {
        int head = 0; // Index of first 'on' pixel
        int tail = -10; // Index of last 'off' pixel
        Color color = Color.RED; // 'On' color (starts red)

        for (int i = 0; i < 10000; i++) {
            if (head >= 0 && head < 150) {
                strip.setPixelColor(head, color);       // Turn on 'head' pixel
            }
            if (tail >= 0 && tail < 150) {
                strip.setPixelColor(tail, Color.BLACK); // Turn off 'tail'
            }

            try {
                strip.show();                       // Refresh strip
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }

            head += 1;                              // Advance head position
            if (head >= 150) {    // Off end of strip?
                head = 0;                           // Reset to start
                if (color == Color.RED) {
                    color = Color.BLUE;
                } else if (color == Color.BLUE) {
                    color = Color.GREEN;
                } else if (color == Color.GREEN) {
                    color = Color.RED;
                }
            }
            tail += 1;                              // Advance tail position
            if (tail >= 150) {
                tail = 0;                           // Off end? Reset
            }
        }
    }
}
