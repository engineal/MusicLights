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

import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;
import com.pi4j.io.spi.SpiMode;
import java.io.IOException;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Aaron Lucia
 */
public class SPIDotStar extends DotStar {
    
    private static final Logger log = LogManager.getLogger(SPIDotStar.class);

    private SpiDevice spi;

    /**
     * Allocate new SPIDotStar object with hardware SPI @ bitrate
     *
     * @param numLEDs The number of LEDs on the SPI channel
     * @param speed The bitrate for SPI communication
     * @throws java.io.IOException
     */
    public SPIDotStar(int numLEDs, int speed) throws IOException {
        super(numLEDs);
        
        if (speed < 500000 || speed > 32000000) {
            throw new IllegalArgumentException("Speed must be between 500kHz - 32MHz");
        }

        spi = SpiFactory.getInstance(SpiChannel.CS0, speed, SpiMode.MODE_0);
    }

    /**
     * Allocate new SPIDotStar object with hardware SPI @ default rate
     *
     * @param numLEDs The number of LEDs on the SPI channel
     * @throws java.io.IOException
     */
    public SPIDotStar(int numLEDs) throws IOException {
        this(numLEDs, 8000000);
    }

    /**
     * Issue pixel buffer to strip
     *
     * @throws java.io.IOException
     */
    @Override
    public void show() throws IOException {
        byte[] header = {0x00, 0x00, 0x00, 0x00};
        byte[] footer = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        
        log.info(Arrays.toString(data));
        
        spi.write(header);
        spi.write(data);
        spi.write(footer);
    }
}
