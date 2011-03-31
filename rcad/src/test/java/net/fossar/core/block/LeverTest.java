package net.fossar.core.block;

import net.fossar.model.Direction;
import net.fossar.model.core.block.*;
import junit.framework.TestCase;
import net.fossar.core.AdjacentBlocks;
import net.fossar.core.grid.DataGrid;

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

		AdjacentBlocks adjacentBlocks = new AdjacentBlocks(new DataGrid(0, 0, 0));
		Wire wire = Mockito.mock(Wire.class);
		Block block = Mockito.mock(Block.class);
		Torch torch = Mockito.mock(Torch.class);

		adjacentBlocks.put(Direction.LEFT, wire);
		adjacentBlocks.put(Direction.RIGHT, block);
		adjacentBlocks.put(Direction.ABOVE, torch);

		lever.powerOn();
		lever.doUpdate(adjacentBlocks);

		Mockito.verify(wire).setInput(16);
		Mockito.verify(block).setInput(16);
		Mockito.verifyZeroInteractions(torch);
	}
}
