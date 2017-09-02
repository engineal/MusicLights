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
import com.engineal.musiclights.display.effects.StillEffect;
import com.engineal.musiclights.display.io.MockDotStar;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Aaron Lucia
 */
public class DotStarDisplayTest {

    private static final Logger LOG = Logger.getLogger(DotStarDisplayTest.class.getName());

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public DotStarDisplayTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of runEffect method, of class DotStarDisplay.
     */
    @Test
    public void testRunEffect() {
        LOG.log(Level.INFO, "runEffect");
        int numLEDs = 150;
        MockDotStar strip = new MockDotStar(numLEDs);
        DotStarDisplay instance = new DotStarDisplay(strip, 5);
        Color color = new Color(63, 123, 255);
        Effect effect = new StillEffect(color);
        instance.applyEffect(effect, 0);

        byte[] data = strip.getData();
        for (int i = 0; i < numLEDs; i++) {
            assertEquals((byte) 0xFF, data[4 * i]);
            assertEquals((byte) color.getBlue(), data[(4 * i) + 1]);
            assertEquals((byte) color.getGreen(), data[(4 * i) + 2]);
            assertEquals((byte) color.getRed(), data[(4 * i) + 3]);
        }
    }
}
