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

package org.panda_lang.framework.language.interpreter.pattern.custom.elements;

import org.panda_lang.framework.design.interpreter.token.Snippet;
import org.panda_lang.framework.language.interpreter.pattern.custom.Buildable;
import org.panda_lang.framework.language.interpreter.pattern.custom.CustomPatternElementBuilder;
import org.panda_lang.framework.language.interpreter.pattern.custom.verifiers.TokenTypeVerifier;
import org.panda_lang.framework.language.resource.syntax.TokenTypes;
import org.panda_lang.framework.language.resource.syntax.auxiliary.Section;

public final class SectionElement {

    private SectionElement() { }

    public static CustomPatternElementBuilder<?, ? extends Buildable<Snippet>> create(String id) {
        return WildcardElement.create(id)
                .verify(new TokenTypeVerifier(TokenTypes.SECTION))
                .map(snippetable -> snippetable.toSnippet().getFirst().toToken(Section.class).getContent());
    }

}
