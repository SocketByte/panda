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

package org.panda_lang.framework.language.architecture.type.generator;

import org.panda_lang.framework.design.architecture.expression.Expression;
import org.panda_lang.framework.design.architecture.expression.ExpressionUtils;
import org.panda_lang.framework.design.architecture.type.Type;
import org.panda_lang.framework.design.architecture.type.TypeField;
import org.panda_lang.framework.design.architecture.module.TypeLoader;
import org.panda_lang.framework.design.runtime.ProcessStack;
import org.panda_lang.framework.language.architecture.expression.AbstractDynamicExpression;
import org.panda_lang.framework.language.architecture.expression.PandaExpression;
import org.panda_lang.framework.language.architecture.type.PandaField;
import org.panda_lang.framework.language.runtime.PandaRuntimeException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

final class FieldGenerator {

    private final TypeGenerator generator;
    private final Type type;
    private final Field field;

    FieldGenerator(TypeGenerator generator, Type type, Field field) {
        this.generator = generator;
        this.type = type;
        this.field = field;
    }

    protected TypeField generate(TypeLoader typeLoader) {
        TypeField typeField = PandaField.builder()
                .name(field.getName())
                .type(type)
                .location(type.getLocation())
                .fieldIndex(type.getFields().size())
                .returnType(generator.findOrGenerate(typeLoader, type.getModule(), field.getType()))
                .isStatic(Modifier.isStatic(field.getModifiers()))
                .mutable(!Modifier.isFinal(field.getModifiers()))
                .isNative(true)
                .nillable(true)
                .build();
        // TODO: Generate bytecode
        field.setAccessible(true);

        Expression fieldExpression = new PandaExpression(new AbstractDynamicExpression(typeField.getReturnType()) {
            @Override
            @SuppressWarnings("unchecked")
            public Object evaluate(ProcessStack flow, Object instance) {
                try {
                    return field.get(instance);
                } catch (IllegalAccessException e) {
                    throw new PandaRuntimeException("Cannot get value of " + field, e);
                }
            }
        });

        typeField.setDefaultValue(fieldExpression);
        typeField.setStaticValue(typeField.isStatic() ? () -> ExpressionUtils.evaluateConstExpression(fieldExpression) : null);
        return typeField;
    }

}
