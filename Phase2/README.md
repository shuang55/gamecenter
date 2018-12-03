#To clone the project:
1. Get the repository URL from markus
2. Open android studio and "check out from version control"
3. Enter the repo URL, and clone the project
4. Import project from gradle
5. Select "/Phase1/GameCenter" in the "Gradle project" pane
6. Use default gradle wrapper
7. If unregistered VCS root message pops up, click add root
8. Open emulator (select Pixel 2, Android 8.1 Oreo 27) and run the app

# Log In
First, you need to sign up for an account. To sign up, simply input a username and a password
(that is at least 1 character) in the corresponding field, then click sign up. A message should
appear on whether the sign up was successful or unsuccessful. If the sign up failed, then either
the username has been taken or the input username/password wasn't at least 1 character. If the sign
up is successful, then you can now click log in enter the game centre. A user is automatically
logged out every time the app is closed. To log out, click logout button.

# Game Centre
In game centre, you can choose to start a new game (currently there is only one - Sliding Tiles),
go to highscore, or go to saved games to load your previous saved games.

# Saved Games
 You can select the game you want to load from from the drop down list - the app only supports
 saving Sliding Tiles at the moment, but more in the future! It includes the last three games saved,
 with the time of the game stored and game difficulty as the label.

# Changing Complexity (Sliding tile start screen)
In the home page, there is a drop down list near the center of the screen that allows you to choose
the complexity of the board.
There are three board sizes to choose from, 3 by 3, 4 by 4, and 5 by 5.

# Auto Save (Sliding tile start screen)
The auto save button leads you to the very last game state you were at, it updates every time you
make a move or when the game pauses, app closure, phone shut down. Click the "Auto Saved" button
on the starting screen if you wish to access this game.

# Easy win (Sliding tile start screen)
This is an additional feature that we implemented. When you click on EASY WIN and choose the desired
board size you want, the game board will have all the tiles arranged in the correct order except the
last one. The user just has to arrange the last tile into the correct position (one move), and win
the game. It is useful to test the scoreboard functionality using this button.

#Undo (Sliding tile game screen)
On the screen of the game the player selects (currently Game Center only supports sliding tiles),
there is an undo button. If the player presses the undo button, they can revert their previous move.
For bonus, we have implemented unlimited undos, which means that the player is able to
keep pressing the undo button until the starting state of the board is reached. Note that the
move counter does not decrement on undo, as we do not want undo to be a feature that will be overly
abused to achieve new highscores.

# Highscore
High score displays the top 10 scores in 3 different categories, and the categories can be toggled
with the drop down bar. If User is selected, then it will display the top 10 scores for all the
games that the user has played (currently there is only one game). If Sliding Tile is selected, then
it will display the top 10 scores for sliding tiles for the user that is currently logged in. If
Sliding Tile (All) is selected, then it will display the top 10 scores for all the users that have
played sliding tiles on that device (i.e. scores of users not currently logged in will also show up)

#CardMatching:
**Card photos taken from hearthstone game.
A card matching game where you can choose between 8, 10 or 12 pairs to match.
You must match all the pairs successfully to win. If an incorrect pair is opened, the pair will
automatically be closed again after 1 second. The game continues until all cards are open.

#Sudoku:
Sudoku is a puzzle game where you fill in the blank spaces with numbers. Each row, column, and 3x3
square section must have each number from 1-9 once. If you enter a number in an empty space
that violates the criteria, the space will be highlighted and you have to enter a correct
number in the space or erase the incorrect number to remove the highlight. You must fill in all the
empty spaces with the correct numbers to finish the game.