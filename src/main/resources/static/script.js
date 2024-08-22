//document.addEventListener("DOMContentLoaded", function () {
//    console.log("Blog loaded");
//
//    // Example: Handle form submissions via AJAX
//    const form = document.querySelector('form');
//    if (form) {
//        form.addEventListener('submit', function (event) {
//            event.preventDefault();
//
//            const formData = new FormData(form);
//            const formAction = form.getAttribute('action');
//
//            fetch(formAction, {
//                method: 'POST',
//                body: formData,
//            })
//            .then(response => response.json())
//            .then(data => {
//                console.log('Success:', data);
//                // Add logic to handle successful form submission
//            })
//            .catch((error) => {
//                console.error('Error:', error);
//                // Add logic to handle errors
//            });
//        });
//    }
//
//    // Example: Load posts dynamically (if needed)
//    const postsContainer = document.getElementById('posts');
//    if (postsContainer) {
//        fetch('/api/posts')
//            .then(response => response.json())
//            .then(data => {
//                // Loop over the posts and display them
//                data.forEach(post => {
//                    const article = document.createElement('article');
//                    const title = document.createElement('h3');
//                    const content = document.createElement('p');
//                    const readMore = document.createElement('a');
//
//                    title.textContent = post.title;
//                    content.textContent = post.content;
//                    readMore.textContent = 'Read more';
//                    readMore.href = `/posts/${post.id}`;
//
//                    article.appendChild(title);
//                    article.appendChild(content);
//                    article.appendChild(readMore);
//                    postsContainer.appendChild(article);
//                });
//            })
//            .catch(error => console.error('Error loading posts:', error));
//    }
//});

document.addEventListener("DOMContentLoaded", function () {
    console.log("Blog loaded");

    // Load posts dynamically
    const postsContainer = document.getElementById('posts');
    if (postsContainer) {
        fetch('/api/posts')
            .then(response => response.json())
            .then(data => {
                data.forEach(post => {
                    const article = document.createElement('article');
                    const title = document.createElement('h3');
                    const content = document.createElement('p');
                    const readMore = document.createElement('a');

                    title.textContent = post.title;
                    content.textContent = post.content;
                    readMore.textContent = 'Read more';
                    readMore.href = `/posts/${post.id}`;

                    article.appendChild(title);
                    article.appendChild(content);
                    article.appendChild(readMore);
                    postsContainer.appendChild(article);
                });
            })
            .catch(error => console.error('Error loading posts:', error));
    }
});
