package com.example.Blog;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    private User author;
    private Post post;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        author = new User();
        author.setUsername("testUser");
        post = new Post("Test Title", "Test Content", author);
    }

    @Test
    public void testCreatePost() {
        when(userRepository.findByUsername(anyString())).thenReturn(author);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post createdPost = postService.createPost("Test Title", "Test Content", "testUser");

        assertNotNull(createdPost);
        assertEquals("Test Title", createdPost.getTitle());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testFindPostById() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        Post foundPost = postService.findPostById(1L);

        assertNotNull(foundPost);
        assertEquals("Test Title", foundPost.getTitle());
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdatePost() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post updatedPost = postService.updatePost(1L, "Updated Title", "Updated Content");

        assertNotNull(updatedPost);
        assertEquals("Updated Title", updatedPost.getTitle());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    public void testDeletePost() {
        doNothing().when(postRepository).deleteById(anyLong());

        postService.deletePost(1L);

        verify(postRepository, times(1)).deleteById(1L);
    }
}

