package ru.kmikhails.metrolog.view.settings;

import ru.kmikhails.metrolog.domain.InspectionPlace;
import ru.kmikhails.metrolog.service.InspectionPlaceService;

import java.util.List;

public class InspectionPlaceSettingsFrame extends AbstractDictionarySettingsFrame {

    private final InspectionPlaceService inspectionPlaceService;

    public InspectionPlaceSettingsFrame(InspectionPlaceService inspectionPlaceService,
                                        List<String> dictionaryElements, String frameName,
                                        ReconfigureDeviceFrameListener listener) {
        super(dictionaryElements, frameName, listener);
        this.inspectionPlaceService = inspectionPlaceService;
    }

    @Override
    protected void updateElement(String oldValue, String newValue) {
        int index = jList.getSelectedIndex();
        InspectionPlace inspectionPlace = inspectionPlaceService.findByName(oldValue);
        inspectionPlace.setInspectionPlace(newValue);
        inspectionPlaceService.update(inspectionPlace);
        listModel.setElementAt(newValue, index);
    }

    @Override
    protected void deleteElement() {
        String element = jList.getSelectedValue();
        if (element != null) {
            InspectionPlace inspectionPlace = inspectionPlaceService.findByName(element);
            inspectionPlaceService.deleteById(inspectionPlace.getId());
            listModel.remove(jList.getSelectedIndex());
        }
    }

    @Override
    protected void addElement(String value) {
        InspectionPlace inspectionPlace = new InspectionPlace();
        inspectionPlace.setInspectionPlace(value);
        inspectionPlaceService.save(inspectionPlace);
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
