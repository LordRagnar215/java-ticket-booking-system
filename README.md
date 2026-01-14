# Java Ticket Booking System (CLI)

A command-line based railway ticket booking application developed using Core Java.  
The project uses JSON files as a local database and follows a clean service-based architecture to demonstrate real-world booking logic.

---

## Overview

This application allows users to book train tickets through a command-line interface.  
It manages users, trains, and ticket bookings while persisting data using local JSON files instead of a database.

The project is designed to showcase **Core Java fundamentals**, **object-oriented programming**, and **clean separation of concerns**.

---

## Key Features

- User registration and management
- Train listing and availability checking
- Ticket booking for registered users
- Seat availability tracking
- JSON-based local data storage
- Input validation and exception handling
- Service-layer based design

---

## Project Structure

app/
└── src/
├── main/
│ ├── java/
│ │ └── org/example/
│ │ ├── entities/
│ │ │ ├── Ticket.java
│ │ │ ├── Train.java
│ │ │ └── User.java
│ │ ├── services/
│ │ │ ├── TrainService.java
│ │ │ └── UserBookingService.java
│ │ ├── util/
│ │ │ └── UserServiceUtil.java
│ │ └── App.java
│ └── resources/
│ └── localDb/
│ ├── trains.json
│ └── users.json
└── test/



---

## Technologies Used

- Java
- Core Java (OOP principles)
- Collections Framework
- File Handling
- JSON-based local storage
- Gradle (build tool)

---

## How the Application Works

1. Application starts from `App.java`
2. User interacts via a command-line menu
3. Train and user data is loaded from JSON files
4. Booking requests are processed using service classes
5. Ticket and user data is updated
6. Changes are persisted back to JSON files

---

## How to Run the Project

### Prerequisites
- Java 8 or higher
- Gradle

### Steps

1. Clone the repository
   ```bash
   git clone <your-repository-url>
   cd java-ticket-booking-system
