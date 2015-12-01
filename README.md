# MemoryDatabase<br>
----------------------
Description
----------------------
In this problem, an in-memory database is implemented. For simplicity's sake, instead of dealing with multiple clients and communicating over the network, the program will receive commands via standard input (stdin), and should write appropriate responses to standard output (stdout). The basic commands are: <br>
1. SET name value – Set the variable name to the value value. Neither variable names nor values will contain spaces.<br>
2. GET name – Print out the value of the variable name, or NULL if that variable is not set.<br>
3. UNSET name – Unset the variable name, making it just like that variable was never set.<br>
4. NUMEQUALTO value – Print out the number of variables that are currently set to value. If no variables equal that value, print 0.<br>

Example :<br>
INPUT<br>
SET ex 10<br>
GET ex<br>
UNSET ex<br>
GET ex<br>
END<br>

Output:<br>
10<br>
<br>
NULL<br>

It also allows the transactions as well. The commands are as follows: <br>
1. BEGIN – Open a new transaction block. Transaction blocks can be nested; a BEGIN can be issued inside of an existing block.<br>
2. ROLLBACK – Undo all of the commands issued in the most recent transaction block, and close the block.
Print nothing if successful, or print NO TRANSACTION if no transaction is in progress.<br>
3. COMMIT – Close all open transaction blocks, permanently applying the changes made in them. 
Print nothing if successful, or print NO TRANSACTION if no transaction is in progress.<br>

-------------------------------
Design Decisions and Algorithm
-------------------------------
The major design idea is to use inheritance to solve the problem<br>
1. Basic commands such as set, get etc are implemented by CommandsHandler which implements the Commands Interface<br>
2. The CommandsHandler uses 2 HashMaps, one for keeping the name value pair and second one to keep track of value(s) count, as a
result all the basic operations (set, get, numequalto and unset) are 0(1) access.<br>
3. Transactions commands begin, rollback and commit are implemented in TransactionHandler that extends CommandHandler and
implements the Transaction Interface<br>
4. The TransactionHandler uses one stack to keep track old values whenever they are modified during a transaction, <br>
5. On Begin a null element is added to the stack to keep track of nested transactions, one set and unset an entry to the stack 
is made i.e (push operation), on rollback all the stack entries until the first null are popped and on commit the stack is
emptied.<br>
6. Commit and Rollback operations take O(m) runtime where m = sum (set + unset) commands within the outermost transaction block<br> 

-----------
Assumptions
-----------
1. The database implementation is build in memory, i.e during an unexpected failure the database will be lost as well
2. UNSET a variable that does not exist on the database return false and doesn't print anything on the screen
3. For wrong inputs INVALID COMMAND is displayed and for missing arguments the command is ignored

----------
How to Run
----------
1. Go to the folder location where jar file or source code is
2. Use the command java -jar <LOCATION_OF_JAR_FILE> <OPTIONAL_ARGUMENT> on command line
for example java -jar MemoryDatabase.jar OR java -jar MemoryDatabase.jar input
3. The optional argument would be the location of the text file with the input commands and program will read it sequentially and display the output. In teh absence of the input file the stdin input and output are displayed.
