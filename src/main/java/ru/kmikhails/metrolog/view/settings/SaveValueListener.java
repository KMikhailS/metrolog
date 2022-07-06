package ru.kmikhails.metrolog.view.settings;

public interface SaveValueListener {

    void saveValue(String value);

    void updateValue(String oldValue, String newValue);
}
