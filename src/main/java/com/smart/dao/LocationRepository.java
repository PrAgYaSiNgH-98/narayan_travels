package com.smart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.smart.entities.Location;


@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
	  // Fetch distinct "From" locations
    List<Location> findDistinctByFromLocationNotNull();

    // Fetch "To" locations based on selected "From"
    List<Location> findByFromLocation(String fromLocation);
	}