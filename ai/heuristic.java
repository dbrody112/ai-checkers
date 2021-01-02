package ai;
import java.util.*;

import javax.lang.model.util.ElementScanner14;
import javax.lang.model.util.ElementScanner6;

class Heuristic
{
    int maximizingPlayer;
    int oppPlayer;
    int oppPieces;
    int allyPieces;
    int oppKings;
    int allyKings;
    int timeLimit;
    int maxDepth;
    long startClock;
    long endClock;
    boolean outOfTime;
    int heuristicType;

    public Heuristic(int maximizingPlayer,int heuristicType)
    {
        this.maximizingPlayer = maximizingPlayer;
        this.heuristicType = heuristicType;
        if(maximizingPlayer==2)
        {
            this.oppPlayer = 1;
        }
        else
        {
            this.oppPlayer =2;
        }
    }
    public Move alphaBeta(Game game) {
        Date date = new Date();
        startClock = date.getTime();
        boardStatus(game);
        outOfTime = false;
        Random random = new Random();
        int bestMoveVal = 0;
        int depthReached = 0;
        Move bestMove = null;
        List<Move> bestMovesAtCurrentDepth;
        List<Move> legalMoves = game.getLegalMoves(game.board,game);
        /*legalMoves.forEach(move->{
            System.out.println(move.getPiecesTaken().size());
        });*/

        if (legalMoves.size() == 1) {
            System.out.println("Searched to depth 0 in 0 seconds.");
            return legalMoves.get(0);
        }
        
        for (maxDepth = 0; maxDepth < 15 && !outOfTime; maxDepth++) {
            bestMovesAtCurrentDepth = new ArrayList<Move>();
            int smallest = Integer.MIN_VALUE;
            for (Move move : legalMoves) {
                Game template = new Game(game);
                template.applyMove(move,template.board);
                int min = minimum(template, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
                if (outOfTime)
                {
                    break;
                } 
                if (min == smallest) {
                    bestMovesAtCurrentDepth.add(move);
                }
                if (min > smallest) {
                    bestMovesAtCurrentDepth.clear();
                    bestMovesAtCurrentDepth.add(move);
                    smallest = min;
                }
                
                if (smallest == Integer.MAX_VALUE)
                {
                    break;
                } 
            }
            if (!outOfTime) {
                int chosen = 0;
                try{
                    chosen = random.nextInt(bestMovesAtCurrentDepth.size());
                }
                catch(IllegalArgumentException e)
                {
                    break;
                }
                bestMove = bestMovesAtCurrentDepth.get(chosen);
                depthReached = maxDepth;
                bestMoveVal = smallest;
            }
            if (bestMoveVal == Integer.MAX_VALUE)
            {
                break;
            } 
        }
        
        System.out.println("Searched to depth " + depthReached + " in " + ((endClock-startClock)/1000) + " seconds.");
        if(outOfTime)
        {
            System.out.println("partial search");
        }
        return bestMove;
    }
    
    public boolean finished(int numMoves, int depth) {
        if (numMoves == 0 || depth == maxDepth){
            return true;
        }
        return false;
    }

    public int maximum(Game game, int alpha, int beta, int depth) {
        Date newDate = new Date();
        endClock = newDate.getTime();
        if ((endClock - startClock) >= timeLimit * 990) {
            outOfTime = true;
            return 0;
        }
        List<Move> legalMoves = game.getLegalMoves(game.board,game);
        if (finished(legalMoves.size(), depth)) {
            return heuristic_chooser(game);
        }
        int v = Integer.MIN_VALUE;
        for (Move move: legalMoves) {
            Game template = new Game(game);
            template.applyMove(move,template.board);
            v = Math.max(v, minimum(template, alpha, beta, depth + 1));
            if (v >= beta) return v;
            alpha = Math.max(alpha, v);
        }
        return v;
    }

    public int minimum(Game game, int alpha, int beta, int depth) {
        Date newDate = new Date();
        endClock = newDate.getTime();
        if ((endClock - startClock) > timeLimit * 980) {
            outOfTime = true;
            return 0;
        }
        List<Move> legalMoves = game.getLegalMoves(game.board,game);
        if (finished(legalMoves.size(), depth)) {
            return heuristic_chooser(game);
        }
        int v = Integer.MAX_VALUE;
        
        for (Move move:legalMoves) {
            Game template = new Game(game);
            template.applyMove(move,template.board);
            v = Math.min(v, maximum(template, alpha, beta, depth + 1));
            if (v <= alpha) return v;
            beta = Math.min(beta, v);
        }
        return v;
    }

    public int middleBonus(int row, int col)
    {
        return 100 - 4*(Math.abs(row-4) + Math.abs(col-4));
    }
    public int betweenPieces(int row, int col, int[][]board)
    {
        int between = 0;
        if(row+1<=7 && col+1 <=7 && row-1 >0 && col-1> 0)
        {
            if((board[row+1][col+1] == oppPlayer || board[row+1][col+1]==oppPlayer+2)
        && (board[row-1][col-1] == oppPlayer || board[row-1][col-1]==oppPlayer+2))
            {
                between+=50;
            }
            else if((board[row+1][col-1] == oppPlayer || board[row+1][col-1]==oppPlayer+2)
            && (board[row-1][col+1]==oppPlayer || board[row-1][col+1]==oppPlayer+2))
            {
                between+=50;
            }   
        }
        return between;
        
    }
    public int oppPlayerBonus(int row)
    {
        if(maximizingPlayer == 1)
        {
            return 80 - Math.abs((row-7));
        }
        else 
        {
            return 80 - Math.abs((row-1));
        } 
    }
    public int originalDefense(int row, int[][]board)
    {
        int orig_defense = 0;
        switch(maximizingPlayer)
        {
            case 1:case 3:
                for(int i = 0; i < board[0].length;i++)
                {
                    orig_defense+=board[0][i]*4;
                    orig_defense+=board[1][i]*3;
                    orig_defense+=board[2][i]*2;
                }
                break;
            case 2:case 4:
                for(int i = 0; i < board[0].length; i++)
                {
                    orig_defense+=(board[7][i]/2)*4;
                    orig_defense+=(board[6][i]/2)*3;
                    orig_defense+=(board[5][i]/2)*2;
                }
        }

        return orig_defense;
    }

    public int attackOnWinning(int player, int pieces){
        int score = 0;
        int allOppPieces = oppPieces + oppKings;
        int allAllyPieces = allyPieces+allyKings;
        if(player == 1 || player == 3)
        {
            if(maximizingPlayer == 1)
            {
                if((allAllyPieces - allOppPieces) >= Math.ceil((float) pieces/8))
                {
                    score+=100;
                }
            
            }
            else
            {      
                if((allOppPieces - allAllyPieces) >= Math.ceil((float) pieces/8))
                {
                        score+=100;
                }
            }
        }
        else
        {
            if(maximizingPlayer == 2)
            {
                if((allAllyPieces - allOppPieces) >= Math.ceil((float) pieces/8))
                {
                    score+=100;
                }
                else
                {      
                    if((allOppPieces - allAllyPieces) >= Math.ceil((float)pieces/8))
                    {
                        score+=100;
                    }
                }
            }   
        }
        return score;   
      }
        
    /*public int block(int[][] board)
    {
        

    }*/

    public int defendingNeighbors(int row, int col, int[][] board)
    {
        int strong_defense=0;
        switch(board[row][col])
        {
            case 1:
                if(row-1 > 0 && col-1>0&&(board[row-1][col-1] == maximizingPlayer||board[row-1][col-1] == maximizingPlayer+2))
                {
                    strong_defense+=80;
                }
                if(row-1>0 && col+1 < 8 &&(board[row-1][col+1] == maximizingPlayer||board[row-1][col+1] == maximizingPlayer+2))
                {
                    strong_defense+=80;
                }
            case 2:
                if(row+1 < 8 && col-1>0&&(board[row+1][col-1] == maximizingPlayer||board[row+1][col-1] == maximizingPlayer+2))
                {
                    strong_defense+=80;
                }
                if(row+1<8 && col+1 < 8 &&(board[row+1][col+1] == maximizingPlayer||board[row+1][col+1] == maximizingPlayer+2))
                {
                    strong_defense+=80;
                }
            case 3:case 4:
                if(row-1 > 0 && col-1>0&&(board[row-1][col-1] == maximizingPlayer||board[row-1][col-1] == maximizingPlayer+2))
                {
                    strong_defense+=80;
                }
                if(row-1>0 && col+1 < 8 &&(board[row-1][col+1] == maximizingPlayer||board[row-1][col+1] == maximizingPlayer+2))
                {
                    strong_defense+=80;
                }
                if(row+1 < 8 && col-1>0&&(board[row+1][col-1] == maximizingPlayer||board[row+1][col-1] == maximizingPlayer+2))
                {
                    strong_defense+=80;
                }
                if(row+1<8 && col+1 < 8 &&(board[row+1][col+1] == maximizingPlayer||board[row+1][col+1] == maximizingPlayer+2))
                {
                    strong_defense+=80;
                }
        }
        return strong_defense;  
                
    }
    
    public int heuristic_chooser(Game game) {
        switch (heuristicType) {
            case 1:
                return easyHeuristic(game);
            case 2:
                return mediumHeuristic(game);
            default:
                return hardHeuristic(game);
                
        }
    }

    public int easyHeuristic(Game game) {
        int rows = game.board.length;
        int cols = game.board[0].length;
        int score = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maximizingPlayer == 1){
                    //zero-sum
                    switch(game.board[i][j]) {
                        case 1:
                            score += 5;
                            break;
                        case 2:
                            score -= 5;
                            break;
                        case 3:
                            score += 8;
                            break;
                        case 4:
                            score -= 8;
                            break;
                    }
                } else {
                    switch(game.board[i][j]) {
                        case 1:
                            score -= 5;
                            break;
                        case 2:
                            score += 5;
                            break;
                        case 3:
                            score -= 8;
                            break;
                        case 4:
                            score += 8;
                            break;
                    }
                }
            }
        }
        return score;
    }
    public int mediumHeuristic(Game game) {
        int numRows = game.board.length;
        int numCols = game.board[0].length;
        int score = 0;
        int numAllyPieces = 0;
        int numAllyKings = 0;
        int numOppPieces = 0;
        int numOppKings = 0;
        int totalPieces=oppPieces+allyPieces+oppKings+allyKings;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if(totalPieces >= 18)
                {
                    if (maximizingPlayer == 1){
                        switch(game.board[i][j]) {
                            case 1:
                                numAllyPieces++;
                                score += defendingNeighbors(i, j, game.board) + originalDefense(i,game.board) + middleBonus(i,j);
                                break;
                            case 2:
                                numOppPieces++;
                                score -= defendingNeighbors(i, j, game.board) + originalDefense(i,game.board)+middleBonus(i,j);
                                break;
                            case 3:
                                numAllyKings++;
                                score +=  middleBonus(i,j) + (15 * i);
                                break;
                            case 4:
                                numOppKings++;
                                score -=  middleBonus(i,j) + (15 * (7-i));
                                break;
                    }
                } else {
                    switch(game.board[i][j]) {
                        case 1:
                            numOppPieces++;
                            score -= defendingNeighbors(i, j, game.board) + originalDefense(i,game.board) + middleBonus(i,j);
                            break;
                        case 2:
                            numAllyPieces++;
                            score += defendingNeighbors(i, j, game.board) + + originalDefense(i,game.board)+middleBonus(i,j);
                            break;
                        case 3:
                            numOppKings++;
                            score -= middleBonus(i,j)+ (15 * i);
                            break;
                        case 4:
                            numAllyKings++;
                            score += middleBonus(i,j) + (15 * (7-i));
                            break; 
                  }
                }
              }
                else if(totalPieces < 18 && totalPieces > 8)
                {
                    if (maximizingPlayer == 1){
                        switch(game.board[i][j]) { //orig case 3: case 4: const 50, case 1:2 const 35
                            case 1:
                                numAllyPieces++;
                                score += defendingNeighbors(i, j, game.board) + originalDefense(i,game.board) + (30 * i);
                                break;
                            case 2:
                                numOppPieces++;
                                score -= defendingNeighbors(i, j, game.board) + originalDefense(i,game.board) + (30 * (7-i));
                                break;
                            case 3:
                                numAllyKings++;
                                score +=  middleBonus(i, j) + (33 * i) ;
                                break;
                            case 4:
                                numOppKings++;
                                score -=  middleBonus(i, j) + (33 * (7-i)) ;
                                break;
                    }
                } else {
                    switch(game.board[i][j]) {
                        case 1:
                            numOppPieces++;
                            score -= defendingNeighbors(i, j, game.board) + originalDefense(i,game.board) + (30 * i);
                            break;
                        case 2:
                            numAllyPieces++;
                            score += defendingNeighbors(i, j, game.board) + originalDefense(i,game.board) + (30 * (7-i));
                            break;
                        case 3:
                            numOppKings++;
                            score -= middleBonus(i, j) + (33 * i);
                            break;
                        case 4:
                            numAllyKings++;
                            score += middleBonus(i, j) + (33 * (7-i));
                            break;
                    }
                }
              }
              else if(totalPieces < 9)
              {
                if (maximizingPlayer == 1){
                    switch(game.board[i][j]) { //orig case 3: case 4: const 50, case 1:2 const 35
                        case 1:
                            numAllyPieces++;
                            score += defendingNeighbors(i, j, game.board)*0.5 + originalDefense(i,game.board)*.5 + (15 * i) ;
                            break;
                        case 2:
                            numOppPieces++;
                            score -= defendingNeighbors(i, j, game.board)*0.5 + originalDefense(i,game.board)*.5 + (15 * (7-i)) ;
                            break;
                        case 3:
                            numAllyKings++;
                            score +=  defendingNeighbors(i, j, game.board)*.5 + (0.5*i);
                            break;
                        case 4:
                            numOppKings++;
                            score -=   defendingNeighbors(i, j, game.board)*.5 + (0.5*(7-i));
                            break;
                }
            } else {
                switch(game.board[i][j]) {
                    case 1:
                        numOppPieces++;
                        score -= defendingNeighbors(i, j, game.board)*0.5 + originalDefense(i,game.board)*.5 + (15 * i) ;
                        break;
                    case 2:
                        numAllyPieces++;
                        score += defendingNeighbors(i, j, game.board)*0.5 + originalDefense(i,game.board)*.5 + (15 * (7-i));
                        break;
                    case 3:
                        numOppKings++;
                        score -= defendingNeighbors(i, j, game.board)*.5+ middleBonus(i,j) + (0.5*i);
                        break;
                    case 4:
                        numAllyKings++;
                        score += defendingNeighbors(i, j, game.board)*.5+middleBonus(i, j) + (0.5*(7-i));
                        break;
                }
            
              }
            }
          }
        }
    
        return score;
    }

    public int hardHeuristic(Game game) {
        int numRows = game.board.length;
        int numCols = game.board[0].length;
        int score = 0;
        int numAllyPieces = 0;
        int numAllyKings = 0;
        int numOppPieces = 0;
        int numOppKings = 0;
        int totalPieces=oppPieces+allyPieces+oppKings+allyKings;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                    if (maximizingPlayer == 1){
                        switch(game.board[i][j]) { //orig case 3: case 4: const 50, case 1:2 const 35
                            case 1:
                                numAllyPieces++;
                                score += defendingNeighbors(i, j, game.board)*.25 + originalDefense(i,game.board)*.5 + (25 * i);
                                break;
                            case 2:
                                numOppPieces++;
                                score -= defendingNeighbors(i, j, game.board)*.25 + originalDefense(i,game.board)*.5 + (25 * (7-i));
                                break;
                            case 3:
                                numAllyKings++;
                                score +=  middleBonus(i, j);
                            case 4:
                                numOppKings++;
                                score -=  middleBonus(i, j);
                                break;
                    }
                } else {
                    switch(game.board[i][j]) {
                        case 1:
                            numOppPieces++;
                            score -= defendingNeighbors(i, j, game.board) + originalDefense(i,game.board) + (25 * i);
                            break;
                        case 2:
                            numAllyPieces++;
                            score += defendingNeighbors(i, j, game.board) + originalDefense(i,game.board) + (25* (7-i));
                            break;
                        case 3:
                            numOppKings++;
                            score -=  middleBonus(i, j);
                            break;
                        case 4:
                            numAllyKings++;
                            score += middleBonus(i, j);
                            break;
                    }
                }
            }
        }
        return score;
    }

    public void boardStatus(Game game) {
        int numRows = game.board.length;
        int numCols = game.board[0].length;
        allyPieces = 0;
        allyKings = 0;
        oppPieces = 0;
        oppKings = 0;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (maximizingPlayer == 1) {
                    switch (game.board[i][j]) {
                        case 1:
                            allyPieces++;
                            break;
                        case 2:
                            oppPieces++;
                            break;
                        case 3:
                            allyKings++;
                            break;
                        case 4:
                            oppKings++;
                            break;
                    }
                }
                else {
                    switch (game.board[i][j]) {
                        case 1:
                            oppPieces++;
                            break;
                        case 2:
                            allyPieces++;
                            break;
                        case 3:
                            oppKings++;
                            break;
                        case 4:
                            allyKings++;
                            break;
                    }
                }
            }
        }
    }

}