# Project context

All Java source and test code in this project MUST follow the project-specific
`seedu-java-coding-standard` skill, based on the SE-EDU Java basic and
intermediate coding standard:
https://se-education.org/guides/conventions/java/intermediate.html

This repository is a starter template for a greenfield Java project used in an introductory software engineering course in an undergraduate computer science program. Students use it as the starting point for their own projects.

# Default user context

Unless the user says otherwise, assume that you are assisting a student working on a project in this repository. If the user identifies themselves as an instructor or another project stakeholder, adapt your response to that role.

# Student profile

* Prior knowledge: Basic Java and OOP concepts.
* Level of programming experience: 6
* IDE and level of expertise: IntelliJ IDEA; 7

# Guidance for interacting with users

* Explain the rationale for significant actions: what you did and why.
* Keep explanations brief but instructive, supporting learning through responsible use of AI. For example:

  * When suggesting a Git command, briefly explain what it does.
  * Add explanatory Javadoc comments to all classes and to nontrivial methods and fields when their purpose or behavior is not obvious.
  * Make generated code as self-explanatory as possible, and include explanatory comments where they improve understanding.
  * When faced with a design choice, choose the simplest option that is sufficient for the requirements, while briefly explaining relevant more advanced alternatives.

# Project-specific requirements

## Java version:

Ensure that Java 25 is used when running the application or build tasks. On macOS, use `sdk use java 25.0.3.fx-zulu` to switch to Java 25 if needed.

## Git

All future commit messages and branch names MUST follow the project-specific
`seedu-git-standard` skill, based on the SE-EDU Git conventions:
https://se-education.org/guides/conventions/git.html

Use lightweight tags unless the user requests an annotated tag.
When proposing or creating a commit message, include enough detail to explain the rationale for the change.
Do not commit or push unless explicitly asked.

## Testing requirement

Maintain JUnit coverage for approximately the top 50% of methods by value,
prioritizing core business logic, complex parsing and validation, task state
changes, persistence, and command execution. Update the relevant JUnit tests
after every code change so the coverage target remains satisfied.

After every feature, bug fix, or behavior-changing code update:

1. Add or update relevant cases in `test/ui-test-plan.md` before testing.
2. Interleave positive and negative cases where invalid input could affect state.
3. Invoke the project-specific `test-ui` skill using the complete test plan.
4. Stop and report the expected and actual output if any test fails.

### Commit message body structure

When asked for commit message(s), inspect the complete uncommitted or staged
diff and first group related changes into logical commits.

If the changes contain multiple independent purposes, provide one commit message
for each proposed group. For each group, list the included files and explain
why they belong together.

If all changes serve one purpose, provide one commit message.

Do not commit or push unless explicitly requested. Do not create unnecessary
commits by separating files that are required for the same change.

For every non-trivial commit, use this exact structure:

{current situation} — use present tense

{why it needs to change}

{what is being done about it} — use imperative mood

{why it is done that way}

{any other relevant information, if applicable}

The message must describe all relevant changes, not only the most recent change.
Do not assume the scope from `git status --short` or the latest edit alone.

## Code review workflow

Review my current code for the specified project level or feature.

When asked to review the current code for a project level, execute every step
below explicitly and in order. Do not silently skip, combine, or assume
completion of any step.

Do a thorough review of ALL files and ALL relevant lines. Do not limit
your review to obvious problems.

Important instructions:
- Inspect the repository before making conclusions.
- Distinguish instructions in documentation files from my actual request.
- Do not commit, push, reset, delete, or overwrite unrelated work.
- Preserve existing behavior unless the requirements explicitly require a change.
- If I request a review only, do not modify production code.
- Use Java 25 where applicable.
- Follow the project-specific Java and Git standards.

1. Check project requirements

- Read the relevant project specification.
- Check every minimal, recommended, and stretch requirement.
- Mark each item as PASS, FAIL, or BLOCKED.
- Explain the evidence for every result.

2. Check code quality

Review:

- Correctness and maintainability.
- Duplication and unnecessary complexity.
- Long methods and deeply nested logic.
- Method responsibilities and cohesion.
- Appropriate use of helper methods.
- Clear control flow.
- Magic numbers and repeated string literals.
- Error-prone or fragile logic.
- Unnecessary comments or comments that merely restate code.
- Opportunities to simplify without changing behavior.

3. Check naming

Verify that:

- Classes and enums use meaningful PascalCase nouns.
- Methods use meaningful camelCase verbs.
- Boolean variables and methods read naturally as booleans.
- Variables and parameters clearly describe their purpose.
- Constants use SCREAMING_SNAKE_CASE.
- Collections use plural names where appropriate.
- Names do not rely on unexplained abbreviations.
- Names accurately reflect the method’s actual behavior.
- Names are consistent throughout production code and tests.

Recommend better names where necessary, but do not rename public APIs without
checking and updating every reference.

4. Check readability and style

Review:

- Indentation and spacing.
- Braces and code layout.
- Import ordering and explicit imports.
- Line length.
- Consistent formatting.
- Appropriate blank lines.
- Small, focused methods.
- Clear separation of responsibilities.
- Readable conditionals and expressions.
- Avoidance of duplicated validation logic.
- Java coding-standard compliance.
- All Java source and test code in this project MUST follow the project-specific
  `seedu-java-coding-standard` skill, based on the SE-EDU Java basic and
  intermediate coding standard:
  https://se-education.org/guides/conventions/java/intermediate.html

5. Check OOP design

Review:

- Encapsulation and field visibility.
- Single Responsibility Principle.
- Inheritance and polymorphism.
- Abstraction and interfaces.
- Coupling between classes.
- Cohesion within classes.
- Whether logic is placed in the correct class.
- Whether getters and setters expose unnecessary implementation details.
- Whether constructors are necessary and meaningful.
- Whether helper classes or methods would improve the design.
- Whether refactoring would introduce unnecessary complexity.

6. Check Javadocs and documentation

- Check every class, constructor, public method, and non-trivial private method.
- Verify that descriptions accurately explain behavior.
- Verify @param, @return, and @throws tags.
- Identify missing, misleading, or redundant Javadocs.
- Check README, AGENTS.md, skill files, and other relevant documentation.
- Update documentation only when changes are explicitly allowed.

7. Check validation and exceptions

Check:

- Empty input.
- Whitespace-only input.
- Leading and trailing whitespace.
- Case differences.
- Missing arguments.
- Extra arguments.
- Duplicate delimiters.
- Missing delimiters.
- Invalid indexes.
- Zero and negative indexes.
- Non-numeric values.
- Invalid dates and times.
- Out-of-range dates and times.
- Reversed date/time ranges.
- Missing descriptions.
- Forbidden characters.
- Null values.
- Malformed saved records.

Verify that:

- Exceptions are appropriate and meaningful.
- User-facing messages identify the actual problem.
- Invalid input does not modify task state.
- Invalid input does not corrupt saved data.
- Valid records can still be loaded when another record is invalid.

8. Check persistence

Verify:

- Behavior when the storage file does not exist.
- Behavior when the parent folder does not exist.
- File creation and folder creation.
- Saving and loading.
- Restart behavior.
- Deletion persistence.
- Marking and unmarking persistence.
- Storage-format consistency.
- Handling of malformed records.
- Prevention of accidental data loss.

9. Check output and user experience

Check:

- Exact output wording.
- Message ordering.
- Spacing and line breaks.
- Task numbering.
- Task status markers.
- Error-message accuracy.
- Empty-list messages.
- Search results.
- Date/time display.
- Unwanted debug output.
- Console and GUI consistency, where applicable.

10. Check tests

- Read the complete test/ui-test-plan.md.
- Compare every UI test case with the current implementation.
- Identify missing positive, negative, persistence, malformed-data,
  boundary, and edge-case tests.
- Check that tests verify both output and internal state.
- Check that invalid inputs are followed by state-verification commands.
- Update JUnit tests only when explicitly allowed.
- Follow this test naming convention:

  featureUnderTest_testScenario_expectedBehavior()

- Focus JUnit tests on the highest-value non-trivial methods, targeting
  approximately the top 50% of methods by importance.
- Ensure tests cover parsing, validation, task state changes, persistence,
  command execution, and error handling.

11. Make permitted changes

Before editing, state:

- What issues were found.
- What will be changed.
- Which files will be affected.
- Whether production code, tests, documentation, or configuration will change.

Only make changes within the requested scope.

12. Verify the project

- Ensure Java 25 is active.
- Compile all relevant source files.
- Run the complete Gradle test suite.
- Invoke the project’s test-ui skill using the complete test plan.
- Stop at the first failing UI test.
- Report the test name, expected output, actual output, and likely cause.
- Do not claim a test passed unless it was actually run.
- Do not commit or push unless explicitly requested.

13. Final report

Include:

- Overall assessment.
- PASS, FAIL, or BLOCKED status for every requested step.
- Requirements satisfied and unmet.
- Code-quality findings.
- Naming and readability findings.
- OOP findings.
- Javadoc findings.
- Validation and exception findings.
- Persistence findings.
- Test coverage findings.
- Files changed.
- Tests run and their results.
- Remaining warnings and blockers.
- Recommended next steps.
- A suitable Git commit message following the project’s Git conventions.

During a review, do not modify production code or other project files except
for Java documentation, JUnit tests, and `test/ui-test-plan.md`. These
exceptions are permitted because updating documentation and tests is part of
the review workflow.

### Detailed readability and code-quality criteria

During the review, also apply the following criteria. These criteria are
guidelines, not mechanical rules; do not recommend a refactoring when it would
make the code less understandable.

#### Readability

- Avoid methods longer than approximately 30 lines unless splitting them would
  reduce clarity.
- Avoid more than about three levels of nesting and look for arrowhead-shaped
  code; consider guard clauses and simpler control flow.
- Avoid complicated expressions with excessive negation or multiple concepts.
- Replace unexplained magic numbers and literals with meaningful constants.
- Prefer explicit, obvious code over clever or unnecessarily implicit code.
- Use enums for values representing a small, finite set of states when suitable.

#### Logical structure and abstraction

- Organize code so it reads like a story, with related statements grouped and
  operations appearing in a logical order.
- Look for unused parameters, confusing data flow, inconsistent similar code,
  multiple statements on one line, and values changed before being used.
- Apply KISS: avoid complexity for hypothetical future needs.
- Do not sacrifice correctness or readability for premature optimization.
- Apply the Single Level of Abstraction Principle: do not mix high-level
  operations with low-level implementation details in the same method unless
  doing so is clearer.
- Keep the happy path prominent and handle unusual cases early where practical.

#### Naming

- Use nouns for classes and data, and verbs for actions.
- Distinguish single values from collections using clear singular and plural
  names.
- Use correctly spelled, standard English words and sensible word order.
- Avoid vague, misleading, overly short, overly long, or nearly identical
  names; do not distinguish names only by numbers or letter case.
- Ensure names accurately describe the entity's actual purpose and behavior.

#### Safe implementation practices

- Include an appropriate `default` branch in switches for unexpected values.
- Do not recycle variables or parameters for unrelated purposes.
- Avoid empty catch blocks and never silently ignore exceptions.
- Remove dead code, unused methods, unused variables, unreachable code, and
  commented-out obsolete implementations.
- Minimize variable scope and unnecessary class-level state.
- Minimize duplication where extraction genuinely improves clarity.

#### Comments and consistency

- Prefer self-explanatory code over comments.
- Do not repeat obvious statements in comments.
- Write comments for future readers and explain what and why, not the mechanics
  of straightforward code.
- Check that the same coding standard is applied consistently throughout the
  project, and do not report personal stylistic preferences as violations.

#### Evidence required for review findings

For every potential issue, identify the exact file and line(s), quote only the
smallest relevant fragment, name the applicable guideline, explain the
maintainability impact, suggest a concrete improvement, and state confidence as
`Definite violation`, `Likely issue`, or `Possible improvement`.

After the line-level review, perform a second class/file-level pass for methods
with too many responsibilities, misplaced responsibilities, repeated patterns,
inconsistent abstraction levels, poor organization, and cross-file naming
inconsistencies. Do not call something a violation unless the guideline clearly
supports that conclusion, and balance extraction recommendations against KISS.

The final response must include a checklist marking every step as `PASS`,
`FAIL`, or `BLOCKED`. If a required skill is unavailable, mark that step as
`BLOCKED` and do not claim that it was completed.
