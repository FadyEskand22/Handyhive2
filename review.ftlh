<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Leave a Review</title>
    <link rel="stylesheet" href="/review.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>

<!-- Navigation Bar -->
<nav class="navbar">
    <div class="nav-left">
        <a href="/" class="logo-link">
            <img src="/logo.png" alt="HandyHive Logo" class="logo">
        </a>
    </div>
    <div class="nav-right">
        <a href="#">Bookings</a>
        <a href="/services/find-services">Services</a>
        <a href="/customers/profile/${customer.id}" class="active">Profile</a>
        <form method="post" action="/customers/logout" style="display:inline;">
            <button type="submit" class="logout-button" style="background:none;border:none;color:inherit;cursor:pointer;">Logout</button>
        </form>
    </div>
</nav>

<!-- Review Container -->
<div class="container profile-container">
    <div class="profile-header">
        <div class="profile-info">
            <h1>
                Leave a Review for
                <#if provider?? && provider.name??>
                    ${provider.name}
                <#else>
                    Provider
                </#if>
            </h1>
            <p class="subtitle">${provider.email!''}</p>
            <p class="member-since"><i class="fas fa-map-marker-alt"></i> ${provider.address!''}</p>
            <p class="member-since"><i class="fas fa-phone"></i> ${provider.phone!''}</p>
            <p class="member-since"><i class="fas fa-briefcase"></i> ${provider.expertise!''}</p>
        </div>
    </div>

    <!-- Review Form Section -->
    <div class="profile-section">
        <h2>Write Your Review</h2>
        <form class="profile-form" action="/reviews/submit-review" method="post">
            <#if provider?? && provider.id??>
                <input type="hidden" name="providerId" value="${provider.id}">
            </#if>

            <div class="form-group">
                <label for="rating">Rating (1 to 5)</label>
                <input type="number" id="rating" name="rating" min="1" max="5" required>
            </div>

            <div class="form-group">
                <label for="comment">Your Review</label>
                <textarea id="comment" name="comment" rows="4" required></textarea>
            </div>

            <button type="submit" class="save-button">Submit Review</button>
        </form>
    </div>
</div>

</body>
</html>
