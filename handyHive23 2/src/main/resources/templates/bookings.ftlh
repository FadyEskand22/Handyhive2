<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Bookings | HandyHive</title>
    <link rel="stylesheet" href="/findServices.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>

<!-- NavBar (reuse consistent layout) -->
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

<!-- Booking List Section -->
<div class="container">
    <h1 style="margin-top: 2rem;">📅 Your Bookings</h1>
    <#if bookings?size == 0>
        <p style="color: #666;">You haven't made any bookings yet.</p>
    <#else>
        <div class="providers-grid">
            <#list bookings as booking>
                <div class="provider-card">
                    <div class="provider-info">
                        <p><strong>Provider:</strong> ${booking.provider.businessName}</p>
                        <p><i class="fas fa-user"></i> ${booking.provider.name}</p>
                        <p><i class="fas fa-calendar"></i> ${booking.appointmentDate}</p>
                        <p><i class="fas fa-clock"></i> ${booking.appointmentTime}</p>
                        <p><i class="fas fa-info-circle"></i> Status: ${booking.status}</p>
                    </div>
                    <form method="post" action="/bookings/delete/${booking.bookingId}" style="margin-top: 10px;">
                        <button type="submit" style="background-color: crimson; color: white; border: none; padding: 8px 16px; border-radius: 6px; cursor: pointer;">
                            Cancel Booking
                        </button>
                    </form>
                </div>
            </#list>
        </div>
    </#if>
</div>

</body>
</html>
