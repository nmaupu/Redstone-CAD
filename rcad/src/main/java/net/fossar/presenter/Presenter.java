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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.fossar.model.Model;

/**
 * Class used to represent a Presenter in the MVP design pattern
 * @author nmaupu
 *
 */
public abstract class Presenter {
	private Map<String,Model> models = null;
	
	/**
	 * Presenter initialization
	 */
	public abstract void doInit();
	
	public void addModel(String k, Model m) {
		if(models == null)
			models = new HashMap<String, Model>();
		
		models.put(k, m);
	}
	
	public void removeModel(String k) {
		if(models == null)
			return;
		
		models.remove(k);
	}
	
	public Model getModel(String k) {
		return models.get(k);
	}
	
	/**
     * This is a convenience method that subclasses can call upon
     * to fire property changes back to the models. This method
     * uses reflection to inspect each of the model classes
     * to determine whether it is the owner of the property
     * in question. If it isn't, a NoSuchMethodException is thrown,
     * which the method ignores.
     *
     * @param propertyName = The name of the property.
     * @param newValue = An object that represents the new value
     * of the property.
     */
    protected void setModelProperty(String propertyName, Object newValue) {
        for (Entry<String, Model> entry : models.entrySet()) {
        	Model model = entry.getValue();
            try {
                Method method = model.getClass().getMethod("set"+propertyName, new Class[] { newValue.getClass() } );
                method.invoke(model, newValue);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
