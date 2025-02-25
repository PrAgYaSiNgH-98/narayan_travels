package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entities.Gallery;

	public interface GalleryRepository extends JpaRepository<Gallery, Long> {

}
