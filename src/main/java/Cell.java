public class Cell {

    public Color cellColor;
    public Integer rowIndex;
    public Integer columnIndex;

    public Cell(Integer rowIndex, Integer columnIndex, Color cellColor){
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.cellColor = cellColor;
    }
}
