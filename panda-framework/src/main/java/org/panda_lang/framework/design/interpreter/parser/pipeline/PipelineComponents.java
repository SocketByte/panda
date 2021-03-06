/*
 * Copyright (c) 2015-2020 Dzikoysk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.panda_lang.framework.design.interpreter.parser.pipeline;

import org.panda_lang.framework.PandaFrameworkException;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Represents collection of pipeline components
 */
public interface PipelineComponents {

    /**
     * Collect pipeline components
     *
     * @return collection of pipeline components
     */
    default Collection<PipelineComponent<?>> collectPipelineComponents() {
        return Arrays.stream(this.getClass().getFields())
                .filter(field -> PipelineComponent.class.isAssignableFrom(field.getType()))
                .map(field -> {
                    try {
                        return (PipelineComponent<?>) field.get(this);
                    } catch (IllegalAccessException e) {
                        throw new PandaFrameworkException(e);
                    }
                })
                .collect(Collectors.toList());
    }

}
