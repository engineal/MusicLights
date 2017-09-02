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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 * @author Aaron Lucia
 */
public class Room {
    
    private static final Logger LOG = Logger.getLogger(Room.class.getName());

    private final Set<Display> displays;

    public Room() {
        this.displays = new HashSet<>();
    }
    
    public void addDisplay(Display display) {
        displays.add(display);
    }
    
    public void applyEffect(Effect effect) {
        displays.stream().forEach(d -> d.applyEffect(effect));
    }
}
