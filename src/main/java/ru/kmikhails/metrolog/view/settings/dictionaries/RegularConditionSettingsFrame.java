package ru.kmikhails.metrolog.view.settings.dictionaries;

import ru.kmikhails.metrolog.domain.RegularCondition;
import ru.kmikhails.metrolog.service.RegularConditionService;

import java.util.List;

public class RegularConditionSettingsFrame extends AbstractDictionarySettingsFrame {

    private final RegularConditionService regularConditionService;

    public RegularConditionSettingsFrame(RegularConditionService regularConditionService,
                                         List<String> dictionaryElements, String frameName,
                                         ReconfigureDeviceFrameListener listener) {
        super(dictionaryElements, frameName, listener);
        this.regularConditionService = regularConditionService;
    }

    @Override
    protected void updateElement(String oldValue, String newValue) {
        int index = jList.getSelectedIndex();
        RegularCondition regularCondition = regularConditionService.findByName(oldValue);
        regularCondition.setRegularCondition(newValue);
        regularConditionService.update(regularCondition);
        listModel.setElementAt(newValue, index);
    }

    @Override
    protected void deleteElement() {
        String element = jList.getSelectedValue();
        if (element != null) {
            RegularCondition regularCondition = regularConditionService.findByName(element);
            regularConditionService.deleteById(regularCondition.getId());
            listModel.remove(jList.getSelectedIndex());
        }
    }

    @Override
    protected void addElement(String value) {
        RegularCondition regularCondition = new RegularCondition();
        regularCondition.setRegularCondition(value);
        regularConditionService.save(regularCondition);
        listModel.addElement(value);
    }

    @Override
    public void saveValue(String value) {
        addElement(value);
    }

    @Override
    public void updateValue(String oldValue, String newValue) {
        updateElement(oldValue, newValue);
    }
}
