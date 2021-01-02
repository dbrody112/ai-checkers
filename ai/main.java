package ai;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner14;

import java.io.FileNotFoundException;
import java.util.*;


class Main {
  public static void main(String[] args) {
    Heuristic cpu1 = new Heuristic(1,3);
    Heuristic cpu2 = new Heuristic(2,3);
    boolean player1iscomputer;
    boolean player2iscomputer;
    Scanner scan = new Scanner(System.in);
    String player1Output = "0";
    String player2Output = "0";
    String loadGame = "0";
    String loadedFile = "0";
    int timeLimit = 0;
    int movesFirst = 0;
    boolean ended = false;
    Game game = new Game();
    System.out.println("Welcome to Chess! Player 1 is Blue and Player 2 is Red. Please respond to the following prompts:");
    System.out.println("\n");
    while(!player1Output.equals("Y") && !player1Output.equals("N"))
    {
      System.out.println("Will player #1 be a computer? (Y/N):");
      player1Output = scan.nextLine();
      System.out.println("\n");
    }
    
    player1iscomputer = player1Output.equals("Y") ? true:false;
    while(!player2Output.equals("Y") && !player2Output.equals("N"))
    {
      System.out.println("Will player #2 be a computer? (Y/N):");
      player2Output = scan.nextLine();
    }
    System.out.println("\n");
    player2iscomputer = player2Output.equals("Y") ? true:false;
    while (!loadGame.equals("Y") && !loadGame.equals("N")) {
      System.out.println("Do you want to load a game from a file? (Y/N): ");
      loadGame = scan.nextLine();
    }
    System.out.println("\n");
    
    if(loadGame.equals("Y"))
    {
      
      while(!game.loadFile(loadedFile))
      {
        
        System.out.println("Enter the name of the file:");
        loadedFile = scan.nextLine();
        
        System.out.println("\n");
      }
      boolean b = game.loadFile(loadedFile);
      cpu1.timeLimit = game.timeLimit;
      cpu2.timeLimit = game.timeLimit;
      
    }
    else
    {
      game.initializeValues();
      while(movesFirst != 1 && movesFirst != 2)
      {
        System.out.println("Which player would you like to move first? (1/2) : ");
        movesFirst = scan.nextInt();
        System.out.println("\n");
      }
      game.playerTurn = movesFirst;
      while(timeLimit < 5 || timeLimit > 100)
      {
        System.out.println("Enter a time limit between 5 and 100: ");
        timeLimit = scan.nextInt();
        System.out.println("\n");
      }
      
      cpu1.timeLimit = timeLimit;
      cpu2.timeLimit = timeLimit;
      game.timeLimit = timeLimit;
    }
    

    while(game.end!=true)
    {
      game = new Game(game);
      game.loadBoard();
      
      switch(game.playerTurn)
      {
        case 1:
          game.printMove(player1iscomputer,game.board,game);
          if(game.end==true)
          {
            game.changePlayerTurn();
            List<Move> legalMoves = game.getLegalMoves(game.board,game);
            
            if(legalMoves.isEmpty())
            {
              System.out.println("DRAW");
              break;
            }
            else
            {
              System.out.println("Player 2 Wins!");
              break;
            }
          }
          if(player1iscomputer)
          {
            Move move = cpu1.alphaBeta(game);
            game.applyMove(move,game.board);
            System.out.println("Player 1's move is ");
            move.printMove();
          }
          break;
        case 2:
          
          
          game.printMove(player2iscomputer,game.board,game);
          if(game.end == true)
          {
            game.changePlayerTurn();
            List<Move> legalMoves = game.getLegalMoves(game.board,game);
          
            if(legalMoves.isEmpty())
            {
              System.out.println("DRAW");
              break;
            }
            else
            {
              System.out.println("Player 1 Wins!");
              break;
            }
          }
          if(player2iscomputer)
          {
            Move move = cpu2.alphaBeta(game);
            game.applyMove(move,game.board);
            System.out.println("Player 2's move is ");
            move.printMove();
          }
          break;
      }
     
      game.changePlayerTurn();
    }
    
    
  }
}