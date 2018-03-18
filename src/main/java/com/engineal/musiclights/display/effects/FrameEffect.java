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
package com.engineal.musiclights.display.effects;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Aaron Lucia
 */
public class FrameEffect implements Effect {
    
    private int place = 0;
    private Map<Integer, Color> value;
    
    public FrameEffect() {
        value = new HashMap<>();
        value.put(1, Color.green);
        value.put(2, Color.black);
    }
    
    public FrameEffect(Map<Integer, Color> value) {
        this.value = value;
    }
    
    public Map<Integer, Color> getValue() {
        return Collections.unmodifiableMap(value);
    }

    public void addSingle(double location, Color color) {

    }

    public void addRange(double from, double to, Color color) {

    }

    @Override
    public Color apply(double x) {
        Color c = value.get(place);
        place++;
        return c;
    }

}
