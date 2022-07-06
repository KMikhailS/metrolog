package ru.kmikhails.metrolog.service;

import ru.kmikhails.metrolog.domain.RegularCondition;
import ru.kmikhails.metrolog.repository.RegularConditionRepository;

public class RegularConditionService extends AbstractService<RegularCondition> {

    private final RegularConditionRepository regularConditionRepository;

    public RegularConditionService(RegularConditionRepository regularConditionRepository) {
        super(regularConditionRepository);
        this.regularConditionRepository = regularConditionRepository;
    }

    public void update(RegularCondition regularCondition) {
        regularConditionRepository.update(regularCondition);
    }
}
