package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", blogService.getAllPosts());
        return "index";
    }

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        model.addAttribute("post", blogService.getPostById(id));
        return "post";
    }

    @GetMapping("/post/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "new_post";
    }

    @PostMapping("/post")
    public String createPost(@ModelAttribute Post post) {
        blogService.createPost(post);
        return "redirect:/";
    }

    @DeleteMapping("/post/{id}")
    public String deletePost(@PathVariable Long id) {
        blogService.deletePost(id);
        return "redirect:/";
    }

    @GetMapping("/post/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        model.addAttribute("post", blogService.getPostById(id));
        return "edit_post";
    }

    @PostMapping("/post/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post) {
        Post existingPost = blogService.getPostById(id);
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        blogService.createPost(existingPost);
        return "redirect:/";
    }
}
