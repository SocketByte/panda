/*
 * Copyright (c) 2015-2019 Dzikoysk
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

package org.panda_lang.panda.framework.design.interpreter.parser.bootstrap;

import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.parser.pipeline.ParserHandler;

import java.util.Optional;

public interface BootstrapContent {

    /**
     * Get associated bootstrap interceptor
     *
     * @return the interceptor
     */
    Optional<BootstrapInterceptor> getInterceptor();

    /**
     * Get associated parser handler
     *
     * @return the parser handler
     */
    Optional<ParserHandler> getHandler();

    /**
     * Get pattern object
     *
     * @param <R> expected type
     * @return the pattern
     */
    <R> Optional<R> getPattern();

    /**
     * Get top-level parser data used by bootstrap
     *
     * @return
     */
    ParserData getData();

    /**
     * Get instance of class used by bootstrap
     *
     * @return
     */
    Object getInstance();

    /**
     * Get bootstrap name, simple class name by default
     *
     * @return the name of bootstrap
     */
    String getName();

}
