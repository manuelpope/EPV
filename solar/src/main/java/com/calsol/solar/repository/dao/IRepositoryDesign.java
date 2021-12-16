package com.calsol.solar.repository.dao;

import com.calsol.solar.domain.entity.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * The interface Repository design.
 */
@RepositoryRestResource(path = "api-design")
public interface IRepositoryDesign extends JpaRepository<Design, Long> {

    /**
     * Find by name design.
     *
     * @param name the name
     * @return the design
     */
    Design findByName(String name);
}
