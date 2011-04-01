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

package net.fossar.presenter;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import net.fossar.model.core.block.AbstractBlock;
import net.fossar.model.core.block.BlockFactory;
import net.fossar.model.core.block.BlockType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class ToolBarActionController extends AbstractAction {
	private static Logger logger = LoggerFactory.getLogger(ToolBarActionController.class);
	
	public static final String ACTION_BLOCK  = "block";
	public static final String ACTION_AIR    = "air";
	public static final String ACTION_WIRE   = "wire";
	public static final String ACTION_TORCH  = "torch";
	public static final String ACTION_UPDATE = "update";
	
	private BlockType blockType = BlockType.AIR;

	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s instanceof JButton) {
			JButton b = (JButton)s;
			String cmd = b.getActionCommand();
			
			if(cmd.equals(ACTION_BLOCK))
				blockType = BlockType.BLOCK;
			else if(cmd.equals(ACTION_TORCH))
				blockType = BlockType.TORCH;
			else if(cmd.equals(ACTION_WIRE))
				blockType = BlockType.WIRE;
			else if(cmd.equals(ACTION_AIR))
				blockType = BlockType.AIR;
			else if(cmd.equals(ACTION_UPDATE)) {
                Director.dataGridController.getDataGrid().doUpdate();
                Director.dataGridController.getDataGrid().tick();
                Director.viewportStack.repaint();
            }
			logger.debug("ActionPerformed - cmd="+cmd);
		}
	}
	
	public AbstractBlock createInstanceOfCurrentSelectedBlock() {
		return BlockFactory.getInstance(blockType);
	}
}
