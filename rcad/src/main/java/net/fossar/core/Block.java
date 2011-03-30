package net.fossar.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Block {
	private BlockType type;
	private int power=BlockConstant.POWER_OFF;
	private Direction direction = Direction.UNDEF;
	
	public Block(BlockType type, int power, Direction direction) {
		this.type = type;
		this.power = power;
		this.direction = direction;
	}
	
	public Block(BlockType type) {
		setType(type);
	}
	
	/**
	 * Draw a torch stick to adjacent block
	 * @param g
	 * @param dir
	 * @param width
	 */
	public void drawTorch(Graphics2D g, Direction dir, int width) {
		int rectS = width/7 * 2;
		int rectB = width/2;
		int x=0, y=0, w=0, h=0;
		
		// Torch pointing opposite direction of given dir
		this.direction = dir == Direction.UNDEF ? Direction.ABOVE : dir.getOpposite();
		
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
	
	
	public boolean isPowered()      { return power > 0; }
	public boolean isUnpowered()    { return power == 0; }
	public boolean getPowerStatus() { return isPowered(); }
	public void poweroff()          { setPower(BlockConstant.POWER_OFF); }
	public void poweron()           { setPower(BlockConstant.POWER_SOURCE); }

	public BlockType getType() {
		return type;
	}

	public int getPower() {
		return power;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setType(BlockType type) {
		if(type == BlockType.TORCH)
			this.power = BlockConstant.POWER_SOURCE;
		else
			this.power = BlockConstant.POWER_OFF;
		
		this.type = type;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	
}
