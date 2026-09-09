# Jelly UI test plan

The tests run in order. The first case creates and modifies the saved task
list; the second case starts Jelly again and verifies that the saved state is
loaded from disk.

## Test case: Create, modify, validate, and save tasks

### Aim
Verify that Jelly creates all supported task types, rejects malformed input,
marks and unmarks tasks, deletes a task, and saves the final task list.

### Input
```text
todo buy milk
deadline submit report /by 2019-12-02 1800
event team meeting /from 2019-12-02 1000 /to 2019-12-02 1100
blah
mark 1
unmark 1
delete 2
list
find milk
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
   [T][ ] buy milk

Now you have 1 tasks in your Jelly list~
Got it! Jelly has added this task as a deadline:
   [D][ ] submit report (by: Dec 02 2019 18:00)

Now you have 2 tasks in your Jelly list~
Got it! Jelly has added this task as an event:
   [E][ ] team meeting (from: Dec 02 2019 10:00 to: Dec 02 2019 11:00)

Now you have 3 tasks in your Jelly list~
Yikes! Jelly doesn't recognize that command. Try again~
Nice! Jelly has marked this task as done~
   [X] buy milk
Ok, Jelly has marked this task as not done yet~
   [ ] buy milk
Congrats! Jelly has removed this task for you :)
[D][ ] submit report (by: Dec 02 2019 18:00)
Now you have 2 tasks in your Jelly list~
Your Jelly Tasks :)
----------------------------------------------------------
1.[T][ ] buy milk
2.[E][ ] team meeting (from: Dec 02 2019 10:00 to: Dec 02 2019 11:00)
----------------------------------------------------------
Here are the matching tasks in your list:
1.[T][ ] buy milk
Bye! Stay jiggly~
```

## Test case: Load saved tasks after restarting

### Aim
Verify that the remaining to-do and event are loaded from disk with their
correct task types and completion states.

### Input
```text
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
Your Jelly Tasks :)
----------------------------------------------------------
1.[T][ ] buy milk
2.[E][ ] team meeting (from: Dec 02 2019 10:00 to: Dec 02 2019 11:00)
----------------------------------------------------------
Bye! Stay jiggly~
```
