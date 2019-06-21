package es.pryades.nadarin.common;


import org.apache.log4j.Logger;

public interface HasLogger {
    default Logger getLogger() {
        return Logger.getLogger(getClass());
    }
}
