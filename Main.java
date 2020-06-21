public class Main {

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        boolean gameWon = game.isGameWon();
        boolean uInputAccepted = false;

        while (!gameWon) { //till the game is finished
            uInputAccepted = game.fillBoard();
            if (uInputAccepted) {
                gameWon = game.isGameWon();
            }
        }
    }

}
