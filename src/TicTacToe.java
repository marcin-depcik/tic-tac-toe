
import java.util.*;

public class TicTacToe {

    private final String COMPUTER = "Computer";
    private final String PLAYER = "Player";
    private List<List> winningPositions = new ArrayList<>();
    private List<Integer> playerPositions = new ArrayList<>();
    private List<Integer> computerPositions = new ArrayList<>();
    private Random random = new Random();
    private char[][] gameBoard;

    public void execution() {
        setUpGameBoard();
        init();
        playGame();
    }

    private void setUpGameBoard() {
        gameBoard = new char[][]{
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}};
    }

    private void init() {
        List topRow = Arrays.asList(1, 2, 3);
        List middleRow = Arrays.asList(4, 5, 6);
        List bottomRow = Arrays.asList(7, 8, 9);
        List leftColumn = Arrays.asList(1, 4, 7);
        List middleColumn = Arrays.asList(2, 5, 8);
        List rightColumn = Arrays.asList(3, 6, 9);
        List leftDiagonal = Arrays.asList(1, 5, 9);
        List rightDiagonal = Arrays.asList(3, 5, 7);

        winningPositions.add(topRow);
        winningPositions.add(middleRow);
        winningPositions.add(bottomRow);
        winningPositions.add(leftColumn);
        winningPositions.add(middleColumn);
        winningPositions.add(rightColumn);
        winningPositions.add(leftDiagonal);
        winningPositions.add(rightDiagonal);
    }

    private void playGame() {
        int playersTurn = random.nextInt(2);
        char firstMove;

        if (playersTurn == 0) {
            firstMove = 'P';
            playersTurn();
        } else {
            firstMove = 'C';
            computersTurn();
        }

        while (true) {
            if (firstMove == 'P') {
                computersTurn();
                playersTurn();
            } else {
                playersTurn();
                computersTurn();
            }
        }
    }

    private void playersTurn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter chosen placement (1-9):");
        int playerPosition = scanner.nextInt();

        while (playerPositions.contains(playerPosition) || computerPositions.contains(playerPosition) ||
                (playerPosition < 1 || playerPosition > 9)) {
            System.out.println("InputPosition: please, enter different position:");
            playerPosition = scanner.nextInt();
        }

        placePosition(gameBoard, PLAYER, playerPosition);
        printGameBoard(gameBoard, PLAYER);

        int result = isGameFinished();
        if (result != 0) {
            printResult(result);
        }
    }

    private void computersTurn() {
        Random random = new Random();
        int computerPosition = random.nextInt(9) + 1;

        while (playerPositions.contains(computerPosition) || computerPositions.contains(computerPosition)) {
            computerPosition = random.nextInt(9) + 1;
        }

        placePosition(gameBoard, COMPUTER, computerPosition);
        printGameBoard(gameBoard, COMPUTER);

        int result = isGameFinished();
        if (result != 0) {
            printResult(result);
        }
    }

    private void printGameBoard(char[][] gameBoard, String user) {
        if (user.equalsIgnoreCase("Player")) {
            System.out.println();
            System.out.println("Player's turn:");
        } else if (user.equalsIgnoreCase("Computer")) {
            System.out.println();
            System.out.println("Computer's turn:");
        }

        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }

        System.out.println("======");
    }

    private void placePosition(char[][] gameBoard, String user, int position) {
        char symbol = ' ';

        if (user.equals("Player")) {
            symbol = 'X';
            playerPositions.add(position);
        } else if (user.equals("Computer")) {
            symbol = 'O';
            computerPositions.add(position);
        }

        switch (position) {
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
        }
    }

    private int isGameFinished() {
        for (List position : winningPositions) {
            if (playerPositions.containsAll(position)) {
                return 1;
            } else if (computerPositions.containsAll(position)) {
                return 2;
            }
        }

        if (playerPositions.size() + computerPositions.size() == 9) {
            return 3;
        }

        return 0;
    }

    private void printResult(int result) {
        switch (result) {
            case 1:
                System.out.println("Player wins!");
                break;
            case 2:
                System.out.println("Computer wins!");
                break;
            case 3:
                System.out.println("Tt is a tie!");
                break;
        }

        System.out.println("Do you want play again?(Y/N)");
        Scanner scanner = new Scanner(System.in);
        String retake = scanner.next();
        if (retake.equalsIgnoreCase("Y")) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    private void resetGame() {
        playerPositions.clear();
        computerPositions.clear();
        execution();
    }
}
