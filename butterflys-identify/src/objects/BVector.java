package objects;


public class BVector{
	
	public int vector_id;
	public int butterfly_id;
	public String linkToPicture;
	public float vector_sum;
	public float vector_average;
	
	
	public BVector(){
		
	}

	public BVector(int butterfly_id , String linkToPicture ,  float vector_sum , float vector_average){
		this.butterfly_id = butterfly_id;
		this.linkToPicture = linkToPicture;
		this.vector_sum = vector_sum;
		this.vector_average = vector_average;
	}
	
	public BVector(int vector_id , String linkToPicture , int butterfly_id , float vector_sum , float vector_average){
		this.vector_id = vector_id;
		this.butterfly_id = butterfly_id;
		this.linkToPicture = linkToPicture;
		this.vector_sum = vector_sum;
		this.vector_average = vector_average;
	}
	
	@Override
	public String toString() {
		String string = "vector id: " + vector_id;
		string += " , butterfly id: " + butterfly_id;
		string += " . link to picture: " + linkToPicture;
		string += " , vector sum: " + vector_sum;
		string += " , vector average: " + vector_average;
		return string;
	}

	
}