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
package com.engineal.musiclights.display.io;

import java.awt.Color;
import java.io.IOException;

/**
 *
 * @author Aaron Lucia
 */
public abstract class DotStar {

    protected final byte[] data; // pixel data

    public DotStar(int numLEDs) {
        if (numLEDs < 1) {
            throw new IllegalArgumentException("You must have at least 1 LED");
        }
        data = new byte[numLEDs * 4];
        for (int i = 0; i < data.length; i += 4) {
            data[i] = (byte) 0xFF;
        }
    }

    /**
     * Set strip data to 'off' (just clears buffer, does not write to strip)
     */
    public void clear() {
        for (int i = 0; i < data.length; i += 4) {
            data[i + 1] = 0x00;
            data[i + 2] = 0x00;
            data[i + 3] = 0x00;
        }
    }

    /**
     * Set the color of the specific pixel
     *
     * @param index The pixel to set
     * @param color The color for the pixel
     */
    public void setPixelColor(int index, Color color) {
        int offset = index * 4;
        if (offset < 0 || offset > data.length) {
            throw new IllegalArgumentException("Index out of range");
        }

        data[offset + 1] = (byte) (0xFF & color.getBlue());
        data[offset + 2] = (byte) (0xFF & color.getGreen());
        data[offset + 3] = (byte) (0xFF & color.getRed());
    }

    public abstract void show() throws IOException;
}
