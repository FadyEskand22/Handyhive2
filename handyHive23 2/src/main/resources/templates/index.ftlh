<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home - HandyHive</title>
    <link rel="stylesheet" href="/profile.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #ffffff;
            color: #333;
            font-family: 'Inter', sans-serif;
        }
        .feed {
            max-width: 700px;
            margin: 3rem auto;
        }
        .post-card {
            background-color: #f9f9f9;
            border: 1px solid #ccc;
            border-radius: 12px;
            padding: 1.5rem;
            margin-bottom: 2rem;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
        }
        .profile-pic {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            object-fit: cover;
            margin-right: 1rem;
        }
        .provider-info {
            display: flex;
            align-items: center;
            margin-bottom: 1rem;
        }
        .post-image {
            width: 100%;
            border-radius: 10px;
            margin-bottom: 1rem;
        }
        .interaction-bar {
            display: flex;
            align-items: center;
            gap: 1rem;
            margin-top: 1rem;
        }
        .interaction-bar i {
            cursor: pointer;
            font-size: 1.2rem;
            transition: color 0.3s ease;
        }
        .interaction-bar i:hover {
            color: #ff2e2e;
        }
        .comment-form {
            margin-top: 1rem;
        }
        .comment-form textarea {
            width: 100%;
            padding: 0.5rem;
            border-radius: 8px;
            border: 1px solid #ccc;
        }
        .comment-form button {
            margin-top: 0.5rem;
            background-color: #ffb700;
            border: none;
            padding: 0.4rem 0.8rem;
            border-radius: 6px;
            font-weight: 500;
            cursor: pointer;
        }
        .comment {
            margin-top: 0.5rem;
            border-top: 1px solid #eee;
            padding-top: 0.5rem;
        }
        .comment-user {
            font-weight: 600;
            margin-right: 0.5rem;
        }
        nav.navbar {
            background-color: #ffb700;
            color: #000000;
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        nav .nav-right a, .nav-button {
            color: #fff;
            margin-left: 1rem;
            text-decoration: none;
        }
        nav .nav-button {
            background-color:rgb(214, 40, 14);
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 6px;
            cursor: pointer;
        }
        nav .logo {
            height: 40px;
        }
    </style>
</head>
<body>

<!-- Navigation Bar -->
<nav class="navbar" style="background-color: #ffb300; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center;">
    <div class="nav-left">
        <a href="/index">
            <img src="/logo.png" alt="HandyHive Logo" class="logo" style="height: 40px;">
        </a>
    </div>

    <div class="nav-right" style="display: flex; align-items: center;">
        <#-- Provider Links -->
        <#if provider??>
            <a href="/index" style="color: #000; margin-left: 1rem; text-decoration: none;">Home</a>
            <a href="/posts" style="color: #000; margin-left: 1rem; text-decoration: none;">Create Post</a>
            <a href="/provider/statistics" style="color: #000; margin-left: 1rem; text-decoration: none;">Statistics</a>
            <a href="/provider/profile" style="color: #000; margin-left: 1rem; text-decoration: none;">Profile</a>
            <form method="post" action="/logout" style="display: inline;">
                <button type="submit" class="nav-button" style="background-color: rgb(214, 40, 14); border: none; padding: 0.5rem 1rem; border-radius: 6px; cursor: pointer; color: #fff; margin-left: 1rem;">Logout</button>
            </form>

        <#-- Customer Links -->
        <#elseif customer??>
            <a href="/index" style="color: #000; margin-left: 1rem; text-decoration: none;">Home</a>
            <a href="/bookings/all" style="color: #000; margin-left: 1rem; text-decoration: none;">Bookings</a>
            <a href="/services/find-services" style="color: #000; margin-left: 1rem; text-decoration: none;">Services</a>
            <a href="/customers/profile/${customer.id}" style="color: #000; margin-left: 1rem; text-decoration: none;">Profile</a>
            <form method="post" action="/customers/logout" style="display: inline;">
                <button type="submit" class="nav-button" style="background-color: rgb(214, 40, 14); border: none; padding: 0.5rem 1rem; border-radius: 6px; cursor: pointer; color: #fff; margin-left: 1rem;">Logout</button>
            </form>

        <#-- Default fallback (optional) -->
        <#else>
            <a href="/login" style="color: #000; margin-left: 1rem; text-decoration: none;">Login</a>
            <a href="/register" style="color: #000; margin-left: 1rem; text-decoration: none;">Register</a>
        </#if>
    </div>
</nav>



<!-- Feed -->
<div class="feed">
    <h1 style="text-align:center; color: #2b2d42;">Explore HandyHive</h1>

    <#list posts as post>
        <div class="post-card" data-post-id="${post.id}">
            <div class="provider-info">
                <#if post.provider.profilePicture?? && post.provider.profilePicture?has_content>
                    <img src="${post.provider.profilePicture}" alt="Profile" class="profile-pic">
                <#else>
                    <img src="/default-profile.jpg" alt="Default Profile" class="profile-pic">
                </#if>
                <span style="font-weight: 600; font-size: 1.1rem;">${post.provider.businessName}</span>
            </div>

            <#if post.imagePath?? && post.imagePath?has_content>
                <img src="${post.imagePath}" alt="Post Image" class="post-image">
            </#if>

            <div>
                <h3>${post.title}</h3>
                <p><strong>Price:</strong> $${post.price}</p>
                <p><strong>Availability:</strong> ${post.availability}</p>
                <p>${post.description}</p>
            </div>

            <!-- Like + Comment Count -->
            <div class="interaction-bar">
                <i class="fas fa-comment"></i>
                <span>${post.comments}</span> comments
            </div>

            <!-- Comment Form -->
            <form class="comment-form" method="post" action="/services/comment/${post.id!}">
                <textarea name="text" rows="2" placeholder="Add a comment..." required></textarea>
                <button type="submit">Comment</button>
            </form>

            <#if post.recentComments?? && (post.recentComments?size > 0)>
    <#list post.recentComments as c>
        <div class="comment">
            <span class="comment-user">${c.username}:</span> ${c.text}
            <small style="color: #666; display: block;">${c.date}</small>
        </div>
    </#list>
</#if>

        </div>
    </#list>
</div>

<!-- Like Button Script -->
<script>
document.querySelectorAll(".like-button").forEach(icon => {
    const postCard = icon.closest(".post-card");
    const postId = postCard.getAttribute("data-post-id");
    const countEl = icon.nextElementSibling;

    fetch(`/services/posts/${postId}/like`)
    .then(res => res.json())
    .then(data => {
        if (data && data.liked) {
            icon.classList.remove("far");
            icon.classList.add("fas");
        }
        countEl.textContent = data.totalLikes;
    });

    icon.addEventListener("click", async () => {
        const res = await fetch(`/services/posts/${postId}/like`, { method: 'POST' });
        const result = await res.json();
        const liked = result.liked;
        const totalLikes = result.totalLikes;

        if (liked) {
            icon.classList.remove("far");
            icon.classList.add("fas");
        } else {
            icon.classList.remove("fas");
            icon.classList.add("far");
        }
        countEl.textContent = totalLikes;
    });
});
</script>


</body>
</html>
