package es.pryades.nadarin.tasks;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import es.pryades.nadarin.common.BaseException;
import es.pryades.nadarin.common.HasLogger;
import es.pryades.nadarin.common.TaskActionDataEditor;
import es.pryades.nadarin.common.Utils;
import es.pryades.nadarin.ui.common.HasAppContext;

public class DatabaseUpdateTaskDataEditor implements TaskActionDataEditor, HasAppContext, HasLogger {
    private static final long serialVersionUID = -4120225853962167158L;

    private TextArea editSql;

    private DatabaseUpdateTaskData data;
    private Binder<DatabaseUpdateTaskData> binder = new Binder<>(DatabaseUpdateTaskData.class);

    @Override
    public Component getComponent(String details, boolean readOnly) {
        data = null;

        try {
            data = (DatabaseUpdateTaskData) Utils.toPojo(details, DatabaseUpdateTaskData.class, false);
        } catch (Throwable e) {
           getLogger().error("Error", e);
        }

        if (data == null) {
            data = new DatabaseUpdateTaskData();
        }

        editSql = new TextArea();

        editSql.setLabel(editSql.getTranslation("databaseupdatetaskdataeditor.sql"));
        binder.forField(editSql).withNullRepresentation("")
                .asRequired(editSql.getTranslation("databaseupdatetaskdataeditor.missing.sql"))
                .withValidator(value -> value != null && !value.isEmpty(), editSql.getTranslation("databaseupdatetaskdataeditor.missing.sql"))
                .bind(DatabaseUpdateTaskData::getSql, DatabaseUpdateTaskData::setSql);

        binder.readBean(data);

        editSql.setWidth("100%");
        editSql.setHeight("6em");

        return editSql;
    }

    @Override
    public String getTaskData() throws BaseException, ValidationException {
        binder.writeBean(data);
        return Utils.toJson(data);
    }

    @Override
    public String isValidInput() {
        if (editSql.isEmpty())
            return getContext().getString("databaseupdatetaskdataeditor.missing.sql");

        return null;
    }
}
