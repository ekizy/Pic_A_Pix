import aima.core.search.csp.Assignment;
import aima.core.search.csp.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class PicPix {

    public static int ROW_SIZE = 10 ;
    public static int COLUMN_SIZE = 10 ;
    public boolean isBackTracking = false ;

    public static Integer [][] COLUMN_CLUES_ARRAY =
            {{4}, {2,1,3},{3,1,1}, {3,1,1,1}, {3,1,1,1},{3,2},{6},{5,4},{7},{4}};

    public static Integer [][] ROW_CLUES_ARRAY = {
            {6},
            {8},
            {9},
            {1,4},
            {2,2,4},
            {1,1,2},
            {1,1,3},
            {3,2},
            {1,1,1},
            {4,1}
    };

    public void playPickPix(){

        List <ArrayList<ArrayList<Cell>>>maps = new ArrayList<ArrayList<ArrayList<Cell>>>();
        List<List<Integer>> columnClues = new ArrayList<List<Integer>>();
        List<List<Integer>> rowClues = new ArrayList<List<Integer>>();

        initializeColumnClues(columnClues);
        initializeRowClues(rowClues);
        
        ArrayList<ArrayList<Cell>> initialMatrix = new ArrayList<ArrayList<Cell>>();

        initializeMatrix(initialMatrix);

        PickPixCSP pickPixCSP = new PickPixCSP(columnClues,rowClues);
        Assignment assignment = new Assignment();

        List <Variable> variables = pickPixCSP.getVariables();

        int variableCounter = 0;
        for(int i = 0; i<initialMatrix.size();i++){
            ArrayList<Cell> currentRow = initialMatrix.get(i);
            for(int j = 0 ; j<currentRow.size(); j++){
                assignment.setAssignment(variables.get(variableCounter),currentRow.get(j));
                variableCounter++;
            }

        }



        //maps.add(initialMap);

        int a = 3;

        int b = 3;
    }

    private void initializeMatrix(ArrayList<ArrayList<Cell>> matrix) {

        for(int i =0 ; i<10;i++){
            ArrayList<Cell> currentRow = new ArrayList<Cell>();
            for(int j =0 ; j<10;j++){
                Cell cell = new Cell(i,j,Color.NOCOLOR);
                currentRow.add(cell);
            }
            matrix.add(currentRow);
        }
    }

    private void initializeColumnClues( List<List<Integer>> columnClues) {
        for(int i = 0; i< COLUMN_CLUES_ARRAY.length; i++){
            List<Integer> currentColumnClue = Arrays.asList(COLUMN_CLUES_ARRAY[i]);
            columnClues.add(currentColumnClue);
        }
    }

    private void initializeRowClues( List<List<Integer>> rowClues) {
        for(int i = 0; i< ROW_CLUES_ARRAY.length; i++){
            List<Integer> currentRowClue = Arrays.asList(ROW_CLUES_ARRAY[i]);
            rowClues.add(currentRowClue);
        }
    }

    public Color getColorValueFromMatrix(int rowIndex, int columnIndex, List<List<Cell>> matrix){

        List<Cell> row;
        Cell currentCell;

        if(matrix.size() >= rowIndex) row  = matrix.get(rowIndex);
        else return null;

        if(row.size()>= columnIndex) currentCell = row.get(columnIndex);
        else return null;

        return currentCell.cellColor;

    }

    public Variable findVariableWithMinimumRemainingValue(PickPixCSP pickPixCSP){
        int minValue = Integer.MAX_VALUE;

        Variable found = null;
        List<Variable> variables = pickPixCSP.getVariables();
        for(Variable var: variables){
            int domainSize = pickPixCSP.getDomain(var).size();
            if(domainSize < minValue && domainSize > 0 && ((Cell) pickPixCSP.getDomain(var).get(0)).cellColor != Color.NOCOLOR){
                minValue = pickPixCSP.getDomain(var).size();
                found = var;
            }
            if(pickPixCSP.getDomain(var).size() == 0){
                isBackTracking = true;
            }

        }
        return found;
    }

}
