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

package org.panda_lang.panda.framework.language.resource.expression.subparsers;

import org.panda_lang.panda.framework.design.architecture.prototype.ClassPrototype;
import org.panda_lang.panda.framework.design.architecture.value.Value;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.design.runtime.flow.Flow;
import org.panda_lang.panda.framework.language.architecture.prototype.array.ArrayClassPrototype;
import org.panda_lang.panda.framework.language.architecture.value.PandaStaticValue;
import org.panda_lang.panda.framework.language.runtime.expression.PandaDynamicExpression;

import java.lang.reflect.Array;

public class ArrayInstanceExpression extends PandaDynamicExpression {

    private final ClassPrototype prototype;
    private final Expression[] capacities;

    public ArrayInstanceExpression(ArrayClassPrototype instancePrototype, ClassPrototype basePrototype, Expression[] capacities) {
        super(instancePrototype);

        this.prototype = basePrototype;
        this.capacities = capacities;
    }

    @Override
    public Value call(Expression expression, Flow flow) {
        int[] capacitiesValues = new int[capacities.length];

        for (int index = 0; index < capacitiesValues.length; index++) {
            capacitiesValues[index] = capacities[index].evaluate(flow).getValue();
        }

        Object array = Array.newInstance(prototype.getAssociatedClass(), capacitiesValues);
        return new PandaStaticValue(prototype, array);
    }

}
