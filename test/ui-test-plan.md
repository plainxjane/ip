# Jelly UI test plan

The `test-ui` skill runs these cases against the observable console interface. Inputs are sent line by line. Expected output includes the complete session, including the startup banner.

## Test case: Add and list a to-do

### Aim
Verify that Jelly accepts a to-do and displays it in the task list.

### Input
```text
todo read course notes
list
bye
```

### Expected output
```text
╭──────────────────────╮
│      J E L L Y       │
│                      │
│       .-""""-.       │
│     .'  o  o  '.     │
│    /      ∆     \    │
│    \    '---'   /    │
│     '._      _.'     │
│        `----`        │
╰──────────────────────╯

Hello! I'm Jelly, your squishy little assistant!
What can I do for you? :)
Got it! Jelly has added this task as a to-do:
   [T][ ] read course notes

Now you have 1 tasks in your Jelly list~
Your Jelly Tasks :)
----------------------------------------------------------
1.[T][ ] read course notes
----------------------------------------------------------
Bye! Stay jiggly~
```

## Test case: Reject invalid commands without changing the task list

### Aim
Verify that empty descriptions, unknown commands, and invalid mark/unmark inputs
produce errors while valid tasks can still be added and listed afterward.

### Input
```text
todo
blah
todo buy milk
mark
mark abc
mark 1
unmark
unmark 1
list
bye
```

### Expected output
```text
╭──────────────────────╮
│      J E L L Y       │
│                      │
│       .-""""-.       │
│     .'  o  o  '.     │
│    /      ∆     \    │
│    \    '---'   /    │
│     '._      _.'     │
│        `----`        │
╰──────────────────────╯

Hello! I'm Jelly, your squishy little assistant!
What can I do for you? :)
____________________________________________________________
 A Jelly to-do description cannot be empty!
____________________________________________________________
____________________________________________________________
 Yikes! Jelly doesn't recognize that command. Try again~
____________________________________________________________
Got it! Jelly has added this task as a to-do:
   [T][ ] buy milk

Now you have 1 tasks in your Jelly list~
____________________________________________________________
 Please enter a valid task number.
____________________________________________________________
____________________________________________________________
 Please enter a valid task number.
____________________________________________________________
Nice! Jelly has marked this task as done~
   [X] buy milk
____________________________________________________________
 Please enter a valid task number.
____________________________________________________________
Ok, Jelly has marked this task as not done yet~
   [ ] buy milk
Your Jelly Tasks :)
----------------------------------------------------------
1.[T][ ] buy milk
----------------------------------------------------------
Bye! Stay jiggly~
```

## Test case: Validate deadline and event formats

### Aim
Verify that malformed deadline and event commands are rejected, while valid
deadline and event commands are stored correctly.

### Input
```text
deadline report
deadline report /by Friday
event meeting
event meeting /from 10am
event meeting /from 10am /to 11am
list
bye
```

### Expected output
```text
╭──────────────────────╮
│      J E L L Y       │
│                      │
│       .-""""-.       │
│     .'  o  o  '.     │
│    /      ∆     \    │
│    \    '---'   /    │
│     '._      _.'     │
│        `----`        │
╰──────────────────────╯

Hello! I'm Jelly, your squishy little assistant!
What can I do for you? :)
____________________________________________________________
 Use: deadline <description> /by <date>
____________________________________________________________
Got it! Jelly has added this task as a deadline:
   [D][ ] report (by: Friday)

Now you have 1 tasks in your Jelly list~
____________________________________________________________
 Use: event <description> /from <start> /to <end>
____________________________________________________________
____________________________________________________________
 Use: event <description> /from <start> /to <end>
____________________________________________________________
Got it! Jelly has added this task as an event:
   [E][ ] meeting (from: 10am to: 11am)

Now you have 2 tasks in your Jelly list~
Your Jelly Tasks :)
----------------------------------------------------------
1.[D][ ] report (by: Friday)
2.[E][ ] meeting (from: 10am to: 11am)
----------------------------------------------------------
Bye! Stay jiggly~
```
