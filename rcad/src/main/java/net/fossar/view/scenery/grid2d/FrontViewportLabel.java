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

import net.fossar.model.Direction;

import java.awt.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nmaupu
 * Date: 10/04/11
 * Time: 11:16
 * To change this template use File | Settings | File Templates.
 */
public class FrontViewportLabel extends AbstractViewportLabel {
    @Override
    protected void drawTorch(Graphics2D g, Direction dir) {
    }

    @Override
    protected void drawWire(Graphics2D g, List<Direction> dirs) {
    }

    @Override
    protected void drawLever(Graphics2D g) {
    }

    @Override
    protected void drawButton(Graphics2D g) {
    }
}
