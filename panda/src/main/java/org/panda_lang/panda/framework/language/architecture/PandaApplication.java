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

package org.panda_lang.panda.framework.language.architecture;

import org.panda_lang.panda.framework.PandaFrameworkException;
import org.panda_lang.panda.framework.design.architecture.Application;
import org.panda_lang.panda.framework.design.architecture.Environment;
import org.panda_lang.panda.framework.design.architecture.Script;
import org.panda_lang.panda.framework.design.runtime.Process;
import org.panda_lang.panda.framework.language.resource.head.MainFrame;
import org.panda_lang.panda.framework.language.runtime.PandaProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PandaApplication implements Application {

    private final Environment environment;
    private final List<Script> scripts = new ArrayList<>();
    private MainFrame main;

    public PandaApplication(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void launch(String... args) {
        if (main == null) {
            selectMain();
        }

        Process process = new PandaProcess(this, main, args);
        process.execute();
    }

    private void selectMain() {
        List<MainFrame> mains = scripts.stream()
                .map(applicationScript -> applicationScript.select(MainFrame.class))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (mains.isEmpty()) {
            throw new PandaFrameworkException("Main statement not found");
        }

        if (mains.size() > 1) {
            throw new PandaFrameworkException("Duplicated main statement");
        }

        this.main = mains.get(0);
    }

    public void addScript(Script script) {
        scripts.add(script);
    }

    @Override
    public List<? extends Script> getScripts() {
        return scripts;
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }

}