Repository Link: https://github.com/sandeepmanglaram/AndroidWorkspace/tree/main

--About App:
Launch Screen: 
	Displays Total expense and Today's expense
	Has 3 Buttons:
	1. Add New Expense: To add new expense for current date
	2. View Expenses: List of Expenses based on date selected(Default: current date)
	3. Visualize Expense: To view graphical pie chart view of expense category

Add New Expense:
	Edit text accepts data from user and inserts data to room database
	
View Expenses: 
	List of expenses, Can select expenses on particular date

Visualize Expenses:
	Can visualize expenses category wise, Export the expense data to CSV file



--AI Prompt:
1. Android Java, Help me design a pie chart based on my List<Expenses>
2. API to fetch data from Room database and export it into CSV file in local directory.
3. Implement Date Picker;

--Services Used:
ExecutorService for background tasks
SharedPreferences flag for one time data loading of previous day
Spinner view for Category selection
Room Database for persistent data storage of expense
ExpenseDAO for SQL Java queries
CategoryTotal to get groupBy sum of expense



