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

package org.panda_lang.framework.language.resource.expression;

import org.panda_lang.framework.design.architecture.module.Imports;
import org.panda_lang.framework.design.architecture.module.ModulePath;
import org.panda_lang.framework.design.architecture.statement.StandardizedFramedScope;
import org.panda_lang.framework.design.architecture.statement.VariableData;
import org.panda_lang.framework.design.architecture.module.TypeLoader;
import org.panda_lang.framework.design.interpreter.parser.Components;
import org.panda_lang.framework.design.interpreter.parser.Context;
import org.panda_lang.framework.language.architecture.module.PandaImports;
import org.panda_lang.framework.language.architecture.module.PandaModule;
import org.panda_lang.framework.language.architecture.module.PandaModulePath;
import org.panda_lang.framework.language.architecture.module.PandaTypeLoader;
import org.panda_lang.framework.language.architecture.statement.StaticScope;
import org.panda_lang.framework.language.interpreter.parser.PandaContext;
import org.panda_lang.framework.language.interpreter.parser.expression.PandaExpressionParser;
import org.panda_lang.panda.language.architecture.PandaScript;
import org.panda_lang.panda.language.resource.ResourcesLoader;
import org.panda_lang.panda.language.resource.syntax.expressions.PandaExpressionUtils;

import java.util.Map;
import java.util.function.Function;

public final class ExpressionContextUtils {

    private ExpressionContextUtils() { }

    /**
     * Create the fake parser context, which contains:
     * - expression parser
     * - variables:
     * > string variable
     * > string[] array
     * > int i
     * - scope linker
     * - abstract scope
     * - module path & loader
     *
     * @return the fake data
     */
    public static Context createFakeContext(Function<Context, Map<VariableData, Object>> variablesSupplier) {
        Context context = new PandaContext();
        context.withComponent(Components.EXPRESSION, new PandaExpressionParser(PandaExpressionUtils.collectSubparsers()));

        ModulePath path = new PandaModulePath();
        TypeLoader loader = new PandaTypeLoader();

        ResourcesLoader resourcesLoader = new ResourcesLoader();
        resourcesLoader.load(path, loader);

        Imports imports = new PandaImports(path, loader);
        imports.importModule("java");
        imports.importModule("panda");

        PandaScript script = new PandaScript("fake-script");
        script.setModule(new PandaModule("fake-module"));

        context.withComponent(Components.SCRIPT, script);
        context.withComponent(Components.TYPE_LOADER, loader);
        context.withComponent(Components.IMPORTS, imports);

        StandardizedFramedScope scope = new StaticScope(variablesSupplier.apply(context));
        context.withComponent(Components.SCOPE, scope);

        return context;
    }

}
