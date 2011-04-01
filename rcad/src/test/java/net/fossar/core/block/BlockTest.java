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

import junit.framework.TestCase;
import net.fossar.model.core.AdjacentBlocks;
import net.fossar.model.core.DataGrid;




import net.fossar.model.Direction;
import net.fossar.model.core.block.*;
import org.mockito.Mockito;
import net.fossar.model.core.clock.Clock;

import java.util.EnumSet;

/**
 * Created by IntelliJ IDEA. User: mgrenonville Date: 30/03/11 Time: 15:51 To
 * change this template use File | Settings | File Templates.
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

	public void testUpdate() throws Exception {
		Block block = new Block();

		AdjacentBlocks adjacentBlocks = new AdjacentBlocks(Mockito.mock(DataGrid.class), null);
		Wire wire = Mockito.mock(Wire.class);
        Mockito.when(wire.getDirections()).thenReturn(EnumSet.of(Direction.LEFT));
        
		Block block2 = Mockito.mock(Block.class);
		Torch torch = Mockito.mock(Torch.class);

		adjacentBlocks.put(Direction.LEFT, new DataBlock(0,0,0,wire));
		adjacentBlocks.put(Direction.RIGHT, new DataBlock(0,0,0, block2));
		adjacentBlocks.put(Direction.ABOVE, new DataBlock(0,0,0, torch));

		block.setInput(AbstractBlock.POWER_MAX);
		assertEquals(0, block.getOutput());
		block.doUpdate(adjacentBlocks);

		Mockito.verify(wire).getOutput();
		Mockito.verifyZeroInteractions(block2);
		Mockito.verifyZeroInteractions(torch);

	}

}
