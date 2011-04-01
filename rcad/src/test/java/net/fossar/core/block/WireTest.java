package net.fossar.core.block;

import net.fossar.model.core.DataGrid;
import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.Lever;
import net.fossar.model.core.block.Torch;
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
        assertEquals(AbstractBlock.POWER_MAX - 1, wire.getOutput());
    }

    public void test_long_wire() {
        DataGrid grid = new DataGrid(16, 1, 1);

        Lever start = new Lever();
        grid.addBlock(start, 0, 0, 0);
        for (int i = 1; i < 11; i++) {
            grid.addBlock(new Wire(), i, 0, 0);
        }
        Wire end = new Wire();
        grid.addBlock(end, 11, 0, 0);

        grid.doUpdate();
        start.powerOn();

        grid.doUpdate();
        assertEquals(AbstractBlock.POWER_MAX - 10, end.getOutput());
    }

    public void test_cycle() {
        DataGrid grid = new DataGrid(16, 16, 1);

        Lever start = new Lever();
        grid.addBlock(start, 0, 0, 0);
        grid.addBlock(new Wire(), 1, 0, 0);
        grid.addBlock(new Wire(), 1, 1, 0);
        grid.addBlock(new Wire(), 1, 2, 0);
        grid.addBlock(new Wire(), 1, 3, 0);

        grid.addBlock(new Wire(), 2, 0, 0);
        grid.addBlock(new Wire(), 2, 3, 0);

        grid.addBlock(new Wire(), 3, 0, 0);
        grid.addBlock(new Wire(), 3, 1, 0);
        grid.addBlock(new Wire(), 3, 2, 0);
        grid.addBlock(new Wire(), 3, 3, 0);



        Wire end = new Wire();
        grid.addBlock(end, 4, 0, 0);

        grid.doUpdate();
        start.powerOn();

        grid.doUpdate();
        assertEquals(AbstractBlock.POWER_SOURCE - 4, end.getOutput());
    }

}
