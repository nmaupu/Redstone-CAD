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
 * Represents a front view stack viewport
 * User: nmaupu
 * Date: 10/04/11
 * Time: 00:05
 */
public class FrontViewportStack extends ViewportStack {
    private static final Logger logger = LoggerFactory.getLogger(FrontViewportStack.class);

    @Override
    protected int getCorrespondingGridRow(int row, int col, int lay) {
        return row;
    }

    @Override
    protected int getCorrespondingGridCol(int row, int col, int lay) {
        return lay;
    }

    @Override
    protected int getCorrespondingGridLay(int row, int col, int lay) {
        return col;
    }


}
