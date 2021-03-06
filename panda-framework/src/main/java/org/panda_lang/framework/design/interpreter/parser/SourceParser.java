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

import org.panda_lang.framework.design.interpreter.token.Snippet;

/**
 * Similar to {@link org.panda_lang.framework.design.interpreter.parser.ContextParser}, but with custom source
 *
 * @param <T> type of result
 */
public interface SourceParser<T> extends Parser {

    /**
     * Parse custom source
     *
     * @param source matched and checked before tokenized source for parser
     * @param context   set of information about source and interpretation process
     */
    T parse(Context context, Snippet source);

}
