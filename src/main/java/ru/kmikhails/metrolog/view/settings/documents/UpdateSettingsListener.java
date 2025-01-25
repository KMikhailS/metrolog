package ru.kmikhails.metrolog.view.settings.documents;

import ru.kmikhails.metrolog.domain.Setting;

import java.util.List;

public interface UpdateSettingsListener {

    void updateValue(List<Setting> settings);
}
