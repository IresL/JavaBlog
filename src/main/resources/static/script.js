document.addEventListener('DOMContentLoaded', () => {
    const postsSection = document.getElementById('posts-section');
    const loginSection = document.getElementById('login-section');
    const registerSection = document.getElementById('register-section');

    document.getElementById('home-link').addEventListener('click', () => {
        postsSection.style.display = 'block';
        loginSection.style.display = 'none';
        registerSection.style.display = 'none';
        loadPosts();
    });

    document.getElementById('login-link').addEventListener('click', () => {
        postsSection.style.display = 'none';
        loginSection.style.display = 'block';
        registerSection.style.display = 'none';
    });

    document.getElementById('register-link').addEventListener('click', () => {
        postsSection.style.display = 'none';
        loginSection.style.display = 'none';
        registerSection.style.display = 'block';
    });

    document.getElementById('login-form').addEventListener('submit', (e) => {
        e.preventDefault();
        loginUser();
    });

    document.getElementById('register-form').addEventListener('submit', (e) => {
        e.preventDefault();
        registerUser();
    });

    function loadPosts() {
        fetch('/api/posts')
            .then(response => response.json())
            .then(posts => {
                const postsContainer = document.getElementById('posts-container');
                postsContainer.innerHTML = '';
                posts.forEach(post => {
                    const postDiv = document.createElement('div');
                    postDiv.className = 'post';
                    postDiv.innerHTML = `<h3>${post.title}</h3><p>${post.content}</p>`;
                    postsContainer.appendChild(postDiv);
                });
            })
            .catch(error => console.error('Error loading posts:', error));
    }

    function loginUser() {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        console.log('Logging in with', username);

        fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        })
        .then(response => {
            if (response.ok) {
                alert('Login successful!');
            } else {
                alert('Login failed. Please check your credentials.');
            }
        })
        .catch(error => console.error('Error during login:', error));
    }

    function registerUser() {
        const username = document.getElementById('reg-username').value;
        const password = document.getElementById('reg-password').value;

        console.log('Registering with', username);

        fetch('/api/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        })
        .then(response => {
            if (response.ok) {
                alert('Registration successful!');
            } else {
                alert('Registration failed. Please try again.');
            }
        })
        .catch(error => console.error('Error during registration:', error));
    }

    loadPosts(); // Load posts initially
});
