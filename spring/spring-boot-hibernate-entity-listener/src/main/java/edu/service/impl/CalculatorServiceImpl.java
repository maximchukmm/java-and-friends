package edu.service.impl;

import edu.persistence.model.TwoNumbers;
import edu.persistence.repository.TwoNumbersRepository;
import edu.service.iface.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    private final TwoNumbersRepository twoNumbersRepository;

    @Autowired
    public CalculatorServiceImpl(TwoNumbersRepository twoNumbersRepository) {
        this.twoNumbersRepository = twoNumbersRepository;
    }

    @Override
    @Transactional
    public Long createTwoNumbers(int a, int b) {
        return twoNumbersRepository.save(new TwoNumbers(a, b)).getId();
    }
}
