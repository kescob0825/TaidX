# Sprint Documentation Template

> **Important:** Ensure all fields are filled out in a clear and structured manner. Please use the format as shown below. For questions, use **blockquotes** and provide detailed explanations where required.

Make sure you refer to the [Kickoff Document](#) for additional details and guidelines.

---

## 1: Basic Information (To be completed before you start Sprint)

### Project Information:
- **Topic Chosen**: Topic 3 API
- **Sprint Number**: 2
- **Scrum Master**: Karl
- **Git Master**: Jedi

### Sprint Planning (For Sprint 1-3):
Fill out your Sprint Planning here, based on the kickoff document and your team's discussions.

#### Sprint Goal:
- **Sprint Goal**: Further enhance and expand the types of API calls made and add UI enhancements.

#### Product Backlog & Sprint Backlog:
- **User Stories in Product Backlog**: 3
- **User Stories in Sprint Backlog**: 5

#### Sprint Planning Questions:
- **Why did you select these User Stories for this Spring?**
  > These were logically the next in development, Login and user information needed to be pulled in order to be able to call stats and other information.

#### Sprint Planning Questions:
- **Why do you think you can complete them in this Sprint?**
  > We don't think we can complete the entire backlog of US's, but we wanted enough tasks with different skill sets to allow everyone a chance to contribute

- **How do these User Stories align with the Sprint Goal?**
  > They all enhance the UI and the API allow the client to call and gather all needed information

- **Do you have a clear idea of the tasks needed to complete these User Stories? (If not, please notify via Slack.)**
  > Yes and if things are not clear we will call a design meeting.

---

## 2: During the Sprint

> Keep this section updated regularly with your meeting minutes, progress, and reflections. This will also assist you with your retrospective and contributions.

### Meeting Minutes (Daily Scrum):
Track meeting notes and progress. Fill in the table below during each Daily Scrum meeting.

| **Date**    | **Who Did Not Attend**                 | **Meeting Notes**                                             | **Burndown Status** (Sprint 2 onwards) | **GitHub Actions Status** (Sprint 4 onwards) | **Additional Notes** |
|-------------|----------------------------------------|---------------------------------------------------------------|----------------------------------------|---------------------------------------------|----------------------|
| _2/5/2025_  | _Everyone Showed_                      | _Sprint planning and creating tasks for User Stories_         | _On track_               | _Pass/Fail_                                 | _Other relevant info_ |
| _2/10/2025_ | _Mohammad didn't show but gave status_ | _Further spring planning and deleting a US due to redundency_ | _On track_               | _Pass/Fail_                                 | _Other relevant info_ |
| _2/12/2025_ | _Mohammad didn't show up_              | _Checking up on everyone's progress_                          | _On track_               | _Pass/Fail_                                 | _Other relevant info_ |
| _2/16/2025_ | _Everyone showed_                      | _Finishing US and tasks_                                      | _On track_               | _Pass/Fail_                                 | _Other relevant info_ |
---

## 3: After the Sprint

### Sprint Review (Answer as a team)
#### Screen Cast Link:  
[YouTube Screencast](https://www.youtube.com/watch?v=cf3RAHO-_Bk)

#### Sprint Review Questions:
- **What value did you create during this Sprint?**
  > For this sprint, we implemented the UI for multiple aspects of the Memoranda project to align with
  the Taiga website. We successfully integrated data from Taiga into several UI components, ensuring
  that the displayed information accurately reflects the data from the API. Overall, we met our sprint
  goal by expanding the variety of API calls used and enhancing the UI for better user experience.

- **Do you feel you worked enough and met expectations?**
  > Yes, as a group, we collaborated effectively to ensure that all user stories and tasks were Completed.
  We also verified that each component functioned as intended, meeting expectations for this sprint.

- **Did you meet the customers’ expectations? Why or why not?**
  > Yes, we believe we met the customers’ expectations for this sprint. Our goal was to further implement
  and enhance the necessary API calls and UI components, which we successfully completed.

---

### Sprint Retrospective (Answer as a team, based on evidence)
Provide clear answers based on your team’s actual data and performance.

- **Did you meet your Sprint Goal?**
  > Yes, we met our sprint goal by expanding the variety of API calls used and enhancing the UI for
  > better user experience.

- **Did you complete all User Stories in the Sprint Backlog?**
  > No, there are still 3 User Stories in the Sprint Backlog that we were unable to complete. More will be added
  > during our sprint 3 planning meeting.

- **If not, what went wrong?**
  > We underestimated the time required to implement the UI and expand the API calls. Despite this,
  we successfully completed 5 User Stories, totaling 23 tasks, which allowed us to meet our Sprint Goal
  even with the remaining items in the backlog. The amount of backend work that needed to be completed before others
  > could start or complete their work was very large. Some work was stalled in "Ready for Test" for a few days waiting
  > on CR's which looks like progress was not being made.

- **Did you work at a consistent rate or velocity?**
  > Yes, we worked consistently, pushing commits almost every day and ensuring that each modification
  did not impact on the functionality of other components.

- **Did you deliver business value and what was it?**
  > Yes, we successfully implemented a UI with API calls that accurately retrieve and display data from Taiga,
  > enhancing the project's functionality and user experience.

- **Did you follow the Scrum process (e.g., moving tasks, updating boards, etc.)?**
  > Yes, we followed the Scrum process by consistently updating the Scrum board and maintaining clear
  communication within the team. This helped us avoid conflicts when merging changes across different
  User Stories and Pull Requests. Conflicts were managed as best as possible.

- **Is there anything you could improve for the next Sprint? (For the last Sprint mention what you would do if you were to continue)**
  > Yes, we could implement additional API calls to retrieve more data for the other UIs we developed. Additionally,
  > we could improve test coverage by creating more test cases for both the UI implementations and API calls. We need to
  > manage state and update the interface when changes are detected. Currently, the pages are not dynamic.

- **How do you feel about the Sprint? (Team optimism pulse)**
  > Overall, this Sprint was both challenging and ambitious, but we successfully completed the User Stories to the
  > best of our abilities. We delivered a product that effectively demonstrates our vision for the project: integrating
  > Taiga’s API to display data accurately.

---

### Burndown Chart (For Sprint 2 and 3):
![Screenshot 2025-02-01 at 22.36.32.png](LinearProgressionChartSprint2.png)
- **Analysis of Burndown**: _Why does it look like this? What would you improve?_

> As mentioned in our Sprint Retrospective, we underestimated the time required to implement the UIs and    expand the API calls. To improve this in future sprints, we should break tasks down into smaller, more manageable units. For example, one sprint could focus solely on UI implementation and data presentation, while a subsequent sprint could focus on integrating Taiga’s API into the UIs. This approach would help us better define each User Story and task, leading to a more accurate estimation of the time required for each.

---

## 4: Contributions (Document each team member’s contributions for the Sprint)

> This section should be filled out by each team member. Below is the structure for how each team member will describe their contributions. You may copy and paste the template below for each person.

### Team Member [Andrew]

#### Consistency and Effort: 
- **Did you work consistently and contribute enough to the project?**
  > Yes, however I need to get better at pushing commits related to my US instead of just updating and working directly 
  > within my local branch. While I finished my task related to my US27 (previously assigned to US33),I added extra 
  > fields to build out and provide more substance than the minimum requirement. I was also able to push my update 
  > without any conflicts with others also on that same branch. 

- **What business value did you deliver personally?**
  > I added the individual stats page that is currently just setup for baseline data points (hardcoded values that will
  > need updated for dynamic use in later sprints) but also provides the framework to build out what can later 
  > be implemented. 

### Team Member [Edwin]

#### Consistency and Effort:
- **Did you work consistently and contribute enough to the project?**
  > Yes, I consistently pushed commits related to US27 (previously known as US33) and ensured that
  changes were merged correctly without overriding functionality implemented by others.

- **What business value did you deliver personally?**
  > I developed the UI for the Scrum Board, and with the help of Jedi, we successfully integrated data
  from Taiga to display User Stories and tasks.

### Team Member [Jedi]

#### Consistency and Effort:
- **Did you work consistently and contribute enough to the project?**
  > Yes, I tried to front load most of the critical US's that needed to be completed so others could do their work.
  > One US was to implement a way to store the state and then reload it on startup. Contributing a little too much.
  > Going to work on what is mandated in the qualitypolicy.md and allow others to take a more active role.

- **What business value did you deliver personally?**
  > Able to store state and reload it on bootup using json. Able to create new projects, send single or bulk invites, 
  > add roles, pull project information, and implement the UI for those calls. Created the backend calls to support all 
  > of those requests.

### Team Member [Jimmy]

#### Consistency and Effort:
- **Did you work consistently and contribute enough to the project?**
  > Yes I did, I commited consistently for every 2-3 days on US27 and ensured that my tables/UI elements were functional
  the values didn't contain the API implementation yet.

- **What business value did you deliver personally?**
  > I helped develop UI elements for the Scrum Board and Product Backlog. While a majority of these tables don't
  have API logic implemented yet, I believe that helping team members and potential stakeholders in a real life
  setting allows for them to see what types of plans are developing behind the scenes as well as show them what they
  can expect once the product is nearing completion.

### Team Member [Karl]

#### Consistency and Effort:
- **Did you work consistently and contribute enough to the project?**
  > Yes, I consistently committed new additions to the UI in Team Stats page under US31 throughout the
  entire sprint. Furthermore, I've completely redone the left hand panel of the Profile Page to be more
  presentable. Lastly, Bugs which were spotted by Jedi were updated to ensure that we deliver a functional
  software.

- **What business value did you deliver personally?**
  > _I developed the UI display of the Team Stats page, and redid the entire left-hand panel of the profile page.
  While the logic behind the Team Stats page has not been fully implemented yet, the UI is ready to display
  the correct data with the correct calculation to define each member's contribution. In addition, the Profile
  Page UI now displays the user's roles, number of closed US, number of projects, and number of contacts, along
  with its backend implementation to retrieve the user's stats from Taiga API_

### Team Member [Mohammed]

#### Consistency and Effort:
- **Did you work consistently and contribute enough to the project?**
  > Yes, I consistently contributed by implementing multiple Scrum boards, merging my changes, and 
  > participating in team meeetings and accepted feedback.

- **What business value did you deliver personally?**
  > I add business value by developing the UI for multiple Scrum boards, allowing the team to track
  > User Stories and tasks efficiently, improving overall productivity.

#### GitHub Links to Work:
**Please provide the links to your key contributions, on GitHub. Include **commits, pull requests**, and other important work items.**

- **GitHub Commits (Up to 5)**
    - [Commit 1](https://github.com/amehlhase316/Ruebezahl_spring25A/commit/8f30861ff097487511be5794d17b6a2c896e8dc4) - Last commit made to complete US27. We cleaned the code in multiple classes to delete tabs that were not being used and added functionality for the 'Needs Info' tab to correctly populate.
    - [Commit 2](https://github.com/amehlhase316/Ruebezahl_spring25A/commit/f7964211360cf8fcc5d016b1ae0b302b6a5eab84) - Last commit made to provide the API implementation logic of pulling milestone information and displaying it on the table.
    - [Commit 3](https://github.com/amehlhase316/Ruebezahl_spring25A/commit/feb4ee3e788f7b312ecb929ec8d34745c564c1f1) - Last commit made to complete US31 to display Team Stats, after bug fixes and edge case re-factorization.
    - [Commit 4](https://github.com/amehlhase316/Ruebezahl_spring25A/commit/85abfcfce89e5570249b204e622e85b17872cc6a) - Finish front and backend implementation of US25
    - [Commit 5](https://github.com/amehlhase316/Ruebezahl_spring25A/commit/95e8907785cabcf26328df44dbf9258dffbf2487) - Individual stats page related to US27

- **GitHub Pull Requests (Up to 3)**
    - [PR 1](https://github.com/amehlhase316/Ruebezahl_spring25A/pull/16) - PR to merge changes from US27 into Dev. This PR has multiple changes made across three USs due to the team working together to solve
      future merging conflicts.
    - [PR 2](https://github.com/amehlhase316/Ruebezahl_spring25A/pull/21) - Last PR to merge our dev branch into main. We had to create a new branch called reset-master to solve conflicts and being able to merge successfully.

- **GitHub Unit Tests (Up to 3, start in Sprint 2)**
    - [Test 1](https://github.com/amehlhase316/Ruebezahl_spring25A/actions/runs/13400434509) - Use CI to streamline JUnit test reports. 
   


- **Code Reviews (Up to 3, start in Sprint 2)**
    - [Review 1](https://github.com/amehlhase316/Ruebezahl_spring25A/pull/16) - Code Review provided by Karl to merge US27 into Dev.
    - [Review 2](https://github.com/amehlhase316/Ruebezahl_spring25A/pull/13) - Code Review provided by Edwin to merge US31 into Dev.
    - [Review 3](https://github.com/amehlhase316/Ruebezahl_spring25A/pull/12) - Code Review provided to merge US25 into dev. Mistakenly, this was pushed to master and later on was reverted to ensure we could
      merge properly into dev.


- **Static Analysis Contributions (Start in Sprint 3)**
    - [Static Analysis 1](https://github.com/amehlhase316/Ruebezahl_spring25A/actions/runs/13359598134) - Last test with CI with Gradle made when merging into main. Build and dependency-submissions passed.
    - [Static Analysis 2](https://github.com/amehlhase316/Ruebezahl_spring25A/actions/runs/13351049792) - Commit to US27 to fix a build problem on the previous commit due to missing some files. This commit fixed it and passed bot Build and dependency sumissions in CI with Gradle.
    - [Static Analysis 3](https://github.com/amehlhase316/Ruebezahl_spring25A/actions/runs/13350078859) - This was a PR from US31 into main, successfully passed both build and dependency submission in CI with gradle.
---

## 5: Sprint Completion Checklist (optional)

Before submitting your Sprint deliverables, make sure to check the following items:

- [X] This document is complete and well-formatted.
- [X] Your software is on the master branch on GitHub, it is tested and compiles/runs.
- [X] Your document is on your GitHub repository.
- [X] Read the Kickoff Document again to ensure you followed all instructions.
- [X] User Stories that were not completed have been moved to the next Sprint’s backlog.
- [X] Quality Policies are up-to-date and accurate.
- [X] Individual Sprint Surveys have been submitted (one per team member).
    - [X] Team member Jedi
    - [X] Team member Edwin
    - [X] Team member Karl
    - [X] Team member Jimmy
    - [X] Team member Andrew
    - [X] Team member Mohammed
- [X] The original template has been copied for the next Sprint (except for the last Sprint).
- [X] New User Stories for the next Sprint are added and well-defined.

#### For the next Sprint:
- [ ] New User Stories have acceptance tests and initial tasks.
- [ ] The Taiga board is up-to-date.
- [ ] Tasks for the next Sprint are clearly defined.

---

### Additional Notes:
- Please ensure that the links are clickable and lead directly to relevant content.
- Double-check formatting to ensure easy readability and grading.

