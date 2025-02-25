package com.smart.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entities.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
	
}
