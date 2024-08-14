document.addEventListener('DOMContentLoaded', () => {
    const postForm = document.getElementById('post-form');
    const postList = document.getElementById('posts');

    // Dummy data for posts
    let posts = [

    ];

    // Function to render posts
    function renderPosts() {
        postList.innerHTML = '';
        posts.forEach(post => {
            const postItem = document.createElement('li');
            postItem.innerHTML = `
                <h3>${post.title}</h3>
                <p>${post.content}</p>
                <p><strong>Author:</strong> ${post.author}</p>
            `;
            postList.appendChild(postItem);
        });
    }

    // Event listener for form submission
    postForm.addEventListener('submit', (e) => {
        e.preventDefault();

        // Get form values
        const title = document.getElementById('title').value;
        const content = document.getElementById('content').value;
        const author = document.getElementById('author').value;

        // Create new post
        const newPost = { title, content, author };
        posts.push(newPost);

        // Re-render posts
        renderPosts();

        // Clear form
        postForm.reset();
    });

    // Initial render
    renderPosts();
});
