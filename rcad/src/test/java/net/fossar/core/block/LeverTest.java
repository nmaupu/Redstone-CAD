package net.fossar.core.block;

import net.fossar.model.core.AdjacentBlocks;
import net.fossar.model.core.DataGrid;
import net.fossar.model.core.block.*;
import junit.framework.TestCase;

import org.mockito.Mockito;

public class LeverTest extends TestCase {

    public void testPowerOn() throws Exception {
        Lever lever = new Lever();
        lever.powerOff();
        lever.powerOn();
        assertEquals(AbstractBlock.POWER_SOURCE, lever.getOutput());
    }

    public void testPowerOff() throws Exception {
        Lever lever = new Lever();
        lever.powerOn();
        lever.powerOff();
        assertEquals(AbstractBlock.POWER_OFF, lever.getOutput());
    }

    public void testUpdate() throws Exception {
        Lever lever = new Lever();

        DataGrid grid = new DataGrid(3, 3, 2);

        Wire wire = Mockito.mock(Wire.class);
        Block block = Mockito.mock(Block.class);
        Torch torch = Mockito.mock(Torch.class);

        grid.addBlock(wire, 0, 1, 0);
        grid.addBlock(block, 1, 0, 0);
        DataBlock datablock = new DataBlock(1, 1, 0, lever);
        grid.add(datablock);

        grid.addBlock(torch, 1, 2, 1);

        AdjacentBlocks adjacentBlocks = new AdjacentBlocks(grid, datablock);

        lever.powerOn();
        lever.doUpdate(adjacentBlocks);

        Mockito.verify(wire).setInput(16);
        Mockito.verify(block).setInput(16);
        Mockito.verify(torch).updateDirection(Mockito.<AdjacentBlocks>any());
        Mockito.verifyNoMoreInteractions(torch);
    }

}
