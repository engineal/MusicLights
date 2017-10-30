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
import com.engineal.musiclights.display.io.Panel2D;
import java.awt.EventQueue;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Aaron Lucia
 */
public class FrameDisplay extends Display {

    private static final Logger LOG = Logger.getLogger(FrameDisplay.class.getName());

    private final Panel2D panel;
    private final int length;

    public FrameDisplay(double width, double height) {
        super(0, width, height);
        this.length = (int) width;
        panel = new Panel2D((int) width, (int) height);
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Music Lights");
            frame.add(panel);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    @Override
    public void applyEffect(Effect effect) {
        for (int i = 0; i < length; i++) {
            panel.setPixelColor(i, effect.apply(i));
        }
    }

}
