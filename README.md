# Cycling Race Management System - ECM1410 Object-Oriented Programming

## Project Overview

This project is a back-end Java package designed to support a local cycling club's staged race events. It manages riders, teams, races, and results for three key race classifications: General Classification, Points Classification, and Mountain Classification.

## Features

The system supports the following functionalities:

1. **Rider's Management**:
   - Add, modify, and remove riders and teams.
   - Assign riders to teams.

2. **Race Design**:
   - Create and manage multi-stage races.
   - Configure each stage with features like intermediate sprints and mountain summits.

3. **Race Results**:
   - Record results for each stage, including rider times, sprint points, and mountain summits.
   - Automatically calculate classifications (General, Points, and Mountain).

## Class Structure

The system is implemented with the following classes:

- `Rider`: Manages individual rider details.
- `Team`: Handles team management, including rider assignments.
- `Stage`: Represents each stage of the race, including configuration for sprints and summits.
- `Race`: Manages the entire race event, storing information about stages and riders.
- `Result`: Captures race results for different classifications.

## How to Use

1. **Setup**:
   - Compile the project using a Java compiler (`javac`).
   - Package the project into a `.jar` file.

2. **Integration**:
   - The compiled `.jar` file can be integrated with the provided front-end by the module leader.

3. **Execution**:
   - Follow the interface guidelines to ensure proper communication between the back-end and front-end.

## Pair Programming Details

- **Contribution**: This project was developed with equal contributions of 50:50.

## Instructions for Running Tests

- To run unit tests, use the following command: `java -jar testfile.jar`.
- Ensure the `testfile.jar` is in the same directory as your compiled package.

## Author Information

- [Your Name]
- [Your Partner’s Name]

## Notes

For more information, please refer to the course document on Object-Oriented Programming Development Paradigm.
