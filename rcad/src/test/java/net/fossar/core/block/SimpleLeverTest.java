/*
 * RCad is a software to help manipulating minecraft's redstone.
 * Copyright (C) 2011. mathieu_dot_grenonville_at_gmail_dot_com - nmaupu_at_gmail_dot_com
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.fossar.core.block;


import net.fossar.model.core.DataGrid;
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
		Assert.assertEquals(AbstractBlock.POWER_MAX, block.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_SOURCE, lever.getOutput());

		lever.powerOff();

		dataGrid.doUpdate();

		Assert.assertEquals(AbstractBlock.POWER_OFF, torch.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_MAX, block.getOutput());
		Assert.assertEquals(AbstractBlock.POWER_OFF, lever.getOutput());

		dataGrid.tick();

		dataGrid.doUpdate();

		Assert.assertEquals(AbstractBlock.POWER_SOURCE, torch.getOutput());
        Assert.assertEquals(AbstractBlock.POWER_OFF, block.getOutput());
        Assert.assertEquals(AbstractBlock.POWER_OFF, lever.getOutput());

	}
}
