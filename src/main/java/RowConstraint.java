import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

import java.util.ArrayList;
import java.util.List;

public class RowConstraint implements Constraint {

    public List<Variable> rowScope;
    public List<Integer> clueOfRow;

    public RowConstraint(ArrayList<Variable> variableArrayList, List<Integer> rowClue){
        rowScope = new ArrayList<Variable>();
        clueOfRow = new ArrayList<Integer>();
        addElementsToRowScope(variableArrayList);
        addElementsToRowClue(rowClue);
    }

    private void addElementsToRowClue(List<Integer> columnClue) {
        for(Integer integer: columnClue){
            clueOfRow.add(integer);
        }
    }

    private void addElementsToRowScope(ArrayList<Variable> variableArrayList) {
        for(Variable var : variableArrayList){
            rowScope.add(var);
        }
    }

    public List<Variable> getScope() {
        return rowScope;
    }


    public boolean isSatisfiedWith(Assignment assignment) {

        Cell [] cellArray = new Cell [10];
        int index = 0;

        for(Variable var : rowScope) {
            if(assignment.getAssignment(var) == null ) break;
            cellArray[index] = (Cell) assignment.getAssignment(var);
            index++;
        }

        int blockIndex = 0;
        Boolean firstBlackCellOccuredInCurrentBlock = false;
        Boolean isLastBlock = false;
        for(int i = 0; i < 10; i++){
            int lengthOfCurrentBlock = clueOfRow.get(blockIndex);
            Cell currentCell = cellArray[i];
            if(currentCell.cellColor == Color.BLACK && !firstBlackCellOccuredInCurrentBlock){
                firstBlackCellOccuredInCurrentBlock = true;
                if(blockIndex == clueOfRow.size() - 1) isLastBlock = true;
                //checkBlock and empty space if it is not last block.
                for(int j = 1 ; j < lengthOfCurrentBlock ; j++){
                    i++;
                    currentCell = cellArray[i];
                    if(currentCell.cellColor == Color.WHITE) return false;
                    else  continue;
                }
                if(!isLastBlock){
                    i = i+1;
                    currentCell = cellArray[i];
                    if(currentCell.cellColor == Color.BLACK) return false;
                }
                blockIndex++;
            }
        }

        return true;
    }
}
