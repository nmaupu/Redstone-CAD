package net.fossar.model.core.block;

import net.fossar.model.Direction;

import java.util.Map;

import net.fossar.model.core.AdjacentBlocks;

public class Lever extends AbstractBlock implements ActiveBlock {

	public Lever() {
		super(POWER_OFF, 0);
	}

	@Override
	public void powerOn() {
		super.setInput(POWER_SOURCE);
	}

	@Override
	public void powerOff() {
		super.setInput(POWER_OFF);
	}

	@Override
	public void doUpdate(AdjacentBlocks adjacentBlocks) {
		for (Map.Entry<Direction, DataBlock> entry : adjacentBlocks.entrySet()) {
			AbstractBlock block = entry.getValue().getBlock();
			if (block instanceof PassiveBlock) {
				block.setInput(getOutput());
				block.doUpdate(adjacentBlocks.getAdjacents(entry.getValue()));
			}
		}

	}

}
