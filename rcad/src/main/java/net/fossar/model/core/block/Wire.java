package net.fossar.model.core.block;

import net.fossar.model.Direction;

import java.util.Map;

import net.fossar.model.core.AdjacentBlocks;

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
		for (Map.Entry<Direction, DataBlock> entry : adjacentBlocks.entrySet()) {
			AbstractBlock block = entry.getValue().getBlock();
			if (block instanceof Wire) {
				block.setInput(getOutput());
				block.doUpdate(adjacentBlocks.getAdjacents(entry.getValue()));
			}
		}
	}

}
