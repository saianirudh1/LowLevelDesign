package components;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class Player {
    private Integer playerId;
    private String playerName;
    @Setter private Cell currCell;
}
