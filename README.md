# GoldenPay

A Spring Boot application that provides a mock credit evaluation API for the GoldenPay banking system. It evaluates client credit requests based on FIN, age, salary, requested amount, and credit duration.

---

## Table of Contents

1. [Features](#features)
2. [Prerequisites](#prerequisites)
3. [Getting Started](#getting-started)

   * [Clone the Repository](#clone-the-repository)
   * [Build & Run](#build--run)
4. [API Endpoints](#api-endpoints)

   * [POST `/evaluate_client`](#post-evaluate_client)
5. [Credit Evaluation Rules](#credit-evaluation-rules)
6. [Usage Examples](#usage-examples)
7. [Configuration](#configuration)
8. [Contributing](#contributing)
9. [License](#license)

---

## Features

* Injects `FinRepository` to retrieve client data.
* Validates client age (18–65).
* Ensures requested amount ≤ 4× annual salary.
* Enforces credit duration between 6 months and 20 years.
* Returns JSON payload with approval status and messages.

---

## Prerequisites

* Java 11 or higher
* Maven 3.6+ (or Maven Wrapper)
* Git

---

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/kamaalg/GoldenPay.git
cd GoldenPay
```

### Build & Run

**Using Maven Wrapper**

```bash
./mvnw spring-boot:run
```

**Or build and run the JAR**

```bash
./mvnw clean package
java -jar target/goldenpay-0.0.1-SNAPSHOT.jar
```

Default server port is `8080`.

---

## API Endpoints

### POST `/evaluate_client`

Evaluate a client’s credit request.

#### Request Body

```json
{
  "fin": "AZE12345678",
  "salary": 5000,
  "requested_amount": 20000,
  "age": 30,
  "credit_duration": "P365D"
}
```

* **`fin`** (`String`) — Client’s FIN identifier.
* **`salary`** (`Integer`) — Annual salary.
* **`requested_amount`** (`Integer`) — Desired credit amount.
* **`age`** (`Integer`) — Client’s age.
* **`credit_duration`** (`Duration`) — ISO‑8601 duration (e.g. `P365D`).

#### Response Body

```json
{
  "approved": true,
  "message": "Congratulations! Credit approved with annual interest rate 7.50% for amount 20000."
}
```

* **`approved`** (`boolean`) — Approval status.
* **`message`** (`String`) — Success or error details.

---

## Credit Evaluation Rules

1. **Age**: 18 ≤ age ≤ 65.
2. **Duration**: 6 months (≈ `P182D`) ≤ credit\_duration ≤ 20 years (≈ `P7300D`).
3. **Amount**: requested\_amount ≤ salary × 4.
4. **Interest Rate**: determined by business rules.

Any violation results in `approved: false` with a reason.

---

## Usage Examples

**cURL**

```bash
curl -X POST http://localhost:8080/evaluate_client \
  -H "Content-Type: application/json" \
  -d '{
    "fin": "AZE12345678",
    "salary": 5000,
    "requested_amount": 20000,
    "age": 30,
    "credit_duration": "P365D"
  }'
```

**Example Failure**

```json
{
  "approved": false,
  "message": "Credit duration must be between 6 months and 20 years."
}
```

---

## Configuration

Customize `src/main/resources/application.properties`:

```properties
server.port=8080
# Other settings...
```

---

## Contributing

1. Fork the repo
2. Create a feature branch (`git checkout -b feature/xyz`)
3. Commit (`git commit -m "Add feature"`)
4. Push (`git push origin feature/xyz`)
5. Open a Pull Request

---

## License

This project is licensed under the MIT License. See `LICENSE` for details.
