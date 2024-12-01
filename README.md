# FIX Engine

A work-in-progress FIX (Financial Information Exchange) protocol engine built with Java and Spring Boot. The server is designed to run on Linux. I’ve tested it using Termux on an older phone, allowing it to function as a Linux server for this application.

## Features

- Basic FIX message exchange
- Supports logon, heartbeat, and logout messages
- Customizable message handling and routing

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/BAlexandru99/Fix-Engine.git
   cd Fix-Engine
   ```

2. Build the project:
   ```bash
   ./mvnw clean install
   ```

3. Run the application:
   ```bash
   java -jar target/FixEngine-0.0.1-SNAPSHOT.jar
   ```

### Usage

- Configure your client to connect to the server’s IP and port.
- Supports basic FIX protocol messages (logon, heartbeat).

### Notes

This project is under development, with additional features planned for future updates. Currently, only basic message handling is supported.
