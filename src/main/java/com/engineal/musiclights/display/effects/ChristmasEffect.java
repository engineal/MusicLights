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
import java.util.Random;

/**
 *
 * @author engin
 */
public class ChristmasEffect implements Effect {
    
    private final Random r;
    private Color previousColor;
    
    public ChristmasEffect() {
        r = new Random();
    }

    @Override
    public Color apply(double x) {
        Color color = getColor();
        while (color.equals(previousColor)) {
            color = getColor();
        }
        previousColor = color;
        return color;
    }
    
    private Color getColor() {
        switch (r.nextInt(5)) {
            case 0:
                return Color.RED;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.YELLOW;
            case 4:
            default:
                return Color.MAGENTA;
        }
    }
    
}
