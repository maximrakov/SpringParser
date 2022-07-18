package org.itmo.backend.service;

import org.itmo.backend.entity.StatRecord;
import org.itmo.backend.repository.StatRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class StatRecordService {
    StatRecordRepository statRecordRepository;
    StatRecord lastRecord;

    @Autowired
    public StatRecordService(StatRecordRepository statRecordRepository) {
        this.statRecordRepository = statRecordRepository;
    }

    public void save(StatRecord statRecord) {
        lastRecord = statRecord;
        statRecordRepository.save(statRecord);
    }

    public StatRecord getLastRecord() {
        return lastRecord;
    }

    public List<StatRecord> getAllRecords(int amount) {
        List<StatRecord> statRecords = statRecordRepository.findAll();
        statRecords.sort(Comparator.comparing(StatRecord::getDate));
        statRecords.subList(0, amount);
        return statRecords;
    }
}
