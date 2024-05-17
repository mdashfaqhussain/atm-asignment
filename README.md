Atm Implementation
Objective
Problem Statement
Write compiling code for "Cash Withdrawal" function, from an ATM which based on user specified amount, dispenses bank-notes.
Ensure that the code takes care of the following:

Minimum number of bank-notes are used while dispensing the amount
Availability of various denominations in the ATM machine is maintained
Code should be flexible to take care of any bank denomination as long as it is multiple of 10
Code should support parallel Cash Withdrawals i.e. two or more customer's should be able to withdraw money
Takes care of exceptional situations
Write appropriate unit test cases too
State any assumptions made
Write the compiling code using choice of your IDE (Eclipse, IntelliJ)
Provide Unit Test Cases using JUNIT (if you are not conversant with JUNIT, just list down unit test cases)
NFRs
Readme.md - Duration of this exercise is 90 minutes. Please manage your time accordingly, - Make any necessary assumption, and clearly state the assumptions made. - Do not seek any help online or through any other source.

Evaluation Criteria
Code Completeness/ Correctness
Code Structure: Modularity, usage of 00 principles and design patterns, size of classes, and functions, n-path complexity of functions.
Code Quality: class/function/variable naming conventions, package/class structure, log messages - please do not provide comments unless necessary
Choice of data structures
Efficiency of code (leverage multi-threading wherever it makes sense)
Code test Cases and follow TDD (if know)

Solution
Code starts from the Main class which initializes the ATM with cash.
The Main class provides a menu for the user to withdraw cash or exit the application.
The withdrawal process is handled by the CashWithdrawal class with the desired amount as input in a single-threaded manner.
The balance is calculated after each transaction is completed.

Assumptions
Denominations in the ATM should be multiples of 100.
There can be multiple denominations of different values and counts.
The amount to be withdrawn should be a multiple of 100.
Test cases for withdrawing using multiple threads are included in the test class.

Testing and Running the Application
Tested with JDK 17 and IntelliJ IDEA.

Steps to Run the Application
Clone the repository.
Resolve Maven dependencies.
Run mvn clean. If this is successful:
Run the application with mvn exec:java and check the console for results.
If running the application with Maven fails, directly run the Main class from your IDE.
