package objects;


public class BVector{
	
	public int vector_id;
	public int butterfly_id;
	public String linkToPicture;
	public int vector_max;
	public int r_mean;
	public int g_mean;
	public int b_mean;
	
	
	public BVector(){
		
	}

	public BVector(int butterfly_id , String linkToPicture ,  int vector_max , int r_mean , int g_mean , int b_mean){
		this.butterfly_id = butterfly_id;
		this.linkToPicture = linkToPicture;
		this.vector_max = vector_max;
		this.r_mean = r_mean;
		this.g_mean = g_mean;
		this.b_mean = b_mean;
	}
	
	public BVector(int vector_id , String linkToPicture , int butterfly_id ,  int vector_max , int r_mean , int g_mean , int b_mean){
		this.vector_id = vector_id;
		this.butterfly_id = butterfly_id;
		this.linkToPicture = linkToPicture;
		this.vector_max = vector_max;
		this.r_mean = r_mean;
		this.g_mean = g_mean;
		this.b_mean = b_mean;
	}
	
	@Override
	public String toString() {
		String string = "vector id: " + vector_id;
		string += " , butterfly id: " + butterfly_id;
		string += " . link to picture: " + linkToPicture;
		string += " , vector max: " + vector_max;
		string += " , vector r_mean: " + r_mean;
		string += " , vector g_mean: " + g_mean;
		string += " , vector b_mean: " + b_mean;
		return string;
	}

	
}