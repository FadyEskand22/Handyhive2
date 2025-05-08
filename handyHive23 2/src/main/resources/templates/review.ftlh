<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Leave a Review</title>
    <link rel="stylesheet" href="/findServices.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .review-container {
            max-width: 800px;
            margin: 3rem auto;
            padding: 2rem;
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
        }
        .review-container h1 {
            text-align: center;
            margin-bottom: 1.5rem;
        }
        .review-form {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }
        .review-form label {
            font-weight: 600;
        }
        .review-form input, .review-form textarea {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 14px;
        }
        .review-form button {
            padding: 12px;
            background-color: #ffb300;
            color: white;
            font-weight: bold;
            border: none;
            border-radius: 8px;
            cursor: pointer;
        }
        .review-form button:hover {
            background-color: #e0a800;
        }
    </style>
</head>
<body>

<!-- Navigation Bar -->
<nav class="navbar">
    <div class="nav-left">
        <a href="/index"><img src="/logo.png" class="logo" alt="Logo" style="height: 40px;"></a>
    </div>
    <div class="nav-right">
        <a href="/index">Home</a>
        <a href="/bookings/all">Bookings</a>
        <a href="/services/find-services">Services</a>
        <a href="/customers/profile/${customer.id}">Profile</a>
        <form method="post" action="/customers/logout" style="display:inline;">
            <button type="submit" class="nav-button" style="background-color: rgb(214, 40, 14); border: none; color: #fff; padding: 0.5rem 1rem; border-radius: 6px;">Logout</button>
        </form>
    </div>
</nav>

<!-- Review Section -->
<div class="review-container">
    <h1>Leave a Review for ${provider.businessName!}</h1>
    <form action="/reviews/submit-review" method="post" class="review-form">
        <input type="hidden" name="providerId" value="${provider.id}">
        <input type="hidden" name="serviceId" value="${service.id}">
        <label for="rating">Rating (1–5):</label>
        <input type="number" name="rating" id="rating" min="1" max="5" required>

        <label for="comment">Your Review:</label>
        <textarea name="comment" id="comment" rows="4" required></textarea>

        <button type="submit">Submit Review</button>
    </form>
</div>

</body>
</html>
