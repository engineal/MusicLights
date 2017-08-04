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
package com.engineal.musiclights.effects;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Moving Rainbows
 *
 * @author Aaron Lucia
 */
public class RainbowEffect extends Effect {
    
    int position;
    private final int segmentLength;

    /**
     *
     * @param segmentLength the length until the colors repeat
     */
    public RainbowEffect(int segmentLength) {
        position = 0;
        this.segmentLength = segmentLength;
    }

    @Override
    public boolean running() {
        return true;
    }

    @Override
    public Map getChanges() {
        Map<Integer, Color> m = new HashMap<>();
        
        double frequency = 2 * Math.PI / segmentLength;
        for (int i = 0; i < 150; i++) {
            int red = (int) (Math.sin(frequency*(i+position)) * 127 + 128);
            int green = (int) (Math.sin(frequency*(i+position) + 2*Math.PI/3) * 127 + 128);
            int blue = (int) (Math.sin(frequency*(i+position) + 4*Math.PI/3) * 127 + 128);
            m.put(i, new Color(red, green, blue));
        }
        return m;
    }

    @Override
    public void advance() {
        position = (position + 1) % 150;
    }
}
