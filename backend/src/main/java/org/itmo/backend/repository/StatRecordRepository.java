package org.itmo.backend.repository;

import org.itmo.backend.entity.StatRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRecordRepository extends JpaRepository<StatRecord, Long>{

}
