package com.example.Blog;

import com.example.blog.controller.BlogController;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
class BlogControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private Principal principal;

    @InjectMocks
    private BlogController blogController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPosts_shouldReturnListOfPosts() {
        // Arrange
        List<Post> mockPosts = Arrays.asList(new Post(), new Post());
        when(postService.findAllPosts()).thenReturn(mockPosts);

        // Act
        ResponseEntity<List<Post>> response = blogController.getAllPosts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPosts, response.getBody());
    }

    @Test
    void getPostById_shouldReturnPostIfExists() {
        // Arrange
        Long postId = 1L;
        Post mockPost = new Post();
        when(postService.findPostById(postId)).thenReturn(mockPost);

        // Act
        ResponseEntity<Post> response = blogController.getPostById(postId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPost, response.getBody());
    }

    @Test
    void getPostById_shouldReturnNotFoundIfPostDoesNotExist() {
        // Arrange
        Long postId = 1L;
        when(postService.findPostById(postId)).thenReturn(null);

        // Act
        ResponseEntity<Post> response = blogController.getPostById(postId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createPost_shouldCreateAndReturnPost() {
        // Arrange
        Post mockPost = new Post();
        mockPost.setTitle("Test Title");
        mockPost.setContent("Test Content");

        when(principal.getName()).thenReturn("testUser");
        when(postService.createPost(mockPost.getTitle(), mockPost.getContent(), "testUser")).thenReturn(mockPost);

        // Act
        ResponseEntity<Post> response = blogController.createPost(mockPost, principal);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockPost, response.getBody());
    }

    @Test
    void registerUser_shouldSaveAndReturnUser() {
        // Arrange
        User mockUser = new User();

        // Act
        ResponseEntity<User> response = blogController.registerUser(mockUser);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockUser, response.getBody());

        // Verify that saveUser was called
        verify(userService, times(1)).saveUser(mockUser);
    }

//    @Test
//    void loginUser_shouldReturnSuccessMessage() {
//        // Act
//        ResponseEntity<String> response = blogController.loginUser();
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Login successful", response.getBody());
//    }

}
