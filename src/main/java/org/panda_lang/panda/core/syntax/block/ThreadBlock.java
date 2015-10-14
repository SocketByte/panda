package org.panda_lang.panda.core.syntax.block;

import org.panda_lang.panda.core.parser.CustomParser;
import org.panda_lang.panda.core.parser.ParameterParser;
import org.panda_lang.panda.core.parser.util.BlockInfo;
import org.panda_lang.panda.core.scheme.BlockScheme;
import org.panda_lang.panda.core.syntax.Block;
import org.panda_lang.panda.core.syntax.Executable;
import org.panda_lang.panda.core.syntax.Parameter;
import org.panda_lang.panda.lang.PObject;
import org.panda_lang.panda.lang.PThread;

public class ThreadBlock extends Block {

    static {
        new BlockScheme(ThreadBlock.class, "thread").parser(new CustomParser<Block>() {
            @Override
            public Block parse(BlockInfo blockInfo, Block current, Block latest) {
                current = new ThreadBlock();
                current.setParameters(new ParameterParser().parse(current, blockInfo.getParameters()));
                return current;
            }
        });
    }

    private PThread pThread;

    public ThreadBlock(){
        super.setName("ThreadBlock");
    }

    public PObject start(final Parameter... vars){
        final Block block = super.getBlock();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(Executable e : block.getExecutables()) {
                    e.run(vars);
                }
            }
        });
        PObject value = parameters[0].getValue();
        thread.setName(pThread.getName());
        thread.start();
        return null;
    }

    @Override
    public PObject run(final Parameter... vars){
        PObject value = parameters[0].getValue();
        if(value instanceof PThread) {
            pThread = (PThread) value;
            pThread.setBlock(this);
        }
        return null;
    }

}