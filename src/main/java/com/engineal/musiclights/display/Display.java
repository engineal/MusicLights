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

/**
 *
 * @author Aaron Lucia
 */
public abstract class Display {

    private final double length;
    private final double width;
    private final double height;

    /**
     *
     * @param length
     * @param width
     * @param height
     */
    public Display(double length, double width, double height) {
        this.length = length;
        this.width = width;
        this.height = height;

    }

    protected abstract FrameBuffer getFrameBuffer();

    /**
     * Apply the effect over duration
     *
     * @param effect
     * @param duration The time in seconds to take for this 
     */
    public void applyEffect(Effect effect) {
        while (effect.hasNext()) {
            FrameBuffer buff = getFrameBuffer();

            effect.apply(buff);

            effect.next();
        }
    }

    /**
     *
     * @return
     */
    public double getLength() {
        return length;
    }

    /**
     *
     * @return
     */
    public double getWidth() {
        return width;
    }

    /**
     *
     * @return
     */
    public double getHeight() {
        return height;
    }
}
