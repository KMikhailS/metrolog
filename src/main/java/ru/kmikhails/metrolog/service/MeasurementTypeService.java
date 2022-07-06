package ru.kmikhails.metrolog.service;

import ru.kmikhails.metrolog.domain.MeasurementType;
import ru.kmikhails.metrolog.repository.MeasurementTypeRepository;

public class MeasurementTypeService extends AbstractService<MeasurementType> {

    private final MeasurementTypeRepository measurementTypeRepository;

    public MeasurementTypeService(MeasurementTypeRepository measurementTypeRepository) {
        super(measurementTypeRepository);
        this.measurementTypeRepository = measurementTypeRepository;
    }

    public void update(MeasurementType measurementType) {
        measurementTypeRepository.update(measurementType);
    }
}
