<!DOCTYPE html>
 <html lang="en">
 <head>
     <meta charset="UTF-8">
     <title>HandyHive | Register</title>
     <link rel="stylesheet" href="/loginStyles.css">
     <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
 </head>
 <body>
 
 <div class="page-container">
     <div class="login-card"> <!-- keep consistent class for layout -->
         <div class="logo-container">
             <img src="/logo.png" alt="HandyHive Logo" class="logo">
         </div>
 
         <form method="post" action="/register/customer" class="login-form">
             <div class="input-group">
                 <input type="text" name="name" id="name" placeholder=" " required>
                 <label for="name">Name</label>
             </div>
 
             <div class="input-group">
                 <input type="email" name="email" id="email" placeholder=" " required>
                 <label for="email">Email</label>
             </div>
 
             <div class="input-group">
                 <input type="text" name="phone" id="phone" placeholder=" ">
                 <label for="phone">Phone</label>
             </div>
 
             <div class="input-group">
                 <input type="text" name="address" id="address" placeholder=" ">
                 <label for="address">Address</label>
             </div>
 
             <div class="input-group">
                 <input type="password" name="password" id="password" placeholder=" " required>
                 <label for="password">Password</label>
             </div>
 
             <button type="submit" class="login-button">Register</button>
 
             <div class="divider">
                 <span>or</span>
             </div>
 
             <button type="button" class="register-button" onclick="location.href='/login'">
                 Back to Login
             </button>
         </form>
     </div>
 </div>
 
 </body>
 </html>