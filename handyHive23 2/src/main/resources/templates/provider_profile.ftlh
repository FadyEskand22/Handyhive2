<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Provider Profile</title>
    <link rel="stylesheet" href="/profile.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>

<!-- Navigation Bar -->
<nav class="navbar">
    <div class="nav-left">
        <a href="/index" class="logo-link">
            <img src="/logo.png" alt="HandyHive Logo" class="logo">
        </a>
    </div>
    <div class="nav-right">
        <a href="/index">Home</a>
        <a href="/posts">Create Post</a>
        <a href="/provider/profile">Profile</a>
        <a href="/provider/statistics">Statistics</a>
        <form method="post" action="/logout" style="display:inline;">
            <button type="submit" class="nav-button">Logout</button>
        </form>
    </div>
</nav>


<!-- Profile Section -->
<div class="container profile-container">
    <div class="profile-header">
        <div class="profile-picture-container">
            <img id="profilePicturePreview"
     src="${provider.profilePicture!'/default-profile.jpg'}?t=${.now?long}"
     class="profile-picture"
     data-provider-id="${provider.id}">
            <label for="profileImageInput" class="upload-button">
                <i class="fas fa-camera"></i> Change Picture
                <input id="profileImageInput" type="file" accept="image/*" style="display: none;">
            </label>
        </div>
        <div class="profile-info">
            <h1>${provider.businessName}</h1>
            <p class="subtitle">${provider.name}</p>
            <p class="member-since"><i class="fas fa-envelope"></i> ${provider.email}</p>
            <p class="member-since"><i class="fas fa-phone"></i> ${provider.phone}</p>
            <p class="member-since"><i class="fas fa-briefcase"></i> ${provider.expertise}</p>
            <p class="member-since"><i class="fas fa-dollar-sign"></i> ${provider.pricingDetails}</p>
        </div>
    </div>

    <!-- Layout -->
    <div class="profile-content">
        <div class="profile-section">
            <h2>Edit Information</h2>
            <form method="post" action="/providers/update/${provider.id}" class="profile-form">
                <div class="form-group"><label>Business Name</label><input type="text" name="businessName" value="${provider.businessName}"></div>
                <div class="form-group"><label>Full Name</label><input type="text" name="name" value="${provider.name}"></div>
                <div class="form-group"><label>Email</label><input type="email" name="email" value="${provider.email}"></div>
                <div class="form-group"><label>Phone</label><input type="text" name="phone" value="${provider.phone}"></div>
                <div class="form-group"><label>Address</label><input type="text" name="address" value="${provider.address}"></div>
                <div class="form-group"><label>Expertise</label><input type="text" name="expertise" value="${provider.expertise}"></div>
                <div class="form-group"><label>Pricing Details</label><input type="text" name="pricingDetails" value="${provider.pricingDetails}"></div>
                <div class="form-group"><label>Availability</label><input type="text" name="availability" value="${provider.availability}"></div>
                <button type="submit" class="save-button">Save Changes</button>
            </form>
        </div>

        <div class="profile-section">
            <div class="availability-header">
                <h2>Business Hours</h2>
            </div>
            <form method="post" action="/providers/update-availability/${provider.id}" class="profile-form business-hours">
                <#assign days = {
                    "sun": "Sunday",
                    "mon": "Monday",
                    "tue": "Tuesday",
                    "wed": "Wednesday",
                    "thu": "Thursday",
                    "fri": "Friday",
                    "sat": "Saturday"
                }>
                <#list days?keys as key>
                    <div class="day-row">
                        <span>${days[key]}</span>
                        <input type="text" name="${key}Start" placeholder="Start time" value="${provider[key + 'Start']!}">
                        <span>to</span>
                        <input type="text" name="${key}End" placeholder="End time" value="${provider[key + 'End']!}">
                        <#if key == "mon">
                            <button type="button" onclick="copyMondayToAll()" class="copy-icon-button" title="Copy Monday to All">
                                <i class="fas fa-copy"></i>
                            </button>
                        </#if>
                    </div>
                </#list>
                <button type="submit" class="save-button">Save Availability</button>
            </form>
        </div>
    </div>
</div>
<div class="profile-section" style="margin-top: 3rem; text-align: center;">
    <form method="post" action="/providers/delete/${provider.id}" onsubmit="return confirm('Are you sure you want to delete your profile? This action cannot be undone.');">
        <button type="submit" class="save-button" style="background-color: crimson; color: white; border: none; padding: 1rem 2rem; border-radius: 10px; font-weight: bold;">
            Delete Profile
        </button>
    </form>
</div>


<script>
    function logout() {
        fetch("/logout", { method: "POST" })
            .then(() => window.location.href = "/login")
            .catch(() => window.location.href = "/login");
    }

    const input = document.getElementById("profileImageInput");
input.addEventListener("change", function () {
    const file = this.files[0];
if (!file) return;

const providerId = document.getElementById("profilePicturePreview").dataset.providerId;
const formData = new FormData();
formData.append("image", file);

fetch(`/providers/upload-profile-picture/${providerId}`, {
    method: "POST",
    body: formData
})
.then(res => {
    if (!res.ok) throw new Error("Upload failed");
    return res.text();
})
.then(path => {
    console.log("✅ Uploaded Path:", path);
    document.getElementById("profilePicturePreview").src = path + `?t=${Date.now()}`;
})
.catch(err => {
    alert("Failed to upload image.");
    console.error(err);
});
});



    function copyMondayToAll() {
        const monStart = document.querySelector("input[name='monStart']").value;
        const monEnd = document.querySelector("input[name='monEnd']").value;
        const days = ["sun", "tue", "wed", "thu", "fri", "sat"];
        days.forEach(day => {
            document.querySelector(`input[name='${day}Start']`).value = monStart;
            document.querySelector(`input[name='${day}End']`).value = monEnd;
        });
    }
</script>

</body>
</html>
