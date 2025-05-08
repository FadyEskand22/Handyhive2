<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>HandyHive | Find Services</title>
    <link rel="stylesheet" href="/findServices.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .modal-overlay {
            display: none;
            position: fixed;
            top: 0; left: 0;
            width: 100%; height: 100%;
            background: rgba(0,0,0,0.4);
            justify-content: center;
            align-items: center;
            z-index: 999;
        }
        .modal-content {
            background: #fff;
            border-radius: 10px;
            padding: 2rem;
            max-width: 600px;
            width: 90%;
            text-align: center;
        }
        .modal-content h2 { margin-bottom: 0.5rem; }
        .modal-content p { margin: 0.2rem 0; }
        .close-btn {
            margin-top: 1rem;
            background-color: #e74c3c;
            color: white;
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        .booking-form {
            margin-top: 1.5rem;
            display: flex;
            flex-direction: column;
            gap: 0.75rem;
        }
        .booking-form input, .booking-form select {
            padding: 0.5rem;
            border-radius: 6px;
            border: 1px solid #ccc;
        }
        .booking-form button {
            margin-top: 0.5rem;
            padding: 0.6rem;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 6px;
            font-weight: 600;
            cursor: pointer;
        }
    </style>
</head>
<body>

<!-- Navigation -->
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

<!-- Search -->
<div class="container">
    <div class="search-section">
        <h1><i class="fas fa-search"></i> Find Services</h1>
        <form action="/services/find-services" method="get" class="search-bar" style="width: 100%;">
            <input type="text"
                   name="search"
                   class="search-input"
                   placeholder="Search by service type, provider name, or business..."
                   value="${search!}" />
        </form>

        <div class="filter-section">
            <a href="/services/find-services" class="filter-button active">All Services</a>
            <a href="/services/find-services?search=Plumbing" class="filter-button">Plumbing</a>
            <a href="/services/find-services?search=Electrical" class="filter-button">Electrical</a>
            <a href="/services/find-services?search=Landscaping" class="filter-button">Landscaping</a>
            <a href="/services/find-services?search=Cleaning" class="filter-button">Cleaning</a>
            <a href="/services/find-services?search=Painting" class="filter-button">Painting</a>
        </div>
    </div>

    <div class="providers-grid">
        <#if providers?size == 0>
            <div class="no-results">No providers found.</div>
        <#else>
            <#list providers as provider>
                <div class="provider-card"
                     data-id="${provider.id}"
                     data-name="${provider.name!'N/A'}"
                     data-business="${provider.businessName!'N/A'}"
                     data-email="${provider.email!'N/A'}"
                     data-phone="${provider.phone!'N/A'}"
                     data-availability="${provider.availability!'N/A'}"
                     data-price="${provider.pricingDetails!'N/A'}"
                     data-expertise="${provider.expertise!'N/A'}"
                     data-picture="${provider.profilePicture!'default-profile.jpg'}">
                    <div class="provider-header">
    <img src="${provider.profilePicture!'default-profile.jpg'}" alt="Profile" />
    <h2>${provider.businessName!'Business Name'}</h2>
    <p style="color: #666;">${provider.name!'Name'}</p>
    <#assign rating = (ratingsMap[provider.id?string])!0>
<#if rating != 0>
    <p><i class="fas fa-star" style="color: gold;"></i> ${rating?string("0.0")} / 5.0 average rating</p>
<#else>
    <p>No reviews yet</p>
</#if>


</div>


                    <div class="provider-info">
                        <p><i class="fas fa-briefcase"></i> ${provider.expertise!'Expertise not set'}</p>
                        <p><i class="fas fa-clock"></i> ${provider.availability!'Not available'}</p>
                        <p><i class="fas fa-dollar-sign"></i> ${provider.pricingDetails!'N/A'}</p>
                    </div>
                    <div class="provider-actions">
                    <button class="view-profile-btn" onclick="openProfileModal(this)">
                        <i class="fas fa-user"></i> View Profile
                    </button>
                    <#assign serviceId = providerToFirstServiceId[provider.id?string]!0>
<#if serviceId != 0>

    <button class="view-profile-btn" onclick="location.href='/reviews/service/${serviceId}'">
        <i class="fas fa-star"></i> Leave Review
    </button>
<#else>
    <button class="view-profile-btn" disabled style="opacity: 0.5;">
        <i class="fas fa-star"></i> No Services Yet
    </button>
</#if>

                </div>
                <button onclick="toggleSave(${provider.id})" class="view-profile-btn save-btn" style="background-color:#4ad9e4;">
    <i class="fas fa-bookmark"></i> Save Provider
</button>



                </div>
            </#list>
        </#if>
    </div>
</div>

<!-- Modal -->
<div class="modal-overlay" id="providerModal">
    <div class="modal-content" id="modalContent">
        <img id="modalPic" src="" style="width:80px;height:80px;border-radius:50%;margin-bottom:1rem;" />
        <h2 id="modalBusinessName"></h2>
        <p id="modalName"></p>
        <p id="modalEmail"></p>
        <p id="modalPhone"></p>
        <p id="modalAvailability"></p>
        <p id="modalPrice"></p>
        <p id="modalExpertise"></p>

        <#if customer??>
        <form method="post" action="/bookings/create" class="booking-form">
            <input type="hidden" name="provider.id" id="bookingProviderId" />
            <label>Date:</label>
            <input type="date" name="appointmentDate" required />
            <label>Time:</label>
            <input type="time" name="appointmentTime" required />
            <button type="submit">Book Now</button>
        </form>
        </#if>

        <button class="close-btn" onclick="closeProfileModal()">Close</button>
    </div>
</div>

<script>
function openProfileModal(button) {
    const card = button.closest('.provider-card');
    document.getElementById('modalBusinessName').textContent = card.dataset.business;
    document.getElementById('modalName').textContent = "Name: " + card.dataset.name;
    document.getElementById('modalEmail').textContent = "Email: " + card.dataset.email;
    document.getElementById('modalPhone').textContent = "Phone: " + card.dataset.phone;
    document.getElementById('modalAvailability').textContent = "Availability: " + card.dataset.availability;
    document.getElementById('modalPrice').textContent = "Pricing: " + card.dataset.price;
    document.getElementById('modalExpertise').textContent = "Expertise: " + card.dataset.expertise;
    document.getElementById('modalPic').src = card.dataset.picture;
    document.getElementById('bookingProviderId').value = card.dataset.id;
    document.getElementById('providerModal').style.display = "flex";
}

function closeProfileModal() {
    document.getElementById('providerModal').style.display = "none";
}
</script>

<script>
function toggleSave(providerId) {
    fetch('/customers/save-provider/' + providerId, {
        method: 'POST'
    })
    .then(res => res.text())
    .then(msg => {
        const toast = document.createElement('div');
        toast.textContent = (msg === 'saved') ? 'Provider saved!' : 'Provider removed.';
        toast.className = 'toast';
        document.body.appendChild(toast);
        setTimeout(() => {
            toast.classList.add('hide');
            setTimeout(() => toast.remove(), 300);
        }, 1500);
    })
    .catch(() => alert("Error saving provider"));
}
</script>

<style>
.toast {
    position: fixed;
    bottom: 20px;
    right: 20px;
    background: #4ad9e4;
    color: #fff;
    padding: 0.75rem 1.25rem;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0,0,0,0.2);
    opacity: 1;
    transition: opacity 0.3s ease;
    z-index: 1000;
}
.toast.hide {
    opacity: 0;
}
</style>


</body>
</html>
