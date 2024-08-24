document.addEventListener("DOMContentLoaded", function () {
    const registerForm = document.getElementById('register-form');
    const loginForm = document.getElementById('login-form');
    const createPostForm = document.getElementById('create-post-form');
    const postsList = document.getElementById('posts-list');
    const createPostSection = document.getElementById('create-post-section');

    let token = '';

    // Register user
    registerForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const username = document.getElementById('register-username').value;
        const password = document.getElementById('register-password').value;

        fetch('/api/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        }).then(response => response.json())
          .then(data => {
              alert('User registered successfully!');
              registerForm.reset();
          }).catch(error => {
              console.error('Error:', error);
              alert('Registration failed!');
          });
    });

    // Login user
    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const username = document.getElementById('login-username').value;
        const password = document.getElementById('login-password').value;

        fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa(username + ':' + password)
            }
        }).then(response => {
            if (response.ok) {
                alert('Login successful!');
                token = btoa(username + ':' + password);
                loginForm.reset();
                createPostSection.style.display = 'block';
                fetchPosts(); // Load posts after login
            } else {
                alert('Login failed!');
            }
        }).catch(error => {
            console.error('Error:', error);
            alert('Login failed!');
        });
    });

    // Fetch posts
    function fetchPosts() {
        fetch('/api/posts', {
            method: 'GET',
            headers: {
                'Authorization': 'Basic ' + token
            }
        }).then(response => response.json())
          .then(posts => {
              postsList.innerHTML = '';
              posts.forEach(post => {
                  const postElement = document.createElement('div');
                  postElement.classList.add('post');
                  postElement.innerHTML = `<h3>${post.title}</h3><p>${post.content}</p><small>by ${post.author.username}</small>`;
                  postsList.appendChild(postElement);
              });
          }).catch(error => {
              console.error('Error:', error);
          });
    }

    // Create new post
    createPostForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const title = document.getElementById('post-title').value;
        const content = document.getElementById('post-content').value;

        fetch('/api/posts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + token
            },
            body: JSON.stringify({ title, content })
        }).then(response => response.json())
          .then(post => {
              alert('Post created successfully!');
              createPostForm.reset();
              fetchPosts(); // Reload posts after creating a new one
          }).catch(error => {
              console.error('Error:', error);
              alert('Failed to create post!');
          });
    });
});
