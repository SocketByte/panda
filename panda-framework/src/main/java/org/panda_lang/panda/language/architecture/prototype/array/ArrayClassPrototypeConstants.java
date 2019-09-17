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

package org.panda_lang.panda.language.architecture.prototype.array;

import org.panda_lang.panda.framework.design.architecture.prototype.method.PrototypeMethod;
import org.panda_lang.panda.language.architecture.prototype.standard.method.PandaMethod;
import org.panda_lang.panda.language.resource.PandaTypes;

import java.util.Arrays;
import java.util.Objects;

final class ArrayClassPrototypeConstants {

    protected static final PrototypeMethod SIZE = PandaMethod.builder()
            .name("size")
            .returnType(PandaTypes.INT.getReference())
            .methodBody((branch, instance, arguments) -> {
                return ((Object[]) Objects.requireNonNull(instance)).length;
            })
            .build();

    protected static final PrototypeMethod TO_STRING = PandaMethod.builder()
            .name("toString")
            .returnType(PandaTypes.STRING.getReference())
            .methodBody((branch, instance, arguments) -> {
                return Arrays.toString((Object[]) instance);
            })
            .build();

}