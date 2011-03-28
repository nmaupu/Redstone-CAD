package net.fossar.core;

public enum Direction {
	ABOVE(),
	BELOW(),
	UP(),
	DOWN(),
	LEFT(),
	RIGHT(),
	UNDEF(),
	UPRIGHT(),
	UPLEFT(),
	DOWNRIGHT(),
	DOWNLEFT();
	
	private Direction() { }
	
	public boolean up()        { return this == UP; }
	public boolean down()      { return this == DOWN; }
	public boolean left()      { return this == LEFT; }
	public boolean right()     { return this == RIGHT; }
	public boolean above()     { return this == ABOVE; }
	public boolean below()     { return this == BELOW; }
	public boolean upright()   { return this == UPRIGHT; }
	public boolean upleft()    { return this == UPLEFT; }
	public boolean downright() { return this == DOWNRIGHT; }
	public boolean downleft()  { return this == DOWNLEFT; }
	public boolean undef()     { return this == UNDEF; }
}
