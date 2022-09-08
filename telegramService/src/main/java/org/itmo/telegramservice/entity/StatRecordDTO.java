package org.itmo.telegramservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatRecordDTO {
    private Long id;
    private Date date;
    private Long amount;

}
