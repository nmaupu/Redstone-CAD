package net.fossar.core.block;

import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.Wire;
import junit.framework.TestCase;


public class WireTest extends TestCase {

    public void test_block_init() {
        Wire wire = new Wire();
        assertEquals(0, wire.getOutput());


    }

    public void test_set_input_with_propagation() {
        Wire wire = new Wire();
        assertEquals(0, wire.getOutput());
        wire.setInput(AbstractBlock.POWER_MAX);
        assertEquals(AbstractBlock.POWER_MAX  - 1, wire.getOutput());

    }
}
