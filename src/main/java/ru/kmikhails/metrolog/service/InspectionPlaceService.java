package ru.kmikhails.metrolog.service;

import ru.kmikhails.metrolog.domain.InspectionPlace;
import ru.kmikhails.metrolog.repository.InspectionPlaceRepository;

public class InspectionPlaceService extends AbstractService<InspectionPlace> {

    private final InspectionPlaceRepository inspectionPlaceRepository;

    public InspectionPlaceService(InspectionPlaceRepository inspectionPlaceRepository) {
        super(inspectionPlaceRepository);
        this.inspectionPlaceRepository = inspectionPlaceRepository;
    }

    public void update(InspectionPlace inspectionPlace) {
        inspectionPlaceRepository.update(inspectionPlace);
    }
}
