package ru.kmikhails.metrolog.view.settings;

import ru.kmikhails.metrolog.domain.InspectionType;
import ru.kmikhails.metrolog.service.InspectionTypeService;

import java.util.List;

public class InspectionTypeSettingsFrame extends AbstractDictionarySettingsFrame {

    private final InspectionTypeService inspectionTypeService;

    public InspectionTypeSettingsFrame(InspectionTypeService inspectionTypeService,
                                       List<String> dictionaryElements, String frameName,
                                       ReconfigureDeviceFrameListener listener) {
        super(dictionaryElements, frameName, listener);
        this.inspectionTypeService = inspectionTypeService;
    }

    @Override
    protected void updateElement(String oldValue, String newValue) {
        int index = jList.getSelectedIndex();
        InspectionType inspectionType = inspectionTypeService.findByName(oldValue);
        inspectionType.setInspectionType(newValue);
        inspectionTypeService.update(inspectionType);
        listModel.setElementAt(newValue, index);
    }

    @Override
    protected void deleteElement() {
        String element = jList.getSelectedValue();
        if (element != null) {
            InspectionType inspectionType = inspectionTypeService.findByName(element);
            inspectionTypeService.deleteById(inspectionType.getId());
            listModel.remove(jList.getSelectedIndex());
        }
    }

    @Override
    protected void addElement(String value) {
        InspectionType inspectionType = new InspectionType();
        inspectionType.setInspectionType(value);
        inspectionTypeService.save(inspectionType);
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
