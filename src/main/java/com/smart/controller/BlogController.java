package com.smart.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.smart.dao.BlogPostRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.BlogPost;
import com.smart.entities.User;
import com.smart.service.BlogPostService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;



	  
	  @Controller
	  public class BlogController {
		  @Autowired
		    private BlogPostService blogPostService;

		    // Display all blog posts
		    @GetMapping("/blogs")
		    public String viewBlogs(Model model) {
		        model.addAttribute("posts", blogPostService.getAllPosts());
		        return "blog_list";
		    }

		    // Display a single blog post
		    @GetMapping("/blog/{id}")
		    public String viewBlog(@PathVariable("id") Long id, Model model) {
		        BlogPost blogPost = blogPostService.getPostById(id);
		        model.addAttribute("post", blogPost);
		        return "blog_detail";
		    }

		    // Add a new blog post (form)
		    @GetMapping("/addBlog")
		    public String addBlogForm(Model model) {
		        model.addAttribute("blogPost", new BlogPost());
		        return "add_blog";
		    }

		    // Save the new blog post
		    @PostMapping("/addBlog")
		    public String saveBlog(BlogPost blogPost) {
		        blogPostService.savePost(blogPost);
		        return "redirect:/blogs";
		    }

		    // Delete a blog post
		    @GetMapping("/deleteBlog/{id}")
		    public String deleteBlog(@PathVariable("id") Long id) {
		        blogPostService.deletePost(id);
		        return "redirect:/blogs";
		    }
	  }
