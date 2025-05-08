<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register Provider</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Inter', sans-serif;
            background: linear-gradient(to right, #fdfbfb, #ebedee);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .register-card {
            background: rgba(255, 255, 255, 0.85);
            backdrop-filter: blur(16px);
            box-shadow: 0 20px 50px rgba(0, 0, 0, 0.1);
            padding: 2.5rem 3rem;
            border-radius: 20px;
            max-width: 600px;
            width: 100%;
            border: 1px solid rgba(255, 255, 255, 0.3);
        }

        h2 {
            text-align: center;
            margin-bottom: 2rem;
            color: #333;
            font-size: 2rem;
        }

        .form-group {
            margin-bottom: 1.4rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #222;
            font-weight: 500;
            font-size: 0.95rem;
        }

        input {
            width: 100%;
            padding: 0.9rem 1rem;
            font-size: 1rem;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #ffffff;
            transition: border 0.3s ease, box-shadow 0.3s ease;
        }

        input:focus {
            border-color: #4ad9e4;
            box-shadow: 0 0 0 3px rgba(74, 217, 228, 0.3);
            outline: none;
        }

        button {
            width: 100%;
            padding: 1rem;
            font-size: 1rem;
            font-weight: bold;
            background: linear-gradient(to right, #FFD700, #ffbf00);
            color: #333;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            transition: background 0.3s ease, transform 0.2s ease;
        }

        button:hover {
            background: linear-gradient(to right, #e5c100, #d4aa00);
            transform: translateY(-2px);
        }
    </style>
</head>
<body>

<div class="register-card">
    <h2>Register Your Business</h2>
    <form action="/register/provider" method="post">
        <div class="form-group">
            <label for="businessName">Business Name</label>
            <input type="text" name="businessName" id="businessName">
        </div>
        <div class="form-group">
            <label for="name">Your Name</label>
            <input type="text" name="name" id="name" required>
        </div>
        <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" name="email" id="email" required>
        </div>
        <div class="form-group">
            <label for="phone">Phone Number</label>
            <input type="text" name="phone" id="phone">
        </div>
        <div class="form-group">
            <label for="address">Business Address</label>
            <input type="text" name="address" id="address">
        </div>
        <div class="form-group">
            <label for="expertise">Expertise</label>
            <input type="text" name="expertise" id="expertise">
        </div>
        <div class="form-group">
            <label for="availability">Availability</label>
            <input type="text" name="availability" id="availability">
        </div>
        <div class="form-group">
            <label for="pricingDetails">Pricing Details</label>
            <input type="text" name="pricingDetails" id="pricingDetails">
        </div>
        <div class="form-group">
            <label for="password">Set Password</label>
            <input type="password" name="password" id="password" required>
        </div>
        <button type="submit">Register</button>
    </form>
</div>

</body>
</html>
