package com.calsol.solar.repository.dao;

import com.calsol.solar.domain.entity.Panel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryPanel extends JpaRepository<Panel, Long> {
}
