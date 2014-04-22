package db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import objects.BVector;

public class VectorsClient extends MysqlClient {

	private String TABLE_NAME = "vectors";
	private String colVectorId = "vector_id";
	private String colbutterflyId = "butterfly_id";
	private String colVectorSum = "vector_sum";
	private String colVectorAverage = "vector_average";
	
	
	public boolean insertVector(BVector bvector) 
			throws ClassNotFoundException, SQLException, IOException{
		String setClause = colVectorId + " = '" + bvector.vector_id + "' , " +
							colbutterflyId + " = '" + bvector.butterfly_id + "' , " +
							colVectorSum + " = '" + bvector.vector_sum + "' , " + 
							colVectorAverage + " = '" + bvector.vector_average + "'";
		return insert(TABLE_NAME , setClause);
	}
	
	public List<BVector> selectVectors(String whereClause) 
			throws ClassNotFoundException, SQLException, IOException{
		List<BVector> vectors = new LinkedList<>();
		select(TABLE_NAME , whereClause);
		while(resultSet.next()){
			BVector bvector = new BVector();
			bvector.vector_id = resultSet.getInt(1);
			bvector.butterfly_id = resultSet.getInt(2);
			bvector.vector_sum = resultSet.getInt(3);
			bvector.vector_average = resultSet.getInt(4);
			vectors.add(bvector);
		}
		return vectors;
		
	}
	
}
