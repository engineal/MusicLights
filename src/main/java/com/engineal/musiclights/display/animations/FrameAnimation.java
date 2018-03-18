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
package com.engineal.musiclights.display.animations;

import com.engineal.musiclights.display.Display;
import com.engineal.musiclights.display.effects.Effect;
import java.util.List;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Aaron Lucia
 */
public class FrameAnimation {
    
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(FrameAnimation.class);

    public List<Effect> frames;

    public void play(Display display) {
        for (Effect frame : frames) {
            display.applyEffect(frame);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                log.error(ex);
            }
        }
    }
}
