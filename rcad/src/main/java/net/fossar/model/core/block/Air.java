package net.fossar.model.core.block;

import net.fossar.model.core.AdjacentBlocks;
import net.fossar.model.Direction;

public class Air extends AbstractBlock {
	public static final Air INSTANCE = new Air();

	protected Air() {
		super(-1, -1);
	}

	@Override
	public void addBlockPowerListener(BlockPowerListener listener) {
		return;
	}


	

	@Override
	public int getOutput() {
		return 0;
	}

	@Override
	public boolean isPowered() {
		return false;
	}

	@Override
	public boolean isUnpowered() {
		return true;
	}

	

	@Override
	public void setInput(int power) {
		return;
	}

	@Override
	public void doUpdate(AdjacentBlocks adjacentBlocks) {
		return;

	}

}
