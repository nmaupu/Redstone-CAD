package net.fossar.core.block;

import net.fossar.core.grid.DataGrid;

import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.Block;
import net.fossar.model.core.block.Lever;
import net.fossar.model.core.block.Torch;
import org.junit.Assert;
import org.junit.Test;

public class SimpleLeverTest {

	@Test
	public void test_lever_with_block_and_torch() {
		DataGrid dataGrid = new DataGrid(3, 3, 3);

		Lever lever = new Lever();
		dataGrid.addBlock(lever, 0, 0, 0);

		Block block = new Block();
		dataGrid.addBlock(block, 0, 1, 0);

		Torch torch = new Torch();
		dataGrid.addBlock(torch, 0, 2, 0);

		lever.powerOn();


		dataGrid.doUpdate();

		Assert.assertEquals(AbstractBlock.POWER_SOURCE, torch.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_OFF, block.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, lever.getOutput());

		dataGrid.tick();

		dataGrid.doUpdate();

		Assert.assertEquals(AbstractBlock.POWER_OFF, torch.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, block.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, lever.getOutput());

		dataGrid.tick();

		dataGrid.doUpdate();

		Assert.assertEquals(AbstractBlock.POWER_OFF, torch.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, block.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, lever.getOutput());

		lever.powerOff();

		dataGrid.doUpdate();

		Assert.assertEquals(AbstractBlock.POWER_OFF, torch.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, block.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_OFF, lever.getOutput());

		dataGrid.tick();

		dataGrid.doUpdate();

		Assert.assertEquals(AbstractBlock.POWER_SOURCE, torch.getOutput());
        Assert.assertEquals(AbstractBlock.POWER_OFF, block.getOutput());
        Assert.assertEquals(AbstractBlock.POWER_OFF, lever.getOutput());

	}
}
