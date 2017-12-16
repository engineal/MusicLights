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
package com.engineal.musiclights.display.effects;

import java.awt.Color;

/**
 * Moving Rainbows
 *
 * @author Aaron Lucia
 */
public class RainbowEffect implements Effect {
    
    private int position;
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
    public Color apply(int x) {
        double frequency = 2 * Math.PI / segmentLength;
        int red = (int) (Math.sin(frequency * (x + position)) * 127 + 128);
        int green = (int) (Math.sin(frequency * (x + position) + 2 * Math.PI / 3) * 127 + 128);
        int blue = (int) (Math.sin(frequency * (x + position) + 4 * Math.PI / 3) * 127 + 128);
        return new Color(red, green, blue);
    }

    public void next() {
        position = (position + 1) % 150;
    }
}
