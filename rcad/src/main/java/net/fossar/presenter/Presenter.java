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
