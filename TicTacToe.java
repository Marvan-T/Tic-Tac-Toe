import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class TicTacToe {
    Scanner scanner;
    private final String[][] board; //game board
    private final String[] inputLine; //every input goes to this array (used for checking win conditions)
    private boolean gameWon; //a flag to check the status of the game
    private Boolean xTurn; //a flag to check who's turn it is (X or Y)
    private int counter; //used to check whether the game is draw

    final int[][] WINNING_COMBINATIONS = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };

    Map<String, Integer> map2DTo1DArray = Map.of( //maps 2D array indexes to an arrayIndex in a 1D array
            "00",0,
            "01",1,
            "02",2,
            "10",3,
            "11",4,
            "12",5,
            "20",6,
            "21",7,
            "22",8
    );

    public TicTacToe() {
        scanner = new Scanner(System.in);
        board = new String[3][3];
        inputLine = new String[9];
        gameWon = false;
        xTurn = true;
        counter = 0;

        //filling the 2D array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = " ";  //initially the board is empty
                inputLine[counter] = " ";
                counter++;
            }
        }

        counter++;
        printBoard(); //printing the board before user input
    }

    /*
Fills the board based on user input coordinates (if the coordinates are valid)
 */
    public boolean fillBoard() {
        boolean inputAccepted = false;
        System.out.println("Enter the coordinates:");
        String[] input = scanner.nextLine().split(" ");

        if (input[0].matches("\\d+") && input[1].matches("\\d+")) {
            int x = Integer.parseInt(input[0]);
            int y = Integer.parseInt(input[1]);

            if (x > 3 || y > 3) { //checking if the provided coordinates could be mapped to the existing board (2D array)
                System.out.println("Coordinates should be from 1 to 3!");
            } else {
                int outerArray = 3 - y;
                int innerArray = x - 1; //mapping the coordinates to the 2D array

                if (Objects.equals(" ", board[outerArray][innerArray])) { //checking if the cell is empty
                    int arrayIndex = map2DTo1DArray.get(Integer.toString(outerArray) + Integer.toString(innerArray));
                    if (xTurn) {
                        board[outerArray][innerArray] = "X"; //passing the user input to the board
                        printBoard();
                        inputLine[arrayIndex] = "X";
                        checkWin();
                        counter++; //to check for draw
                        xTurn = false;
                    } else {
                        board[outerArray][innerArray] = "O"; //passing the user input to the board
                        printBoard();
                        inputLine[arrayIndex] = "O";
                        checkWin();
                        counter++; //to check for draw
                        xTurn = true;
                    }
                    inputAccepted = true;
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            }
        } else {
            System.out.println("You should only enter numbers");
        }
        return inputAccepted;
    }

    /*
   Prints the board to the terminal
    */
    private void printBoard() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
    }

    /*
Checks who won the game
 */
    private void checkWin() {
        boolean xWon;
        boolean oWon;

        for (int i = 0; i < 8; i++) { //there is only 8 maximum win conditions
            int[] aCondition = WINNING_COMBINATIONS[i];
            xWon = checkWIN(aCondition,"X"); //checking if X won

            if (xWon) {
                gameWon = true;
                System.out.println("X wins");
                break;
            }
        }

        for (int i = 0; i < 8; i++) {
            int[] aCondition = WINNING_COMBINATIONS[i];
            oWon = checkWIN(aCondition,"O"); //checking if O won

            if (oWon) {
                gameWon = true;
                System.out.println("O wins");
                break;
            }
        }

        if (counter == 9) {
            System.out.println("Draw");
            gameWon = true;
        }
    }

    /*
  Checks who won the game based on a win condition
  */
    private boolean checkWIN(int[] winCondition, String characterToCheck) { //character to check "X" or "O"
        boolean aWin = false;

        switch (characterToCheck.toUpperCase()) {
            case "X":
                if (inputLine[winCondition[0]].equalsIgnoreCase("X") && inputLine[winCondition[1]].equalsIgnoreCase("X") && inputLine[winCondition[2]].equalsIgnoreCase("X")) {
                    aWin = true;
                }
                break;

            case "O":
                if (inputLine[winCondition[0]].equalsIgnoreCase("O") && inputLine[winCondition[1]].equalsIgnoreCase("O") && inputLine[winCondition[2]].equalsIgnoreCase("O")) {
                    aWin = true;
                }
                break;
        }
        return aWin;
    }

    /*
    Returns whether the game is finished or no
     */
    public boolean isGameWon() {
        return gameWon;
    }
}
