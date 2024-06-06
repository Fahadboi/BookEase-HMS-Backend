# BookEase - Hotel Management System (Backend)

BookEase is a comprehensive Hotel Management System designed to streamline hotel operations and enhance the guest experience. Built with a modern technology stack, BookEase provides robust features for managing rooms, guests, staff, bookings, billing, and more.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [Contact](#contact)

## Features

- **Manage Rooms**: Add, edit, and remove room details, including room types, availability, and pricing.
- **Manage Guests**: Handle guest information, check-in, and check-out processes efficiently.
- **Manage Staff**: Manage staff details, roles, and assignments.
- **Manage Room Service Types**: Define and manage different types of room services offered.
- **Manage Room Services Ordered**: Track room services ordered by guests and their statuses.
- **Staff Assignment**: Assign staff to specific tasks or rooms.
- **Manage Booking**: Handle room bookings, cancellations, and modifications seamlessly.
- **Manage Billing**: Generate and manage bills for guest stays and services.
- **Manage Invoices**: Create and manage invoices for guests and services provided.
- **Manage Payments**: Record and track payments made by guests.

## Technologies Used

### Backend

- **Spring Boot**: Java-based framework for building web applications
- **MySQL**: Relational database management system

## Project Structure

```
BookEase/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   ├── resources/
│   │   ├── test/
│   ├── .gitignore
│   ├── pom.xml
└── README.md
```

## Installation

### Prerequisites

- Java Spring Boot
- MySQL

### Backend

1. Navigate to the `HMS_backend` directory:

    ```sh
    cd BookEase-HMS-Backend/HMS_Backend
    ```

2. Configure the `application.properties` file in `src/main/resources` with your MySQL database details.

3. Build the project with Maven:

    ```sh
    mvn clean install
    ```

4. Run the Spring Boot application:

    ```sh
    mvn spring-boot:run
    ```

## Usage

1. Ensure the backend server is running.
2. Use a REST client or integrate with the frontend to access the backend services.

## Contributing

Contributions are welcome! Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -am 'Add your feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Create a new Pull Request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any inquiries or feedback, please contact:

- **Your Name**: [fahadzafarmayo123@gmail.com]
- **GitHub**: [https://github.com/Fahadboi](https://github.com/Fahadboi)
