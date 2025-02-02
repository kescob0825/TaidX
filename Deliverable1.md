# Sprint Documentation Template

> **Important:** Ensure all fields are filled out in a clear and structured manner. Please use the format as shown below. For questions, use **blockquotes** and provide detailed explanations where required. 

Make sure you refer to the [Kickoff Document](#) for additional details and guidelines. 

---

## 1: Basic Information (To be completed before you start Sprint)

### Project Information:
- **Topic Chosen**: _Topic 3_
- **Sprint Number**: _1_
- **Scrum Master**: _Jedi_
- **Git Master**: _Edwin_

### Sprint Planning (For Sprint 1-3):
Fill out your Sprint Planning here, based on the kickoff document and your team's discussions.

#### Sprint Goal:
- **Sprint Goal**: _Fix bugs, and enhance memoranda to get ready for Taiga API implementation in sprint 2._

#### Product Backlog & Sprint Backlog:
- **User Stories in Product Backlog**: _8_
- **User Stories in Sprint Backlog**: _13_

#### Sprint Planning Questions:
- **Why did you select these User Stories for this Sprint?**
    > _9 were provided and the one that was added was to create a functional user interface to be able to interact with the API implemented in Sprint 2+._
  > _Our team added three US's late in the Sprint due to our pace._

 #### Sprint Planning Questions:
- **Why do you think you can complete them in this Sprint?**
    > _8 of these tasks are a light load and the 2 that are not have enough tasks to be spread throughout the team._

- **How do these User Stories align with the Sprint Goal?**
    > _They all enhance the user experience by fixing bugs, improving the UI and put the team in a position to implement API features in sprint 2+._

- **Do you have a clear idea of the tasks needed to complete these User Stories? (If not, please notify via Slack.)**
    > _For this Sprint US5 is ambiguous need to read out to PO to get a better understanding, however there are some nuances that will need to be ironed out for some US's in the PB._

---

## 2: During the Sprint

> Keep this section updated regularly with your meeting minutes, progress, and reflections. This will also assist you with your retrospective and contributions.

### Meeting Minutes (Daily Scrum):
Track meeting notes and progress. Fill in the table below during each Daily Scrum meeting.

| **Date**   | **Who Did Not Attend** | **Meeting Notes**                                                                   | **Burndown Status** (Sprint 2 onwards) | **GitHub Actions Status** (Sprint 4 onwards) | **Additional Notes**|
|------------|------------------------|-------------------------------------------------------------------------------------|----------------------------------------|---------------------------------------------|----------------------|
| _1/20/2025_| _Noel Trujillo_        | _Discuss group assignment, vote for Scrum and Git Master, vote for topic_           | _On track/Behind/Ahead_                | _Pass/Fail_                                 | _Other relevant info_ |
| _1/24/2025_| _Noel Trujillo_        | _10 US, discuss US, tasks added. Layout design discussed and approved _             | _On track/Behind/Ahead_                | _Pass/Fail_                                 | _Other relevant info_ |
| _1/24/2025_|                        | _Status update, 3 task not assigned. Mohammed and Noel do not have tasks_           | _On track/Behind/Ahead_                | _Pass/Fail_                                 | _Other relevant info_ |
|            |                        | _Every member updated their progress, Mohammed and Noel will select tasks today_    |                                        |                                             |                       |
|            |                        | _Three tasks left unnasigned, we might have to create more task or USs to meet req._|                                        |                                             |                       | 
|_1/27/2025_ | _Noel Trujillo_        | _US50 and US28 added_                                                               |                                        | _Pass/Fail_                                 |                       | 
|_1/30/2025_ | _Noel Trujillo_        | _US7 has version 2 of the project, will be pushed during the second sprint._        | _On track/Behind/Ahead_                |                                             |                       |                                        
| _Date_     | _Name(s)_              | _Brief notes_                                                                       | _On track/Behind/Ahead_                | _Pass/Fail_                                 | _Other relevant info_ |
 

---

## 3: After the Sprint

### Sprint Review (Answer as a team)
#### Screen Cast Link:  
- _Insert the link to your Sprint review screencast here._

#### Sprint Review Questions:
- **What value did you create during this Sprint?**
    > We established a link between the UI and the Taiga API. This creates value
  > because the token allows a user to be able to request
  > information on projects, US's, tasks, metadata on all of those, stats on
  > on the Scrum, and other calls. 

- **Do you feel you worked enough and met expectations?**
    > Everyone put in work on the sprint and tasks. There was a bit of a skill gap between some due to API's being novel, 
  > but those who had worked with them produced resources to help others learn the basics and replicate these calls
  > on postman or boomerang. We will work to close this gap as the expectations will be higher in sprint 2 being heavy
  > in the API calls and parsing the JSON data.

- **Did you meet the customers’ expectations? Why or why not?**
    > We met the minimum standard for out v1.0 release. We have kept our main release shelved until sprint 2. 
  > We fixed all the bugs that were causing issues with functionality and introduced some new ones.
  > Our US28 while finished, houses our v2.0. Having done a complete UI overhaul to better line up with the core
  > functionality of the app we believe it was in the best interest of the customer to not release an incomplete
  > implementation of the product. After we do our PR to master from our tested dev, we will force push US28 to dev
  > and that will be our new dev branch.

---

### Sprint Retrospective (Answer as a team, based on evidence)
Provide clear answers based on your team’s actual data and performance.

- **Did you meet your Sprint Goal?**
    > _Your Answer_

- **Did you complete all User Stories in the Sprint Backlog?**
    > Yes, all ten original US's were complete in addition to the 3 added later in Sprint.
> 1. US1 window closes when the "x" is pressed without the process continuing.
> 2. US2 Splash screen has been changed.
> 3. US3 window now minimizes to the taskbar without disappearing.
> 4. US4 *****
> 5. US5 Look and feel was deleted from preferences and added to top menu as "Theme"
> 6. US6 UI has been overhauled. Useless classes were deleted.
> 7. US7 Left side icons reflect functionality.
> 8. US8 UML document drafted ****
> 9. US9 All deprecation warnings fixed. A few xLint warnings still appear due to old libraries used with old gradle build.
> 10. US28 Api implemented that pulls all data for the US's and prints to the console. Need to build UI to display information
> 11. US34 Authenicate and TaigaLoginDialog classes implemented and operational.
> 12. US47 use a singleton object to pass into UI components. Same instance used program wide.
> 13. US50 upon signout auth and refresh tokens assigned to NULL. 

- **If not, what went wrong?**
    > _Your Answer_

- **Did you work at a consistent rate or velocity?**
    > _Your Answer_ (Provide evidence: task completion rate, Git commit rate, etc.)

- **Did you deliver business value and what was it?**
    > _Your Answer_

- **Did you follow the Scrum process (e.g., moving tasks, updating boards, etc.)?**
    > _Your Answer_

- **Is there anything you could improve for the next Sprint? (For the last Sprint mention what you would do if you were to continue)**
    > _Your Answer_

- **How do you feel about the Sprint? (Team optimism pulse)**
    > _Your Answer_

---

### Burndown Chart (For Sprint 2 and 3):
Please include a screenshot of your Burndown chart here, and provide a detailed analysis. 
- **Analysis of Burndown**: _Why does it look like this? What would you improve?_
  
> _Your Answer_

---

## 4: Contributions (Document each team member’s contributions for the Sprint)

> This section should be filled out by each team member. Below is the structure for how each team member will describe their contributions. You may copy and paste the template below for each person.

### Team Member [Jedi]

#### Consistency and Effort:
- **Did you work consistently and contribute enough to the project?**
    > Yes, I completed the most tasks, hosted meetings. Debugged and tested other peoples code.
  > Was lead on overhauling the UI for the unreleased v2.0. Coordinated design talks and the standup meetings.
  > Taught others how to make the api calls (WIP) Populated the Scrumboard, product backlog with additional
  > US's, AC's, and tasks. 

- **What business value did you deliver personally?**
    > I worked with Karl to create the initial api authorization token with Karl. He created the UI and I worked on 
  > the backend implementation of the TaigaClient. I would later work on the API modules (Projects and User Story) and models which will be instrumental
  > in the future sprints. I also worked on a UI overhaul that modernized the app and deleted the antiquated functionality of the
  > app. This will be viewable on the dev branch. 
### Team Member [Jimmy]

#### Consistency and Effort:
- **Did you work consistently and contribute enough to the project?**
  > I personally think I worked consistently and contributed enough for the project; I spent time trying to understand the logic of how the memoranda software works and consulted my team members about features/functions that we deemed not important towards our grand goal of implementing the Taiga API into the software. At first I attempted to fix the features to make it more usable but it seems that the logic is either faulty or there are issues with JavaFX that make it simply impossible to fit the functions we want. This led me to push for the removal of the resource tab as well as associated functions related to this feature allowing me to sift through the code and remove it without the introduction of any bugs or faults.

- **What business value did you deliver personally?**
  > I believe that by removing files and features that aren't desired or needed anymore and taking the time to remove all aspects of it including pngs and calls makes troubleshooting in the future much easier since if we simply made it functional by disabling the feature instead of deleting it and an engineer were to do a code review over an unrelated bug/feature, then they would potentially waste tons of time sifting through useless code that should really be not be there in the first place.
### Team Member [Edwin]

#### Consistency and Effort:
- **Did you work consistently and contribute enough to the project?**
  > _Your Answer_

- **What business value did you deliver personally?**
  > _Your Answer_
### Team Member [Karl]

#### Consistency and Effort:
- **Did you work consistently and contribute enough to the project?**
  > _Your Answer_

- **What business value did you deliver personally?**
  > _Your Answer_
### Team Member [Andrew]

#### Consistency and Effort:
- **Did you work consistently and contribute enough to the project?**
  > _Your Answer_

- **What business value did you deliver personally?**
  > _Your Answer_
### Team Member [Mohammed]

#### Consistency and Effort:
- **Did you work consistently and contribute enough to the project?**
  > _Your Answer_

- **What business value did you deliver personally?**
  > _Your Answer_
#### GitHub Links to Work:
Please provide the links to your key contributions on GitHub. Include **commits, pull requests**, and other important work items.

- **GitHub Commits (Up to 5)**
    - [Commit 1](link) - _Short description of what the commit is about_
    - [Commit 2](link) - _Short description of what the commit is about_

- **GitHub Pull Requests (Up to 3)**
    - [PR 1](link) - _Short description of the PR_
    - [PR 2](link) - _Short description of the PR_

- **GitHub Unit Tests (Up to 3, start in Sprint 2)**
    - [Test 1](link) - _Short description of the test_
    - [Test 2](link) - _Short description of the test_

- **Code Reviews (Up to 3, start in Sprint 2)**
    - [Review 1](link) - _Short description of the code review_
    - [Review 2](link) - _Short description of the code review_

- **Static Analysis Contributions (Start in Sprint 3)**
    - [Static Analysis 1](link) - _Short description of the analysis contribution_
    - [Static Analysis 2](link) - _Short description of the analysis contribution_

---

## 5: Sprint Completion Checklist (optional)

Before submitting your Sprint deliverables, make sure to check the following items:

- [ ] This document is complete and well-formatted.
- [ ] Your software is on the master branch on GitHub, it is tested and compiles/runs.
- [ ] Your document is on your GitHub repository.
- [ ] Read the Kickoff Document again to ensure you followed all instructions.
- [ ] User Stories that were not completed have been moved to the next Sprint’s backlog.
- [ ] Quality Policies are up-to-date and accurate.
- [ ] Individual Sprint Surveys have been submitted (one per team member).
  - [ ] Team member Jedi
  - [ ] Team member Edwin
  - [ ] Team member Karl
  - [ ] Team member Jimmy
  - [ ] Team member Andrew
  - [ ] Team member Mohammed
- [ ] The original template has been copied for the next Sprint (except for the last Sprint).
- [ ] New User Stories for the next Sprint are added and well-defined.

#### For the next Sprint:
- [ ] New User Stories have acceptance tests and initial tasks.
- [ ] The Taiga board is up-to-date.
- [ ] Tasks for the next Sprint are clearly defined.

---

### Additional Notes:
- Please ensure that the links are clickable and lead directly to relevant content.
- Double-check formatting to ensure easy readability and grading.

