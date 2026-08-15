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
