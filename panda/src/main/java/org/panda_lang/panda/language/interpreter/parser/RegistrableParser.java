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

package org.panda_lang.panda.language.interpreter.parser;

import org.panda_lang.framework.design.interpreter.parser.ContextParser;
import org.panda_lang.framework.design.interpreter.parser.pipeline.Handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegistrableParser {

    /**
     * Default pipelines: {@link org.panda_lang.panda.language.interpreter.parser.PandaPipeline}
     *
     * @return the array of pipelines
     */
    String[] pipeline();

    /**
     * Default priorities {@link org.panda_lang.panda.language.resource.syntax.PandaPriorities}
     *
     * @return the priority
     */
    double priority() default 0;

    /**
     * Get parser class
     *
     * @return the parser class
     */
    Class<? extends ContextParser> parserClass() default ContextParser.class;

    /**
     * Get handler class
     *
     * @return the handler class
     */
    Class<? extends Handler> handlerClass() default Handler.class;

}


