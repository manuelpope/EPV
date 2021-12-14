package com.calsol.solar.repository.dao;

import com.calsol.solar.domain.entity.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryCondition extends JpaRepository<Condition, Long> {
}
