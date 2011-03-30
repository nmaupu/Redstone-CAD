package net.fossar.core.block;

import junit.framework.TestCase;
import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.Block;
import net.fossar.model.core.clock.Clock;

/**
 * Created by IntelliJ IDEA.
 * User: mgrenonville
 * Date: 30/03/11
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class BlockTest extends TestCase {

    public void test_block_init() {
        Block block = new Block();
        assertEquals(0, block.getOutput());
    }

    public void test_set_input_with_propagation() {
        Block block = new Block();
        block.setInput(AbstractBlock.POWER_MAX);

        assertEquals(AbstractBlock.POWER_OFF, block.getOutput());
        Clock.next();
        assertEquals(AbstractBlock.POWER_OFF, block.getOutput());
        Clock.next();
        assertEquals(AbstractBlock.POWER_MAX, block.getOutput());
    }


}
