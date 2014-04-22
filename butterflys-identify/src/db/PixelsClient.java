package db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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
	
}
