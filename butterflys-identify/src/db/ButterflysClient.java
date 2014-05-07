package db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import objects.Butterfly;

public class ButterflysClient extends MysqlClient {

	private String TABLE_NAME = "butterflys_general";
	private String colId = "id";
	private String colName = "butterfly_name";
	private String colLinkToPicture = "link_to_picture";
	private String colDescription = "description";
	
	
	public int insertButterfly(Butterfly butterfly) 
			throws ClassNotFoundException, SQLException, IOException{
		String setClause = colName + " = '" + butterfly.name + "' , " +
							colLinkToPicture + " = '" + butterfly.linkToPicture + "' , " +
							colDescription + " = '" + butterfly.description + "'";
		return insert(TABLE_NAME , setClause);
	}
	
	public Butterfly selectSingleButterfly(String whereClause) throws ClassNotFoundException, SQLException, IOException{
		List<Butterfly> list = selectButterfly(whereClause);
		if(list.size() == 1)
			return list.get(0);
		else{
			System.out.println("ERROR: more than one butterfly in result set");
			return null;
		}
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

	public String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}
	
	public String getColId() {
		return colId;
	}

	public void setColId(String colId) {
		this.colId = colId;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColLinkToPicture() {
		return colLinkToPicture;
	}

	public void setColLinkToPicture(String colLinkToPicture) {
		this.colLinkToPicture = colLinkToPicture;
	}

	public String getColDescription() {
		return colDescription;
	}

	public void setColDescription(String colDescription) {
		this.colDescription = colDescription;
	}
	
	
}
