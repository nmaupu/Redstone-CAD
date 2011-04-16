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

package net.fossar.view.toolbar;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JToolBar;

import net.fossar.presenter.ToolBarActionController;
import net.fossar.view.IView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class MainToolBar extends JToolBar implements IView {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(MainToolBar.class);
	
	AbstractAction toolBarActionController = null;
	private JButton blockButton   = new JButton();
	private JButton airButton     = new JButton();
	private JButton wireButton    = new JButton();
	private JButton torchButton   = new JButton();
	private JButton updateButton  = new JButton();
    private JButton repaintButton = new JButton();
	
	public MainToolBar(AbstractAction aa) {
		super("Main tool bar");
		
		toolBarActionController = aa;
		
		blockButton.setAction(toolBarActionController);
		airButton.setAction(toolBarActionController);
		wireButton.setAction(toolBarActionController);
		torchButton.setAction(toolBarActionController);
		updateButton.setAction(toolBarActionController);
        repaintButton.setAction(toolBarActionController);
				
		// Text
		blockButton.setText("Block");
		airButton.setText("Air");
		wireButton.setText("Wire");
		torchButton.setText("Torch");
		updateButton.setText("Update");
        repaintButton.setText("Repaint");
		
		// Action type
		blockButton.setActionCommand(ToolBarActionController.ACTION_BLOCK);
		airButton.setActionCommand(ToolBarActionController.ACTION_AIR);
		wireButton.setActionCommand(ToolBarActionController.ACTION_WIRE);
		torchButton.setActionCommand(ToolBarActionController.ACTION_TORCH);
		updateButton.setActionCommand(ToolBarActionController.ACTION_UPDATE);
        repaintButton.setActionCommand(ToolBarActionController.ACTION_REPAINT);

        this.add(repaintButton);
		this.add(updateButton);
		this.add(airButton);
		this.add(blockButton);
		this.add(wireButton);
		this.add(torchButton);
	}
}
