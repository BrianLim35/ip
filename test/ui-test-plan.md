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
todo read book
bye
```

Expected output: The chatbot confirms that `read book` was added.

## Test 3: List tasks

Aim: Verify that stored tasks are displayed in insertion order.

Input:

```text
todo read book
todo return book
list
bye
```

Expected output: The list contains `read book` as task 1 and `return book` as task 2.

## Test 4: Mark and unmark a task

Aim: Verify that a task can be marked done and then marked incomplete.

Input:

```text
todo read book
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
todo read book
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
todo read book
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

## Test 9: Invalid typed-task input is rejected

Aim: Verify that incomplete Level-4 commands are rejected without changing the task list.

Input:

```text
todo valid task
deadline return book
event project meeting
list
todo another task
list
bye
```

Expected output: The incomplete deadline and event produce error messages. The task list remains unchanged after each invalid command.

## Test 10: Empty and unknown commands are rejected

Aim: Verify that empty descriptions and unknown commands do not add tasks.

Input:

```text
todo
blah
list
bye
```

Expected output, in order:

- The empty to-do description produces an error.
- The unknown command produces an error.
- The chatbot reports that the task list is empty.

## Test 11: Malformed separators are rejected

Aim: Verify that missing and duplicate date/time separators are rejected.

Input:

```text
deadline task /by Sunday /by Monday
event meeting /from Monday
event meeting /from Monday /to 4pm /to 5pm
list
bye
```

Expected output: Each malformed command produces an error, and the task list remains empty.

## Test 12: Deadline validation identifies the correct error

Aim: Verify that deadline errors distinguish a missing description, separator, and date/time.

Input:

```text
deadline
deadline submit report
deadline /by Sunday
deadline submit report /by
bye
```

Expected output, in order:

- The empty deadline description produces an error.
- The missing `/by` separator produces an error.
- The missing deadline description produces an error.
- The missing deadline date/time produces an error.
