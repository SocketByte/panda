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

package org.panda_lang.panda.framework.language.resource.parsers.overall;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.panda.framework.design.architecture.Environment;
import org.panda_lang.panda.framework.design.architecture.PandaScript;
import org.panda_lang.panda.framework.design.architecture.module.Module;
import org.panda_lang.panda.framework.design.architecture.module.ModuleLoader;
import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.BootstrapParserBuilder;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.ParserBootstrap;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.annotations.Autowired;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.annotations.AutowiredParameters;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.annotations.Component;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.annotations.Src;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.annotations.Type;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.handlers.TokenHandler;
import org.panda_lang.panda.framework.design.interpreter.parser.component.UniversalComponents;
import org.panda_lang.panda.framework.design.interpreter.parser.pipeline.UniversalPipelines;
import org.panda_lang.panda.framework.design.interpreter.token.TokenRepresentation;
import org.panda_lang.panda.framework.design.interpreter.token.snippet.Snippet;
import org.panda_lang.panda.framework.design.resource.parsers.ParserRegistration;
import org.panda_lang.panda.framework.language.architecture.statement.ImportStatement;
import org.panda_lang.panda.framework.language.interpreter.parser.PandaParserFailure;
import org.panda_lang.panda.framework.language.interpreter.parser.generation.GenerationCycles;
import org.panda_lang.panda.framework.language.interpreter.source.PandaURLSource;
import org.panda_lang.panda.framework.language.interpreter.token.TokenUtils;
import org.panda_lang.panda.framework.language.resource.syntax.keyword.Keywords;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

@ParserRegistration(target = UniversalPipelines.OVERALL_LABEL)
public class RequireParser extends ParserBootstrap {

    @Override
    protected BootstrapParserBuilder initialize(ParserData data, BootstrapParserBuilder defaultBuilder) {
        return defaultBuilder
                .handler(new TokenHandler(Keywords.REQUIRE))
                .pattern("require (<require:condition token {type:unknown}, token {value:-}, token {value:.}>|<requiredFile>)");
    }

    @Autowired(type = GenerationCycles.TYPES_LABEL)
    @AutowiredParameters(skip = 1, value = {
            @Type(with = Component.class),
            @Type(with = Component.class),
            @Type(with = Component.class),
            @Type(with = Src.class, value = "require"),
            @Type(with = Src.class, value = "requiredFile")
    })
    void parse(ParserData data, Environment environment, ModuleLoader loader, PandaScript script, @Nullable Snippet require, @Nullable Snippet requiredFile) {
        if (require != null) {
            parseModule(data, environment, loader, script, require);
            return;
        }

        parseFile(data, Objects.requireNonNull(requiredFile));
    }

    private void parseModule(ParserData data, Environment environment, ModuleLoader loader, PandaScript script, Snippet require) {
        String moduleName = require.asString();
        Optional<Module> module = environment.getModulePath().get(moduleName);

        if (!module.isPresent()) {
            throw PandaParserFailure.builder("Unknown module " + moduleName, data)
                    .withStreamOrigin(require)
                    .withNote("Make sure that the name does not have a typo and module is added to the module path")
                    .build();
        }

        loader.include(module.get());
        script.getStatements().add(new ImportStatement(module.get()));
    }

    private void parseFile(ParserData data, Snippet requiredFile) {
        TokenRepresentation token = requiredFile.getFirst();

        if (!TokenUtils.hasName(token, "String")) {
            throw PandaParserFailure.builder("Invalid token ", data)
                    .withStreamOrigin(token)
                    .withNote("You should use string sequence to import file")
                    .build();
        }

        File file = new File(data.getComponent(UniversalComponents.ENVIRONMENT).getDirectory(), token.getValue() + ".panda");

        if (!file.exists()) {
            throw PandaParserFailure.builder("File " + file + " does not exist", data)
                    .withStreamOrigin(token)
                    .withNote("Make sure that the path does not have a typo")
                    .build();
        }

        data.getComponent(UniversalComponents.SOURCES).addSource(PandaURLSource.fromFile(file));
    }

}