package es.pryades.nadarin.ui.i18n;

import com.vaadin.flow.i18n.I18NProvider;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.ResourceBundle.getBundle;

@Log4j
public class NadarinI18n implements I18NProvider {
    public static final String RESOURCE_BUNDLE_NAME = "language";
    private static final Locale LOCALE_ES = new Locale("es");

    private static final ResourceBundle RESOURCE_BUNDLE_ES = getBundle(RESOURCE_BUNDLE_NAME , LOCALE_ES);
    private static final List<Locale> providedLocales = unmodifiableList(asList(LOCALE_ES));
    private final Map<Locale, ResourceBundle> cache;

    public NadarinI18n() {
        cache = new HashMap<>();
        providedLocales.forEach(locale -> cache.put(locale, readProperties(locale)));
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return providedLocales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        if (StringUtils.isEmpty(key)) {
            log.warn("Llave nula o vacía!");
            return "!";
        }

       // final ResourceBundle bundle = cache.get(locale);
        final ResourceBundle bundle = cache.get(LOCALE_ES);

        if (bundle == null) {
            log.warn("No existen recursos para idioma " + locale);
            return "!key";
        }

        try {
            String value = bundle.getString(key);
            if (params.length > 0) {
                value = MessageFormat.format(value, params);
            }
            return value;
        } catch (final MissingResourceException e) {
            log.warn("Missing resource", e);
            return "!" + locale.getLanguage() + ": " + key;
        }
    }

    private static ResourceBundle readProperties(final Locale locale) {
        final ClassLoader loader = NadarinI18n.class.getClassLoader();

        ResourceBundle propertiesBundle = null;
        try {
            propertiesBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale, loader);
        } catch (final MissingResourceException e) {
            log.warn("Missing resource", e);
        }
        return propertiesBundle;
    }
}
