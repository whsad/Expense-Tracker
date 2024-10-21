# Expense Tracker

**Expense Tracker** is a command-line application designed to help users manage and track their daily expenses. It supports adding, updating, deleting, and listing expenses, as well as providing expense summaries for a specified month. All expenses are stored as JSON objects in a file for persistence.

## Environment Setup

### System Requirements

- Java 1.8 or higher

## Features
- **Add Expense**: Add a new expense with a description and amount.
- **Update Expense**: Update an existing expense's description and amount using its ID.
- **Delete Expense**: Remove an expense by its ID.
- **List Expenses**: Display all recorded expenses with their details.
- **Monthly Summary**: Provide the total expenses for a specific month.
- **Data Persistence**: Save expenses as JSON and load them automatically on startup.

## Compilation Instructions
Ensure you have the Java Development Kit (JDK) installed on your system.

1. Clone the repository:
   ```bash
   git clone https://github.com/whsad/Expense-Tracker.git
   cd Expense-Tracker
   ```

2. Compile the project:
   ```bash
   javac -encoding UTF-8 Expense.java ExpenseCliApp.java ExpenseManager.java
   ```

## Usage
Run the application with the following commands:

- **Add a new expense**:
  ```bash
  java ExpenseCliApp add --description "Lunch" --amount 20
  ```

- **Update an existing expense**:
  ```bash
  java ExpenseCliApp update --id <expenseId> --description "New description" --amount 15
  ```

- **Delete an expense**:
  ```bash
  java ExpenseCliApp delete --id <expenseId>
  ```

- **List all expenses**:
  ```bash
  java ExpenseCliApp list
  ```

- **Display a summary**:
  - Total expenses:
    ```bash
    java ExpenseCliApp summary
    ```
  - Total expenses for a specific month:
    ```bash
    java ExpenseCliApp summary --month 8
    ```

## Notes
- Expenses are stored in `expenses.json` located in the project directory.
- If the JSON file is missing, the application creates one on startup.

This is a solution to a project challenge in roadmap.sh.
