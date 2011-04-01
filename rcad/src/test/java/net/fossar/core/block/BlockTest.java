package net.fossar.core.block;

import junit.framework.TestCase;
import net.fossar.model.core.AdjacentBlocks;
import net.fossar.model.core.DataGrid;




import net.fossar.model.Direction;
import net.fossar.model.core.block.*;
import org.mockito.Mockito;
import net.fossar.model.core.clock.Clock;

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
