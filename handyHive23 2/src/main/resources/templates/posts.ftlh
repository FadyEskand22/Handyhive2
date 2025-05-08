<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Post</title>
    <link rel="stylesheet" href="/profile.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>

<nav class="navbar">
    <div class="nav-left">
        <a href="/index" class="logo-link">
            <img src="/logo.png" alt="HandyHive Logo" class="logo">
        </a>
    </div>
    <div class="nav-right">
        <a href="/index" style="color: #000;">Home</a>
        <a href="/posts" style="color: #000;">Create Post</a>
        <a href="/provider/profile" style="color: #000;">Profile</a>
        <a href="/provider/statistics" style="color: #000;">Statistics</a>
        <form method="post" action="/logout" style="display:inline;">
            <button type="submit" class="nav-button">Logout</button>
        </form>
    </div>
</nav>

<div class="container" style="padding: 2rem; color: #FFb700;">
    <h2>Create a New Post</h2>
    <form method="post" action="/services/${provider.id}" enctype="multipart/form-data" style="margin-top: 1rem;">
        <div class="form-group">
            <label>Title</label>
            <input type="text" name="title" required>
        </div>
        <div class="form-group">
            <label>Description</label>
            <textarea name="description" rows="4" required></textarea>
        </div>
        <div class="form-group">
            <label>Price</label>
            <input type="number" name="price" step="0.01" required>
        </div>
        <div class="form-group">
            <label>Availability</label>
            <input type="text" name="availability" required>
        </div>
        <div class="form-group">
            <label>Upload Image</label>
            <input type="file" name="image" accept="image/*">
        </div>
        <button type="submit" class="save-button">Post</button>
    </form>
    <img id="imagePreview" src="#" alt="Preview" style="display:none; max-width: 100%; margin-top: 1rem; border-radius: 10px;">

    <!-- Post Listing -->
    <h3 style="margin-top: 3rem;">Your Posts</h3>
    <div class="post-list">
        <#if posts?? && (posts?size > 0)>
            <#list posts as post>
                <div class="post-card" style="border: 1px solid #FFb700; padding: 1rem; margin-top: 1rem; border-radius: 12px; background-color: rgb(237, 237, 237); color: #FFD700;">
                    <h4>${post.title}</h4>
                    <p>${post.description}</p>
                    <p><strong>Price:</strong> $${post.price}</p>
                    <p><strong>Availability:</strong> ${post.availability}</p>
                    
<#if post.imagePath??>
    <img src="${post.imagePath}" alt="Post Image" style="max-width: 100%; border-radius: 10px; margin-top: 0.5rem;">
</#if>


                    
                    <form method="post" action="/services/delete/${post.id}" style="margin-top: 0.5rem;">
                        <button type="submit" class="save-button" style="background-color: crimson; border: none;">Delete</button>
                    </form>
                </div>
            </#list>
        <#else>
            <p>No posts yet.</p>
        </#if>
    </div>
</div>

<script>
    const imageInput = document.querySelector('input[name="image"]');
    const preview = document.getElementById("imagePreview");

    imageInput.addEventListener("change", function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                preview.src = e.target.result;
                preview.style.display = "block";
            };
            reader.readAsDataURL(file);
        } else {
            preview.src = "#";
            preview.style.display = "none";
        }
    });
</script>

</body>
</html>
