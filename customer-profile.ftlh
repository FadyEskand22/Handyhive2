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
<nav class="navbar">
    <div class="nav-left">
        <a href="/" class="logo-link">
            <img src="/logo.png" alt="HandyHive Logo" class="logo">
        </a>
    </div>
    <div class="nav-right">
        <a href="#">Bookings</a>
        <a href="/services/find-services">Services</a>
        <form method="post" action="/customers/logout" style="display:inline;">
            <button type="submit" class="logout-button" style="background:none;border:none;color:inherit;cursor:pointer;">Logout</button>
        </form>
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

        <!-- User Info + Delete Button wrapper -->
        <div class="profile-info-wrapper" style="display: flex; justify-content: space-between; align-items: flex-start; width: 100%;">
            <div class="profile-info">
                <h1>${customer.name}</h1>
                <p class="subtitle">${customer.email}</p>
                <p class="member-since"><i class="fas fa-map-marker-alt"></i> ${customer.address}</p>
                <p class="member-since"><i class="fas fa-phone"></i> ${customer.phone}</p>
            </div>
            <!-- Delete Account Button -->
            <form method="post" action="/customers/delete/${customer.id}" onsubmit="return confirm('Are you sure you want to delete your account? This action cannot be undone.');">
                <button type="submit" class="delete-button" style="background-color:#e74c3c;color:white;padding:10px 15px;border:none;border-radius:5px;cursor:pointer;">
                    <i class="fas fa-user-times"></i> Delete Account
                </button>
            </form>
        </div>
    </div>

    <!-- Profile Form Section -->
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
