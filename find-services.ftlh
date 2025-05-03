<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>HandyHive | Find Services</title>
    <link rel="stylesheet" href="/findServices.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>

<!-- Navigation Bar -->
<nav class="navbar">
    <div class="nav-left">
        <a href="/dashboard" class="logo-link">
            <img src="/logo.png" alt="HandyHive Logo" class="logo">
        </a>
    </div>
    <div class="nav-right">
        <a href="#">Bookings</a>
        <a href="/customers/profile/${customer.id}">Profile</a>
        <form method="post" action="/customers/logout" style="display:inline;">
            <button type="submit" class="logout-button">Logout</button>
        </form>
    </div>
</nav>

<!-- Search and Filter Section -->
<div class="container">
    <div class="search-section">
        <h1><i class="fas fa-search"></i> Find Services</h1>
        <div class="search-bar">
            <input type="text" class="search-input" placeholder="Search by service provider name or business name...">
        </div>
        <div class="filter-section">
            <a href="/services/find-services" class="filter-button active">All Services</a>
            <a href="/services/find-services?search=Plumber" class="filter-button">Plumbers</a>
            <a href="/services/find-services?search=Electrician" class="filter-button">Electricians</a>
            <a href="/services/find-services?search=Carpenter" class="filter-button">Carpenters</a>
            <a href="/services/find-services?search=Painter" class="filter-button">Painters</a>
            <a href="/services/find-services?search=Gardener" class="filter-button">Gardeners</a>
        </div>

    </div>

    <div class="providers-grid">
        <#if services?size == 0>
            <div class="no-results">No services found.</div>
        <#else>
            <#list services as service>
                <div class="provider-card">
                    <div class="provider-header">
                        <img src="${service.imagePath!'/default-profile.jpg'}" alt="Provider" />
                        <h2>${service.provider.name}</h2>
                        <p><i class="fas fa-tools"></i> ${service.title}</p>
                    </div>
                    <div class="provider-info">
                        <p><i class="fas fa-envelope"></i> ${service.provider.email}</p>
                        <p><i class="fas fa-phone"></i> ${service.provider.phone}</p>
                        <p><i class="fas fa-map-marker-alt"></i> ${service.provider.location!'N/A'}</p>
                        <p><i class="fas fa-business-time"></i> ${service.availability}</p>
                    </div>
                    <div class="provider-actions">
                        <button class="view-profile-btn" onclick="location.href='/provider/profile/${service.provider.id}'">
                            <i class="fas fa-user"></i> View Profile
                        </button>
                        </button>
                        <button class="view-profile-btn" onclick="location.href='/reviews/service/${service.id}'">
                            <i class="fas fa-star"></i> Leave Review
                        </button>
                    </div>
                </div>
            </#list>
        </#if>
    </div>
</div>

</body>
</html>
