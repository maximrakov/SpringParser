package org.itmo.backend.controller;

import org.itmo.backend.DTO.StatRecordDTO;
import org.itmo.backend.entity.ApplicationUser;
import org.itmo.backend.entity.StatRecord;
import org.itmo.backend.service.ApplicationUserService;
import org.itmo.backend.service.StatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StatisticsController {
    StatRecordService statRecordService;
    ApplicationUserService applicationUserService;


    @Autowired
    public StatisticsController(ApplicationUserService applicationUserService, StatRecordService statRecordService) {
        this.statRecordService = statRecordService;
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/current")
    public StatRecordDTO getCurrentStat() {
        StatRecord statRecord = statRecordService.getLastRecord();
        return new StatRecordDTO(statRecord.getId(),
                statRecord.getDate(),
                statRecord.getAmount());
    }


    @PostMapping("/register")
    public void register(@RequestBody ApplicationUser applicationUser) {
        applicationUserService.save(applicationUser);
    }

    @GetMapping("/previousStat/{amount}")
    public List<StatRecord> previousStat(@PathVariable String amount) {
        return statRecordService.getAllRecords(Integer.parseInt(amount));
    }
}
