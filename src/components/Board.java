package components;

import java.util.Set;
import java.util.Queue;
import java.util.Random;
import java.util.HashSet;

import static components.Constants.minLadderLength;
import static components.Constants.minSnakeLength;

public class Board {
    private final Dice dice;
    private final Cell[][] cells;
    private final Queue<Player> playerQueue;
    private Player winner;

    public Board(Integer noOfDice, Integer boardSize, Queue<Player> playerQueue) {
        this.dice = new Dice(noOfDice);
        this.cells = new Cell[boardSize][boardSize];
        this.playerQueue = playerQueue;
        createBoard();
        addPlayerPositions();
        addSnakesAndLadders();
    }

    private void createBoard() {
        int n = cells.length;
        for(int row = 0; row < n; row++) {
            for(int col = 0; col < n; col++) {
                Integer cellId = getPosition(row, col, n);
                cells[row][col] = new Cell(cellId);
            }
        }
    }

    private void addSnakesAndLadders() {
        int n = cells.length;
        Random random = new Random();
        Set<Integer> snakeStart = new HashSet<>();
        for(int row = 0; row <= n/2; row += 2) {
            int startCol = random.nextInt(n);
            int endRow = random.nextInt(row + minSnakeLength);
            int endCol = random.nextInt(n);

            Integer from = getPosition(row, startCol, n);
            Integer to = getPosition(endRow, endCol, n);
//            System.out.println("Snake - " + from + " " + to);
            Jump jump = new Jump(from, to);
            cells[row][startCol].makeCellSpecial(jump);
            snakeStart.add(from);
        }

        for(int row = cells.length - 1; row >= n/2; row -= 2) {
            int startCol = random.nextInt(n);
            int endRow = random.nextInt(row - minLadderLength);
            int endCol = random.nextInt(n);

            Integer from = getPosition(row, startCol, n);

            if(snakeStart.contains(from)) {
                row += 2;
                continue;
            }

            Integer to = getPosition(endRow, endCol, n);
            Jump jump = new Jump(from, to);
//            System.out.println("Ladder - " + from + " " + to);
            cells[row][startCol].makeCellSpecial(jump);
        }
    }

    private void addPlayerPositions() {
        for(Player player : playerQueue) {
            Cell cell = getCell(1, cells.length);
            player.setCurrCell(cell);
        }
    }

    public void play() {
        Integer max = cells.length * cells.length;
        while (winner == null) {
            Player player = playerQueue.poll();
            Cell playerCell = player.getCurrCell();
            Integer diceRoll = dice.rollDice();
            System.out.printf("Player %d rolls %d%n", player.getPlayerId(), diceRoll);
            Integer newPos = playerCell.getCellId() + diceRoll;

            if (newPos > max) {
                System.out.printf("Player %d rolled out of the board, Staying in the same position%n", player.getPlayerId());
            } else if (newPos.equals(max)) {
                System.out.printf("Congratulations Player %d! You have reached the top!%n", player.getPlayerId());
                winner = player;
            } else {
                System.out.printf("Player %d moves to %d%n", player.getPlayerId(), newPos);
                Cell newCell = getCell(newPos, cells.length);
                if (newCell.getIsSpecial()) {
                    Jump jump = newCell.getJump();
                    String adj = "Yay!", type = "Ladder";
                    if (jump.getFrom() > jump.getTo()) {
                        adj = "Oh no!";
                        type = "Snake";
                    }

                    System.out.printf("%s There is a %s on %d, Player %d moves to %d%n", adj, type, newPos, player.getPlayerId(), jump.getTo());
                    newCell = getCell(jump.getTo(), cells.length);
                }

                player.setCurrCell(newCell);
            }

            playerQueue.offer(player);
        }
    }

    private Cell getCell(Integer pos, Integer n) {
        int row = n - (pos - 1)/n - 1;
        int c = (pos - 1) % n;

        int col = (n % 2 == row % 2) ? n - c - 1 : c;
        return cells[row][col];
    }

    private Integer getPosition(int row, int col, int n) {
        int c = (n % 2 == row % 2) ? col : n - col - 1;
        return n * (n - row) - c;
    }
}
