# Dec 01 meeting minutes
** WRITE README.MD File

##setup instructions:
- Have the emulator running before we enter the room
- play a few games beforehand so the scoreboard is loaded
    -make sure to play with different accounts so they can see user functionality
-make sure the unittest coverage is set to the correct classes
-go into the presentation with saved games that are already almost finished


#introductions: talk about what each person made in both phases.

## presentation details:
-UML DIAGRAM?
- Carlos will talk about how he implemented solvability for slidingtiles, and walkthrough that part
of the code, and show unittest coverage. (We will show sudokuboardmanagertest as our 100% coverage).
-kelvin will do the login portion (authenticate, etc.) code, and talk about design patterns in sudoku,
and also why sudokugameactivity has 0% unittest coverage
-sam will talk about the design patterns in cardmatching, e.g. MVC : model is card,
controller is cardMatchingBoardManager, gameactivity is view
-maggie will discuss the scoreboard implementation, including how we store/manage user scores,
and show the code associated with this portion
-wilson will do the emulator walkthrough