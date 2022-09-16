package ru.kmikhails.metrolog.view.settings.dictionaries;

public interface SaveValueListener {

    void saveValue(String value);

    void updateValue(String oldValue, String newValue);
}
