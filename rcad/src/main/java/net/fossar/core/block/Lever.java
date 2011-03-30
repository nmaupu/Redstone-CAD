package net.fossar.core.block;

import java.util.Map;

import net.fossar.core.AdjacentBlocks;
import net.fossar.core.Direction;

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
		for (Map.Entry<Direction, AbstractBlock> entry : adjacentBlocks.entrySet()) {
			AbstractBlock block = entry.getValue();
			if (block instanceof PassiveBlock) {
				block.setInput(getOutput());
				block.doUpdate(adjacentBlocks.getAdjacents(block));
			}
		}

	}

}
