# ai-checkers
Within the ai folder/package is my implementation of AI checkers with alpha beta search through a heuristic with iterative deepening. As for the legal moves, if there are any jumps then slides are not legal but if there are no jumps then slides are legal. 

<h1>Description</h1>


As for the code that I used, I used Java as the language because I was more familiar with the class structures in that language. My project consists of four files: main.java, game1.java, move.java, and heuristic.java, all in the “ai” package. Since all classes are in the same package, each class can call another class without having to import it. The game1.java contains a game class that has sets the time limit, board, abd player turn as an integer, 2d integer array, and integer respectively. The class also sets the player turn, changes the player turn, checks for legal moves, loads the board (and can load the file), initialize values in the board to print out the classic opening setup, and prints out the legal moves to the players in a presentable way. As for the legal moves, there is a separate function for “jumps”, moves where a player can take a piece, and “slides”, where a player can move one space along the board. The reason for this is that it makes it easier to set up the rules for “jumps” because “jumps” require different rules than “slides”. This brings me to move.java where I specify the move class. 


The move class is where the start row, start column, end row, end column, and pieces taken are defined, where the first four variables are integers and the last is a list of pairs. The game class receives all the legal moves by iterating through the different possible Moves that are defined by the start row, start col, end row, and end col for the given player. E.g. for a “jump” that takes two pieces where the start column = end column, something like this may be specified:

end row = start row + 4
end col = start col
list.append(Move(start row, start col, end row, end col)). 

The list of Moves then moves to the conditions specific to the type of move, where any applicable move gets appended to the final legal moves list and any pieces that would get taken for the jumps get added to the piecesTaken list for that respective move. 
That brings me now to heuristic.java that houses the heuristic class. The heuristic class sets the time limit, heuristic type, maximizing player,number of pieces for the maximizing player and opposing player, and max depth of search, all implemented as integers.  The heuristic class also implements the alpha beta search with the specified requirements from the project #1 description (e.g. if multiple moves are tied for the best, the program should choose between them randomly)  and max depth is 15


There is an easy heuristic, medium heuristic, and a hard heuristic but the hard heuristic is implemented by default.  The hard heuristic is different based on how many pieces are on the board with differences when there are more than 18 pieces, less than 18 but more than 8, and 8 or less pieces. Essentially, however they are all variations that act on a bonus to go to towards the middle of the board (a valid opening strategy), a score on their defending neighbors, a score on if the pieces will defend their side of the board, and a score that increases if the player in that score has more than or equal to an arbitrary number of pieces than their opponent, specifically min((float)totalPieces/8)) (to be more aggressive when winning).

Lastly, the main.java is not a class but rather implements the game. Here the appropriate questions are asked (e.g. prompts for either player being a computer, specifying the time limit, and starting player turn) and the game goes on until game.end is called. In this case, the turn is switched and if the other player has no legal moves then it prints a draw. Otherwise, the player wins.

<h1>Playing The Game</h1>

In order to play the game you can download this github and run main.java within the ai package in the latest version of Visual Studio Code. If java is not supported in your VS code there are a number of downloads online to make it supported.

![checkerboard](https://user-images.githubusercontent.com/59486373/103464221-0c803680-4d00-11eb-8d4e-c870c10f87d1.png)

