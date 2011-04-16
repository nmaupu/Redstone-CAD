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

package net.fossar.presenter.event;


@SuppressWarnings("serial")
public class GridViewEvent extends PresenterEvent {
	private int row;
	private int col;
	private int lay;
    private EventType eventType;
	
	public GridViewEvent(Object source, int r, int c , int l, EventType t) {
		super(source);
		this.row = r;
		this.col = c;
		this.lay = l;
        this.eventType = t;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getLay() {
		return lay;
	}

    public EventType getEventType() {
        return eventType;
    }
}
