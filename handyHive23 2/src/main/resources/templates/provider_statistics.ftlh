<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>HandyHive | Provider Statistics</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f5f5f5;
            margin: 0;
        }
        .navbar {
            background-color: #ffb300;
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .nav-left img {
            height: 40px;
        }
        .nav-right a {
            margin-left: 1rem;
            text-decoration: none;
            color: #333;
            font-weight: 500;
        }
        .stats-container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1rem;
        }
        .stats-header {
            background: white;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 2rem;
        }
        .stats-header h1 {
            color: #333;
            margin-bottom: 1rem;
        }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }
        .stat-card {
            background: white;
            padding: 1.5rem;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
            transition: transform 0.3s;
        }
        .stat-card:hover {
            transform: translateY(-5px);
        }
        .stat-icon {
            font-size: 2rem;
            color: #FFB300;
            margin-bottom: 1rem;
        }
        .stat-value {
            font-size: 2.5rem;
            font-weight: 600;
            color: #333;
        }
        .stat-label {
            color: #666;
            font-size: 1rem;
        }
        .chart-section {
            background: white;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 2rem;
        }
        .chart-section h2 {
            color: #333;
            margin-bottom: 1.5rem;
        }
        .engagement-item {
            display: flex;
            align-items: center;
            padding: 1rem;
            border-bottom: 1px solid #eee;
        }
        .engagement-icon {
            font-size: 1.5rem;
            margin-right: 1rem;
            color: #FFB800;
        }
        .engagement-title {
            font-weight: 500;
            color: #333;
        }
        .engagement-meta {
            font-size: 0.875rem;
            color: #666;
        }
        .reviews-scrollable {
            max-height: 300px;
            overflow-y: auto;
            margin-top: 1rem;
            border-top: 1px solid #ddd;
        }
        .review-entry {
            padding: 1rem 0;
            border-bottom: 1px solid #eee;
        }
        .review-entry:last-child {
            border-bottom: none;
        }
        .review-rating {
            color: #f39c12;
            font-weight: 600;
        }
    </style>
</head>
<body>

<!-- Navigation -->
<nav class="navbar">
    <div class="nav-left">
        <a href="/index"><img src="/logo.png" alt="Logo" class="logo"></a>
    </div>
    <div class="nav-right">
        <a href="/index">Home</a>
        <a href="/posts">Create Post</a>
        <a href="/provider/profile">Profile</a>
        <a href="/providers/statistics" class="active">Statistics</a>
        <form method="post" action="/logout" style="display:inline;">
            <button type="submit" class="nav-button" style="background:none; border:none; font-weight:500; color:#333;">Logout</button>
        </form>
    </div>
</nav>

<!-- Statistics Content -->
<div class="stats-container">
    <div class="stats-header">
        <h1><i class="fas fa-chart-line"></i> Business Statistics</h1>
        <p>Track your business performance and customer engagement</p>
    </div>

    <form method="get" action="/provider/statistics/pdf">
        <button type="submit" style="
            padding: 0.6rem 1.2rem;
            background-color: #ffb300;
            border: none;
            border-radius: 8px;
            font-weight: 600;
            font-size: 1rem;
            color: #fff;
            cursor: pointer;
            margin-bottom: 1.5rem;
        ">
            <i class="fas fa-file-pdf"></i> Convert to PDF
        </button>
    </form>

    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-icon"><i class="fas fa-bookmark"></i></div>
            <div class="stat-value">${savedCount}</div>
            <div class="stat-label">Saved by Customers</div>
        </div>
        <div class="stat-card">
            <div class="stat-icon"><i class="fas fa-comment"></i></div>
            <div class="stat-value">${commentCount}</div>
            <div class="stat-label">Total Comments</div>
        </div>
        <div class="stat-card">
            <div class="stat-icon"><i class="fas fa-image"></i></div>
            <div class="stat-value">${postCount}</div>
            <div class="stat-label">Total Posts</div>
        </div>
    </div>

    <!-- Engagement Section -->
    <div class="chart-section">
        <h2><i class="fas fa-comments"></i> Recent Engagement</h2>
       <#if recentEngagement?? && recentEngagement?size gt 0>
            <#list recentEngagement as item>
                <div class="engagement-item">
                    <div class="engagement-icon"><i class="fas fa-comment"></i></div>
                    <div>
                        <div class="engagement-title">${item.username}</div>
                        <div class="engagement-meta">${item.text} <br><small>${item.date}</small></div>
                    </div>
                </div>
            </#list>
        <#else>
            <p style="text-align:center; color:#666;">No recent engagement</p>
        </#if>
    </div>

    <!-- Reviews Section -->
<div class="chart-section">
    <h2><i class="fas fa-star"></i> Reviews</h2>
    
    <p>
        Average Rating: 
        <#if averageRating?? && averageRating != "N/A">
            ${averageRating} / 5.0
        <#else>
            <span style="color: gray;">N/A</span>
        </#if>
    </p>
    
    <p style="color: blue;">
        Review Count: 
        <#if allReviews??>
            ${allReviews?size}
        <#else>
            0
        </#if>
    </p>

    <div class="reviews-scrollable">
        <#if allReviews?? && allReviews?size gt 0>
            <#list allReviews as review>
                <div class="review-entry">
                    <p class="engagement-title">${review.customerName}</p>
                    <p class="review-rating">Rating: ${review.rating} / 5</p>
                    <p class="engagement-meta">${review.comment}</p>
                </div>
            </#list>
        <#else>
            <p style="text-align:center; color:#666;">No reviews yet.</p>
        </#if>
    </div>
</div>

<!-- Bookings Section -->
<div class="chart-section">
    <h2><i class="fas fa-calendar-check"></i> Bookings</h2>
    
    <#if bookings?? && bookings?size gt 0>
        <div class="reviews-scrollable">
            <#list bookings as booking>
                <div class="review-entry">
                    <p class="engagement-title">${booking.customer.name}</p>
                    <p class="engagement-meta">
                        Date: ${booking.appointmentDate} <br>
                        Time: ${booking.appointmentTime}
                    </p>
                </div>
            </#list>
        </div>
    <#else>
        <p style="text-align:center; color:#666;">No bookings yet.</p>
    </#if>
</div>


</div>

</body>
</html>
