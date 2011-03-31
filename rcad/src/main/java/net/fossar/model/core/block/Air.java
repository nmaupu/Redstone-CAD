package net.fossar.model.core.block;

import net.fossar.core.AdjacentBlocks;
import net.fossar.model.Direction;
import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.BlockPowerListener;

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
	public Direction getDirection() {
		return Direction.UNDEF;
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
	public void setDirection(Direction direction) {
		return;
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
