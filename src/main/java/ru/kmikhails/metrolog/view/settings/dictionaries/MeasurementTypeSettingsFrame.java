package ru.kmikhails.metrolog.view.settings.dictionaries;

import ru.kmikhails.metrolog.domain.MeasurementType;
import ru.kmikhails.metrolog.service.MeasurementTypeService;

import java.util.List;

public class MeasurementTypeSettingsFrame extends AbstractDictionarySettingsFrame {

    private final MeasurementTypeService measurementTypeService;

    public MeasurementTypeSettingsFrame(MeasurementTypeService measurementTypeService,
                                        List<String> dictionaryElements, String frameName,
                                        ReconfigureDeviceFrameListener listener) {
        super(dictionaryElements, frameName, listener);
        this.measurementTypeService = measurementTypeService;
    }

    @Override
    protected void updateElement(String oldValue, String newValue) {
        int index = jList.getSelectedIndex();
        MeasurementType measurementType = measurementTypeService.findByName(oldValue);
        measurementType.setMeasurementType(newValue);
        measurementTypeService.update(measurementType);
        listModel.setElementAt(newValue, index);
    }

    @Override
    protected void deleteElement() {
        String element = jList.getSelectedValue();
        if (element != null) {
            MeasurementType measurementType = measurementTypeService.findByName(element);
            measurementTypeService.deleteById(measurementType.getId());
            listModel.remove(jList.getSelectedIndex());
        }
    }

    @Override
    protected void addElement(String value) {
        MeasurementType measurementType = new MeasurementType();
        measurementType.setMeasurementType(value);
        measurementTypeService.save(measurementType);
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
