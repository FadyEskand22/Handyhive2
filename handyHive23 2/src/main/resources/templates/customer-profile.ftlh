<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customer Profile</title>
    <link rel="stylesheet" href="/profile.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
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
        <#if provider??>
            <a href="/index" style="color: #000; margin-left: 1rem;">Home</a>
            <a href="/posts" style="color: #000; margin-left: 1rem;">Create Post</a>
            <a href="/provider/statistics" style="color: #000; margin-left: 1rem;">Statistics</a>
            <a href="/provider/profile" style="color: #000; margin-left: 1rem;">Profile</a>
            <form method="post" action="/logout" style="display: inline;">
                <button type="submit" class="nav-button" style="background-color: rgb(214, 40, 14); border: none; padding: 0.5rem 1rem; border-radius: 6px; cursor: pointer; color: #fff; margin-left: 1rem;">Logout</button>
            </form>
        <#elseif customer??>
            <a href="/index" style="color: #000; margin-left: 1rem;">Home</a>
            <a href="/bookings/all" style="color: #000; margin-left: 1rem;">Bookings</a>
            <a href="/services/find-services" style="color: #000; margin-left: 1rem;">Services</a>
            <a href="/customers/profile/${customer.id}" style="color: #000; margin-left: 1rem;">Profile</a>
            <form method="post" action="/customers/logout" style="display: inline;">
                <button type="submit" class="nav-button" style="background-color: rgb(214, 40, 14); border: none; padding: 0.5rem 1rem; border-radius: 6px; cursor: pointer; color: #fff; margin-left: 1rem;">Logout</button>
            </form>
        </#if>
    </div>
</nav>

<!-- Profile Section -->
<div class="container profile-container">
    <div class="profile-header">
        <div class="profile-picture-container">
            <img id="profilePicturePreview" src="${customer.profilePicture!'/default-profile.jpg'}?t=${.now?long}"
                 alt="Profile Picture" class="profile-picture" data-customer-id="${customer.id}">
            <label for="profileImageInput" class="upload-button">
                <i class="fas fa-camera"></i> Change Picture
                <input id="profileImageInput" type="file" accept="image/*" style="display: none;">
            </label>
        </div>

        <div class="profile-info-wrapper" style="display: flex; justify-content: space-between; align-items: flex-start; width: 100%;">
            <div class="profile-info">
                <h1>${customer.name}</h1>
                <p class="subtitle">${customer.email}</p>
                <p class="member-since"><i class="fas fa-map-marker-alt"></i> ${customer.address}</p>
                <p class="member-since"><i class="fas fa-phone"></i> ${customer.phone}</p>
            </div>
            <form method="post" action="/customers/delete/${customer.id}" onsubmit="return confirm('Are you sure you want to delete your account? This action cannot be undone.');">
                <button type="submit" class="delete-button" style="background-color:#e74c3c;color:white;padding:10px 15px;border:none;border-radius:5px;cursor:pointer;">
                    <i class="fas fa-user-times"></i> Delete Account
                </button>
            </form>
        </div>
    </div>

    <div class="profile-section">
        <h2>Edit Your Information</h2>
        <form method="post" action="/customers/update/${customer.id}" class="profile-form">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" value="${customer.name}">
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="${customer.email}">
            </div>
            <div class="form-group">
                <label for="phone">Phone</label>
                <input type="text" id="phone" name="phone" value="${customer.phone}">
            </div>
            <div class="form-group">
                <label for="address">Address</label>
                <input type="text" id="address" name="address" value="${customer.address}">
            </div>
            <button type="submit" class="save-button">Save Changes</button>
        </form>
    </div>

    <div class="profile-section">
        <h2>Saved Providers</h2>
        <div class="providers-grid">
            <#if savedProviders?? && (savedProviders?size > 0)>
                <#list savedProviders as provider>
                    <div class="provider-card" style="border: 1px solid #ddd; padding: 1rem; border-radius: 8px; margin-bottom: 1.5rem; background-color: #f9f9f9;">
                        <h2 style="margin-bottom: 0.5rem; color: #222;">${provider.businessName!'Business Name'}</h2>
                        <p><strong>Name:</strong> ${provider.name!'N/A'}</p>
                        <p><strong>Email:</strong> ${provider.email!'N/A'}</p>
                        <p><strong>Phone:</strong> ${provider.phone!'N/A'}</p>
                        <p><strong>Availability:</strong> ${provider.availability!'Not provided'}</p>
                        <p><strong>Expertise:</strong> ${provider.expertise!'N/A'}</p>
                        <p><strong>Pricing:</strong> ${provider.pricingDetails!'N/A'}</p>
                    </div>
                    <hr style="border: none; border-top: 1px solid #ccc; margin: 1rem 0;">
                </#list>
            <#else>
                <p style="margin-top:2rem;">No saved providers yet.</p>
            </#if>
        </div>
    </div>
</div>

<script>
    const input = document.getElementById("profileImageInput");
    input.addEventListener("change", function () {
        const file = this.files[0];
        if (!file) return;

        const customerId = document.getElementById("profilePicturePreview").dataset.customerId;
        const formData = new FormData();
        formData.append("image", file);

        fetch(`/customers/upload-profile-picture/${customerId}`, {
            method: "POST",
            body: formData
        })
        .then(res => {
            if (!res.ok) throw new Error("Upload failed");
            return res.text();
        })
        .then(path => {
            document.getElementById("profilePicturePreview").src = path + `?t=${Date.now()}`;
        })
        .catch(err => {
            alert("Failed to upload image.");
            console.error(err);
        });
    });
</script>

</body>
</html>
