package net.fossar.model.core.block;

import java.util.ArrayList;
import java.util.List;

import net.fossar.model.core.AdjacentBlocks;
import net.fossar.model.Direction;
import net.fossar.model.Model;
import net.fossar.model.core.clock.Time;

public abstract class AbstractBlock implements Model {
    public static final int PROPAGATION_DELAY = 20;
    public static final int POWER_OFF = 0;
    public static final int POWER_MAX = 15;
    public static final int POWER_SOURCE = POWER_MAX + 1;



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
	
	protected void setPower(int power) {
		this.power = power;
	}

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"[" + "power=" + power + ']';
    }
}
