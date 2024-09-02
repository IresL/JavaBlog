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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BlogApplicationTests {

	@Mock
	private PostService postService;

	@Mock
	private UserService userService;
	@Mock
	private AuthenticationManager authenticationManager; // Mocking the AuthenticationManager

	@InjectMocks
	private BlogController blogController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	// Test for fetching all posts
	@Test
	public void testGetAllPosts() {
		List<Post> posts = new ArrayList<>();
		posts.add(new Post("Title 1", "Content 1", null));
		posts.add(new Post("Title 2", "Content 2", null));

		when(postService.findAllPosts()).thenReturn(posts);

		ResponseEntity<List<Post>> response = blogController.getAllPosts();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}

	// Test for fetching a post by ID
	@Test
	public void testGetPostById_Success() {
		Post post = new Post("Title 1", "Content 1", null);
		post.setId(1L);

		when(postService.findPostById(1L)).thenReturn(post);

		ResponseEntity<Post> response = blogController.getPostById(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Title 1", response.getBody().getTitle());
	}

	@Test
	public void testGetPostById_NotFound() {
		when(postService.findPostById(1L)).thenReturn(null);

		ResponseEntity<Post> response = blogController.getPostById(1L);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	// Test for creating a post
	@Test
	public void testCreatePost() {
		Post post = new Post("New Post", "Content", null);
		Principal principal = () -> "testUser";

		when(postService.createPost(anyString(), anyString(), anyString())).thenReturn(post);

		ResponseEntity<Post> response = blogController.createPost(post, principal);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("New Post", response.getBody().getTitle());
	}

	// Test for user registration
	@Test
	public void testRegisterUser() {
		User newUser = new User();
		newUser.setUsername("testUser");
		newUser.setPassword("password");

		// Simply mocking saveUser without Expectation for return
		doNothing().when(userService).saveUser(any(User.class));

		ResponseEntity<User> response = blogController.registerUser(newUser);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("testUser", response.getBody().getUsername());
	}

	// Test for user login
	@Test
	public void testLoginUser_Success() {
		User user = new User();
		user.setUsername("testUser");
		user.setPassword("password");

		when(userService.findUserByUsername("testUser")).thenReturn(user);

		// Here we need to simulate the authentication
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(null); // Adjust this according to your authentication implementation

		ResponseEntity<String> response = blogController.loginUser(user);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Login successful", response.getBody());
	}

	@Test
	public void testLoginUser_Failed() {
		User user = new User();
		user.setUsername("wrongUser");
		user.setPassword("wrongPassword");

		// Mock the behavior when user is not found
		when(userService.findUserByUsername("wrongUser")).thenReturn(null);

		// Simulate an authentication failure by throwing an exception
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenThrow(new RuntimeException("Authentication failed"));

		ResponseEntity<String> response = blogController.loginUser(user);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Invalid username or password", response.getBody());
	}
}