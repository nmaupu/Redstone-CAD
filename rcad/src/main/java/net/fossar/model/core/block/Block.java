package net.fossar.model.core.block;

import net.fossar.core.AdjacentBlocks;
import net.fossar.core.block.PassiveBlock;
import net.fossar.model.Direction;

import java.util.Map;

public class Block extends AbstractBlock implements PassiveBlock {
	
	public Block() {
		super(POWER_OFF, PROPAGATION_DELAY);
	}

	@Override
	public void doUpdate(AdjacentBlocks adjacentBlocks) {
		int result = 0;
		for (Map.Entry<Direction, AbstractBlock> entry : adjacentBlocks.entrySet()) {
			AbstractBlock block = entry.getValue();
			int output = 0;
			if (block instanceof Wire) {
				output = block.getOutput();
				block.doUpdate(adjacentBlocks.getAdjacents(block));
			}
			if (block instanceof Torch) {
				output = block.isPowered() ? AbstractBlock.POWER_OFF : AbstractBlock.POWER_MAX;
			}
			if (block instanceof Lever) {
				output = block.isPowered() ? AbstractBlock.POWER_MAX : AbstractBlock.POWER_OFF;
			}
			if (result < output) {
				result = output;
			}
		}
		setInput(result);
	}

}
