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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Aaron Lucia
 */
public class DotStarTest {

    public DotStarTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of clear method, of class DotStar.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        int numLEDs = 150;
        DotStar instance = new MockDotStar(numLEDs);
        instance.clear();

        for (int i = 0; i < numLEDs; i++) {
            assertEquals((byte) 0xFF, instance.data[4 * i]);
            assertEquals((byte) 0x00, instance.data[(4 * i) + 1]);
            assertEquals((byte) 0x00, instance.data[(4 * i) + 2]);
            assertEquals((byte) 0x00, instance.data[(4 * i) + 3]);
        }
    }

    /**
     * Test of setPixelColor method, of class DotStar.
     */
    @Test
    public void testSetPixelColor() {
        System.out.println("setPixelColor");
        int numLEDs = 150;
        Color color = new Color(63, 123, 255);
        DotStar instance = new MockDotStar(numLEDs);
        for (int i = 0; i < numLEDs; i++) {
            instance.setPixelColor(i, color);
        }

        for (int i = 0; i < numLEDs; i++) {
            assertEquals((byte) 0xFF, instance.data[4 * i]);
            assertEquals((byte) color.getBlue(), instance.data[(4 * i) + 1]);
            assertEquals((byte) color.getGreen(), instance.data[(4 * i) + 2]);
            assertEquals((byte) color.getRed(), instance.data[(4 * i) + 3]);
        }
    }

    /**
     * Test that show() does not modify data array
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testShow() throws Exception {
        System.out.println("show");
        int numLEDs = 150;
        Color color = new Color(63, 123, 255);
        DotStar instance = new MockDotStar(numLEDs);
        for (int i = 0; i < numLEDs; i++) {
            instance.setPixelColor(i, color);
        }

        instance.show();

        for (int i = 0; i < numLEDs; i++) {
            assertEquals((byte) 0xFF, instance.data[4 * i]);
            assertEquals((byte) color.getBlue(), instance.data[(4 * i) + 1]);
            assertEquals((byte) color.getGreen(), instance.data[(4 * i) + 2]);
            assertEquals((byte) color.getRed(), instance.data[(4 * i) + 3]);
        }
    }

    /**
     * Test that we have to have at least 1 LED
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testZeroLEDs() {
        System.out.println("zero LEDs");
        DotStar instance = new MockDotStar(0);
        fail("Minimum LED count should be 1");
    }

    /**
     * Test that we cannot set negative LEDs
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testNegativeIndex() {
        int numLEDs = 150;
        Color color = Color.WHITE;
        DotStar instance = new MockDotStar(numLEDs);
        instance.setPixelColor(-1, color);
        fail("Index cannot be less than 0");
    }

    /**
     * Test that we cannot set larger LEDs
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testLargerIndex() {
        int numLEDs = 150;
        Color color = Color.WHITE;
        DotStar instance = new MockDotStar(numLEDs);
        instance.setPixelColor(numLEDs + 1, color);
        fail("Index cannot be less than 0");
    }

}
