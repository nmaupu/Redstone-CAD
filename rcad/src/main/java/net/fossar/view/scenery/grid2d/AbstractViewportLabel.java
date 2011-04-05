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

package net.fossar.view.scenery.grid2d;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import net.fossar.model.Direction;
import net.fossar.model.core.block.BlockType;
import net.fossar.model.core.block.DataBlock;

import net.fossar.view.Colors;
import net.fossar.view.IView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing a <em>block</em> in a grid2d viewport style
 */
public abstract class AbstractViewportLabel extends JLabel implements IView {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(AbstractViewportLabel.class);

	protected DataBlock dataBlock;

    /**
     * Construct a default AbstractViewportLabel with default options (size, borders, background)
     */
	public AbstractViewportLabel() {
		this.setPreferredSize(new Dimension(AbstractViewport.LABEL_WIDTH, AbstractViewport.LABEL_WIDTH));
		this.setBounds(0, 0, AbstractViewport.LABEL_WIDTH, AbstractViewport.LABEL_WIDTH);
		this.setOpaque(true);
		this.setBorder(BorderFactory.createLineBorder(Colors.COLOR_VIEWPORT_BORDERS, AbstractViewport.BORDER_WIDTH));
		this.setBackground(Colors.COLOR_AIR);
	}

    /**
     * Override paintComponent to set some default properties
     * @param g1
     */
	@Override
	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D)g1;
		
		// anti aliasing
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		renderViewportLabel(g);
	}
	
	private void renderViewportLabel(Graphics2D g) {
		if(dataBlock == null)
			return;
		
		switch (BlockType.getBlockType(dataBlock.getBlock())) {
		case AIR:
			drawAir(g);
			break;
		case BLOCK:
			drawBlock(g);
			break;
		case WIRE:
			drawWire(g, new ArrayList<Direction>(dataBlock.getBlock().getDirections()));
			break;
		case TORCH:
			drawTorch(g, dataBlock.getBlock().getDirections().iterator().next());
			break;
		case LEVER:
			// TODO Draw a lever attach to first available adjacent block
			//drawLever(g, d);
			break;
		}
	}

    /**
     * Fix a new dataBlock and repaint this label
     * @param dataBlock
     */
    public void repaint(final DataBlock dataBlock) {
		this.dataBlock = dataBlock;
        super.repaint();
	}

    /**
     * Draw a Torch block
     * @param g
     * @param dir
     */
    protected abstract void drawTorch(Graphics2D g, Direction dir);

    /**
     * Draw a Wire block
     * @param g
     * @param dirs
     */
	protected abstract void drawWire(Graphics2D g, List<Direction> dirs);

    /**
     * Draw a Lever block
     * @param g
     */
    protected abstract void drawLever(Graphics2D g);

    /**
     * Draw a Button block
     * @param g
     */
	protected abstract void drawButton(Graphics2D g);

    /**
     * Draw a Block block
     * @param g
     */
	protected void drawBlock(Graphics2D g) {
        setBackground(Colors.COLOR_BLOCK);
    }

    /**
     * Draw a Air block
     * @param g
     */
	protected void drawAir(Graphics2D g) {
        setBackground(Colors.COLOR_AIR);
    }
}
