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

package org.panda_lang.framework.design.architecture.type;

import io.vavr.control.Option;
import org.panda_lang.framework.design.architecture.expression.Expression;

/**
 * Container for constructors
 */
public interface Constructors extends Properties<TypeConstructor> {

    /**
     * Adjust property to the given arguments (with varargs support)
     *
     * @param arguments arguments to match and adjust
     * @return the adjusted executable
     */
    Option<Adjustment<TypeConstructor>> getAdjustedConstructor(Expression[] arguments);

    /**
     * Get constructor that fits to the given types
     *
     * @param types types to search for
     * @return the result constructor
     */
    Option<TypeConstructor> getConstructor(Type[] types);

}
