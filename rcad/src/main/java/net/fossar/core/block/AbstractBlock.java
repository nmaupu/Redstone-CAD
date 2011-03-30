package net.fossar.core.block;

import java.util.ArrayList;
import java.util.List;

import net.fossar.core.AdjacentBlocks;
import net.fossar.core.Direction;
import net.fossar.core.clock.Time;

public abstract class AbstractBlock {
	public static final int PROPAGATION_DELAY = 20;
	public static final int POWER_OFF = 0;
	public static final int POWER_MAX = 15;
	public static final int POWER_SOURCE = POWER_MAX + 1;

	private int row;
	private int col;
	private int layer;

	protected int power = 0;
	Direction direction = Direction.UNDEF;
	protected List<BlockPowerListener> listeners = null;

	private int powerToInject = 0;
	private long delay = 0;
	private Time lastUpdate = new Time();

	public abstract void doUpdate(AdjacentBlocks adjacentBlocks);

	public AbstractBlock(int power, int delay) {
		this.power = power;
		this.delay = delay;
	}

	public void setInput(int power) {
		powerToInject = power;
		power = getOutput();
	}

	public int getOutput() {
		if (lastUpdate.isExpired(delay)) {
			lastUpdate = new Time();
			if (power != powerToInject) {
				power = powerToInject;
				// Notify listeners
				dispatchEventPowerChange();
			}
		}

		return power;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean isPowered() {
		return power > 0;
	}

	public boolean isUnpowered() {
		return power == 0;
	}

	protected void dispatchEventPowerChange() {
		if (listeners == null) {
			return;
		}
		for (BlockPowerListener l : listeners) {
			l.powerChange(this);
		}
	}

	public void addBlockPowerListener(BlockPowerListener listener) {
		if (listeners == null) {
			listeners = new ArrayList<BlockPowerListener>();
		}

		listeners.add(listener);
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getRow() {
		return row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getCol() {
		return col;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public int getLayer() {
		return layer;
	}

}
