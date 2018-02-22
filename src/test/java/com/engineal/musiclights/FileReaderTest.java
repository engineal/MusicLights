/*
 * Copyright 2018 Aaron Lucia.
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
package com.engineal.musiclights;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class FileReaderTest {

    private static final Logger log = LogManager.getLogger(FileReaderTest.class);
    
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
     * Test of parseLine method, of class FileReader.
     */
    @Test
    public void testParseFrameLine() {
        log.info("parseFrameLine");
        String frameLine = "frame";
        FileReader fileReader = new FileReader();
        assertEquals(0, fileReader.getFrames().size());
        fileReader.parseLine(frameLine);
        assertEquals(1, fileReader.getFrames().size());
        fileReader.parseLine(frameLine);
        assertEquals(2, fileReader.getFrames().size());
        fileReader.parseLine(frameLine);
        assertEquals(3, fileReader.getFrames().size());
    }

    /**
     * Test of parseLine method, of class FileReader.
     */
    @Test
    public void testParseSingleLine() {
        log.info("parseSingleLine");
        String frameLine = "frame";
        String line1 = "1 = #000000";
        String line2 = "2 = #0099aa";
        String line3 = "3 = #FFFFFF";
        FileReader fileReader = new FileReader();
        assertEquals(0, fileReader.getFrames().size());
        fileReader.parseLine(frameLine);
        assertEquals(1, fileReader.getFrames().size());
        fileReader.parseLine(line1);
        assertEquals(1, fileReader.getFrames().size());
        fileReader.parseLine(line2);
        assertEquals(1, fileReader.getFrames().size());
        fileReader.parseLine(frameLine);
        assertEquals(2, fileReader.getFrames().size());
        fileReader.parseLine(line3);
        assertEquals(2, fileReader.getFrames().size());
    }

    /**
     * Test of parseLine method, of class FileReader.
     */
    @Test
    public void testParseRangeLine() {
        log.info("parseRangeLine");
        String frameLine = "frame";
        String line1 = "[1-2] = #000000";
        String line2 = "[2-3] = #0099aa";
        String line3 = "[3-4] = #FFFFFF";
        FileReader fileReader = new FileReader();
        assertEquals(0, fileReader.getFrames().size());
        fileReader.parseLine(frameLine);
        assertEquals(1, fileReader.getFrames().size());
        fileReader.parseLine(line1);
        assertEquals(1, fileReader.getFrames().size());
        fileReader.parseLine(line2);
        assertEquals(1, fileReader.getFrames().size());
        fileReader.parseLine(frameLine);
        assertEquals(2, fileReader.getFrames().size());
        fileReader.parseLine(line3);
        assertEquals(2, fileReader.getFrames().size());
    }
}
