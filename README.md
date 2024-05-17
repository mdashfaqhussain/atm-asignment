# ATM Cash Withdrawal System

## Objective

To implement a "Cash Withdrawal" function for an ATM that dispenses bank-notes based on a user-specified amount. The implementation should:

- Use the minimum number of bank-notes while dispensing the amount.
- Maintain the availability of various denominations in the ATM machine.
- Be flexible to handle any bank denomination as long as it is a multiple of 100.
- Support parallel cash withdrawals, allowing two or more customers to withdraw money simultaneously.
- Handle exceptional situations appropriately.
- Include unit test cases.

## Problem Statement

Develop a cash withdrawal function for an ATM. Ensure that the solution:

1. Uses the minimum number of bank-notes for dispensing the specified amount.
2. Maintains the availability of various denominations within the ATM.
3. Is flexible to support any denomination as long as it is a multiple of 100.
4. Supports concurrent cash withdrawals.
5. Handles exceptional situations effectively.

## Non-Functional Requirements (NFRs)

- **Readme.md**: The duration of this exercise is 90 minutes. Manage your time accordingly.
- Clearly state any assumptions made.
- Do not seek help online or through other sources.

## Evaluation Criteria

1. **Code Completeness/Correctness**: The solution should be complete and correct.
2. **Code Structure**: Ensure modularity, use of OOP principles and design patterns, appropriate size of classes and functions, and low n-path complexity.
3. **Code Quality**: Follow proper naming conventions, logical package/class structure, and include relevant log messages (avoid unnecessary comments).
4. **Choice of Data Structures**: Use appropriate data structures for efficiency.
5. **Efficiency of Code**: Leverage multi-threading where applicable.
6. **Code Test Cases**: Follow TDD principles if possible and provide sufficient test coverage.

## Solution

* Code starts from the `Main` class which initializes the ATM with cash.
* The `Main` class provides a menu for the user to withdraw cash or exit the application.
* The withdrawal process is handled by the `CashWithdrawal` class with the desired amount as input in a single-threaded manner.
* The balance is calculated after each transaction is completed.

### Assumptions

1. Denominations in the ATM will be multiples of 100.
2. There can be multiple denominations of different values and counts.
3. The amount to be withdrawn should be a multiple of 100.
4. Test cases for withdrawing using multiple threads are included in the test class.


## Testing and Running the Application

* Tested with JDK 21 and IntelliJ IDEA.

### Steps to Run the Application

1. Clone the repository.
2. Resolve Maven dependencies.
3. Run `mvn clean`. If this is successful:
4. Run the application with `mvn exec:java` and check the console for results.
5. If running the application with Maven fails, directly run the `Main` class from your IDE.
