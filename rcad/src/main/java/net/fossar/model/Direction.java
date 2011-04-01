/*
 * <one line to give the program's name and a brief idea of what it does.>
 *     Copyright (C) 2011.  <name of author>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.fossar.model;

public enum Direction {
	ABOVE(),
	BELOW(),
	UP(),
	DOWN(),
	LEFT(),
	RIGHT(),
	UNDEF();
		
	public boolean up()        { return this == UP;        }
	public boolean down()      { return this == DOWN;      }
	public boolean left()      { return this == LEFT;      }
	public boolean right()     { return this == RIGHT;     }
	public boolean above()     { return this == ABOVE;     }
	public boolean below()     { return this == BELOW;     }
	public boolean undef()     { return this == UNDEF;     }
	public Direction getOpposite() {		
		switch(this) {
		case ABOVE :
			return BELOW;
		case BELOW :
			return ABOVE;
		case UP :
			return DOWN;
		case DOWN :
			return UP;
		case LEFT :
			return RIGHT;
		case RIGHT :
			return LEFT;
		default :
			return UNDEF;
		}
	}
}
