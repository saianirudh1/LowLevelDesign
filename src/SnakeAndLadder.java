import java.util.*;

import components.Board;
import components.Player;

import static components.Constants.boardSize;
import static components.Constants.diceCount;

public class SnakeAndLadder {
    public static void main(String[] args) {
        Board board = new Board(diceCount, boardSize, getPlayers());
        board.play();
    }

    private static Queue<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter no. of players");
        int playerCount = sc.nextInt();
        for(int index = 0; index < playerCount; index++) {
            Player player = new Player(index + 1, "name", null);
            players.add(player);
        }

        return new LinkedList<>(players);
    }
}
