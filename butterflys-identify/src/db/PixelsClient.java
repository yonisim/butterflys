package db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import objects.Pixel;


public class PixelsClient extends MysqlClient {

	private String TABLE_NAME = "pixels";
	private String colVectorId = "vector_id";
	private String colPixelNum = "pixel_num";
	private String colH = "H";
	private String colS = "S";
	private String colV = "V";
	
	
	public int insertPixel(Pixel pixel) 
			throws ClassNotFoundException, SQLException, IOException{
		String setClause = colVectorId + " = '" + pixel.vector_id + "' , " +
							colPixelNum + " = '" + pixel.pixelNum + "' , " +
							colH + " = '" + pixel.H + "' , " + 
							colS + " = '" + pixel.S + "' , " + 
							colV + " = '" + pixel.V + "'";
		return insert(TABLE_NAME , setClause);
	}
	
	public List<Pixel> selectPixels(String whereClause) 
			throws ClassNotFoundException, SQLException, IOException{
		List<Pixel> pixels = new LinkedList<>();
		select(TABLE_NAME , whereClause);
		while(resultSet.next()){
			Pixel pixel = new Pixel();
			pixel.vector_id = resultSet.getInt(1);
			pixel.pixelNum = resultSet.getInt(2);
			pixel.H = resultSet.getInt(4);
			pixel.S = resultSet.getInt(5);
			pixel.V = resultSet.getInt(6);
			pixels.add(pixel);
		}
		return pixels;
		
	}
	
    public Vector<Integer> getVectorFromDb(int vectorId) throws ClassNotFoundException, SQLException, IOException{
    	Vector<Integer> vector = new Vector<>();
    	List<Pixel> pixels = selectPixels(colVectorId + " = " + vectorId);
    	for(Pixel pixel : pixels){
    		vector.add(pixel.H);
    		vector.add(pixel.S);
    		vector.add(pixel.V);
    	}
    	return vector;
    }
    
    public void insertVectorToPixelsTable(Vector<Integer> vector , int vector_id) throws ClassNotFoundException, SQLException, IOException{
    	for(int i = 0 ; i < vector.size() -3; i += 3){
    		String setClause = colVectorId + " = '" + vector_id + "' , " +
					colPixelNum + " = " + i/3 + " , " +
					colH + " = " + vector.get(i) + " , " + 
					colS + " = " + vector.get(i+1) + " , " +
					colV + " = " + vector.get(i+2);
    		insert(TABLE_NAME , setClause);
    	}
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

	public String getColPixelNum() {
		return colPixelNum;
	}

	public void setColPixelNum(String colPixelNum) {
		this.colPixelNum = colPixelNum;
	}

	public String getColH() {
		return colH;
	}

	public void setColH(String colH) {
		this.colH = colH;
	}

	public String getColS() {
		return colS;
	}

	public void setColS(String colS) {
		this.colS = colS;
	}

	public String getColV() {
		return colV;
	}

	public void setColV(String colV) {
		this.colV = colV;
	}

	
	
}
