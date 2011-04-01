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

package net.fossar.presenter.event;

import java.util.ArrayList;
import java.util.EventListener;

/**
 * Created by IntelliJ IDEA.
 * User: nmaupu
 * Date: 01/04/11
 * Time: 23:55
 * To change this template use File | Settings | File Templates.
 */
public abstract class PresenterEventManager {
    private ArrayList<IPresenterListener> presenterListeners = new ArrayList<IPresenterListener>();

    public void addGridViewEventListener(IPresenterListener listener) {
        presenterListeners.add(listener);
    }

    public void removeGridViewEventListener(IPresenterListener listener) {
        presenterListeners.remove(listener);
    }

    public void notifyPresenterListeners(PresenterEvent e) {
        for(IPresenterListener l : presenterListeners) {
            l.presenterEventFired(e);
        }
    }
}
