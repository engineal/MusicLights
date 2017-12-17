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

import com.engineal.musiclights.display.effects.Effect;
import com.engineal.musiclights.display.io.DotStar;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Aaron Lucia
 */
public class DotStarDisplay extends Display {
    
    private static final Logger log = LogManager.getLogger(DotStarDisplay.class);

    private final DotStar strip;
    private final int length;

    /**
     * Create a new DotStarDisplay
     *
     * @param strip The DotStar strip
     * @param length The physical length of the display in m
     */
    public DotStarDisplay(DotStar strip, double length) {
        super(length, 0, 0);
        this.strip = strip;
        this.length = (int) length;
    }

    @Override
    public void applyEffect(Effect effect) {
        double resolution = length / strip.getNumLEDs();
        for (int i = 0; i < strip.getNumLEDs(); i++) {
            strip.setPixelColor(i, effect.apply(i * resolution));
        }
        try {
            strip.show();
        } catch (IOException ex) {
            log.error(ex);
        }
    }
}
