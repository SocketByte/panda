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

package org.panda_lang.panda.language.interpreter.parser;

import org.panda_lang.panda.framework.design.architecture.Environment;
import org.panda_lang.panda.language.architecture.PandaApplication;
import org.panda_lang.panda.language.architecture.PandaScript;
import org.panda_lang.panda.framework.design.architecture.module.ModuleLoader;
import org.panda_lang.panda.framework.design.interpreter.Interpretation;
import org.panda_lang.panda.framework.design.interpreter.lexer.Lexer;
import org.panda_lang.panda.framework.design.interpreter.parser.Context;
import org.panda_lang.panda.framework.design.interpreter.parser.Parser;
import org.panda_lang.panda.framework.design.interpreter.parser.component.UniversalComponents;
import org.panda_lang.panda.framework.design.interpreter.parser.pipeline.UniversalPipelines;
import org.panda_lang.panda.framework.design.interpreter.source.Source;
import org.panda_lang.panda.framework.design.interpreter.source.SourceSet;
import org.panda_lang.panda.framework.design.interpreter.token.Snippet;
import org.panda_lang.panda.framework.design.interpreter.token.SourceStream;
import org.panda_lang.panda.framework.design.resource.Resources;
import org.panda_lang.panda.language.architecture.module.PandaModuleLoader;
import org.panda_lang.panda.language.interpreter.lexer.PandaLexer;
import org.panda_lang.panda.language.interpreter.parser.generation.GenerationCycles;
import org.panda_lang.panda.language.interpreter.parser.generation.PandaGeneration;
import org.panda_lang.panda.language.interpreter.source.PandaSourceSet;
import org.panda_lang.panda.language.interpreter.token.PandaSourceStream;
import org.panda_lang.panda.language.resource.parsers.PipelineParser;

public class ApplicationParser implements Parser {

    private final Interpretation interpretation;

    public ApplicationParser(Interpretation interpretation) {
        this.interpretation = interpretation;
    }

    public PandaApplication parse(Source source) {
        Environment environment = interpretation.getEnvironment();
        Resources resources = environment.getResources();

        PandaApplication application = new PandaApplication(environment);
        ModuleLoader loader = new PandaModuleLoader(environment.getModulePath());

        PandaGeneration generation = new PandaGeneration();
        generation.initialize(GenerationCycles.getValues());

        SourceSet sources = new PandaSourceSet();
        sources.addSource(source);

        Lexer lexer = PandaLexer.of(interpretation.getLanguage().getSyntax())
                .enableSections()
                .build();

        Context context = new PandaContext()
                .withComponent(UniversalComponents.APPLICATION, application)
                .withComponent(UniversalComponents.ENVIRONMENT, environment)
                .withComponent(UniversalComponents.INTERPRETATION, interpretation)
                .withComponent(UniversalComponents.GENERATION, generation)
                .withComponent(UniversalComponents.MODULE_LOADER, loader)
                .withComponent(UniversalComponents.PIPELINE, resources.getPipelinePath())
                .withComponent(UniversalComponents.EXPRESSION, resources.getExpressionSubparsers().toParser())
                .withComponent(UniversalComponents.SOURCES, sources);

        for (Source current : sources) {
            PandaScript script = new PandaScript(current.getTitle(), new PandaModuleLoader(loader));
            application.addScript(script);

            interpretation.execute(() -> {
                Snippet snippet = lexer.convert(current);
                SourceStream sourceStream = new PandaSourceStream(snippet);

                Context delegatedContext = context.fork()
                        .withComponent(UniversalComponents.SOURCE, snippet)
                        .withComponent(UniversalComponents.STREAM, sourceStream)
                        .withComponent(UniversalComponents.MODULE_LOADER, script.getModuleLoader())
                        .withComponent(UniversalComponents.SCRIPT, script)
                        .withComponent(PandaComponents.PANDA_SCRIPT, script);

                PipelineParser<?> parser = new PipelineParser<>(UniversalPipelines.HEAD, delegatedContext);
                interpretation.execute(() -> parser.parse(delegatedContext, true));
            });
        }

        return interpretation
                .execute(generation::launch)
                .execute(() -> application);
    }

}