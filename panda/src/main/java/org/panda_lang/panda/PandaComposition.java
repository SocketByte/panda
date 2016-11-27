package org.panda_lang.panda;

import org.panda_lang.panda.implementation.syntax.SyntaxComposition;
import org.panda_lang.panda.implementation.interpreter.parser.ParserComposition;

public class PandaComposition {

    private final SyntaxComposition syntaxComposition;
    private final ParserComposition parserComposition;

    public PandaComposition() {
        this.syntaxComposition = new SyntaxComposition();
        this.parserComposition = new ParserComposition();
    }

    public ParserComposition getParserComposition() {
        return parserComposition;
    }

    public SyntaxComposition getSyntaxComposition() {
        return syntaxComposition;
    }

}