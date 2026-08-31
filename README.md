# Sunrise Dental Clinic Management System

A Java web application built for **Advanced Programming Module**. The system replaces Sunrise Dental Clinic's paper-based appointment process with a secure, multi-user web application that prevents double-booking, tracks patient and billing history, and gives staff a live overview of clinic activity.

## Features

- **Secure login** with role-based access (Admin / Receptionist), BCrypt-hashed passwords, and session-based route protection (`AuthFilter`).
- **Appointment registration** with NIC-based existing-patient lookup (type-ahead search), support for multiple treatments per appointment, and duplicate-booking prevention (a dentist cannot be double-booked for the same date and time).
- **View, reschedule and cancel appointments**, each with server-side validation and a custom confirmation popup for destructive actions.
- **Billing** — automatic total calculation (consultation fee + treatment cost), duplicate-bill prevention, automatic status update to *Completed*, and a printable A4 receipt.
- **Dashboard** — live statistics (today's/this month's appointments, monthly revenue with a trend sparkline, most-booked treatment) and three Chart.js visualisations (status breakdown, revenue trend, top treatments).
- **Reports** — Daily Appointments, Monthly Revenue, and Dentist-wise Appointments, each printable.
- **Manage Staff** (Admin only) — add, edit and remove staff accounts, with protection against demoting the last remaining Admin.
- **My Account** — self-service password change.
- **Patients** — a searchable, paginated list of every registered patient.
- **Help section** with step-by-step instructions for new staff.
- **REST API endpoints** (`GET /api/appointments/{number}`, `GET /api/patients/search`) returning JSON, used internally by the NIC type-ahead search and demonstrating the system's web-service capability.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Web Framework | Jakarta Servlet 6.0 / JSP |
| Server | Apache Tomcat 10.1 |
| Database | MySQL (via XAMPP), plain JDBC with `PreparedStatement` |
| Build Tool | Maven |
| Frontend | Bootstrap 5, Bootstrap Icons, Chart.js |
| Security | jBCrypt (password hashing) |
| Testing | JUnit 5 |

---

## Architecture

The application follows a layered architecture:

```
Browser (JSP views)
      ↓
Servlet (Controller)  — reads requests, calls the Service layer, forwards/redirects
      ↓
Service (Business logic + validation)
      ↓
DAO (Data access — the only layer that touches JDBC/SQL)
      ↓
MySQL
```

**Design patterns used:**
- **Singleton** — `DBConnection` provides a single shared database connection.
- **DAO (Data Access Object)** — every entity has a DAO interface and a `*Impl` class, keeping SQL isolated from business logic.
- **Dependency Injection** — every `*ServiceImpl` class accepts its DAO dependencies through a constructor (interface-typed), allowing JUnit tests to inject in-memory fake DAOs instead of a live database connection.

**OOP principles:** encapsulation (private fields + getters/setters throughout the model layer), abstraction (Servlets depend only on Service *interfaces*), inheritance (every Servlet extends `HttpServlet`), and polymorphism (DAO/Service fields typed to their interface, resolved to different implementations at runtime — a real implementation in production, a fake one in tests).

---

## Project Structure

```
src/main/java/com/dental/clinic/
  ├── model/      Patient, Dentist, TreatmentType, Appointment, Bill, User
  ├── dao/        DAO interfaces + *Impl classes (JDBC access)
  ├── service/    Service interfaces + *Impl classes (business logic, validation)
  ├── servlet/    Controllers (one per feature area)
  ├── filter/     AuthFilter (session-based access control)
  └── util/       DBConnection (Singleton), ValidationUtil (static helpers)
src/main/webapp/  JSP views, CSS, common/ (sidebar, reusable modals)
src/test/java/    JUnit 5 tests + in-memory fake DAOs
```

---

## Setup / Running Locally

1. **Prerequisites:** JDK 17, Apache Maven, XAMPP (MySQL), Apache Tomcat 10.1.
2. **Database:** start MySQL in XAMPP, then import `src/main/resources/schema.sql` into a database named `dental_clinic_system`.
3. **Configuration:** copy `src/main/resources/db.properties.example` to `db.properties` and set your local MySQL credentials. (`db.properties` is git-ignored and must be created locally — it is not committed to this repository.)
4. **Build:**
   ```bash
   mvn clean package
   ```
5. **Deploy:** copy `target/DentalClinicSystem.war` into Tomcat's `webapps/` folder and start Tomcat.
6. **Run tests:**
   ```bash
   mvn test
   ```
   All 57 JUnit 5 tests run against in-memory fake DAOs and do not require a database connection.

---


## Testing

The Service layer is unit-tested with JUnit 5 (57 tests, 5 test classes), covering validation rules, the NIC-based patient-reuse logic, duplicate-booking prevention, bill calculation, and staff account management (including the last-remaining-Admin protection). Each `*ServiceImpl` class exposes a constructor that accepts its DAO dependencies as interfaces, so tests run against small hand-written in-memory fakes rather than a live database — see `src/test/java/com/dental/clinic/service/`.

---

## Author

Tharani Gamage
