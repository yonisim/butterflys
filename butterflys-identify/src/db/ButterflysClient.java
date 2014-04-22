package db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import objects.Butterfly;

public class ButterflysClient extends MysqlClient {

	private String TABLE_NAME = "butterflys_general";
	private String colName = "butterfly_name";
	private String colLinkToPicture = "link_to_picture";
	private String colDescription = "description";
	
	
	public boolean insertButterfly(Butterfly butterfly) 
			throws ClassNotFoundException, SQLException, IOException{
		String setClause = colName + " = '" + butterfly.name + "' , " +
							colLinkToPicture + " = '" + butterfly.linkToPicture + "' , " +
							colDescription + " = '" + butterfly.description + "'";
		return insert(TABLE_NAME , setClause);
	}
	
	public List<Butterfly> selectButterfly(String whereClause) 
			throws ClassNotFoundException, SQLException, IOException{
		List<Butterfly> butterflys = new LinkedList<>();
		select(TABLE_NAME , whereClause);
		while(resultSet.next()){
			Butterfly butterfly = new Butterfly();
			butterfly.id = resultSet.getInt(1);
			butterfly.name = resultSet.getString(2);
			butterfly.linkToPicture = resultSet.getString(3);
			butterfly.description = resultSet.getString(4);
			butterflys.add(butterfly);
		}
		return butterflys;
		
	}
	
}
