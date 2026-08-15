# Penguin UI Test Plan

These tests exercise the chatbot through its console input and output.

## Test 1: Greeting and exit

Aim: Verify that the chatbot displays its greeting and exits on `bye`.

Input:

```text
bye
```

Expected output: The greeting is displayed, followed by `Bye. Hope to see you again soon!`.

## Test 2: Add a task

Aim: Verify that a task is added to the task list.

Input:

```text
read book
bye
```

Expected output: The chatbot confirms that `read book` was added.

## Test 3: List tasks

Aim: Verify that stored tasks are displayed in insertion order.

Input:

```text
read book
return book
list
bye
```

Expected output: The list contains `read book` as task 1 and `return book` as task 2.

## Test 4: Mark and unmark a task

Aim: Verify that a task can be marked done and then marked incomplete.

Input:

```text
read book
mark 1
unmark 1
bye
```

Expected output: The chatbot reports the task as marked, then unmarked.

## Test 5: Create typed tasks

Aim: Verify that user input creates to-do, deadline, and event tasks with their date/time details.

Input:

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Monday 2pm /to 4pm
list
bye
```

Expected output: The list contains `[T]`, `[D]`, and `[E]` task markers, with the deadline displaying `by: Sunday` and the event displaying `from: Monday 2pm to: 4pm`.

## Test 6: Invalid mark input preserves task state

Aim: Verify that invalid task numbers produce an error and do not change existing tasks.

Input:

```text
read book
mark abc
list
mark 99
list
bye
```

Expected output, in order:

- The task is added.
- A valid-task-number error is displayed for `mark abc`.
- The list still shows `1. [ ] read book`.
- An invalid-index error is displayed for `mark 99`.
- The list still shows `1. [ ] read book`.

## Test 7: Invalid unmark input preserves completed state

Aim: Verify that invalid unmark commands do not undo a valid completion.

Input:

```text
read book
mark 1
unmark abc
list
bye
```

Expected output, in order:

- The task is marked done.
- A valid-task-number error is displayed for `unmark abc`.
- The list still shows `1. [X] read book`.

## Test 8: Empty input does not add a task

Aim: Verify that an empty command is rejected without changing the task count.

Input:

```text

list
bye
```

Expected output, in order:

- An empty-input error is displayed.
- The chatbot reports that the task list is empty.

## Test 9: Incomplete typed-task input is recorded for future error handling

Aim: Expose Level-5 validation gaps for incomplete Level-4 commands.

Input:

```text
deadline return book
event project meeting
list
bye
```

Expected output: The current implementation records these commands with empty date/time values. This test should be changed to expect error messages when Level-5 validation is implemented.
