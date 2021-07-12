package tictactoe;

import java.util.Scanner;

public class Main {

    static char[][] field = new char[3][3];
    static String state;
    static Scanner scanner;
    static char currentPlayer = 'X';

    private enum State{
        DRAW,
        X_WINS,
        O_WINS,
        IMPOSSIBLE,
        GAME_NOT_FINISHED
    }

    private static boolean checkDiagonal(char symbol) {

        boolean toRight, toLeft;
        toRight = true;
        toLeft = true;

        for (int i=0; i < 3; ++i) {

            toRight &= (field[i][i] == symbol);
            toLeft &= (field[3-i-1][i] == symbol);

        }

        return toLeft || toRight;
    }

    private static boolean checkLanes(char symbol) {

        boolean rows, cols;

        for (int i = 0; i < 3; ++i) {

            rows = true;
            cols = true;

            for (int j = 0; j < 3; ++j) {

                rows &= (field[i][j] == symbol);
                cols &= (field[j][i] == symbol);

            }

            if (rows || cols) {
                return true;
            }

        }

        return false;

    }

    public static State analyseState() {

        int numberOfXCells = 0;
        int numberOfOCells = 0;

        for (char[] row : field) {
            for (char element : row) {
                switch (element) {
                    case 'X':
                        ++numberOfXCells;
                        break;
                    case 'O':
                        ++numberOfOCells;
                        break;
                    default:
                        break;
                }
            }
        }

        if (Math.abs(numberOfOCells - numberOfXCells) > 1) {
            return State.IMPOSSIBLE;
        }

        boolean xWins = checkDiagonal('X');
        if (!xWins) {
            xWins = checkLanes('X');
        }

        boolean oWins = checkDiagonal('O');
        if (!oWins) {
            oWins = checkLanes('O');
        }

        if (xWins && oWins) {

            return State.IMPOSSIBLE;

        }

        if (xWins) {
            return State.X_WINS;
        } else if (oWins) {
            return State.O_WINS;
        }

        if (numberOfOCells + numberOfXCells == 9) {
            return State.DRAW;
        }

        return State.GAME_NOT_FINISHED;

    }

    private static void fillField(String state) {

        for (int i = 0; i < 3; ++i) {

            for (int j = 0; j < 3; ++j) {

                field[i][j] = state.charAt(3 * i + j);
                field[i][j] = field[i][j] == '_' ? ' ': field[i][j];

            }

        }

    }

    private static void printField() {

        if (state != null) {
            System.out.println("Enter cells: " + state);
        }
        System.out.println("---------");
        for (char[] row : field) {

            System.out.print("| ");

            for (char element : row) {

                System.out.print(element + " ");

            }

            System.out.print("|\n");

        }

        System.out.println("---------");

    }

    private static void fillEmptyField() {

        for (int i = 0; i < 3; ++i) {

            for (int j = 0; j < 3; ++j) {

                field[i][j] = ' ';

            }

        }

    }

    private static void getAndSetCoordinatesFromPlayer() {

        boolean success = false;

        while (!success) {

            System.out.print("Enter the coordinates: ");
            int row, col;
            try {
                row = scanner.nextInt() - 1;
                col = scanner.nextInt() - 1;
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                continue;
            }

            if (!(-1 < row && row < 3) || !(-1 < col && col < 3)) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if (field[row][col] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            field[row][col] = currentPlayer;
            success = true;

        }
    }

    public static void main(String[] args) {

        scanner = new Scanner(System.in);

        fillEmptyField();
        printField();

        boolean gameEnd = false;
        while (!gameEnd) {

            getAndSetCoordinatesFromPlayer();
            printField();
            switch (analyseState()) {
                case DRAW:
                        System.out.println("Draw");
                        gameEnd = true;
                    break;
                case X_WINS:
                        System.out.println("X wins");
                        gameEnd = true;
                    break;
                case O_WINS:
                        System.out.println("O wins");
                        gameEnd = true;
                    break;
                case IMPOSSIBLE:
                        System.out.println("Impossible");
                        gameEnd = true;
                    break;
                case GAME_NOT_FINISHED:
                    break;
            }

            currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
        }
    }
}
