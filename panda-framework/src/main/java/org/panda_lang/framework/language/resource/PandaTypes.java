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

package org.panda_lang.framework.language.resource;

import org.panda_lang.framework.design.architecture.module.Module;
import org.panda_lang.framework.design.architecture.prototype.Prototype;
import org.panda_lang.framework.design.architecture.prototype.Reference;
import org.panda_lang.framework.language.architecture.module.PandaModule;
import org.panda_lang.framework.language.architecture.prototype.PandaPrototypeUtils;
import org.panda_lang.framework.language.architecture.prototype.array.PandaArray;
import org.panda_lang.framework.language.architecture.prototype.generator.ClassPrototypeGeneratorManager;

public final class PandaTypes {

    public static final Module MODULE = new PandaModule("panda-core");

    public static final Prototype VOID = PandaPrototypeUtils.of(MODULE, void.class, "void").fetch();
    public static final Prototype BOOLEAN = PandaPrototypeUtils.of(MODULE, Boolean.class, "Boolean").fetch();
    public static final Prototype CHAR = PandaPrototypeUtils.of(MODULE, Character.class, "Char").fetch();
    public static final Prototype BYTE = PandaPrototypeUtils.of(MODULE, Byte.class, "Byte").fetch();
    public static final Prototype SHORT = PandaPrototypeUtils.of(MODULE, Short.class, "Short").fetch();
    public static final Prototype INT = PandaPrototypeUtils.of(MODULE, Integer.class, "Int").fetch();
    public static final Prototype LONG = PandaPrototypeUtils.of(MODULE, Long.class, "Long").fetch();
    public static final Prototype FLOAT = PandaPrototypeUtils.of(MODULE, Float.class, "Float").fetch();
    public static final Prototype DOUBLE = PandaPrototypeUtils.of(MODULE, Double.class, "Double").fetch();

    public static final Prototype OBJECT = PandaPrototypeUtils.of(MODULE, Object.class, "Object").fetch();
    public static final Prototype ARRAY = PandaPrototypeUtils.of(MODULE, PandaArray.class, "Array").fetch();

    public static final Prototype STRING = of(String.class).fetch();
    public static final Prototype NUMBER = of(Number.class).fetch();
    public static final Prototype ITERABLE = of(Iterable.class).fetch();

    private static Reference of(Class<?> clazz) {
        Reference reference = ClassPrototypeGeneratorManager.getInstance().generate(MODULE, clazz, clazz.getSimpleName());
        MODULE.add(reference. getName(), reference.getAssociatedClass(), () -> reference);
        return reference;
    }

}
