package ru.kmikhails.metrolog.view.settings.documents;

import javax.swing.*;

public class SettingsPanelInfo {
    private Long settingId;
    private JTextField valueField;

    public Long getSettingId() {
        return settingId;
    }

    public void setSettingId(Long settingId) {
        this.settingId = settingId;
    }

    public JTextField getValueField() {
        return valueField;
    }

    public void setValueField(JTextField valueField) {
        this.valueField = valueField;
    }
}
