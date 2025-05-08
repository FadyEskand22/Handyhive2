<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>HandyHive | Login</title>
    <link rel="stylesheet" href="/loginStyles.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
</head>
<body>

<div class="page-container">
    <div class="login-card">
        <div class="logo-container">
            <img src="/logo.png" alt="HandyHive Logo" class="logo">
        </div>

        <form method="post" action="/login" class="login-form">
            <div class="input-group">
                <input type="text" name="email" id="email" placeholder=" " required>
                <label for="email">Email</label>
            </div>

            <div class="input-group">
                <input type="password" name="password" id="password" placeholder=" " required>
                <label for="password">Password</label>
            </div>

            <button type="submit" class="login-button">Log In</button>

            <div class="divider">
                <span>or</span>
            </div>

            <button type="button" class="register-button" onclick="openRegisterPopup()">
                Create New Account
            </button>

            <button type="button" class="forgot-password" onclick="alert('Feature coming soon!')">
                Forgot Password?
            </button>

            <#if error??>
                <p style="color: red; font-size: 12px; text-align: center;">${error}</p>
            </#if>
        </form>
    </div>
</div>

<!-- Modal Overlay for Role Selection -->
<div class="modal-overlay" id="modalOverlay">
    <div class="registration-popup" id="registrationPopup">
        <div class="popup-header">
            <h2>Join HandyHive</h2>
            <p>Choose how you want to join our community</p>
        </div>
        <div class="popup-buttons">
            <button class="register-provider" onclick="location.href='/register/provider'">
                <span class="button-title">Join as Provider</span>
                <span class="button-desc">Offer your services to customers</span>
            </button>
            <button class="register-shopper" onclick="location.href='/register/customer'">
                <span class="button-title">Join as Customer</span>
                <span class="button-desc">Find and book services</span>
            </button>
            <button class="close-popup" onclick="closeRegisterPopup()">Cancel</button>
        </div>
    </div>
</div>

<script>
    function openRegisterPopup() {
        document.getElementById("modalOverlay").style.display = "flex";
        document.getElementById("registrationPopup").style.display = "block";
    }

    function closeRegisterPopup() {
        document.getElementById("modalOverlay").style.display = "none";
        document.getElementById("registrationPopup").style.display = "none";
    }

    document.getElementById("modalOverlay").addEventListener("click", function (e) {
        if (e.target === this) closeRegisterPopup();
    });
</script>

</body>
</html>
