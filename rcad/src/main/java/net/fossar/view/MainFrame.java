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

package net.fossar.view;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.fossar.view.scenery.grid2d.ViewportStack;
import net.fossar.view.toolbar.MainToolBar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements IMainFrame {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(MainFrame.class);
	
	public static final String VERSION="0.1";
	public static final String APPNAME="Redstone CAD";
	public static final int DEFAULT_WIDTH=800;
	public static final int DEFAULT_HEIGHT=600;
	
	
	private MainToolBar mainToolBar;
	private ViewportStack viewportStack;
	
	public MainFrame(MainToolBar mtb, ViewportStack vps) {
		this.mainToolBar = mtb;
		this.viewportStack = vps;
		init();
	}

	public void init() {
		super.setTitle(APPNAME+" - v"+VERSION);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container pane = super.getContentPane();
		pane.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		BoxLayout layout = new BoxLayout(pane, BoxLayout.Y_AXIS);
		
		// Set and organize main layout
		pane.setLayout(layout);
		
		// Viewports
		JPanel p = new JPanel();
		p.add(viewportStack);
		JScrollPane scrollPaneViewport = new JScrollPane(p);
		viewportStack.firstLayer();
		
		pane.add(scrollPaneViewport);
		pane.add(mainToolBar);
		
		super.pack();
		super.setLocationByPlatform(true);
		super.setVisible(true);
	}
	
	public ViewportStack getViewportStack() {
		return viewportStack;
	}
}
