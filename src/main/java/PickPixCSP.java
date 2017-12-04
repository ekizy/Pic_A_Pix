import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;

import java.util.ArrayList;
import java.util.List;

public class PickPixCSP extends CSP {

    public ArrayList<Variable> variableArrayList;
    public List<List<Integer>> columnClues;
    public List<List<Integer>> rowClues;
    public PickPixCSP(List<List<Integer>> columnClues,List<List<Integer>> rowClues){
        variableArrayList = new ArrayList<Variable>();
        this.columnClues = columnClues;
        this.rowClues = rowClues;

        for(int i = 0 ; i < 10 ; i++){
            for(int j = 0 ; j < 10 ; j++){
                String rowNumber = Integer.toString(i+1);
                String columnNumber = Integer.toString(j+1);
                String name = "Cell Row:" + rowNumber + ",Column:" + columnNumber;
                Variable var = new Variable(name);
                variableArrayList.add(var);
            }
        }

        Domain colors = new Domain(new Object[] {Color.BLACK,Color.WHITE});

        for(Variable var : variableArrayList) addVariable(var);

        for(Variable var : getVariables()) setDomain(var,colors);

        initializeRowConstraints();

        initializeColumnConstraints();


    }

    private void initializeRowConstraints() {

        //Export each row from variable list. Each row consists of 10 elements so for every element.

        int counter = 0;
        int rowIndex = 0;
        ArrayList<Variable> vars = new ArrayList<Variable>();
        for (Variable var : variableArrayList){
            vars.add(var);
            counter++;
            if (counter % 10 == 0){ // got 10 elements. Save current row, then record the next row.
                List<Integer> rowClue = rowClues.get(rowIndex);
                addConstraint(new RowConstraint(vars,rowClue));
                vars.clear();
                rowIndex++;
            }
        }
    }

    private void initializeColumnConstraints(){

        //Export each column from variable list. Try to make lists with every row's first, second,third and ... tenth element together.

        int columnSize = 10;
        int rowSize = 10;
        ArrayList<Variable> vars = new ArrayList<Variable>();
        for(int columnIndex = 0; columnIndex< 10 ; columnIndex++){
            for (int rowIndex = 0; rowIndex <columnSize ;rowIndex++){
                Variable var = variableArrayList.get(columnIndex + rowIndex * rowSize);
                vars.add(var);
            }
            List<Integer> columnClue = columnClues.get(columnIndex);
            addConstraint(new ColumnConstraint(vars,columnClue));
            vars.clear();
        }
    }
}
