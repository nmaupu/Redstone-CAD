package net.fossar.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public enum BlockType {
	AIR(Colors.COLOR_AIR, "Air"),
	BLOCK(Colors.COLOR_BLOCK, "Block"),
	WIRE(Colors.COLOR_AIR, "Wire"),
	TORCH(Colors.COLOR_AIR, "Torch"),
	LEVER(Colors.COLOR_AIR, "Lever"),
	P_PLATE(Colors.COLOR_AIR, "Pressure Plate"),
	DOOR_O(Colors.COLOR_AIR, "Door"),
	DOOR_C(Colors.COLOR_AIR, "Door"),
	SHADOW(Colors.COLOR_SHADOW, "Shadow");
	
	String description;
	Color backgroundColor;
	
	private BlockType(Color backgroundColor, String description) {
		this.backgroundColor = backgroundColor;
		this.description = description;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public boolean isAir()          { return this == AIR || this == SHADOW; }
	public boolean isController()   { return this == LEVER || this == P_PLATE; }
	public boolean isBlock()        { return this == BLOCK; }
	public boolean isWire()         { return this == WIRE; }
}

abstract class BlockConstant {
	public static final int CONDUCT_AIR   = 0;
	public static final int CONDUCT_BLOCK = 1;
	public static final int CONDUCT_WIRE  = 2;
	public static final int POWER_LIMIT=15;
	public static final int POWER_SOURCE=POWER_LIMIT+1;
	public static final int POWER_OFF=0;
}
