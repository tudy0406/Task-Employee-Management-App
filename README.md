Task Management Application
Project Overview

This is a JavaFX application developed for Fundamental Programming Techniques - Assignment 1.
The project simulates a Task Management System for a software company, allowing project managers to:

    Add employees

    Create and assign simple or complex tasks

    View employee information and tasks

    Modify task statuses

    View work duration and task completion statistics

All data is persisted using Java Serialization.
Features

    Employee Management: Add and view employees.

    Task Management: Create Simple or Complex tasks and assign them to employees.

    Task Status Update: Mark tasks as "Completed" or "Uncompleted."

    Work Duration Estimation: Estimate how long an employee has worked based on completed tasks.

    Statistics:

        Filter employees with work duration over 40 hours, sorted by duration.

        View the number of completed and uncompleted tasks for each employee.

Technical Specifications

    JavaFX for the Graphical User Interface (GUI)

    Object-Oriented Design principles

    Layered Architectural Pattern

    Java Sealed Classes (Task as a sealed abstract class)

    Composite Design Pattern (for complex tasks)

    Serialization for data persistence

    UML diagrams included (Use Case, Package, and Class diagrams)

Project Structure

    Employee class

    Task sealed abstract class

        SimpleTask

        ComplexTask

    TasksManagement class

    Utility class

    GUI classes for user interaction

How to Run

    Clone the repository from GitHub.

    Open the project in IntelliJ IDEA or another Java IDE.

    Build and run the project.

    Use the GUI to manage employees and tasks.

UML Diagrams

Diagrams are provided in the project as .drawio files:

    Use Case Diagram

    Package Diagram

    Class Diagram

Requirements Fulfilled

    Object-oriented programming

    JavaFX GUI

    Serialization for persistence

    Sealed and composite design patterns

    Java coding conventions

    Class/method size restrictions

    UML diagrams
