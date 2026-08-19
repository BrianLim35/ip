# Penguin UI Test Plan

These tests exercise the chatbot through its console input and output.

Before running a session, use an empty `data/penguin.txt` or a separate temporary working directory so saved tasks from another session do not affect the expected state.

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
event meeting /from Monday /to
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

## Test 13: Delete a task and renumber the list

Aim: Verify that deletion removes the selected task, reports the deleted task, and renumbers remaining tasks.

Input:

```text
todo read book
todo return book
todo buy bread
delete 2
list
bye
```

Expected output: The chatbot reports that `return book` was removed. The final list contains `read book` as task 1 and `buy bread` as task 2, and does not contain `return book`.

## Test 14: Invalid deletion preserves the list

Aim: Verify that invalid deletion commands do not remove or alter tasks.

Input:

```text
todo read book
delete
list
delete abc
list
delete 0
list
delete 99
list
bye
```

Expected output: Each invalid deletion produces an appropriate error. Every list still contains exactly `1. [T][ ] read book`.

## Test 15: Delete each task type

Aim: Verify that to-dos, deadlines, and events can all be deleted correctly.

Input:

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Monday 2pm /to 4pm
delete 1
list
delete 1
list
delete 1
list
bye
```

Expected output: Each deletion reports the selected task, and the final list is empty.

## Test 16: Display enum-based task types

Aim: Verify that each task subtype uses the correct `TaskType` enum symbol.

Input:

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Monday 2pm /to 4pm
list
bye
```

Expected output: The list displays `[T][ ]`, `[D][ ]`, and `[E][ ]` respectively, with each task's details unchanged.

## Test 17: Enum markers survive state changes

Aim: Verify that marking, unmarking, and deleting tasks do not change their enum-based type markers.

Input:

```text
todo read book
deadline return book /by Sunday
event meeting /from Monday /to Tuesday
mark 1
unmark 2
delete 1
list
bye
```

Expected output: The final list contains the deadline with `[D][ ]` and the event with `[E][ ]`. The deleted to-do is absent.

## Test 18: Case-insensitive typed commands retain enum types

Aim: Verify that uppercase command keywords and separators still create the correct enum-based task types.

Input:

```text
TODO buy groceries
DEADLINE pay bills /BY Friday
EVENT dentist /FROM Monday /TO Tuesday
list
bye
```

Expected output: The list displays `[T][ ]`, `[D][ ]`, and `[E][ ]` in that order.

## Test 19: Command enum dispatch

Aim: Verify that supported commands are dispatched correctly.

Input:

```text
todo read book
list
mark 1
unmark 1
delete 1
list
bye
```

Expected output: Each command performs its intended action, the deleted task is reported correctly, and the final list is empty.

## Test 20: Commands with invalid arguments

Aim: Verify that recognized commands still validate their arguments.

Input:

```text
list now
mark
delete abc
bye now
list
bye
```

Expected output: Each invalid command produces a specific error. The chatbot remains running until the final valid `bye`, and the task list remains empty.

## Test 21: Save and reload all task types

Aim: Verify that tasks are saved after changes and restored when the chatbot starts again.

First session input:

```text
todo read book
deadline return book /by Sunday
event meeting /from Monday /to Tuesday
mark 1
bye
```

Restart the chatbot, then enter:

```text
list
bye
```

Expected output after restart:

```text
1. [T][X] read book
2. [D][ ] return book (by: Sunday)
3. [E][ ] meeting (from: Monday to: Tuesday)
```

## Test 22: Save after deletion and unmarking

Aim: Verify that deletion and unmarking are persisted across restarts.

First session input:

```text
todo read book
todo return book
mark 1
unmark 1
delete 2
bye
```

Restart the chatbot, then enter:

```text
list
bye
```

Expected output after restart:

```text
1. [T][ ] read book
```

## Test 23: Missing storage file

Aim: Verify that the chatbot starts with an empty task list when the storage file does not exist.

Setup: Run the chatbot from a fresh temporary directory with no `data/penguin.txt`.

Input:

```text
list
bye
```

Expected output:

```text
Penguin: Your task list is empty!
```

## Test 24: Corrupted storage data

Aim: Verify that malformed saved data produces an error instead of silently creating incorrect tasks.

Setup: Place each malformed line below in `data/penguin.txt`, one test at a time:

```text
T | 2 | read book
D | 0 | return book
E | 0 | meeting | Monday
X | 0 | unknown task
```

Expected output: The chatbot reports a specific loading error and does not silently treat malformed data as a valid task.

## Test 25: Reserved delimiter in a description

Aim: Verify that a description containing the persistence delimiter is rejected or safely handled instead of being truncated or reloaded as a different task.

Input:

```text
todo read | book
list
bye
```

Expected output: The chatbot rejects the reserved `|` character with a clear error, both when entered with spaces (`read | book`) and without spaces (`read|book`). It must not silently save one description and reload a different one.
