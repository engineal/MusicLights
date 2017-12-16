/*
 * Copyright 2017 Pivotal Software, Inc..
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
package com.engineal.musiclights.server.config;

import com.engineal.musiclights.display.Display;
import com.engineal.musiclights.display.DotStarDisplay;
import com.engineal.musiclights.display.FrameDisplay;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Aaron Lucia
 */
@Configuration
public class DisplayConfig {

    @Bean
    @ConditionalOnProperty(name = "display", havingValue = "frame", matchIfMissing = true)
    public Display frameDisplay() {
        return new FrameDisplay(500, 700);
    }
    
    @Bean
    @ConditionalOnProperty(name = "display", havingValue = "dotstar")
    public Display dotStarDisplay() {
        return new DotStarDisplay(null, 150);
    }
}
