package net.fossar.model.core.block;

import net.fossar.model.core.AdjacentBlocks;
import net.fossar.model.Direction;

import java.util.Map;

public class Block extends AbstractBlock implements PassiveBlock {
	
	public Block() {
		super(POWER_OFF, PROPAGATION_DELAY);
	}

	@Override
	public void doUpdate(AdjacentBlocks adjacentBlocks) {
		int result = 0;
		for (Map.Entry<Direction, DataBlock> entry : adjacentBlocks.entrySet()) {
			AbstractBlock block = entry.getValue().getBlock();
			int output = 0;
			if (block instanceof Wire) {
				output = block.getOutput();
				block.doUpdate(new AdjacentBlocks(adjacentBlocks, entry.getValue()));
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

	public void resetBlockPower() {
		super.setPower(POWER_OFF);
	}
}
