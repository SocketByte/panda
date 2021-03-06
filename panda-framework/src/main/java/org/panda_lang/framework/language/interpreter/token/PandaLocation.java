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

package org.panda_lang.framework.language.interpreter.token;

import org.panda_lang.framework.design.interpreter.source.Source;
import org.panda_lang.framework.design.interpreter.source.Location;

public final class PandaLocation implements Location {

    private final Source source;
    private final int line;
    private final int position;

    public PandaLocation(Source source, int line, int position) {
        this.source = source;
        this.line = line;
        this.position = position;
    }

    public PandaLocation(Source source) {
        this(source, UNKNOWN_LOCATION, UNKNOWN_LOCATION);
    }

    @Override
    public int getIndex() {
        return position;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public Source getSource() {
        return source;
    }

    @Override
    public String toString() {
        return source.getId() + " [" + (line + 1) + ":" + position + "]";
    }

}
