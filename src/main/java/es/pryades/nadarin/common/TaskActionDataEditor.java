package es.pryades.nadarin.common;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.binder.ValidationException;

public interface TaskActionDataEditor
{
	public Component getComponent(String data, boolean readOnly );
	public String getTaskData() throws BaseException, ValidationException;
	public String isValidInput(); 
}
