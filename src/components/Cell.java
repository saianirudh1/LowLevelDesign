package components;

import lombok.Getter;

@Getter
public class Cell {
    private Integer cellId;
    private Jump jump;
    private Boolean isSpecial;

    public Cell(Integer cellId) {
        this.cellId = cellId;
        this.isSpecial = false;
    }

    public void makeCellSpecial(Jump jump) {
        this.jump = jump;
        this.isSpecial = true;
    }
}
