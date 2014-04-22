package objects;


public class BVector{
	
	public int vector_id;
	public int butterfly_id;
	public int vector_sum;
	public int vector_average;
	
	
	public BVector(){
		
	}
	
	public BVector(int vector_id , int butterfly_id , int vector_sum , int vector_average){
		this.vector_id = vector_id;
		this.butterfly_id = butterfly_id;
		this.vector_sum = vector_sum;
		this.vector_average = vector_average;
	}
	
	@Override
	public String toString() {
		String string = "vector id: " + vector_id;
		string += " , butterfly id: " + butterfly_id;
		string += " , vector sum: " + vector_sum;
		string += " , vector average: " + vector_average;
		return string;
	}

	
}