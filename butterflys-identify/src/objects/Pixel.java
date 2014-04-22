package objects;


public class Pixel{
	
	public int vector_id;
	public int row;
	public int col;
	public int red;
	public int green;
	public int blue;
	
	
	public Pixel(){
		
	}
	
	public Pixel(int vector_id , int row , int col , int red , int green , int blue){
		this.vector_id = vector_id;
		this.row = row;
		this.col = col;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	@Override
	public String toString() {
		String string = "vector id: " + vector_id;
		string += " , row: " + row;
		string += " , col: " + col;
		string += " , red: " + red;
		string += " , green: " + green;
		string += " , blue: " + blue;
		return string;
	}

	
}