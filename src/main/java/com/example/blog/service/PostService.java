package com.example.blog.service;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(String title, String content, String username) {
        User author = userRepository.findByUsername(username);
        if (author == null) {
            throw new IllegalArgumentException("User not found");
        }

        Post post = new Post(title, content, author);
        return postRepository.save(post);
    }

    public Post findPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null);
    }

    public List<Post> findPostsByAuthor(String username) {
        User author = userRepository.findByUsername(username);
        if (author == null) {
            throw new IllegalArgumentException("User not found");
        }
        return postRepository.findByAuthor(author);
    }

    public Post updatePost(Long id, String title, String content) {
        Post post = findPostById(id);
        if (post == null) {
            return null;
        }

        post.setTitle(title);
        post.setContent(content);
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }
}
