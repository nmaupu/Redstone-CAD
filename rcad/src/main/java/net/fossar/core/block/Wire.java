package net.fossar.core.block;

import java.util.Map;

import net.fossar.core.AdjacentBlocks;
import net.fossar.core.Direction;

public class Wire extends AbstractBlock implements PassiveBlock {

	public Wire() {
		super(POWER_OFF, 0);
	}

	@Override
	public int getOutput() {
		return super.getOutput() == 0 ? 0 : super.getOutput() - 1;
	}

	@Override
	public void doUpdate(AdjacentBlocks adjacentBlocks) {
		for (Map.Entry<Direction, AbstractBlock> entry : adjacentBlocks.entrySet()) {
			AbstractBlock block = entry.getValue();
			if (block instanceof Wire) {
				block.setInput(getOutput());
				block.doUpdate(adjacentBlocks.getAdjacents(block));
			}
		}
	}

}
