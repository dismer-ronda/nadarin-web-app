package es.pryades.nadarin.ui.settings.parameters;

import es.pryades.nadarin.dto.Parameter;
import es.pryades.nadarin.dto.query.UserQuery;
import es.pryades.nadarin.ui.common.ConfigBar;

public class ParameterSearchBar extends ConfigBar<Parameter> {

    public ParameterSearchBar(){
        super();
        removeClassName("top-bar");
        getAddBtn().setVisible(false);
    }

    @Override
    protected void init() {
        filterVisible(false);
    }

    @Override
    protected String getAddText() {
        return getTranslation("operation.add");
    }

    @Override
    public Parameter getQuery() {
        return new Parameter();
    }
}
