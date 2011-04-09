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

import net.fossar.presenter.event.GridViewEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: nmaupu
 * Date: 10/04/11
 * Time: 00:05
 * To change this template use File | Settings | File Templates.
 */
public class FrontViewportStack extends ViewportStack {
    private static final Logger logger = LoggerFactory.getLogger(FrontViewportStack.class);

    @Override
    public void mousePressed(MouseEvent e) {
        int r = getRowFromPoint(e.getPoint());
        int c = getColFromPoint(e.getPoint());

        // r,c and l does not correspond to r,c and l in datagrid for this view !
        int realR = r;
        int realC = currentLayer;
        int realL = c;
        gridViewEventManager.notifyPresenterListeners(
            new GridViewEvent(e.getSource(), realR, realC, realL)
        );
    }
}
