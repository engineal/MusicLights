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

import com.engineal.musiclights.display.FrameBuffer;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Aaron Lucia
 */
public class StillEffect implements Effect {

    private static final Logger LOG = Logger.getLogger(StillEffect.class.getName());

    private boolean running;
    private final Color color;

    /**
     *
     * @param color
     */
    public StillEffect(Color color, double duration) {
        running = true;
        this.color = color;

    }

    @Override
    public boolean hasNext() {
        return running;
    }

    @Override
    public void apply(FrameBuffer buffer) {
        Map<Integer, Color> m = new HashMap<>();
        for (int i = 0; i < 150; i++) {
            m.put(i, color);
        }
    }
    
    @Override
    public void next() {
        running = false;
    }
}
