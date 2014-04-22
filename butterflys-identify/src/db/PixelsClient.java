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
	private String colRow = "row";
	private String colCol = "col";
	private String colRed = "red";
	private String colGreen = "green";
	private String colBlue = "blue";
	
	
	public boolean insertPixel(Pixel pixel) 
			throws ClassNotFoundException, SQLException, IOException{
		String setClause = colVectorId + " = '" + pixel.vector_id + "' , " +
							colRow + " = '" + pixel.row + "' , " +
							colCol + " = '" + pixel.col + "' , " + 
							colRed + " = '" + pixel.red + "' , " + 
							colGreen + " = '" + pixel.green + "' , " + 
							colBlue + " = '" + pixel.blue + "'";
		return insert(TABLE_NAME , setClause);
	}
	
	public List<Pixel> selectPixels(String whereClause) 
			throws ClassNotFoundException, SQLException, IOException{
		List<Pixel> pixels = new LinkedList<>();
		select(TABLE_NAME , whereClause);
		while(resultSet.next()){
			Pixel pixel = new Pixel();
			pixel.vector_id = resultSet.getInt(1);
			pixel.row = resultSet.getInt(2);
			pixel.col = resultSet.getInt(3);
			pixel.red = resultSet.getInt(4);
			pixel.green = resultSet.getInt(5);
			pixel.blue = resultSet.getInt(6);
			pixels.add(pixel);
		}
		return pixels;
		
	}
	
    public Vector<Integer> getVectorFromDb(int vectorId) throws ClassNotFoundException, SQLException, IOException{
    	Vector<Integer> vector = new Vector<>();
    	List<Pixel> pixels = selectPixels(colVectorId + " = " + vectorId);
    	for(Pixel pixel : pixels){
    		vector.add(pixel.red);
    		vector.add(pixel.green);
    		vector.add(pixel.blue);
    	}
    	return vector;
    }
    
    public void insertVector(Vector<Integer> vector){
    	for(int i = 0 ; i < vector.size() ; i += 3){
    		//to be implemented
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

	public String getColRow() {
		return colRow;
	}

	public void setColRow(String colRow) {
		this.colRow = colRow;
	}

	public String getColCol() {
		return colCol;
	}

	public void setColCol(String colCol) {
		this.colCol = colCol;
	}

	public String getColRed() {
		return colRed;
	}

	public void setColRed(String colRed) {
		this.colRed = colRed;
	}

	public String getColGreen() {
		return colGreen;
	}

	public void setColGreen(String colGreen) {
		this.colGreen = colGreen;
	}

	public String getColBlue() {
		return colBlue;
	}

	public void setColBlue(String colBlue) {
		this.colBlue = colBlue;
	}
	
	
}
