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
import java.util.logging.Logger;

/**
 *
 * @author Aaron Lucia
 */
public class FadeEffect implements Effect {

    private static final Logger LOG = Logger.getLogger(FadeEffect.class.getName());
    
    /**
     * Creates an effect that fades
     */
    public FadeEffect() {

    }

    @Override
    public Color apply(int x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}