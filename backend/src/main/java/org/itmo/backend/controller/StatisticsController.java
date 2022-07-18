package org.itmo.backend.controller;

import org.itmo.backend.entity.StatRecord;
import org.itmo.backend.service.StatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticsController {
    StatRecordService statRecordService;

    @Autowired
    public StatisticsController(StatRecordService statRecordService) {
        this.statRecordService = statRecordService;
    }

    @GetMapping("/current")
    public StatRecord getCurrentStat() {
        return statRecordService.getLastRecord();
    }

    @GetMapping("/previousStat/{amount}")
    public List<StatRecord> previousStat(@PathVariable String amount) {
        return statRecordService.getAllRecords(Integer.parseInt(amount));
    }
}
