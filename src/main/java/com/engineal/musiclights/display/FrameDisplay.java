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
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author Aaron Lucia
 */
public class FrameDisplay extends Display {

    private final Panel2D panel;
    private final double resolution;
    private final double scaling;
    
    public FrameDisplay(double width, double height, double resolution) {
        this(width, height, resolution, 1);
    }

    public FrameDisplay(double width, double height, double resolution, double scaling) {
        super(0, width, height);
        this.resolution = resolution;
        this.scaling = scaling;
        panel = new Panel2D((int) (width * resolution * scaling), (int) (height * resolution * scaling));
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
        int x = 0;
        Color xcolor = effect.apply(x);
        for (int i = 0; i < panel.getWidth(); i++) {
            if ((int) (i / scaling) != x) {
                x = (int) (i / scaling);
                xcolor = effect.apply(x);
            }
            panel.setPixelColor(i, xcolor);
        }
        panel.showChanges();
    }
}
