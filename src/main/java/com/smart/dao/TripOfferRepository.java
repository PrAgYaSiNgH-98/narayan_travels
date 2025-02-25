package com.smart.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart.entities.Offer;
@Repository
public interface TripOfferRepository extends JpaRepository<Offer, Long> {

}
