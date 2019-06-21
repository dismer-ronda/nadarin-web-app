package es.pryades.nadarin.ui.common;

import com.vaadin.flow.shared.Registration;
import es.pryades.nadarin.dto.BaseDto;

public class EntityState<T extends BaseDto> {

    private T entity;
    //private String entityName;
    private Registration okRegistration;
    private Registration cancelRegistration;
    private boolean isNew = false;

    public void updateEntity(T entity, boolean isNew) {
        this.entity = entity;
        //this.entityName = EntityUtil.getName(this.entity.getClass());
        this.isNew = isNew;
    }

    void updateRegistration(Registration okRegistration,Registration cancelRegistration) {
        clearRegistration(this.okRegistration);
        clearRegistration(this.cancelRegistration);
        this.okRegistration = okRegistration;
        this.cancelRegistration = cancelRegistration;
    }

    public void clear() {
        this.entity = null;
        //this.entityName = null;
        this.isNew = false;
        updateRegistration(null, null);
    }

    private void clearRegistration(Registration registration) {
        if(registration != null) {
            registration.remove();
        }
    }

    public T getEntity() {
        return entity;
    }

//    public String getEntityName() {
//        return entityName;
//    }

    public boolean isNew() {
        return isNew;
    }

}
