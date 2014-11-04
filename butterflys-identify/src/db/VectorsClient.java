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
        private String colH_mean = "h_mean";
	private String colS_mean = "s_mean";
	private String colV_mean = "v_mean";
        private String colH_max = "h_max";
	private String colS_max = "s_max";
	private String colV_max = "v_max";
	
	
	public int insertVector(BVector bvector) 
			throws ClassNotFoundException, SQLException, IOException{
		String setClause = colVectorId + " = " + bvector.vector_id + " , " +
							colbutterflyId + " = " + bvector.butterfly_id + " , " +
							colLinkToPicture + " = '" + bvector.linkToPicture + "' , " +
							colVectorMax + " = " + bvector.vector_max + " , " + 
							colR_mean + " = " + bvector.r_mean + " , " +
							colG_mean + " = " + bvector.g_mean + " , " +
							colB_mean + " = " + bvector.b_mean + " , " +
                                                        colH_mean + " = " + bvector.h_mean + " , " +
							colS_mean + " = " + bvector.s_mean + " , " +
							colV_mean + " = " + bvector.v_mean + " , " +
                                                        colH_max + " = " + bvector.h_max + " , " +
							colS_max + " = " + bvector.s_max + " , " +
                                                        colV_max + " = " + bvector.v_max;
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
                        bvector.h_mean = resultSet.getFloat(8);
                        bvector.s_mean = resultSet.getFloat(9);
                        bvector.v_mean = resultSet.getFloat(10);
                        bvector.h_max = resultSet.getFloat(11);
                        bvector.s_max = resultSet.getFloat(12);
                        bvector.v_max = resultSet.getFloat(13);
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
	
	public String getColH_mean(){
            return this.colH_mean;
        }
        
        public void setColH_mean(String colH_mean){
            this.colH_mean = colH_mean;
        }
        
        public String getColS_mean(){
            return this.colS_mean;
        }
        
        public void setColS_mean(String colS_mean){
            this.colS_mean = colS_mean;
        }
        
        public String getcolV_mean(){
            return this.colV_mean;
        }
        
        public void setcolV_mean(String colV_mean){
            this.colV_mean = colV_mean;
        }
        
        public String getcolH_max(){
            return this.colH_max;
        }
        
        public void setcolH_max(String colH_max){
            this.colH_max = colH_max;
        }
        
        public String getcolS_max(){
            return this.colS_max;
        }
        
        public void setcolS_max(String colS_max){
            this.colS_max = colS_max;
        }
        
        public String getcolV_max(){
            return this.colV_max;
        }
        
        public void setcolV_max(String colV_max){
            this.colV_max = colV_max;
        }
}
