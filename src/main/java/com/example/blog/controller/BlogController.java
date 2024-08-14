package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public String getAllPosts(Model model) {
        List<Post> posts = blogService.getAllPosts();
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable Long id, Model model) {
        Post post = blogService.getPostById(id);
        model.addAttribute("post", post);
        return "post";
    }

    @PostMapping("/posts")
    public String createPost(@ModelAttribute Post post, @RequestParam String authorName) {
        blogService.createNewPost(post, authorName);
        return "redirect:/";
    }

    @PutMapping("/posts/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post) {
        blogService.updatePost(id, post);
        return "redirect:/posts/" + id;
    }

    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id) {
        blogService.deletePost(id);
        return "redirect:/";
    }
}
