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

package org.panda_lang.framework.design.interpreter.parser;

import org.panda_lang.framework.design.interpreter.parser.pipeline.Handler;

/**
 * Parser container
 *
 * @param <P> type of stored parser
 */
public interface ParserRepresentation<P> {

    /**
     * Add 1 to number of use. It's used to optimization process of parsing.
     */
    void increaseUsages();

    /**
     * @return amount of usages
     */
    int getUsages();

    /**
     * @return priority
     */
    double getPriority();

    /**
     * @return associated handler
     */
    Handler getHandler();

    /**
     * @return associated parser
     */
    P getParser();

}
