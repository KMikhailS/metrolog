package ru.kmikhails.metrolog.view.settings.documents;

import ru.kmikhails.metrolog.domain.Setting;
import ru.kmikhails.metrolog.repository.SettingRepository;
import ru.kmikhails.metrolog.view.util.HorizontalPanel;
import ru.kmikhails.metrolog.view.util.VerticalPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class SettingsFrame extends JFrame implements UpdateSettingsListener {

    private final SettingRepository settingRepository;
    private final UpdateSettingsListener listener;

    public SettingsFrame(SettingRepository settingRepository, UpdateSettingsListener listener) {
        this.settingRepository = settingRepository;
        this.listener = listener;
    }

    public void init() {
        VerticalPanel mainPanel = new VerticalPanel(10, 0.f);
        List<Setting> settings = settingRepository.findAll();

        HorizontalPanel buttonPanel = new HorizontalPanel(10, 0.f);

        int fontSize = 16;
        Font font = new Font(null, Font.PLAIN, fontSize);
        Setting yearSetting = settings.stream()
                .filter(setting -> "YEAR".equals(setting.getType()))
                .findFirst()
                .orElse(null);
        Setting nameSetting = settings.stream()
                .filter(setting -> "NAME".equals(setting.getType()))
                .findFirst()
                .orElse(null);

        List<SettingsPanelInfo> panelInfos = new ArrayList<>();
        if (yearSetting != null) {
            SettingsPanelInfo yearInfo = addVerticalPanelForSetting(mainPanel, yearSetting.getDescription(), yearSetting, font);
            panelInfos.add(yearInfo);
        }
        if (nameSetting != null) {
            SettingsPanelInfo nameInfo = addVerticalPanelForSetting(mainPanel, nameSetting.getDescription(), nameSetting, font);
            panelInfos.add(nameInfo);
        }
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(300, 25));
        buttonPanel.add(emptyPanel);
        JPanel okPanel = new JPanel(new BorderLayout());
        okPanel.setPreferredSize(new Dimension(45, 35));
        JButton okButton = new JButton("Сохранить");
        okButton.setFont(font);
        okPanel.add(okButton);
        okButton.addActionListener(e -> updateSettings(panelInfos));
        buttonPanel.add(okPanel);
        mainPanel.add(emptyPanel);
        mainPanel.add(buttonPanel);

        setTitle("Настройки");
        setLayout(new BorderLayout(5, 10));
        add(mainPanel);
        setSize(new Dimension(300, 260));
        setLocationRelativeTo(null);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        setResizable(false);
        setVisible(true);
    }

    private void updateSettings(List<SettingsPanelInfo> panelInfos) {
        for (SettingsPanelInfo panelInfo : panelInfos) {
            JTextField valueField = panelInfo.getValueField();
            String newValue = valueField.getText();
            settingRepository.update(panelInfo.getSettingId(), newValue);
        }
        List<Setting> settings = settingRepository.findAll();
        updateValue(settings);
        this.dispose();
    }

    private SettingsPanelInfo addVerticalPanelForSetting(VerticalPanel mainPanel, String description, Setting setting, Font font) {
        VerticalPanel panel = new VerticalPanel(5, 0.f);
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(font);
        panel.add(descriptionLabel);
        JTextField valueField = new JTextField(setting.getValue());
        valueField.setFont(font);
        panel.add(valueField);
        mainPanel.add(panel);
        SettingsPanelInfo settingsPanelInfo = new SettingsPanelInfo();
        settingsPanelInfo.setSettingId(setting.getId());
        settingsPanelInfo.setValueField(valueField);

        return settingsPanelInfo;
    }

    @Override
    public void updateValue(List<Setting> settings) {
        listener.updateValue(settings);
    }
}
