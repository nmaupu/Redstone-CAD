package net.fossar.core.block;

import net.fossar.core.AdjacentBlocks;
import net.fossar.core.clock.Clock;
import net.fossar.core.grid.DataGrid;

import org.junit.Assert;
import org.junit.Test;

public class SimpleLeverTest {

	@Test
	public void test_lever_with_block_and_torch() {
		DataGrid dataGrid = new DataGrid(1, 3, 1);

		Lever lever = new Lever();
		dataGrid.addBlock(lever, 0, 0, 0);

		Block block = new Block();
		dataGrid.addBlock(block, 0, 1, 0);

		Torch torch = new Torch();
		dataGrid.addBlock(torch, 0, 2, 0);

		lever.powerOn();
		AdjacentBlocks adjacentBlocks = new AdjacentBlocks(dataGrid);

		lever.doUpdate(adjacentBlocks.getAdjacents(lever));
		torch.doUpdate(adjacentBlocks.getAdjacents(torch));
		block.doUpdate(adjacentBlocks.getAdjacents(block));

		Assert.assertEquals(AbstractBlock.POWER_SOURCE, torch.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_OFF, block.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, lever.getOutput());

		Clock.next();
		Clock.next();

		lever.doUpdate(adjacentBlocks.getAdjacents(lever));
		torch.doUpdate(adjacentBlocks.getAdjacents(torch));
		block.doUpdate(adjacentBlocks.getAdjacents(block));

		Assert.assertEquals(AbstractBlock.POWER_OFF, torch.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, block.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, lever.getOutput());

		Clock.next();
		Clock.next();

		lever.doUpdate(adjacentBlocks.getAdjacents(lever));
		torch.doUpdate(adjacentBlocks.getAdjacents(torch));
		block.doUpdate(adjacentBlocks.getAdjacents(block));

		Assert.assertEquals(AbstractBlock.POWER_OFF, torch.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, block.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, lever.getOutput());

		lever.powerOff();

		lever.doUpdate(adjacentBlocks.getAdjacents(lever));
		torch.doUpdate(adjacentBlocks.getAdjacents(torch));
		block.doUpdate(adjacentBlocks.getAdjacents(block));

		Assert.assertEquals(AbstractBlock.POWER_OFF, torch.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, block.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_OFF, lever.getOutput());

		Clock.next();
		Clock.next();

		lever.doUpdate(adjacentBlocks.getAdjacents(lever));
		torch.doUpdate(adjacentBlocks.getAdjacents(torch));
		block.doUpdate(adjacentBlocks.getAdjacents(block));

		Assert.assertEquals(AbstractBlock.POWER_OFF, block.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, torch.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_OFF, lever.getOutput());

	}
}
