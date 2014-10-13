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
	private String colLinkToPicture = "link_to_picture";
	private String colVectorMax = "vector_max";
	private String colR_mean = "r_mean";
	private String colG_mean = "g_mean";
	private String colB_mean = "b_mean";
	
	
	public int insertVector(BVector bvector) 
			throws ClassNotFoundException, SQLException, IOException{
		String setClause = colVectorId + " = " + bvector.vector_id + " , " +
							colbutterflyId + " = " + bvector.butterfly_id + " , " +
							colLinkToPicture + " = '" + bvector.linkToPicture + "' , " +
							colVectorMax + " = " + bvector.vector_max + " , " + 
							colR_mean + " = " + bvector.r_mean + " , " +
							colG_mean + " = " + bvector.g_mean + " , " +
							colB_mean + " = " + bvector.b_mean;
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
			bvector.linkToPicture = resultSet.getString(3);
            bvector.vector_max = resultSet.getInt(4);
			bvector.r_mean = resultSet.getInt(5);
			bvector.g_mean = resultSet.getInt(6);
			bvector.b_mean = resultSet.getInt(7);
			vectors.add(bvector);
		}
		return vectors;
		
	}

	public String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}

	public String getColVectorId() {
		return colVectorId;
	}

	public void setColVectorId(String colVectorId) {
		this.colVectorId = colVectorId;
	}

	public String getColbutterflyId() {
		return colbutterflyId;
	}

	public void setColbutterflyId(String colbutterflyId) {
		this.colbutterflyId = colbutterflyId;
	}

	public String getColLinkToPicture() {
		return colLinkToPicture;
	}

	public void setColLinkToPicture(String colLinkToPicture) {
		this.colLinkToPicture = colLinkToPicture;
	}

	public String getColVectorMax() {
		return colVectorMax;
	}

	public void setColVectorMax(String colVectorMax) {
		this.colVectorMax = colVectorMax;
	}

	public String getColR_mean() {
		return colR_mean;
	}

	public void setColR_mean(String colR_mean) {
		this.colR_mean = colR_mean;
	}

	public String getColG_mean() {
		return colG_mean;
	}

	public void setColG_mean(String colG_mean) {
		this.colG_mean = colG_mean;
	}

	public String getColB_mean() {
		return colB_mean;
	}

	public void setColB_mean(String colB_mean) {
		this.colB_mean = colB_mean;
	}
	
	
}
