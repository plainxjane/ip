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
