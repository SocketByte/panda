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

package org.panda_lang.panda.language.interpreter.parser;

import org.panda_lang.framework.design.interpreter.parser.ContextComponent;
import org.panda_lang.panda.Panda;
import org.panda_lang.panda.language.architecture.PandaScript;

/**
 * Default list of names used by {@link org.panda_lang.framework.design.interpreter.parser.Context} for components
 */
public final class PandaComponents {

    public static final ContextComponent<Panda> PANDA = ContextComponent.of("panda", Panda.class);

    public static final ContextComponent<PandaScript> PANDA_SCRIPT = ContextComponent.of("panda-script", PandaScript.class);

    private PandaComponents() { }

}
