/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
public class AlgoWeightsClient extends MysqlClient {

    private String TABLE_NAME = "algorithm_weights";
    private String colAlgorithmId = "algo_id";
    private String colAlgorithmWeight = "algorithm_weight";

    public int insertAlgoWeight(int algoID, float algoWeight) throws ClassNotFoundException, SQLException, IOException {
        String setClause = colAlgorithmId + " = '" + algoID + "' , "
                + colAlgorithmWeight + " = '" + algoWeight + "'";
        return insert(TABLE_NAME, setClause);
    }

    public List<Float> selectAlgoWeightsSorted(String clause)
        throws ClassNotFoundException, SQLException, IOException {
        List<Float> weights = new LinkedList<>();
        clause = clause + " ORDER BY " + colAlgorithmId;
        selectNoWhere(TABLE_NAME, clause);
        while (resultSet.next()) {
            weights.add(resultSet.getFloat(2));
        }
        return weights;
    }
    
    public void CleanAlgoWeightsTable() throws ClassNotFoundException, SQLException, IOException{
        Delete(TABLE_NAME);
    }
}
