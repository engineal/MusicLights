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
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Aaron Lucia
 */
public class Panel2D extends JPanel {

    private static final long serialVersionUID = 1L;
    
    protected final Color[] data; // pixel data

    public Panel2D(int width, int height, double scaling) {
        if (width < 1) {
            throw new IndexOutOfBoundsException("You must have at least 1 pixel");
        }
        super.setMinimumSize(new Dimension(width, height));
        super.setPreferredSize(new Dimension(width, height));
        super.setMaximumSize(new Dimension(width, height));
        super.setBackground(Color.black);

        data = new Color[(int)(width / scaling)];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        double resolution = (double) data.length / getWidth();
        for (int i = 0; i < getWidth(); i++) {
            g.setColor(data[(int)(i * resolution)]);
            g.drawLine(i, 0, i, getHeight());
        }
    }
    
    public int getNumPixels() {
        return data.length;
    }

    public void clear() {
        for (int i = 0; i < data.length; i++) {
            data[i] = Color.black;
        }
    }

    /**
     * Set the color of the specific pixel
     *
     * @param index The pixel to set
     * @param color The color for the pixel
     */
    public void setPixelColor(int index, Color color) {
        if (index < 0 || index >= data.length) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }

        data[index] = color;
    }
    
    public void showChanges() {
        repaint();
        revalidate();
    }
}
