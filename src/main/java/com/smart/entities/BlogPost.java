package com.smart.entities;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class BlogPost {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	    private String title;
	    private String content;
	    private String author;
	    private Date datePosted;
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }

	    public String getAuthor() {
	        return author;
	    }

	    public void setAuthor(String author) {
	        this.author = author;
	    }

	    public Date getDatePosted() {
	        return datePosted;
	    }

	    public void setDatePosted(Date datePosted) {
	        this.datePosted = datePosted;
	    }
}
