package ai;
import java.util.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.*;
import java.util.logging.Level; 
import java.util.logging.Logger; 
import java.util.AbstractMap;

import java.util.Map;


import java.io.File; 
//try to find javafx





class Game{
//colors used
  public static final String ANSI_RESET  = "\u001B[0m";
  public static final String ANSI_BRIGHT_RED    = "\u001B[91m";
  public static final String ANSI_RED    = "\u001B[31m";
  public static final String ANSI_BRIGHT_CYAN   = "\u001B[96m";
  public static final String ANSI_CYAN   = "\u001B[36m";
  public static final String ANSI_BG_BLACK  = "\u001B[40m";
  public static final String ANSI_BG_WHITE  = "\u001B[47m";
  
//symbols used
  public static final String COPYRIGHT_STRING = "\u00A9";
  public static final String DELTA_STRING = "\u0394";
  public static final String REG_TRADEMARK_STRING = "\u00AE";
  public static final String LON_STRING = "       ";
  public static final String PHI_STRING = "\u03C6";
  
  protected static final String[] colorListMod2 = {"   " +ANSI_BG_WHITE+ LON_STRING, ANSI_BG_BLACK+ LON_STRING,ANSI_BG_WHITE+ LON_STRING, ANSI_BG_BLACK+ LON_STRING, ANSI_BG_WHITE+ LON_STRING, ANSI_BG_BLACK+ LON_STRING, ANSI_BG_WHITE+ LON_STRING, ANSI_BG_BLACK+ LON_STRING};

  protected static final String[] colorListMod1 = {"   " + ANSI_BG_BLACK+ LON_STRING, ANSI_BG_WHITE+ LON_STRING,ANSI_BG_BLACK+ LON_STRING, ANSI_BG_WHITE+ LON_STRING, ANSI_BG_BLACK+ LON_STRING, ANSI_BG_WHITE+ LON_STRING, ANSI_BG_BLACK+ LON_STRING, ANSI_BG_WHITE+ LON_STRING};
  

  int[][] board;
  int playerTurn = 1;
  int timeLimit;
  int i = 0;
  Boolean end = false;
  Logger logger = Logger.getLogger(Game.class.getName());
  int turns=0;

  public Game(){

  }

  public Game(Game game){
    this.board = new int[game.board.length][game.board[0].length];
    for(int i = 0; i < game.board.length; i++){
      for(int j = 0; j < game.board[0].length;j++){
        board[i][j] = game.board[i][j];
      }
    }
    this.playerTurn = game.playerTurn;
    this.timeLimit = game.timeLimit;
  }
//consider possibly differentiating white from black with the #'s for easability'
  public void changePlayerTurn()
  {
    if(playerTurn == 1)
    {
      playerTurn =2;
    }
    else{
      playerTurn = 1;
    }
  }
  public void initializeValues()
  {
    board = new int[8][8];
    for(int i = 0; i < 3; i++)
    {
      for(int j = 0; j < 8; j+=2)
      {
        if(i==1)
        {
          board[i][j] = 1;
          board[i][j+1] = 0;
        }
        else
        {
          board[i][j] = 0;
          board[i][j+1] = 1;
        }
      }
    }
    for(int k = 3; k < 5; k++)
    {
      for(int l = 0; l < 8; l++)
      {
          board[k][l] = 0;
      }
      
    }
    for(int m = 5; m < 8; m++)
    {
      for(int n = 0; n < 8; n+=2)
      {
        if(m==6)
        {
          board[m][n] = 0;
          board[m][n+1] = 2;
        }
        else
        {
          board[m][n] = 2;
          board[m][n+1] = 0;
        }
      }
    }
  }


public boolean loadFile(String filename)
{
  board=new int[8][8] ;
    try {
      File myObj = new File(filename);
      Scanner myReader = new Scanner(myObj);
      int g =0;
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        
       
        for(int j = 0; j < 8;j+=2)
        
        {
          
          
          switch(data.trim().charAt(j*2))
          {
            case '0':
              board[g][j] = 0;
              board[g][j+1] = 0;
              break;

            case '1':
              if(g%2==0)
              {
                board[g][j] = 0;
                board[g][j+1] = 1;
              }
              else 
              {
                board[g][j] = 1;
                board[g][j+1] = 0;
              }
              break;

            case '2':
              if(g%2==0)
              {
                board[g][j] = 0;
                board[g][j+1] = 2;
              }
              else 
              {
                board[g][j] = 2;
                board[g][j+1] = 0;
              }
              break;

            case '3':
              if(g%2==0)
              {
                board[g][j] = 0;
                board[g][j+1] = 3;
              }
              else 
              {
                board[g][j] = 3;
                board[g][j+1] = 0;
              }
              break;

            case '4':
              if(g%2==0)
              {
                board[g][j] = 0;
                board[g][j+1] = 4;
              }
              else 
              {
                board[g][j] = 4;
                board[g][j+1] = 0;
              }
              break;
            default:
              System.out.println("not applicable");
              break;


            }
          
        }
        g++;
        if(g==8)
        {
          break;
        }
      }
      playerTurn = Character.getNumericValue(myReader.nextLine().trim().charAt(0));
      timeLimit =  Integer.parseInt(myReader.nextLine().trim());
      myReader.close();
      
    } catch (FileNotFoundException e) {
      return false;
    }
    
    System.out.println("the current player is " + playerTurn + " and the time per move is " + timeLimit + " seconds");
    return true;
    
    }

  public void loadBoard()
  {
    System.out.print("      1      2      3      4      5      6      7      8");
    System.out.print("\n");
    for(int i = 0; i < 8; i++)
    {
      boolean isMod2;
      if(i%2==0)
      {
        for(String m: colorListMod2)
        {
          System.out.print(m);
        }
        isMod2 = true;
      }
      else
      {
        for(String m:colorListMod1)
        {
          System.out.print(m);
        }
      isMod2 = false;
      }
      System.out.println(ANSI_RESET);

      for(int j =0; j < 8; j++)
      {
        String statement;
        if(j == 0)
        {
          statement = " " + (i+1) + " ";
        }
        else
        {
          statement = "";
        }
        
        switch(board[i][j])
        {
          case 0:
           String blankSpace = LON_STRING;
           if(j%2==0 && i%2 == 0)
           {
           
             statement+= ANSI_BG_WHITE+ blankSpace;
           }
           else if(j%2 == 0 && i%2 != 0)
           {
             statement+= ANSI_BG_BLACK + blankSpace;
           }
           else if(j%2!= 0 && i%2 == 0)
           {
             statement+= ANSI_BG_BLACK + blankSpace;
           }
           else
           {
             statement+= ANSI_BG_WHITE + blankSpace;
           }
           break;

          case 1:
            String piece1 = "   " + "©" + "   ";
            if(j%2==0 && i%2 == 0)
           {
             statement+= ANSI_BG_WHITE + ANSI_BRIGHT_CYAN + piece1;
           }
           else if(j%2 == 0 && i%2 != 0)
           {
             statement+= ANSI_BG_BLACK + ANSI_BRIGHT_CYAN + piece1;
           }
           else if(j%2!= 0 && i%2 == 0)
           {
             statement+= ANSI_BG_BLACK + ANSI_BRIGHT_CYAN + piece1;
           }
           else
           {
             statement+= ANSI_BG_WHITE + ANSI_BRIGHT_CYAN + piece1;
           }
           break;
          case 2:
            String piece2 = "   " +  REG_TRADEMARK_STRING + "   ";
            if(j%2==0 && i%2 == 0)
           {
             statement+= ANSI_BG_WHITE + ANSI_BRIGHT_RED+piece2;
           }
           else if(j%2 == 0 && i%2 != 0)
           {
             statement+= ANSI_BG_BLACK + ANSI_BRIGHT_RED+piece2;
           }
           else if(j%2!= 0 && i%2 == 0)
           {
             statement+= ANSI_BG_BLACK + ANSI_BRIGHT_RED+piece2;
           }
           else
           {
             statement+= ANSI_BG_WHITE + ANSI_BRIGHT_RED+piece2;
           }
           break;
          case 3:
            String piece3 = "   " + DELTA_STRING + "   ";
            if(j%2==0 && i%2 == 0)
           {
             statement+= ANSI_BG_WHITE + ANSI_BRIGHT_CYAN + piece3;
           }
           else if(j%2 == 0 && i%2 != 0)
           {
             statement+= ANSI_BG_BLACK + ANSI_BRIGHT_CYAN + piece3;
           }
           else if(j%2!= 0 && i%2 == 0)
           {
             statement+= ANSI_BG_BLACK + ANSI_BRIGHT_CYAN + piece3;
           }
           else
           {
             statement+= ANSI_BG_WHITE + ANSI_BRIGHT_CYAN + piece3;
           }
           break;
          case 4:
            String piece4 = "   " + DELTA_STRING + "   ";
            if(j%2==0 && i%2 == 0)
           {
             statement+= ANSI_BG_WHITE+ANSI_BRIGHT_RED + piece4;
           }
           else if(j%2 == 0 && i%2 != 0)
           {
             statement+= ANSI_BG_BLACK+ANSI_BRIGHT_RED + piece4;
           }
           else if(j%2!= 0 && i%2 == 0)
           {
             statement+= ANSI_BG_BLACK+ANSI_BRIGHT_RED + piece4;
           }
           else
           {
             statement+= ANSI_BG_WHITE +ANSI_BRIGHT_RED + piece4;
           }
           break;
          default:
           System.out.println("not applicable");
           break;
          
            

        }
        System.out.print(statement);
      }
      System.out.println(ANSI_RESET);
      if(isMod2)
      {
        for(String m: colorListMod2)
        {
          System.out.print(m);
        }
      }
      else
      {
        for(String m: colorListMod1)
        {
          System.out.print(m);
        }
      }
      System.out.println(ANSI_RESET);
    }
  }

  public List<Move> getJumps(){
    //playerTurn
    List<Move> moveList = new ArrayList<Move>();
    for(int i = 0; i < 8; i++)
    {
      for(int j = 0; j < 8; j++)
      {
        if(board[i][j] == playerTurn || board[i][j] == playerTurn + 2){
          boolean isKing = board[i][j] >2;
          Hashtable<Integer,Integer> dict = new Hashtable<>();
          dict.put(1,1);
          dict.put(2,-1);

          
          

          List<Move> tempMoveList = new ArrayList<>();
          ArrayList<Map.Entry<Integer, Integer>> endDims = new ArrayList<>();
          int startRow =0;
          int startCol=0;
          int endRow=0;
          int endCol=0;
          
          startRow = i;
          startCol = j;

          //firstCase
          endRow = i + dict.get(playerTurn)*2;
          endCol = j + 2;
          endDims.add(Pair.of(endRow,endCol));

          endRow = i + dict.get(playerTurn)*2;
          endCol = j - 2;
          endDims.add(Pair.of(endRow,endCol));
          
          //thirdCase
          endRow = i + dict.get(playerTurn)*4;
          endCol = j + 4;
          endDims.add(Pair.of(endRow,endCol));

          endRow = i + dict.get(playerTurn)*4;
          endCol = j - 4;
          endDims.add(Pair.of(endRow,endCol));

          //fifthCase
          endRow = i + dict.get(playerTurn)*4;
          endCol = j;
          endDims.add(Pair.of(endRow,endCol));

          

         
          
          //if king:

          //sixthCase
          if(isKing)
          {
            
            endRow = i - dict.get(playerTurn)*2;
            endCol = j - 2;
            endDims.add(Pair.of(endRow,endCol));

            //seventhCase
            endRow = i - dict.get(playerTurn)*2;
            endCol = j + 2;
            endDims.add(Pair.of(endRow,endCol));

            //eighthCase
            endRow = i - dict.get(playerTurn)*4;
            endCol = j - 4;
            endDims.add(Pair.of(endRow,endCol));

            //ninthCase
            endRow = i - dict.get(playerTurn)*4;
            endCol = j + 4;
            endDims.add(Pair.of(endRow,endCol));

            //tenthCase
            endRow = i - dict.get(playerTurn)*4;
            endCol = j;
            endDims.add(Pair.of(endRow,endCol));

            //lastCase
            endRow = i;
            if(4-j <= 0){
              endCol = j-4;
            }
            else{
              endCol = j+4;
            }
            endDims.add(Pair.of(endRow,endCol));
            
          }
          


          
         
         
          if(isKing)
          {
            for(int k = 0; k < endDims.size(); k++){
              Move move = new Move(startRow,startCol,endDims.get(k).getKey(),endDims.get(k).getValue());
              //System.out.println("playerTurn: " + playerTurn + " startRow: " + (startRow+1) + " , startcol: " + (startCol+1) + " , endrow: " + (endDims.get(k).getKey()+1)
              //+ " , endcol: " + (endDims.get(k).getValue()+1));
              tempMoveList.add(move);
            }
          }
          else
          {
            for(int m = 0; m < endDims.size(); m++){
              Move move = new Move(startRow,startCol,endDims.get(m).getKey(),endDims.get(m).getValue());
              //System.out.println("playerTurn: " + playerTurn + " startRow: " + (startRow+1) + " , startcol: " + (startCol+1) + " , endrow: " + (endDims.get(m).getKey()+1)
              //+ " , endcol: " + (endDims.get(m).getValue()+1));
              tempMoveList.add(move);
             }
              
          }
          
//make sure that there are other pieces to jump
//especially for the two steps
//find some way to iterate
//solve the (5,8) -> (7,6) problem 
          //change o to max 10 uf required.
          int move_type = 0;
          int x = 0;
          int y = 0;
          int x1 = 0;
          int y1 = 0;
          boolean upper = true;
          boolean lower = true;
          boolean doneBefore = false;
          


          for(int o = 0; o<8; o++)
          {
          for(int n = 0; n < tempMoveList.size();n++)
          {
            
            
            
            
            
            if(tempMoveList.get(n).endCol >= 8|| tempMoveList.get(n).endRow >= 8 || tempMoveList.get(n).endRow < 0 ||tempMoveList.get(n).endCol < 0 )
            {
              
              tempMoveList.remove(n);
              
              continue;
            }
            try{
              int g = board[tempMoveList.get(n).endRow][tempMoveList.get(n).endCol];
              
            }
            catch(ArrayIndexOutOfBoundsException exception)
            {
              
              tempMoveList.remove(n);
              continue;     
            }
            if(board[tempMoveList.get(n).endRow][tempMoveList.get(n).endCol] != 0)
            {
              
              
              tempMoveList.remove(n);
              
              continue;
              
            }
            else if(Math.abs(tempMoveList.get(n).endCol - tempMoveList.get(n).startCol) == 4 || Math.abs(tempMoveList.get(n).endRow - tempMoveList.get(n).startRow) == 4)
            {
              //System.out.println("pair: " + "( " + tempMoveList.get(n).startRow + " , " + tempMoveList.get(n).startCol+" )" + "-->" + "( " + tempMoveList.get(n).endRow + " , " + tempMoveList.get(n).endCol + " )");
              
              upper = true;
              lower = true;
              if(tempMoveList.get(n).endRow == tempMoveList.get(n).startRow)
              {
                
                //add try-catch statements and separate by startcol - endcol.
                
                //System.out.println("col:  " + tempMoveList.get(n).endCol+ "    row:  " + tempMoveList.get(n).endRow + "  startRow-endRow: " + (tempMoveList.get(n).startRow < tempMoveList.get(n).endRow) + "  startCol-endCol: " + (tempMoveList.get(n).startCol<tempMoveList.get(n).endCol));
                
                if(tempMoveList.get(n).startCol > (tempMoveList.get(n).endCol))
                {
                  
                  try{
                    if(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol + 1] == 0||(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol + 1] == playerTurn)
                    ||(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol + 1] == playerTurn+2)
                    ||board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol - 1] == 0||(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol - 1] == playerTurn)
                    ||(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol - 1] == playerTurn+2)||board[tempMoveList.get(n).endRow - 2][tempMoveList.get(n).endCol + 2] != 0)
                    {
                      
                      upper = false;
                    }
                    }catch(ArrayIndexOutOfBoundsException e)
                    {
                      upper = false;
                    }
                    try{
                      //System.out.println("row: " + board[tempMoveList.get(n).endRow + 2][tempMoveList.get(n).endCol + 2]);
                      if(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol + 1] == 0||(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol + 1] == playerTurn)
                      ||(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol + 1] == playerTurn+2)
                      ||board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol - 1] == 0||(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol - 1] == playerTurn)
                      ||(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol - 1] == playerTurn+2)||board[tempMoveList.get(n).endRow + 2][tempMoveList.get(n).endCol + 2] != 0)
                    {
                       
                      lower = false;
                    }
                    }catch(ArrayIndexOutOfBoundsException e)
                    {
                      lower = false;
                    }
                    if(!upper && !lower)
                    {
                      tempMoveList.remove(n);
                      continue;
                    }
                    if(upper&&lower&&!doneBefore)
                    {
                      endRow = tempMoveList.get(n).endRow;
                      endCol = tempMoveList.get(n).endCol;
                      Move move = new Move(startRow,startCol,endRow,endCol);
                      tempMoveList.add(move);
                      x = tempMoveList.get(n).endRow + 1;
                      y = tempMoveList.get(n).endCol + 1;
                      x1 = tempMoveList.get(n).startRow + 1;
                      y1 = tempMoveList.get(n).startCol - 1;
                      tempMoveList.get(tempMoveList.size()-1).addPiecesTaken(x, y);
                      tempMoveList.get(tempMoveList.size()-1).addPiecesTaken(x1, y1);
                      x = tempMoveList.get(n).endRow - 1;
                      y = tempMoveList.get(n).endCol + 1;
                      x1 = tempMoveList.get(n).startRow - 1;
                      y1 = tempMoveList.get(n).startCol - 1;
                      tempMoveList.get(n).addPiecesTaken(x, y);
                      tempMoveList.get(n).addPiecesTaken(x1, y1);
                      doneBefore = true;
                      continue;
                    }
                    if(upper)
                    {
                      x = tempMoveList.get(n).endRow - 1;
                      y = tempMoveList.get(n).endCol + 1;
                      x1 = tempMoveList.get(n).startRow - 1;
                      y1 = tempMoveList.get(n).startCol - 1;
                    }
                    else if(lower)
                    {
                      x = tempMoveList.get(n).endRow + 1;
                      y = tempMoveList.get(n).endCol + 1;
                      x1 = tempMoveList.get(n).startRow + 1;
                      y1 = tempMoveList.get(n).startCol - 1;
    
                    }
                    if(tempMoveList.get(n).piecesTaken.size() < 2)
                    {
                      tempMoveList.get(n).addPiecesTaken(x, y);
                      tempMoveList.get(n).addPiecesTaken(x1, y1);
                      continue;  
                    }
    

                }
                else if (tempMoveList.get(n).startCol < (tempMoveList.get(n).endCol))
                {
                  try{
                    if(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol + 1] == 0||(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol + 1] == playerTurn)
                    ||(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol + 1] == playerTurn+2)
                    ||board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol - 1] == 0||(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol - 1] == playerTurn)
                    ||(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol - 1] == playerTurn+2)||board[tempMoveList.get(n).startRow - 2][tempMoveList.get(n).startCol + 2] != 0)
                    {
                      
                      upper = false;
                    }
                    }catch(ArrayIndexOutOfBoundsException e)
                    {
                      upper = false;
                    }
                    try{
                    if(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol + 1] == 0||(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol + 1] == playerTurn)
                    ||(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol + 1] == playerTurn+2)
                    ||board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol - 1] == 0||(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol - 1] == playerTurn)
                    ||board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol - 1] == playerTurn+2||board[tempMoveList.get(n).startRow + 2][tempMoveList.get(n).startCol + 2] != 0)
                    {
                       
                      lower = false;
                    }
                    }catch(ArrayIndexOutOfBoundsException e)
                    {
                      lower = false;
                    }
                    if(!upper && !lower)
                    {
                      tempMoveList.remove(n);
                      continue;
                    }
                    if(upper&&lower&&!doneBefore)
                    {
                      endRow = tempMoveList.get(n).endRow;
                      endCol = tempMoveList.get(n).endCol;
                      Move move = new Move(startRow,startCol,endRow,endCol);
                      tempMoveList.add(move);
                      x = tempMoveList.get(n).startRow + 1;
                      y = tempMoveList.get(n).startCol + 1;
                      x1 = tempMoveList.get(n).endRow + 1;
                      y1 = tempMoveList.get(n).endCol - 1;
                      tempMoveList.get(tempMoveList.size()-1).addPiecesTaken(x, y);
                      tempMoveList.get(tempMoveList.size()-1).addPiecesTaken(x1, y1);
                      x = tempMoveList.get(n).startRow - 1;
                      y = tempMoveList.get(n).startCol + 1;
                      x1 = tempMoveList.get(n).endRow - 1;
                      y1 = tempMoveList.get(n).endCol - 1;
                      tempMoveList.get(n).addPiecesTaken(x, y);
                      tempMoveList.get(n).addPiecesTaken(x1, y1);
                      doneBefore = true;
                      continue;
                    }
                    if(upper)
                    {
                      x = tempMoveList.get(n).startRow - 1;
                      y = tempMoveList.get(n).startCol + 1;
                      x1 = tempMoveList.get(n).endRow - 1;
                      y1 = tempMoveList.get(n).endCol - 1;
                    }
                    else if(lower)
                    {
                      x = tempMoveList.get(n).startRow + 1;
                      y = tempMoveList.get(n).startCol + 1;
                      x1 = tempMoveList.get(n).endRow + 1;
                      y1 = tempMoveList.get(n).endCol - 1;
    
                    }
                    if(tempMoveList.get(n).piecesTaken.size() < 2)
                    {
                      
                      tempMoveList.get(n).addPiecesTaken(x, y);
                      tempMoveList.get(n).addPiecesTaken(x1, y1);
                      continue;  
                    }
    

                }
                  //if true, upper portion does not exist
                
              }
              
              if(!upper || !lower)
              {
                //System.out.println(1);
                if(tempMoveList.get(n).endCol == tempMoveList.get(n).startCol)
                {
                  tempMoveList.remove(n);
                  continue;
                }
              }
              else if(tempMoveList.get(n).endCol == tempMoveList.get(n).startCol)
              {
                 
                upper = true;
                lower = true;
                
                if(tempMoveList.get(n).startRow<tempMoveList.get(n).endRow)
                {
                  try{
                    //System.out.println("val: " + board[tempMoveList.get(n).endRow - 2][tempMoveList.get(n).endCol + 2]);
                    if(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol - 1] == 0||(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol - 1] == playerTurn)
                    ||(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol - 1] == playerTurn+2)
                    ||board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol - 1] == 0||(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol - 1] == playerTurn)
                    ||(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol - 1] == playerTurn+2)||board[tempMoveList.get(n).endRow -2][tempMoveList.get(n).endCol -2] != 0)
                  {
                    lower = false;
                  }
                  }catch(ArrayIndexOutOfBoundsException e)
                  {
                    lower = false;
                  }
                  try{
                  if(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol + 1] == 0||(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol + 1] == playerTurn)
                  ||(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol + 1] == playerTurn+2)
                  ||tempMoveList.get(n).startRow + 1 < 8 && board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol + 1] == 0||(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol + 1] == playerTurn)
                  ||(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol + 1] == playerTurn+2)||board[tempMoveList.get(n).endRow -2][tempMoveList.get(n).endCol +2] != 0)
                  {
                    upper = false;
                  }
                  }catch(ArrayIndexOutOfBoundsException e)
                  {
                    upper = false;
                  }
                  if(!upper && !lower)
                  {
                    tempMoveList.remove(n);
                    continue;
                  }
                  if(upper&&lower&&!doneBefore)
                  {
                    
                    endRow = tempMoveList.get(n).endRow;
                    endCol = tempMoveList.get(n).endCol;
                    Move move = new Move(startRow,startCol,endRow,endCol);
                    tempMoveList.add(move);
                    x = tempMoveList.get(n).endRow - 1;
                    y = tempMoveList.get(n).endCol - 1;
                    x1 = tempMoveList.get(n).startRow + 1;
                    y1 = tempMoveList.get(n).startCol - 1;
                    tempMoveList.get(tempMoveList.size()-1).addPiecesTaken(x, y);
                    tempMoveList.get(tempMoveList.size()-1).addPiecesTaken(x1, y1);
                    x = tempMoveList.get(n).endRow - 1;
                    y = tempMoveList.get(n).endCol + 1;
                    x1 = tempMoveList.get(n).startRow + 1;
                    y1 = tempMoveList.get(n).startCol + 1;
                    tempMoveList.get(n).addPiecesTaken(x, y);
                    tempMoveList.get(n).addPiecesTaken(x1, y1);
                    doneBefore = true;
                    continue;
                  


                  }
                  else if(lower)
                  {
                    x = tempMoveList.get(n).endRow - 1;
                    y = tempMoveList.get(n).endCol - 1;
                    x1 = tempMoveList.get(n).startRow + 1;
                    y1 = tempMoveList.get(n).startCol - 1;
                  }
                  else if(upper)
                  {
                    x = tempMoveList.get(n).endRow - 1;
                    y = tempMoveList.get(n).endCol + 1;
                    x1 = tempMoveList.get(n).startRow + 1;
                    y1 = tempMoveList.get(n).startCol + 1;

                  }
                  if(tempMoveList.get(n).piecesTaken.size() < 2)
                  {
                    
                    tempMoveList.get(n).addPiecesTaken(x, y);
                    tempMoveList.get(n).addPiecesTaken(x1, y1);
                    continue;  
                  }

                }
                else
                {
                  try{
                    //System.out.println("val: " + board[tempMoveList.get(n).startRow - 2][tempMoveList.get(n).startCol + 2]);
                    if(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol - 1] == 0||(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol - 1] == playerTurn)
                    ||(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol - 1] == playerTurn+2)
                    ||board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol - 1] == 0||(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol - 1] == playerTurn)
                    ||(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol - 1] == playerTurn+2)||board[tempMoveList.get(n).startRow -2][tempMoveList.get(n).startCol -2] != 0)
                  {
                    lower = false;
                  }
                  }catch(ArrayIndexOutOfBoundsException e)
                  {
                    lower = false;
                  }
                  try{
                    //System.out.println("val: " + board[tempMoveList.get(n).startRow - 2][tempMoveList.get(n).startCol - 2]);
                    if(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol + 1] == 0||(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol + 1] == playerTurn)
                    ||(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol + 1] == playerTurn+2)
                    ||board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol + 1] == 0||(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol + 1] == playerTurn)
                    ||(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol + 1] == playerTurn+2)||board[tempMoveList.get(n).startRow -2][tempMoveList.get(n).startCol +2] != 0 )
                  {
                    
                    upper = false;
                  }
                  }catch(ArrayIndexOutOfBoundsException e)
                  {
                    //System.out.println(1);
                    upper = false;
                  }
                  
                  if(!upper && !lower)
                  {
                    tempMoveList.remove(n);
                    continue;
                  }
                  if(upper&&lower&&!doneBefore)
                  {

                    
                    endRow = tempMoveList.get(n).endRow;
                    endCol = tempMoveList.get(n).endCol;
                    Move move = new Move(startRow,startCol,endRow,endCol);
                    tempMoveList.add(move);
                    
                    x = tempMoveList.get(n).startRow - 1;
                    y = tempMoveList.get(n).startCol - 1;
                    x1 = tempMoveList.get(n).endRow + 1;
                    y1 = tempMoveList.get(n).endCol - 1;
                    tempMoveList.get(tempMoveList.size()-1).addPiecesTaken(x, y);
                    tempMoveList.get(tempMoveList.size()-1).addPiecesTaken(x1, y1);
                    x = tempMoveList.get(n).startRow - 1;
                    y = tempMoveList.get(n).startCol + 1;
                    x1 = tempMoveList.get(n).endRow + 1;
                    y1 = tempMoveList.get(n).endCol + 1;
                    tempMoveList.get(n).addPiecesTaken(x, y);
                    tempMoveList.get(n).addPiecesTaken(x1, y1);
                    
                    doneBefore = true;
                    continue;
                  


                  }
                  else if(lower)
                  {
                    x = tempMoveList.get(n).startRow - 1;
                    y = tempMoveList.get(n).startCol - 1;
                    x1 = tempMoveList.get(n).endRow + 1;
                    y1 = tempMoveList.get(n).endCol - 1;
                  }
                  else if(upper)
                  {
                    x = tempMoveList.get(n).startRow - 1;
                    y = tempMoveList.get(n).startCol + 1;
                    x1 = tempMoveList.get(n).endRow + 1;
                    y1 = tempMoveList.get(n).endCol + 1;

                  }
                  if(tempMoveList.get(n).piecesTaken.size() < 2)
                  {
                    
                    tempMoveList.get(n).addPiecesTaken(x, y);
                    tempMoveList.get(n).addPiecesTaken(x1, y1);
                    continue;  
                  }
                  

                }
                
                

              }
              else
              {
                
                if(tempMoveList.get(n).startRow < tempMoveList.get(n).endRow && tempMoveList.get(n).startCol < tempMoveList.get(n).endCol)
                {
                  
                  if(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol + 1] == 0||(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol + 1] == playerTurn)
                  ||(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol + 1] == playerTurn+2)||board[tempMoveList.get(n).startRow + 2][tempMoveList.get(n).startCol + 2] != 0)
                  {
                    tempMoveList.remove(n);
                    continue;
                  }
                  move_type = 0;
                }
                else if(tempMoveList.get(n).startRow > tempMoveList.get(n).endRow && tempMoveList.get(n).startCol < tempMoveList.get(n).endCol)
                {
                  if(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol + 1] == 0||(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol + 1] == playerTurn)
                  ||(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol + 1] == playerTurn+2)||board[tempMoveList.get(n).startRow - 2][tempMoveList.get(n).startCol + 2] != 0)
                  {
                    tempMoveList.remove(n);
                    continue;
                  }
                  move_type = 1;
                }
                else if(tempMoveList.get(n).startRow < tempMoveList.get(n).endRow && tempMoveList.get(n).startCol > tempMoveList.get(n).endCol)
                {
                  //System.out.println("board val, less startrow, greater startcol: " + board[tempMoveList.get(n).startRow + 2][tempMoveList.get(n).startCol - 2] + " startRow, startCol: " + tempMoveList.get(n).startCol + ", " + tempMoveList.get(n).startRow);
                  if(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol - 1] == 0||(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol - 1] == playerTurn)
                  ||(board[tempMoveList.get(n).startRow + 1][tempMoveList.get(n).startCol - 1] == playerTurn+2)||board[tempMoveList.get(n).startRow + 2][tempMoveList.get(n).startCol - 2] != 0)
                  {
                    tempMoveList.remove(n);
                    continue;
                  }
                  move_type = 2;
                }
                else
                {
                  if(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol - 1] == 0||(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol - 1] == playerTurn)
                  ||(board[tempMoveList.get(n).startRow - 1][tempMoveList.get(n).startCol - 1] == playerTurn+2)||board[tempMoveList.get(n).startRow - 2][tempMoveList.get(n).startCol - 2] != 0)
                  {
                    tempMoveList.remove(n);
                    continue;
                  }
                  move_type = 3;
                }
                switch(move_type)
                {
                case 0:
                  x = tempMoveList.get(n).startRow + 1;
                  y = tempMoveList.get(n).startCol + 1;
                  break;

                case 1: 
                  x = tempMoveList.get(n).startRow - 1;
                  y = tempMoveList.get(n).startCol + 1;
                  break;
                  
                case 2:
                  x = tempMoveList.get(n).startRow + 1;
                  y = tempMoveList.get(n).startCol - 1;
                  break;
                  
                case 3:
                  x = tempMoveList.get(n).startRow - 1;
                  y = tempMoveList.get(n).startCol - 1;
                  break;
                  
                default:
                  System.out.println("Not applicable");

                }
                if(tempMoveList.get(n).piecesTaken.size() < 2)
                {
                  tempMoveList.get(n).addPiecesTaken(x, y); 
                }  
              }
            }


            if(true)
            {
              
              if(tempMoveList.get(n).startRow < tempMoveList.get(n).endRow && tempMoveList.get(n).startCol<tempMoveList.get(n).endCol)
              {
                if(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol - 1] == 0||(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol - 1] == playerTurn)
                ||(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol - 1] == playerTurn+2))
                {
                  
                  
                  tempMoveList.remove(n);
                  continue;
                }
                move_type = 0;
                //implement way to detect two places  
              }
              else if(tempMoveList.get(n).startRow < tempMoveList.get(n).endRow && tempMoveList.get(n).startCol > tempMoveList.get(n).endCol)
              {
                if(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol + 1] == 0||(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol + 1] == playerTurn)
                ||(board[tempMoveList.get(n).endRow - 1][tempMoveList.get(n).endCol + 1] == playerTurn+2))
                {
                  
                  
                  tempMoveList.remove(n);
                  continue;
                }
                move_type = 1;
              }
              else if(tempMoveList.get(n).startRow > tempMoveList.get(n).endRow && tempMoveList.get(n).startCol < tempMoveList.get(n).endCol)
              {
                if(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol - 1] == 0||(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol - 1] == playerTurn)
                ||(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol - 1] == playerTurn+2))
                {
                  
                  
                  tempMoveList.remove(n);
                  continue;
                }
                move_type = 2;
              }
              else if(tempMoveList.get(n).startRow > tempMoveList.get(n).endRow && tempMoveList.get(n).startCol > tempMoveList.get(n).endCol){
                if(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol + 1] == 0||(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol + 1] == playerTurn)
                ||(board[tempMoveList.get(n).endRow + 1][tempMoveList.get(n).endCol + 1] == playerTurn+2))
                {
                  
                  
                  tempMoveList.remove(n);
                  continue;

                }
                move_type = 3;
              }
              
              switch(move_type)
              {
                case 0:
                  x = tempMoveList.get(n).endRow - 1;
                  y = tempMoveList.get(n).endCol - 1;
                  break;

                case 1: 
                  x = tempMoveList.get(n).endRow - 1;
                  y = tempMoveList.get(n).endCol + 1;
                  break;
                  
                case 2:
                  x = tempMoveList.get(n).endRow + 1;
                  y = tempMoveList.get(n).endCol - 1;
                  break;
                  
                case 3:
                  x = tempMoveList.get(n).endRow + 1;
                  y = tempMoveList.get(n).endCol + 1;
                  break;
                  
                default:
                  System.out.println("Not applicable");

              }
              if(tempMoveList.get(n).piecesTaken.size() <= Math.abs(tempMoveList.get(n).endCol - tempMoveList.get(n).startCol)/2-1)
              {
                
                tempMoveList.get(n).addPiecesTaken(x, y);
              }
   
            }

          
          }
        }
        if(!tempMoveList.isEmpty())
        {
          tempMoveList.forEach(move -> {
            if(move.endRow >= 0){
              moveList.add(move);
            }
              
          });
        
        

       }

      }
          

          
          
    }
  }
  return moveList;   
 }
 public List<Move> getSlides(){
  List<Move> moveList = new ArrayList<Move>();
  for(int i = 0; i < 8; i++)
  {
    for(int j = 0; j < 8; j++)
    {
      if(board[i][j] == playerTurn || board[i][j] == playerTurn + 2){
        boolean isKing = board[i][j] >2;
        Hashtable<Integer,Integer> dict = new Hashtable<>();
        dict.put(1,1);
        dict.put(2,-1);
        List<Move> tempMoveList = new ArrayList<>();
        ArrayList<Map.Entry<Integer, Integer>> endDims = new ArrayList<>();
        int startRow =0;
        int startCol=0;
        int endRow=0;
        int endCol=0;
        
        startRow = i;
        startCol = j;

        //firstCase (no king)
        endRow = i + dict.get(playerTurn)*1;
        endCol = j + 1;
        endDims.add(Pair.of(endRow,endCol));

        endRow = i + dict.get(playerTurn)*1;
        endCol = j - 1;
        endDims.add(Pair.of(endRow,endCol));

        if(isKing)
        {
          endRow = i - dict.get(playerTurn)*1;
          endCol = j - 1;
          endDims.add(Pair.of(endRow,endCol));

          endRow = i - dict.get(playerTurn)*1;
          endCol = j + 1;
          endDims.add(Pair.of(endRow,endCol));

        }
        if(isKing)
          {
            for(int k = 0; k < endDims.size(); k++){
              Move move = new Move(startRow,startCol,endDims.get(k).getKey(),endDims.get(k).getValue());
              tempMoveList.add(move);
            }
          }
          else
          {
            for(int m = 0; m < endDims.size(); m++){
              Move move = new Move(startRow,startCol,endDims.get(m).getKey(),endDims.get(m).getValue());
                
              tempMoveList.add(move);
             }             
          }
          for(int o = 0; o<8; o++)
          {
          for(int n = 0; n < tempMoveList.size();n++)
          { 
            if(tempMoveList.get(n).endCol >= 8|| tempMoveList.get(n).endRow >= 8 || tempMoveList.get(n).endRow < 0 ||tempMoveList.get(n).endCol < 0 )
            {
              tempMoveList.remove(n);    
              continue;
            }
            try{
              int g = board[tempMoveList.get(n).endRow][tempMoveList.get(n).endCol];
            }
            catch(ArrayIndexOutOfBoundsException exception)
            {
              tempMoveList.remove(n);
              continue;     
            }
            if(board[tempMoveList.get(n).endRow][tempMoveList.get(n).endCol] != 0)
            {
              tempMoveList.remove(n);
              continue; 
            }
          }
        }
        if(!tempMoveList.isEmpty())
        {
          tempMoveList.forEach(move -> {
            if(move.endRow >= 0){
              moveList.add(move);
            }
              
          });
        }
      }
    }
  }
 return moveList;
 }
 int[][] game_board = board;
 public List<Move> getLegalMoves(int[][] state,Game game)
 {
  List<Move> jumps = getJumps();
  for(int i =0; i<jumps.size();i++)
  {
    
    List<Move> subJumps = jumps;
    int once = 0;
    Game copy = new Game(game);
    
    
    copy.applyMove(jumps.get(i),copy.board);
      
    subJumps = copy.getJumps();
    
    for(int j = 0; j < subJumps.size(); j++)
    {
      if(jumps.get(i).endRow == subJumps.get(j).startRow && jumps.get(i).endCol == subJumps.get(j).startCol )
      {
        Boolean inList = false;
        int summation = 0;
        Move move = new Move(jumps.get(i).startRow, jumps.get(i).startCol,subJumps.get(j).endRow, subJumps.get(j).endCol);
        jumps.get(i).getPiecesTaken().forEach(jump->{
          move.addPiecesTaken(jump.getKey(),jump.getValue());
        });
        subJumps.get(j).getPiecesTaken().forEach(jump->{
          move.addPiecesTaken(jump.getKey(),jump.getValue());
        });
        if(playerTurn == 1 && jumps.get(i).endRow == 7 && board[jumps.get(i).startRow][jumps.get(i).startCol] == 1)
        {
          inList =true;
        }
        else if(playerTurn == 2 && jumps.get(i).endRow == 0 && board[jumps.get(i).startRow][jumps.get(i).startCol] == 2)
        {
          inList = true;
        }
        for(int k = 0; k<jumps.size();k++)
        {
          
          if(jumps.get(k).startRow == move.startRow && jumps.get(k).startCol == move.startCol && jumps.get(k).endRow == move.endRow && jumps.get(k).endCol == move.endCol)
          {
            
            if(jumps.get(k).getPiecesTaken().size() == move.getPiecesTaken().size())
            {
              summation = 0;
              for(int l = 0; l < jumps.get(k).getPiecesTaken().size();l++)
              {
                for(int m= 0; m < jumps.get(k).getPiecesTaken().size();m++)
                {
                  if(jumps.get(k).getPiecesTaken().get(l).getKey() == move.getPiecesTaken().get(m).getKey() && 
                  jumps.get(k).getPiecesTaken().get(l).getValue() == move.getPiecesTaken().get(m).getValue())
                  {
                    summation++;
                  }
                }
    
              }
              if(summation == jumps.get(k).getPiecesTaken().size())
              {
                inList = true;
              }
            }
            
            
          }
        }
        if(inList == false)
        {
          jumps.add(move);
        }
      }
    }
    
    copy = new Game(copy);
    copy.applyMove(jumps.get(i),copy.board);
      
    subJumps = copy.getJumps();
    for(int j = 0; j < subJumps.size(); j++)
    {
      if(jumps.get(i).endRow == subJumps.get(j).startRow && jumps.get(i).endCol == subJumps.get(j).startCol )
      {
        
        Move move = new Move(jumps.get(i).startRow, jumps.get(i).startCol,subJumps.get(j).endRow, subJumps.get(j).endCol);
        jumps.get(i).getPiecesTaken().forEach(jump->{
          move.addPiecesTaken(jump.getKey(),jump.getValue());
        });
        subJumps.get(j).getPiecesTaken().forEach(jump->{
          move.addPiecesTaken(jump.getKey(),jump.getValue());
        });
        int summation = 0;
        Boolean inList = false;
        if(playerTurn == 1 && jumps.get(i).endRow == 7 && board[jumps.get(i).startRow][jumps.get(i).startCol] == 1)
        {
          inList =true;
        }
        else if(playerTurn == 2 && jumps.get(i).endRow == 0 && board[jumps.get(i).startRow][jumps.get(i).startCol] == 2)
        {
          inList = true;
        }
        for(int k = 0; k<jumps.size();k++)
        {
          if(jumps.get(k).startRow == move.startRow && jumps.get(k).startCol == move.startCol && jumps.get(k).endRow == move.endRow && jumps.get(k).endCol == move.endCol)
          {
            if(jumps.get(k).getPiecesTaken().size() == move.getPiecesTaken().size())
            {
              summation = 0;
              for(int l = 0; l < jumps.get(k).getPiecesTaken().size();l++)
              {
                for(int m= 0; m < jumps.get(k).getPiecesTaken().size();m++)
                {
                  if(jumps.get(k).getPiecesTaken().get(l).getKey() == move.getPiecesTaken().get(m).getKey() && 
                  jumps.get(k).getPiecesTaken().get(l).getValue() == move.getPiecesTaken().get(m).getValue())
                  {
                    summation++;
                  }
                }
              }
              if(summation==jumps.get(k).getPiecesTaken().size())
              {
                inList = true;
              }
            }
            
            
          }
        }
        if(inList == false)
        {
          jumps.add(move);
        }
        
      }
    }
    
    
    
    
  }
  
  List<Move> slides = getSlides();
  List<Move> newList = new ArrayList<Move>();
  if(jumps.isEmpty())
  {
    newList.addAll(slides);
    return newList;
  }
  else
  {
    newList.addAll(jumps);
    return newList;
  }
 }

 public void applyMove(Move move,int[][] state)
 {

  state[move.endRow][move.endCol] = state[move.startRow][move.startCol]; 
  state[move.startRow][move.startCol] = 0;
  if(!move.piecesTaken.isEmpty())
  {
    move.piecesTaken.forEach(piece->{
      
      state[piece.getKey()][piece.getValue()] = 0;
    });
  }
  if(playerTurn == 1 && move.endRow == 7&& state[move.endRow][move.endCol] == 1)
  {
    state[move.endRow][move.endCol] = playerTurn+2;
  }
  if(playerTurn == 2 && move.endRow == 0&& state[move.endRow][move.endCol] == 2)
  {
    state[move.endRow][move.endCol] = playerTurn+2;
  }
  
 }
 
 
 public void printMove(boolean isComputer,int[][] state,Game game)
 {
   
   System.out.println("the current player is " + playerTurn + " and the time limit is " + timeLimit);
   System.out.println("");
   Scanner myObj = new Scanner(System.in);
   
   
   
   List<Move> jumps = getJumps();
   
  for(int i =0; i<jumps.size();i++)
  {
    
    List<Move> subJumps = jumps;
    int once = 0;
    Game copy = new Game(game);
    
    
    copy.applyMove(jumps.get(i),copy.board);
      
    subJumps = copy.getJumps();
    
    for(int j = 0; j < subJumps.size(); j++)
    {
      if(jumps.get(i).endRow == subJumps.get(j).startRow && jumps.get(i).endCol == subJumps.get(j).startCol )
      {
        Boolean inList = false;
        int summation = 0;
        Move move = new Move(jumps.get(i).startRow, jumps.get(i).startCol,subJumps.get(j).endRow, subJumps.get(j).endCol);
        jumps.get(i).getPiecesTaken().forEach(jump->{
          move.addPiecesTaken(jump.getKey(),jump.getValue());
        });
        subJumps.get(j).getPiecesTaken().forEach(jump->{
          move.addPiecesTaken(jump.getKey(),jump.getValue());
        });
        if(playerTurn == 1 && jumps.get(i).endRow == 7 && board[jumps.get(i).startRow][jumps.get(i).startCol] == 1)
        {
          inList =true;
        }
        else if(playerTurn == 2 && jumps.get(i).endRow == 0 && board[jumps.get(i).startRow][jumps.get(i).startCol] == 2)
        {
          inList = true;
        }
        for(int k = 0; k<jumps.size();k++)
        {
          
          if(jumps.get(k).startRow == move.startRow && jumps.get(k).startCol == move.startCol && jumps.get(k).endRow == move.endRow && jumps.get(k).endCol == move.endCol)
          {
            
            if(jumps.get(k).getPiecesTaken().size() == move.getPiecesTaken().size())
            {
              summation = 0;
              for(int l = 0; l < jumps.get(k).getPiecesTaken().size();l++)
              {
                for(int m= 0; m < jumps.get(k).getPiecesTaken().size();m++)
                {
                  if(jumps.get(k).getPiecesTaken().get(l).getKey() == move.getPiecesTaken().get(m).getKey() && 
                  jumps.get(k).getPiecesTaken().get(l).getValue() == move.getPiecesTaken().get(m).getValue())
                  {
                    summation++;
                  }
                }
    
              }
              if(summation == jumps.get(k).getPiecesTaken().size())
              {
                inList = true;
              }
            }
            
            
          }
        }
        if(inList == false)
        {
          jumps.add(move);
        }
      }
    }
    
    copy = new Game(copy);
    copy.applyMove(jumps.get(i),copy.board);
      
    subJumps = copy.getJumps();
    for(int j = 0; j < subJumps.size(); j++)
    {
      if(jumps.get(i).endRow == subJumps.get(j).startRow && jumps.get(i).endCol == subJumps.get(j).startCol )
      {
        
        Move move = new Move(jumps.get(i).startRow, jumps.get(i).startCol,subJumps.get(j).endRow, subJumps.get(j).endCol);
        jumps.get(i).getPiecesTaken().forEach(jump->{
          move.addPiecesTaken(jump.getKey(),jump.getValue());
        });
        subJumps.get(j).getPiecesTaken().forEach(jump->{
          move.addPiecesTaken(jump.getKey(),jump.getValue());
        });
        int summation = 0;
        Boolean inList = false;
        if(playerTurn == 1 && jumps.get(i).endRow == 7 && board[jumps.get(i).startRow][jumps.get(i).startCol] == 1)
        {
          inList =true;
        }
        else if(playerTurn == 2 && jumps.get(i).endRow == 0 && board[jumps.get(i).startRow][jumps.get(i).startCol] == 2)
        {
          inList = true;
        }
        for(int k = 0; k<jumps.size();k++)
        {
          if(jumps.get(k).startRow == move.startRow && jumps.get(k).startCol == move.startCol && jumps.get(k).endRow == move.endRow && jumps.get(k).endCol == move.endCol)
          {
            if(jumps.get(k).getPiecesTaken().size() == move.getPiecesTaken().size())
            {
              summation = 0;
              for(int l = 0; l < jumps.get(k).getPiecesTaken().size();l++)
              {
                for(int m= 0; m < jumps.get(k).getPiecesTaken().size();m++)
                {
                  if(jumps.get(k).getPiecesTaken().get(l).getKey() == move.getPiecesTaken().get(m).getKey() && 
                  jumps.get(k).getPiecesTaken().get(l).getValue() == move.getPiecesTaken().get(m).getValue())
                  {
                    summation++;
                  }
                }
              }
              if(summation==jumps.get(k).getPiecesTaken().size())
              {
                inList = true;
              }
            }
            
            
          }
        }
        if(inList == false)
        {
          jumps.add(move);
        }
        
      }
    }
    
    
    
    
  }
  
   List<Move> slides = getSlides();
   List<Move> newList = new ArrayList<Move>();
   
   if(newList.isEmpty()&&slides.isEmpty())
   {
     
     end = true;
     return;
     
  }
  else if(!jumps.isEmpty())
  {
    newList.addAll(jumps);
   {
    jumps.forEach(jump ->{
      System.out.println(i + "   (" + (jump.startRow+1)+ "," + (jump.startCol+1) +")" + "  ->  " + "(" + (jump.endRow+1)+ "," + (jump.endCol+1) +") + "+jump.showPiecesTaken());
      i++;
      
    });
   }
  }
  else if(!slides.isEmpty())
  {
    newList.addAll(slides);
    slides.forEach(slide ->{
      System.out.println(i + "   (" + (slide.startRow+1)+ "," + (slide.startCol+1) +")" + "  ->  " + "(" + (slide.endRow+1)+ "," + (slide.endCol+1) +")");
      i++;
    });
  }
   
  if(!isComputer)
  {
    Scanner scan = new Scanner(System.in);
    int choice = -1;
    while(choice < 0 || choice > newList.size())
    {
      System.out.print("Choose Move #:        ");
      choice = Integer.parseInt(scan.nextLine());
    }
    board[newList.get(choice).endRow][newList.get(choice).endCol] = board[newList.get(choice).startRow][newList.get(choice).startCol]; 
    board[newList.get(choice).startRow][newList.get(choice).startCol] = 0;
    if(!newList.get(choice).piecesTaken.isEmpty())
    {
      newList.get(choice).piecesTaken.forEach(piece->{
        board[piece.getKey()][piece.getValue()] = 0;
      });
    }
    if(playerTurn == 1 && newList.get(choice).endRow == 7&& board[newList.get(choice).endRow][newList.get(choice).endCol] == 1)
    {
      board[newList.get(choice).endRow][newList.get(choice).endCol] = playerTurn+2;
    }
    if(playerTurn == 2 && newList.get(choice).endRow == 0&& board[newList.get(choice).endRow][newList.get(choice).endCol] == 2)
    {
      board[newList.get(choice).endRow][newList.get(choice).endCol] = playerTurn+2;
    }
  
 }
  
  
 }
 
}
