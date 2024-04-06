## Welcome to the changelog for Troll Game. 
### We always reccomend you to run the latest version. 

This is because later versions may cause instability, and/or are not easy to run/modify. The oldest versions we reccomend you to go is the v4 lineup.

**Tags:**

[overlay]: Modifications to game print statements

[remodel]: Game modifications to current features

[feature]: New feature added

[bug-fix]: Bug fix created for a known issues

[remodel/feature]: Feature added to remodeling

**Lineups are referred to specific general versions with feature additions.**

We use a modified version of semantic versioning that may be difficult to understand. By reading above, we hope you understand the versioning scheme better!

---
V1 Lineup:

v1.0: Base Update
v1.1: Secure Update (Details Unavailable)

---
V2 Lineup:

v2.0: [overlay] New Commenting, new formats, and BlueJ support

---
V3 Lineup:

v3.0-delta: 
- [feature] Soldiers are now removed when a battle is lost if difficulty >3
- [feature] If difficulty = 5, more soldiers are taken, and money workers are also taken
- [feature] Higher-interactive shop created with multiple purchase support
- [bug-fix] Added level verification system so user cannot select past level 5, or go into negatives
- [overlay]] Directions screen cleaned
- [bug-fix] Verification for numofdays played has been added, with noninteger values not causing game shutdown (try/catch)

v3.1:
- [bug-fix] Fixed shop double entry errors for submission
- [bug-fix] Check system added for level input for Java errors
- [feature] Delay method added that allows devmode selection
- [feature] Added accessible devmode by inputting "dev" on directions input, reducing delays

---
V4 Lineup

v4.0-delta:
- [feature] Winstreak system added
- [feature] If user has a win streak <4, bounty starts
- [feature] Hellhound creator summons when bounty starts
- [feature] Flee option is given, but random chance of small loss
- [feature] Fight option given. If fought and wins, mass bonus. If lost, game ends

v4.1:
- [bug-fix] Try/Catch implementation added in shop
- [feature/bug-fix] Integer parsing used instead of strings for shop selection
- [bug-fix] Duel shop occurance fixed
- [bug-fix] Shop after loop end fixed
- [overlay] Grammatical issues and formatting issues resolved
### Jar file additions start
v4.2:
- [bug-fix] Difficulty not impacting bounty system resolved
- [bug-fix] Double looping battle bug fixed
- [remodel] Commenting formats fixed
- [remodel] Better game-sense
- [remodel/feature] If difficulty <3, bounty fail causes game shut down. If less, significant amounts of money are taken
- [bug-fix] Removed thank-you message in some files due to ASCII incompatibility in BlueJ
### Full compatibility added for BlueJ and IntelliJ, Jar cleaned
v4.3:
- [remodel/feature] If difficulty is 1, initial battle descriptions are added
- [remodel/feature] If diffiuclty is 1, always win the first battle
- [bug-fix] Archie object is instantiated outside of the loop and conditionals to improve future modifications


### updated 4/5/2024 - adhere to license guidelines when using code. 
### Copyright (c) 2024 Kniev
