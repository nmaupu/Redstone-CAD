package net.fossar.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public enum Block {
	
	AIR(false, Colors.COLOR_AIR, "Air", BlockConstant.CONDUCT_AIR),
	BLOCK(false, Colors.COLOR_BLOCK, "Block", BlockConstant.CONDUCT_BLOCK),
	WIRE(false, Colors.COLOR_AIR, "Wire", BlockConstant.CONDUCT_WIRE),
	TORCH(true, Colors.COLOR_AIR, "Torch", BlockConstant.CONDUCT_WIRE),
	LEVER(false, Colors.COLOR_AIR, "Lever", BlockConstant.CONDUCT_WIRE),
	P_PLATE(false, Colors.COLOR_AIR, "Pressure Plate", BlockConstant.CONDUCT_WIRE),
	DOOR_O(false, Colors.COLOR_AIR, "Door", BlockConstant.CONDUCT_BLOCK),
	DOOR_C(false, Colors.COLOR_AIR, "Door", BlockConstant.CONDUCT_BLOCK),
	SHADOW(false, Colors.COLOR_SHADOW, "Shadow", BlockConstant.CONDUCT_AIR);
	
	String description;
	Color backgroundColor;
	int conductorType;
	boolean isOn;
	
	private Block(boolean isOn, Color backgroundColor, String description, int conductorType) {
		this.isOn = isOn;
		this.backgroundColor = backgroundColor;
		this.description = description;
		this.conductorType = conductorType;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public boolean isAir()          { return this == AIR || this == SHADOW;    }
	public boolean isController()   { return this == LEVER || this == P_PLATE; }
	public boolean isBlock()        { return this == BLOCK;                    }
	public boolean isPowered()      { return isOn;                             }
	public boolean isUnpowered()    { return ! isPowered();                    }
	public boolean getPower()       { return isOn;                             }
	public void power()             { setPower(true);                          }
	public void unpower()           { setPower(false);                         }
	public void setPower(boolean b) { isOn = b;                                }
	
	
	public void drawTorch(Graphics2D g, Direction dir, int width) {
		int rectS = width/7 * 2;
		int rectB = width/2;
		int x=0, y=0, w=0, h=0;
		
		switch(dir) {
		case UP :
		case DOWN :
			x = width/2 - rectS / 2;
			y = dir.up() ? 0 : rectB;
			w = rectS;
			h = rectB;
			break;
		case RIGHT :
		case LEFT :
			x = dir.right() ? rectB : 0;
			y = width/2 - rectS/2;
			w = rectB;
			h = rectS;
			break;
		}
		
		// Draw base
		g.setColor(Colors.COLOR_TORCH_BASE);
		g.fillRect(x, y, w, h);
		
		// Oval in center
		Color c = isPowered() ? Colors.COLOR_WIRE_ON : Colors.COLOR_WIRE_OFF;
		g.setColor(c);
		x = width/2 - width/4;
		y = width/2 - width/4;
		w = width/4 * 2;
		h = width/4 * 2;
		g.fillOval(x, y, w, h);
	}
	
	public void drawWire(Graphics2D g, List<Direction> dirs, int width) {
		
		Color c = isPowered() ? Colors.COLOR_WIRE_ON : Colors.COLOR_WIRE_OFF;
		g.setColor(c);
		List<Direction> ds = dirs == null || dirs.size() == 0 ? new ArrayList<Direction>() : dirs ;
		
		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		int rectS = width/4;
		int rectB = width;
		
		if(dirs == null || dirs.size() == 0) {
			//Draw a cross wire
			ds.add(Direction.UP);
			ds.add(Direction.DOWN);
			ds.add(Direction.LEFT);
			ds.add(Direction.RIGHT);
			drawWire(g, ds, width);
		}
		
		int size = ds.size();
		for (int i=0; i<size; i++) {
			switch(ds.get(i)) {
			case UP :
			case DOWN :
				x = width/2 - rectS/2;
				y = 0;
				w = rectS;
				h = rectB;
				
				if(ds.contains(Direction.LEFT) || ds.contains(Direction.RIGHT)) {
					h = h/2;
					if(ds.get(i) == Direction.DOWN)
						y = width/2;
				}
				break;
			case RIGHT :
			case LEFT :
				x = 0;
				y = width/2 - rectS/2;
				w = rectB;
				h = rectS;
				
				if(ds.contains(Direction.UP) || ds.contains(Direction.DOWN)) {
					w = w / 2;
					if(ds.get(i) == Direction.RIGHT)
						x = width/2 - rectS/2;
						w = w + rectS/2;
				}
				break;
			}
			
			g.fillRect(x, y, w, h);
		}
	}
}

abstract class BlockConstant {
	public static final int CONDUCT_AIR   = 0;
	public static final int CONDUCT_BLOCK = 1;
	public static final int CONDUCT_WIRE  = 2;
}
