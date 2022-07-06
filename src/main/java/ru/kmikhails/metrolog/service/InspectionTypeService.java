package ru.kmikhails.metrolog.service;

import ru.kmikhails.metrolog.domain.InspectionType;
import ru.kmikhails.metrolog.repository.InspectionTypeRepository;

public class InspectionTypeService extends AbstractService<InspectionType> {

    private final InspectionTypeRepository inspectionTypeRepository;

    public InspectionTypeService(InspectionTypeRepository inspectionTypeRepository) {
        super(inspectionTypeRepository);
        this.inspectionTypeRepository = inspectionTypeRepository;
    }

    public void update(InspectionType inspectionType) {
        inspectionTypeRepository.update(inspectionType);
    }
}
