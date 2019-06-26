package es.pryades.nadarin.ui.common;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import es.pryades.nadarin.common.Constants;

/**
 * Interface for views showing notifications to users
 *
 */
public interface HasNotifications extends HasElement {

	default void showNotification(String message) {
		showNotification(message, false);
	}

	default void showNotification(String message, boolean persistent) {
		if (persistent) {
			Button close = new Button( "Cerrar");
			close.getElement().setAttribute("theme", "tertiary small error");
			Notification notification = new Notification(new Text(message), close);
			notification.setPosition(Position.BOTTOM_STRETCH);
			notification.setDuration(0);
			close.addClickListener(event -> notification.close());
			notification.open();
		} else {
			Notification.show(message, Constants.NOTIFICATION_DURATION, Position.BOTTOM_STRETCH);
		}
	}
}
