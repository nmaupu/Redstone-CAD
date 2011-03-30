package net.fossar.core.block;

import java.util.Date;

import net.fossar.core.Direction;

public abstract class AbstractBlock {
	public static final int PROPAGATION_DELAY=20;
	public static final int POWER_OFF = 0;
	public static final int POWER_MAX = 15;
	public static final int POWER_SOURCE = POWER_MAX+1;
	
	protected int power = 0;
	Direction direction = Direction.UNDEF;
	
	private int powerToInject = 0;
	private long delay = 0;
	private Date lastUpdate = new Date();
	
	public abstract void doUpdate();
	
	public AbstractBlock(int power, int delay) {
		this.power = power;
		this.delay = delay;
	}
	
	public void setInput(int power) {
		powerToInject = power;
		power = getOutput();
	}
	
	public int getOutput() {
		long currentTime = System.currentTimeMillis();
		if(lastUpdate.getTime() - delay > currentTime) {
			power = powerToInject;
			lastUpdate = new Date(currentTime);
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
}
