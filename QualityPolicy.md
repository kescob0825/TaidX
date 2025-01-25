### Quality Policy
> To ensure quality is adhered to, team members will meet 3 times a week in a standup. If unable to attend a detailed
> message on progress will be sent to the Scrum Master in the _standup channel. During the brief standup meeting, team
> members will let the SM know of progress, impediments, and an estimated completion time. SM will go over the Scrum
> Board to ensure it is updated before signing off. Team members are expected to be transparent about progress. 
> Team members are expected to make incremental progress and complete ~2 CR's w/ meaningful comments and checklist per sprint,
> ~500 lines of code per sprint, 5 tasks per sprint, no more than 1-2 tasks at once, update taiga SB witch progress,
> and communicate with the team. Team will adhere to a rigid tree structure for managing the remote repo.

## PR Checklist:
### 1. Code Quality
- [ ] Code compiles without errors or warnings.
- [ ] No unnecessary debugging statements (e.g., `console.log`, `print`, or commented-out code).
- [ ] Code follows the project's coding standards and style guide (e.g., linting rules).
- [ ] Naming conventions for variables, functions, and files are clear and meaningful.
- [ ] Code is modular, with functions/classes logically grouped.

### 2. Testing
- [ ] New code has corresponding unit tests.
- [ ] Existing tests pass without failures (`./gradlew test`, `npm test`, `pytest`, etc.).
- [ ] Manual testing has been performed where applicable.
- [ ] Edge cases and error handling are properly addressed.

### 3. Documentation
- [ ] Code comments explain complex logic and reasoning.
- [ ] Public methods and APIs have appropriate documentation (e.g., Javadoc, docstrings).
- [ ] README or relevant documentation files are updated if necessary.
- [ ] Commit messages are clear and follow the project guidelines (e.g., Conventional Commits).

### 4. Git Best Practices
- [ ] Branch name follows project conventions (e.g., `feature/xyz`, `bugfix/abc`).
- [ ] Commit history is clean, and unnecessary commits have been squashed if needed.
- [ ] No large binary files or sensitive data included.
- [ ] PR is scoped appropriately (not too large or too small).
<<<<<<< HEAD
- [ ] Each user story branch should be occupied by 1 person to prevent any conflicts with merges.
=======
>>>>>>> 4bd13d37bde6563a5ec86e6a51c0e43e02697e56

### 5. Dependencies and Configuration
- [ ] Dependencies have been reviewed and unnecessary ones removed.
- [ ] Configuration files (e.g., `.env`, `application.properties`) are correctly set and not included in commits.

### 6. Cross-Functional Concerns
- [ ] Code follows security best practices (e.g., no hardcoded credentials, input validation).
- [ ] Performance optimizations have been considered.
- [ ] Localization and accessibility have been taken into account if applicable.

### 7. PR Readiness
- [ ] PR title and description are clear and descriptive.
- [ ] Linked to relevant issues/tickets (e.g., "Closes #123").
- [ ] Relevant reviewers, assignees, and labels have been added.
- [ ] Screenshots or GIFs attached if UI changes are included.

## PR Checklist for reviewer:

### 1. Code Quality
- [ ] Does the code meet the project’s style and formatting guidelines?
- [ ] Is the code maintainable and scalable?
- [ ] Are functions/methods/classes appropriately sized and named?
- [ ] Are there any redundant or duplicate pieces of code?

### 2. Functional Correctness
- [ ] Does the code accomplish what the PR intends to fix or implement?
- [ ] Are all edge cases considered and handled correctly?
- [ ] Have potential bugs or unintended side effects been considered?

### 3. Testing
- [ ] Do existing tests pass after the changes?
- [ ] Are there sufficient new tests covering the changes?
- [ ] Have integration tests been considered where applicable?

### 4. Performance Considerations
<<<<<<< HEAD
- [ ] Is the code optimized for performance (e.g., no unnecessary loops, minimal memory usage, proper data structures are used)?
=======
- [ ] Is the code optimized for performance (e.g., no unnecessary loops, minimal memory usage)?
>>>>>>> 4bd13d37bde6563a5ec86e6a51c0e43e02697e56
- [ ] Have any potential performance bottlenecks been addressed?
- [ ] Are database queries optimized and efficient?

### 5. Security and Compliance
- [ ] Are security best practices followed (e.g., input validation, sanitization)?
- [ ] Are sensitive data and credentials properly handled and excluded from commits?
- [ ] Are external dependencies up-to-date and secure?

### 6. Documentation
- [ ] Are function/method/class comments sufficient and up-to-date?
- [ ] Has the documentation been updated if the feature requires it?
- [ ] Is the PR description clear and informative?

### 7. Usability (If Applicable)
<<<<<<< HEAD
- [ ] Is the feature user-friendly and intuitive(can a random person who has no idea how this software works use this without confusion?
=======
- [ ] Is the feature user-friendly and intuitive?
>>>>>>> 4bd13d37bde6563a5ec86e6a51c0e43e02697e56
- [ ] Are UI/UX changes visually consistent with the rest of the application?
- [ ] Have accessibility considerations been met?

### 8. Compliance with Project Requirements
- [ ] Does the PR adhere to the specifications or acceptance criteria?
- [ ] Does the implementation align with architectural guidelines?

### 9. Git Practices
- [ ] Are commits well-structured and meaningful?
<<<<<<< HEAD
- [ ] Do commits have detailed comments explaining what changes are taking place?
=======
>>>>>>> 4bd13d37bde6563a5ec86e6a51c0e43e02697e56
- [ ] Is the branch rebased correctly and free of merge conflicts?
- [ ] Is the PR scoped appropriately (not too broad or too narrow)?

### 10. Providing Feedback
- [ ] Provide constructive and actionable feedback.
- [ ] Highlight positive aspects of the code.
- [ ] Ask questions if any functionality is unclear.
- [ ] Use inline comments for specific code areas.
- [ ] Approve or request changes based on the review.

**GitHub Workflow** (start Sprint 1)
  > 1. Team member will review Sprint backlog US's.
  > 2. Team member will choose task and then move it to "In-Progress".
  > 3. Team member will fetch latest master and navigate to the US# branch.
  > 4. If no task branch exists for their task number they will checkout and create one.
  > 5. Team member will work on task to completion, and commit the changes with the proper subject line. "US# Task# description"
  > 6. Upon completion, they will rebase their code with the US# they are working on and resolve conflicts.
  > 7. Before merging with the US# they will ensure it compiles and passes Unit Tests. Update Scrum Board.
  > 8. Team member will switch to the US# branch and merge the task with the US# branch and ensure it compiles. 
  > 9. Team member will assign someone to do a CR if applicable and update the _git channel on slack of progress.
  > 10. If all tasks are complete and AC's are fulfilled a PR will be made to merge the US# with the dev branch. Update Scrum Board. (git rebase -i to squash commits)
  > 11. Another team member will review changes on the protected dev branch and leave a detailed CR.
  > 12. Process will repeat until sprint ends or all US's are finished. Whichever happens first.
  > 13. Team member will update the Deliverable1.md document as needed.
  > 14. Upon Sprint completion Git Master and Scrum Master will merge the dev and doc to the master.
<<<<<<< HEAD

## Collaboration Policy: (TBD)
> In the event 2 or more team members are working on features that interact with each other
  > 1.Team members will notify the Scrum Master (SM) and the team in Discord when their tasks involve interacting features. 
  > 2.A brief discussion will be held during the standup or planning session to outline dependencies, shared responsibilities, and integration points(IE: Person A is reworking a feature that has dependencies and should expect certain features to not work until the task is finished.
  > 3.If 2 or more members are working on a feature, both members should communicate and coordinate commits and merges to ensure smooth changes and prevent and conflicts before merging.
  > 4.If a conflict occurs during a commit, both team members will need to coordinate and resolve conflicts collaboratively to ensure application functionality.
  > 5. Once dependent features are done and no conflicts exist, notify the Scrum Master to complete the tasks.
=======
>>>>>>> 4bd13d37bde6563a5ec86e6a51c0e43e02697e56

**Unit Tests Blackbox** (start Sprint 2)
  > Describe your Blackbox testing policy 

 **Unit Tests Whitebox** (online: start Sprint 2, campus: start Sprint 3)
  > Describe your Whitebox testing policy 

**Code Review** (online: start Sprint 2, campus: start Sprint 2)
  > Describe your Code Review policy for on campus it is ok to have a less formal process in Sprint 2,
  > should be updated in Sprint 3 though

<<<<<<< HEAD
  > Include a checklist/questions list which every developer will need to fill out/answer when creating a
  > Pull Request to the Dev branch. 

  > Include a checklist/question list which every reviewer will need to fill out/answer when conducting a
=======
  > Include a checklist/questions list which every developer will need to fill out/answe when creating a
  > Pull Request to the Dev branch. 

  > Include a checklist/question list which every reviewer will need to fill out/anser when conducting a
>>>>>>> 4bd13d37bde6563a5ec86e6a51c0e43e02697e56
  > review, this checklist (and the answers of course) need to be put into the Pull Request review.

**Static Analysis**  (online: start Sprint 3, campus: start Sprint 3)
  > Your Static Analysis policy   

**Continuous Integration**  (start Sprint 3, campus: start Sprint 3)
  > Your Continuous Integration policy
