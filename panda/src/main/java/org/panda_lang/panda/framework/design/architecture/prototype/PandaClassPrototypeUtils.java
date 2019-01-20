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

package org.panda_lang.panda.framework.design.architecture.prototype;

import org.panda_lang.panda.framework.design.architecture.module.ModuleLoader;
import org.panda_lang.panda.utilities.commons.ClassUtils;

import java.util.Collection;

public class PandaClassPrototypeUtils {

    public static boolean isAssignableFrom(Class<?> from, Class<?> to) {
        return from != null && to != null && (from == to || ClassUtils.isAssignableFrom(from, to));
    }

    public static boolean hasCommonClasses(Collection<Class<?>> fromClasses, Collection<Class<?>> toClasses) {
        for (Class<?> from : fromClasses) {
            for (Class<?> to : toClasses) {
                if (from == to) {
                    return true;
                }

                if (isAssignableFrom(from, to)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean hasCommonPrototypes(Collection<ClassPrototype> fromPrototypes, Collection<ClassPrototype> toPrototypes) {
        for (ClassPrototype from : fromPrototypes) {
            for (ClassPrototype to : toPrototypes) {
                if (from.equals(to)) {
                    return true;
                }

                if (isAssignableFrom(from.getAssociated(), to.getAssociated())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static ClassPrototype[] toTypes(ModuleLoader loader, Class<?>... types) {
        ClassPrototype[] prototypes = new ClassPrototype[types.length];

        for (int i = 0; i < types.length; i++) {
            prototypes[i] = loader.forClass(types[i]);
        }

        return prototypes;
    }

}