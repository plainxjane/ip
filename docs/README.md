# Jelly User Guide

Jelly is a lightweight task manager with support for to-do tasks, deadlines,
events, completion states, searching, and tags. It is available through both
the command-line interface and the JavaFX interface.

## Adding deadlines

Use `deadline <description> /by yyyy-mm-dd HHmm` to create a deadline.
For example:

`deadline submit report /by 2026-09-30 1800`

Jelly confirms the new deadline and saves it to the local data file.

```
expected output
```

## Other commands

* `todo <description>` adds a to-do task.
* `event <description> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm` adds an event.
* `list` displays all tasks.
* `mark <task number>` and `unmark <task number>` update completion state.
* `find <keyword>` searches task descriptions.
* `tag <task number> <tag>` and `untag <task number>` manage tags.
* `delete <task number>` removes a task.
* `bye` exits Jelly.

Malformed commands and invalid saved records are reported or skipped safely;
they do not terminate the application.
