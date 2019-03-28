package org.prime.internship.service;

import org.prime.internship.entity.Turnover;
import org.prime.internship.repository.TurnoverRepository;

import java.time.LocalDate;

class TurnoverService {

    private final TurnoverRepository turnoverRepository;

    TurnoverService() {
        this.turnoverRepository = new TurnoverRepository();
    }

    void processTurnoverToDB(int employeeId, String date, double value) {
        Turnover turnover = new Turnover();
        turnover.setEmployeeId(employeeId);
        turnover.setDate(LocalDate.parse(date));
        turnover.setTurnoverValue(value);
        turnoverRepository.insert(turnover);
    }
}
