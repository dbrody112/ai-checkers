package ai;
import java.util.*;


//have same symbol for player q and player 2 if allowed (check prompt)
class Pair
{
    // Return a map entry (key-value pair) from the specified values
    public static <T, U> Map.Entry<T, U> of(T first, U second)
    {
        return new AbstractMap.SimpleEntry<>(first, second);
    }
}
class Move{
  int startRow;
  int startCol;
  int endRow;
  int endCol;
  String statement = "pieces taken: ";

  ArrayList<Map.Entry<Integer, Integer>> piecesTaken = new ArrayList<>();
  
  //if not king

  public Move(){

  }

  public Move(int startRow,int startCol,int endRow,int endCol){
    this.startRow = startRow;
    this.startCol = startCol;
    this.endRow = endRow;
    this.endCol = endCol;
    
  }
  

  public void setStartRow(int startRow){
    this.startRow = startRow;
  }

  public void setStartCol(int startCol){
    this.startCol = startCol;
  }

  public void addPiecesTaken(int x, int y)
  {
    piecesTaken.add(Pair.of(x,y));
  }
  public ArrayList<Map.Entry<Integer, Integer>> getPiecesTaken(){
    return piecesTaken;
  }
  public String showPiecesTaken()
  {
    
    piecesTaken.forEach(piece ->{
      statement+="(" + (piece.getKey()+1)+ "," + (piece.getValue()+1) +") , " ;
    });
    return statement;
  }
  public void printMove()
  {
    System.out.println("("+(startRow+1) +", " +(startCol+1) +") --> (" + (endRow+1) +", "+ (endCol+1) +")");
    if(!piecesTaken.isEmpty())
    {
      System.out.println(showPiecesTaken());
    }
  }

  

}
