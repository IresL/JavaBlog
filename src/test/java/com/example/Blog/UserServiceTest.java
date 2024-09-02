package com.example.Blog;

import com.example.blog.model.Role;
import com.example.blog.model.User;
import com.example.blog.repository.RoleRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRoles(roles);
    }

    @Test
    void testSaveUser() {
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.saveUser(user);

        assertNotNull(user.getPassword());
        assertEquals("encodedPassword", user.getPassword());

        verify(userRepository, times(1)).save(user);
        verify(passwordEncoder, times(1)).encode("password123");
    }

    @Test
    void testFindUserByUsername() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = userService.findUserByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void testPasswordMatches() {
        when(passwordEncoder.matches(any(CharSequence.class), any(String.class))).thenReturn(true);

        boolean matches = userService.passwordMatches("rawPassword", "encodedPassword");

        assertTrue(matches);

        verify(passwordEncoder, times(1)).matches("rawPassword", "encodedPassword");
    }
}
