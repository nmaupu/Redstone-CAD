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

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.fossar.presenter.event.GridViewEvent;

import net.fossar.presenter.event.GridViewEventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopViewportStack extends ViewportStack {
	private static final Logger logger = LoggerFactory.getLogger(TopViewportStack.class);

	@Override
	public void mousePressed(MouseEvent e) {
		int r = getRowFromPoint(e.getPoint());
		int c = getColFromPoint(e.getPoint());

        gridViewEventManager.notifyPresenterListeners(
            new GridViewEvent(e.getSource(), r, c, currentLayer)
        );
	}
}
