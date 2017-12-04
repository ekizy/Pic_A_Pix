import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

import java.util.ArrayList;
import java.util.List;

public class ColumnConstraint implements Constraint {

    public List<Variable> columnScope;
    public List<Integer> clueOfColumn;

    public ColumnConstraint(ArrayList<Variable> variableArrayList,List<Integer> columnClue){

        columnScope = new ArrayList<Variable>();
        clueOfColumn = new ArrayList<Integer>();
        addElementsToColumnScope(variableArrayList);
        addElementsToColumnClue(columnClue);

    }

    private void addElementsToColumnClue(List<Integer> columnClue) {
        for(Integer integer: columnClue){
            clueOfColumn.add(integer);
        }
    }

    private void addElementsToColumnScope(ArrayList<Variable> variableArrayList) {
        for(Variable var : variableArrayList){
            columnScope.add(var);
        }
    }

    public List<Variable> getScope() {
        return columnScope;
    }


    public boolean isSatisfiedWith(Assignment assignment) {

        Cell [] cellArray = new Cell [10];
        int index = 0;

        for(Variable var : columnScope) {
            if(assignment.getAssignment(var) == null ) break;
            cellArray[index] = (Cell) assignment.getAssignment(var);
            index++;
        }

        int blockIndex = 0;
        Boolean firstBlackCellOccuredInCurrentBlock = false;
        Boolean isLastBlock = false;
        for(int i = 0; i < 10; i++){
            int lengthOfCurrentBlock = clueOfColumn.get(blockIndex);
            Cell currentCell = cellArray[i];
            if(currentCell.cellColor == Color.BLACK && !firstBlackCellOccuredInCurrentBlock){
                firstBlackCellOccuredInCurrentBlock = true;
                if(blockIndex == clueOfColumn.size() - 1) isLastBlock = true;
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
