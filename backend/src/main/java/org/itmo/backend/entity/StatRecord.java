package org.itmo.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity(name = "statRecord")
@Getter
@Setter
@NoArgsConstructor
public class StatRecord {

    @Id
    @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    @Column(name = "time")
    private Date date;

    @Column(name = "amount")
    private Long amount;
}