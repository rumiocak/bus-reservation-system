# Bus Reservation System 🚌

A Java-based bus ticket booking system developed for BBM104: Introduction to Programming Laboratory II at Hacettepe University.

## About the Project

This project simulates a **Bus Reservation System** that manages seat bookings across different bus types. The system supports creating/cancelling voyages, selling and refunding tickets, and generating reports — all processed via command-line input files.

## Features

- Three bus types: **Standard (2+2)**, **Premium (1+2)**, **Minibus (2)**
- Initialize and cancel voyages
- Sell and refund tickets (with configurable refund cut percentage)
- Premium seat pricing support
- Z Report: lists all active voyages sorted by ID
- Robust error handling for invalid commands and edge cases

## Technologies

- Java 8
- Object-Oriented Programming (Abstraction, Encapsulation, Inheritance, Polymorphism)
- JavaDoc commenting style

## How to Run

```bash
javac *.java
java -cp . BookingSystem input.txt output.txt
```

## Input Commands

| Command | Description |
|---------|-------------|
| `INIT_VOYAGE` | Create a new voyage |
| `SELL_TICKET` | Sell one or more seats |
| `REFUND_TICKET` | Refund one or more seats |
| `PRINT_VOYAGE` | Print seating plan of a voyage |
| `CANCEL_VOYAGE` | Cancel a voyage and refund all tickets |
| `Z_REPORT` | Print all active voyages |

## Course

**BBM104** - Introduction to Programming Laboratory II  
Hacettepe University, Spring 2024
