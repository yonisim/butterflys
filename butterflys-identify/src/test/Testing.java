package test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import objects.Butterfly;

import db.ButterflysClient;
import db.MysqlClient;

public class Testing {

	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		ButterflysClient client = new ButterflysClient();
		
		List<Butterfly> list = client.selectButterfly("");
		System.out.println(list.size());
		for(Butterfly b : list){
			System.out.println(b.toString());
		}
		
		Butterfly butterfly = new Butterfly("pink7", "pink7" , "this butterfly is very very pink");
		while(client.insertButterfly(butterfly)){
			butterfly.name += 1;
			butterfly.linkToPicture += 1;
		}
		
		list = client.selectButterfly("");
		System.out.println(list.size());
		for(Butterfly b : list){
			System.out.println(b.toString());
		}
	}
	
}
