package objects;


public class Pixel{
	
	public int vector_id;
	public int pixelNum;
	public int H;
	public int S;
	public int V;
	
	
	public Pixel(){
		
	}
	
	public Pixel(int vector_id , int row , int h , int s , int v){
		this.vector_id = vector_id;
		this.pixelNum = row;
		this.H = h;
		this.S = s;
		this.V = v;
	}
	
	@Override
	public String toString() {
		String string = "vector id: " + vector_id;
		string += " , row: " + pixelNum;
		string += " , H: " + H;
		string += " , S: " + S;
		string += " , V: " + V;
		return string;
	}

	
}