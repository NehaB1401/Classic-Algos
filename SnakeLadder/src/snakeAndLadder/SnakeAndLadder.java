package snakeAndLadder;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * File Name: SnakeAndLadder.java 
 * SnakeAndLadder concrete class
 * 
 * 
 * To Compile: IntUtil.java RandomInt.java SnakeAndLadder.java SnakeAndLadderBase.java
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

class SnakeAndLadder extends SnakeAndLadderBase{
	//You can have any number of private data or private functions here
	SnakeAndLadder() {
		//NOTHING CAN BE CHANGED HERE
		testBench();
	}
	
	@Override
  void play() {
		//NOTHING CAN BE CHANGED HERE
		//WRITE YOUE CODE in private procedure alg()
		//YOU CAN HAVE ANY NUMBER OF PRIVATE functions and variables
		alg() ;
	}
	
	/*-------------------------WTITE CODE BELOW-----------------------------*/
	//YOU CAN HAVE ANY NUMBER OF PRIVATE functions and variables

	private void alg() {
		int currentTurn = 1,			// ongoing turn
	        currentSquare = 1,			// current square
	        diceRolledCount = 0,		// no of times dice rolled
	        diceRolled = 0;				// dice value
		
		int _ = 10;

        max = 0;
        min = Integer.MAX_VALUE;

        // initialized min and max span with 1st square
        StringBuilder minsTemp = new StringBuilder();	
        StringBuilder maxsTemp = new StringBuilder();

        Map<Integer, Integer> shiftMap = new HashMap<>();

        for (int i = 0; i < ladders.length; i++) {
            if (ladders[i][0] < ladders[i][1])					// check for reverse values
                shiftMap.put(ladders[i][0], ladders[i][1]);
            else
                shiftMap.put(ladders[i][1], ladders[i][0]);
        }

        for (int i = 0; i < snakes.length; i++) {
            if (snakes[i][0] > snakes[i][1])					// check for reverse values
                shiftMap.put(snakes[i][0], snakes[i][1]);
            else
                shiftMap.put(snakes[i][1], snakes[i][0]);
        }

        StringBuilder span = new StringBuilder("1");

        Random r = new Random();

        while (currentTurn <= numGames) {

            diceRolled = RandomInt.getRandomInt(r, 5) + 1;
            
            // check to keep the player under 100 square 
            if (((currentSquare + diceRolled) <= numSquare)) {
                currentSquare = currentSquare + diceRolled;
            }

            diceRolledCount++;
            span.append("->");

            // if mapping found, update current square 
            if (shiftMap.containsKey(currentSquare)) {
                span.append("(");
                span.append(currentSquare);
                span.append(")");
                currentSquare = shiftMap.get(currentSquare);
            }

            span.append(currentSquare);

            // if last square is reached, re-initialized default values and updated min/max
            if (currentSquare == numSquare) {
                if (diceRolledCount < min) {
                    min = diceRolledCount;
                    minsTemp.replace(0, minsTemp.length(), span.toString());
                }
                if (diceRolledCount > max) {
                    max = diceRolledCount;
                    maxsTemp.replace(0, maxsTemp.length(), span.toString());
                }
                currentSquare = 1;
                currentTurn++;
                diceRolledCount = 0;
                span.replace(0, span.length(), "1");
            }
        }
        
        // assigned temp string builder values to Strings (mins & maxs)
        mins = minsTemp.toString();
        maxs = maxsTemp.toString();
    }
	
	public static void main(String[] args) {
		//NOTHING CAN BE CHANGED HERE
		System.out.println("SnakeAndLadder problem STARTS");
		SnakeAndLadder m = new SnakeAndLadder() ;
		System.out.println("SnakeAndLadder problem ENDS");
	}
}