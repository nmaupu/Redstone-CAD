/*
 * RCad is a software to help manipulating minecraft's redstone.
 * Copyright (C) 2011. mathieu_dot_grenonville_at_gmail_dot_com - nmaupu_at_gmail_dot_com
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.fossar.model.core.block;

import java.util.*;

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
	protected Set<Direction> direction;

	protected List<BlockPowerListener> listeners = null;

	private int powerToInject = 0;
	private long delay = 0;
	private Time lastUpdate = new Time();

	public abstract void doUpdate(AdjacentBlocks adjacentBlocks);

	public AbstractBlock(int power, int delay) {
		this.power = power;
		this.delay = delay;
        initDirections();
	}

    protected void initDirections() {
         direction = EnumSet.noneOf(Direction.class);
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

    public Set<Direction> getDirections(){
        return Collections.unmodifiableSet(direction);
    }

	protected void addDirection(Direction direction){
        this.direction.add(direction);
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
