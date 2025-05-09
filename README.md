
# Handy Hive

### 👥 Team Members
- **Austin Rehner**
- **Fady Eskandr**

---

### 📌 Project Description

Handy Hive is a full-stack web application that connects homeowners with reliable professional service providers such as electricians, plumbers, HVAC technicians, and other repair professionals. The application supports customer-provider interaction, booking, reviewing, analytics, and more.

---

### 🎯 Project Goals

- **Customers** can easily find highly rated professionals and schedule services.
- **Providers** can promote services, view engagement stats, and manage their business.
- **Admins** can moderate listings, manage user permissions, and analyze platform activity.

---

### ⚙️ Features

#### 🧑‍💻 Customer (Austin Rehner)
- Sign up and manage a customer profile
- Browse categorized services with filters
- View provider info and promotions
- Book services via a contact method
- Leave reviews for completed services

#### 🛠️ Provider (Fady Eskandr)
- Register and manage a provider profile
- Create services with details and availability
- View statistics: posts, comments, saves, likes, and reviews
- Generate PDF reports with engagement metrics and charts
- Reply to customer reviews

---

### 🧪 Technologies Used

- **Backend**: Java, Spring Boot, Hibernate, JPA  
- **Frontend**: HTML, CSS, FreeMarker Templates  
- **Database**: MySQL (XAMPP)  
- **Charting**: JFreeChart (PDF chart export)  
- **PDF Export**: OpenPDF (Lowagie)

---

### ▶️ How to Run Handy Hive Locally

---

#### ✅ 1. Clone the Repository

```bash
git clone https://github.com/your-username/handyhive.git
cd handyhive
```

---

#### ✅ 2. Set Up the Database

1. Launch **XAMPP** and start both **Apache** and **MySQL**.
2. Open your browser and go to:  
   `http://localhost/phpmyadmin`
3. Create a new database called:

```
handyhive_DB
```

4. Import the SQL schema:
   - Click on the `handyhive_db` database
   - Go to the **Import** tab
   - Choose the file `handyhive_schema.sql` from the project directory
   - Click **Go** to execute the import

---

#### ✅ 3. Configure Application Settings

Open the file:  
`src/main/resources/application.properties`

Ensure it includes:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/handyhive
spring.datasource.username=root
spring.datasource.password=mysql      
spring.jpa.hibernate.ddl-auto=update
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
spring.freemarker.suffix=.ftlh
```

---

#### ✅ 4. Run the Project

Open the project in **IntelliJ** or **VS Code** and:

1. Navigate to the main class:

```
src/main/java/com/handyHive23/handyHive23/HandyHive23Application.java
```

2. Right-click → **Run**, or from terminal:

```bash
./mvnw spring-boot:run
```

---

#### ✅ 5. Access the App

Visit:  
```
http://localhost:8081
```

From there, you can:
- Register as a **Customer** or **Provider**
- Browse and post services
- Submit and read reviews
- Book appointments
- View business insights
- Generate a **PDF report** With Statistics

---

### 📊 PDF Reports & Analytics

Providers can export a full **business performance report** via the "Convert to PDF" button. This includes:

- Total posts, comments, saves, and reviews
- A detailed list of all customer comments and reviews
- A list of recent bookings
---

### 💡 Requirements

- Java 17+
- Maven (or use `./mvnw`)
- XAMPP (with MySQL running)
- Internet browser
