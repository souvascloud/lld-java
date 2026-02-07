#  Low Level Design (LLD) Projects in Java

##  Overview

This repository is a collection of **Low Level Design (LLD)**
implementations built in **plain Java**.\
Each project models a real-world system and focuses on:

-   Object-Oriented Design
-   SOLID principles
-   Design patterns
-   Clean code & package structure
-   UML & Sequence diagrams
-   Unit testing

The goal is to demonstrate how to go from **requirements → design → code
→ tests → diagrams**, just like in real-world backend systems.

------------------------------------------------------------------------

##  Projects

###  Parking Lot System

# Low Level Design (LLD) Projects in Java

## Overview

This repository is a collection of **Low Level Design (LLD)** implementations built in **plain Java**.
Each project models a real-world system and focuses on:

- Object-Oriented Design
- SOLID principles
- Design patterns
- Clean code & package structure
- Concurrency & correctness
- UML & Sequence diagrams
- Unit testing

The goal is to demonstrate how to go from:

Requirements → Design → Code → Concurrency → Tests → Diagrams

just like in real-world backend systems.

---

## Projects

### Parking Lot System (Phase-1 & Phase-2)

Design and implement a multi-floor parking lot supporting different vehicle types with real-world constraints.

Phase-1 Highlights:
- Domain modeling (ParkingLot, Floor, ParkingSlot, Vehicle, Ticket)
- Strategy pattern for fee calculation
- ParkingManager for orchestration
- Edge-case handling (full lot, invalid ticket, double exit)
- Basic unit tests
- UML & sequence diagrams

Phase-2 Enhancements:
- Concurrency-safe slot allocation
- Size-based slot compatibility
- Optimized slot lookup using indexed free slots
- Entry and Exit gate modeling
- ExecutorService-based concurrency simulation
- Monetary correctness using BigDecimal
- Comprehensive unit tests including concurrency scenarios

Folder: parkingLot/

---

## Project Structure

Each project folder contains:
- Source code
- Unit tests
- Diagrams
- Phase-wise README with design explanations

---

 Folder: `parkingLot/`\
 [View Project](./parkingLot)

------------------------------------------------------------------------


Each project folder contains: - Source code - Unit tests - Diagrams - A
dedicated README with design explanation

------------------------------------------------------------------------

##  Design Principles Followed

-    SOLID principles
-    Separation of concerns
-    Domain-driven thinking
-    Extensible & testable design
-    Clean Java code

------------------------------------------------------------------------

##  Tech Stack

-   Java 8+
-   JUnit 5
-   IntelliJ IDEA
-   Mermaid / UML diagrams

------------------------------------------------------------------------


##  How to Use

1.  Clone the repo:

    ``` bash
    git clone https://github.com/your-username/lld-projects-java.git
    ```

2.  Go into any project folder:

    ``` bash
    cd parking-lot
    ```

3.  Follow that project's README.

------------------------------------------------------------------------

##  Author

**Souvanik Saha**\
Senior Software Engineer\
Passionate about backend systems, design, and clean code.

-   LinkedIn: https://www.linkedin.com/in/souvanik-saha-7172a810a/

------------------------------------------------------------------------

 If you find this repository useful, consider giving it a star!
