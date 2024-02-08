package ru.kmikhails.metrolog.view.settings.productiondate;

import com.github.lgooddatepicker.components.DatePicker;

public class ProductionDateBox {
    private Long deviceId;
    private DatePicker productionDateDatePicker;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public DatePicker getProductionDateDatePicker() {
        return productionDateDatePicker;
    }

    public void setProductionDateDatePicker(DatePicker productionDateDatePicker) {
        this.productionDateDatePicker = productionDateDatePicker;
    }
}
