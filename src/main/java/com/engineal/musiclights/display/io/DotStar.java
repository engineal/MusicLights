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

import com.pi4j.wiringpi.Spi;
import java.awt.Color;
import java.io.IOException;

/**
 *
 * @author Aaron Lucia
 */
public class DotStar {

    private int fd;

    private final byte[] data; // pixel data

    /**
     * Allocate new DotStar object with hardware SPI @ bitrate
     *
     * @param numLEDs The number of LEDs on the SPI channel
     * @param speed The bitrate for SPI communication
     * @throws java.io.IOException
     */
    public DotStar(int numLEDs, int speed) throws IOException {
        if (numLEDs < 1) {
            throw new IllegalArgumentException("You must have at least 1 LED");
        }
        data = new byte[numLEDs * 4];
        for (int i = 0; i < data.length; i += 4) {
            data[i] = (byte) 0xFF;
        }

        if (speed < 500000 || speed > 32000000) {
            throw new IllegalArgumentException("Speed must be between 500kHz - 32MHz");
        }

        fd = Spi.wiringPiSPISetupMode(0, speed, 0x40);
        if (fd <= -1) {
            throw new IOException("SPI port setup failed, wiringPiSPISetupMode returned " + fd);
        }
    }

    /**
     * Allocate new DotStar object with hardware SPI @ default rate
     *
     * @param numLEDs The number of LEDs on the SPI channel
     * @throws java.io.IOException
     */
    public DotStar(int numLEDs) throws IOException {
        this(numLEDs, 8000000);
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
        
        data[offset + 1] = (byte) (0xFF & color.getRed());
        data[offset + 2] = (byte) (0xFF & color.getGreen());
        data[offset + 3] = (byte) (0xFF & color.getBlue());
    }

    /**
     * Issue pixel buffer to strip
     *
     * @throws java.io.IOException
     */
    public void show() throws IOException {
        System.out.println(data);
        if (Spi.wiringPiSPIDataRW(0, data) <= 0) {
            throw new IOException("Failed to write data to SPI channel: " + 0);
        }
    }

    /**
     * Issue data to strip. Raw data must be in strip-ready format (4
     * bytes/pixel, 0xFF/B/G/R) and no brightness scaling is performed...it's
     * all about speed (for POV, etc.)
     *
     * @param data raw bytearray to issue to strip
     * @throws com.engineal.musiclights.display.io.DotStarException
     */
    /*public void show(byte[] data) throws DotStarException {
        if (spi == null) {
            throw new DotStarException("SPI device never initialized");
        }

        try {
            spi.write(data);
        } catch (IOException ex) {
            throw new DotStarException("Could not write to SPI device", ex);
        }
    }*/
}
