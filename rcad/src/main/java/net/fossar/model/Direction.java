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
