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

package org.panda_lang.framework.design.architecture.module;

import org.panda_lang.framework.design.architecture.type.Type;

/**
 * Represents references imported in the specific space, e.g. file
 */
public interface Imports extends ModuleResource {

    /**
     * Import module using the given name
     *
     * @param name the name of module
     */
    void importModule(String name);

    /**
     * Import module
     *
     * @param module the module to import
     * // @return if type with the given name is already imported, the method will interrupt importing and return the name of that type
     */
    void importModule(Module module);

    /**
     * Import reference
     *
     * @param name the name of type to import as (may be different than type name)
     * @param type the reference to type
     * @return if type with the given name is already imported, the method will return false, otherwise true
     */
    boolean importType(String name, Type type);

    /**
     * Get associated type loader
     *
     * @return the associated type loader
     */
    TypeLoader getTypeLoader();
}
