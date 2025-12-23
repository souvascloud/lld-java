#  Parking Lot System -- Low Level Design in Java

##  Overview

This project is a complete **Low Level Design (LLD)** implementation of
a **Parking Lot System** using plain Java.\
It demonstrates how to design, implement, and test a real-world system
by applying **OOP principles, SOLID design, and clean architecture**.

The goal is to showcase: - Domain modeling - Service orchestration -
Extensible design - Unit testing - UML & sequence diagrams

------------------------------------------------------------------------

##  Features

-   Supports multiple floors and parking slots
-   Handles different vehicle types: **Bike, Car, Truck**
-   Allocates nearest available slot based on type
-   Issues parking tickets on entry
-   Calculates parking fee on exit using **Strategy Pattern**
-   Frees slot after exit and allows reuse\
-   Handles edge cases like:
    -   Parking lot full
    -   Invalid / reused tickets
    -   Double exit attempts

------------------------------------------------------------------------

##  Design Highlights

-   **SOLID principles** applied
-   **Strategy Pattern** for pricing logic
-   **Encapsulation** of domain rules inside entities
-   **Service layer** (`ParkingManager`) for orchestration
-   **Immutable domain objects** where applicable
-   Easily extensible for new vehicle types and pricing rules

------------------------------------------------------------------------

##  Package Structure

    com.example.parkinglot
    │
    ├── model
    │   ├── ParkingLot
    │   ├── Floor
    │   ├── ParkingSlot
    │   ├── Ticket
    │   └── Vehicle
    │
    ├── model.vehicle
    │   ├── Bike
    │   ├── Car
    │   └── Truck
    │
    ├── service
    │   ├── ParkingManager
    │   └── FeeCalculator
    │
    ├── service.impl
    │   └── DefaultFeeCalculator
    │
    ├── strategy
    │   ├── PricingStrategy
    │   ├── BikePricing
    │   ├── CarPricing
    │   └── TruckPricing
    │
    ├── enums
    │   ├── VehicleType
    │   └── SlotType
    │
    └── Main

------------------------------------------------------------------------

##  UML & Sequence Diagrams

### Class Diagram
![Capture](https://github.com/user-attachments/assets/944a555c-87ae-429d-9f94-6e04cac00b9f)



### Sequence Diagram

![parking-sequnece](https://github.com/user-attachments/assets/c6d62eaa-87ad-41f0-a479-704c8c3e552b)

------------------------------------------------------------------------

##  How to Run

### Prerequisites

-   Java 8+\
-   IntelliJ IDEA or any Java IDE

### Steps

``` bash
javac -d out $(find src -name "*.java")
java -cp out com.example.parkinglot.Main
```

Or simply run `Main` from IntelliJ.

------------------------------------------------------------------------

##  Unit Tests

Unit tests are written using **JUnit 5** and cover: - Normal parking
flow
- Exit & fee calculation
- Slot reuse after exit
- Parking lot full scenario
- Double exit / invalid ticket handling
- Slot state cleanup

Run directly from IntelliJ using the test runner.

------------------------------------------------------------------------

##  Edge Cases Handled

-    Parking when no slots available
-    Reusing the same ticket for exit
-    Invalid  unknown tickets
-    Null inputs
-    Minimum fee even for short duration
-    Slot freed after exit

------------------------------------------------------------------------

##  Possible Extensions

-   REST API using Spring Boot
-   Persistent storage (DB)
-   Multiple parking lots
-   Concurrency handling
-   Dynamic pricing rules
-   Support for reservations

------------------------------------------------------------------------

##  About This Project

This project is built as a **learning ** to
practice: - Low Level Design - Object-Oriented Modeling - Clean Java
code - Unit testing - Design documentation


------------------------------------------------------------------------

##  Contact

**Author:** Souvanik Saha
**LinkedIn:** https://www.linkedin.com/in/souvanik-saha-7172a810a/

 If you find this helpful, consider giving the repo a star!
