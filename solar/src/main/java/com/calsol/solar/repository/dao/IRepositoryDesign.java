package com.calsol.solar.repository.dao;

import com.calsol.solar.domain.entity.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Repository design.
 */
@Repository
public interface IRepositoryDesign extends JpaRepository<Design, Long> {

    /**
     * Find by name design.
     *
     * @param emailAddress the email address
     * @return the design
     */
    Design findByName(String emailAddress);
}
