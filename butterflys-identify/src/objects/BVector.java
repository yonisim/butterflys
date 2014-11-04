package objects;


public class BVector{
	
	public int vector_id;
	public int butterfly_id;
	public String linkToPicture;
	public int vector_max;
	public int r_mean;
	public int g_mean;
	public int b_mean;
        public float h_mean;
        public float s_mean;
        public float v_mean;
        public float h_max;
        public float s_max;
        public float v_max;
	
	
	public BVector(){
		
	}

	public BVector(int butterfly_id , String linkToPicture ,  int vector_max , int r_mean , int g_mean , int b_mean,
                float h_mean, float s_mean, float v_mean, float h_max, float s_max, float v_max)
        {
		this.butterfly_id = butterfly_id;
		this.linkToPicture = linkToPicture;
		this.vector_max = vector_max;
		this.r_mean = r_mean;
		this.g_mean = g_mean;
		this.b_mean = b_mean;
                this.h_mean = h_mean;
                this.s_mean = s_mean;
                this.v_mean = v_mean;
                this.h_max = h_max;
                this.s_max = s_max;
                this.v_max = v_max;
	}
	
	public BVector(int vector_id , String linkToPicture , int butterfly_id ,  int vector_max , int r_mean , int g_mean , int b_mean,
                float h_mean, float s_mean, float v_mean, float h_max, float s_max, float v_max)
        {
		this.vector_id = vector_id;
		this.butterfly_id = butterfly_id;
		this.linkToPicture = linkToPicture;
		this.vector_max = vector_max;
		this.r_mean = r_mean;
		this.g_mean = g_mean;
		this.b_mean = b_mean;
                this.h_mean = h_mean;
                this.s_mean = s_mean;
                this.v_mean = v_mean;
                this.h_max = h_max;
                this.s_max = s_max;
                this.v_max = v_max;
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
                string += " , vector h_mean: " + h_mean;
                string += " , vector s_mean: " + s_mean;
                string += " , vector v_mean: " + v_mean;
                string += " , vector h_max: " + h_max;
                string += " , vector s_max: " + s_max;
                string += " , vector v_max: " + v_max;
		return string;
	}

	public int getVector_id() {
		return vector_id;
	}

	public void setVector_id(int vector_id) {
		this.vector_id = vector_id;
	}

	public int getButterfly_id() {
		return butterfly_id;
	}

	public void setButterfly_id(int butterfly_id) {
		this.butterfly_id = butterfly_id;
	}

	public String getLinkToPicture() {
		return linkToPicture;
	}

	public void setLinkToPicture(String linkToPicture) {
		this.linkToPicture = linkToPicture;
	}

	public int getVector_max() {
		return vector_max;
	}

	public void setVector_max(int vector_max) {
		this.vector_max = vector_max;
	}

	public int getR_mean() {
		return r_mean;
	}

	public void setR_mean(int r_mean) {
		this.r_mean = r_mean;
	}

	public int getG_mean() {
		return g_mean;
	}

	public void setG_mean(int g_mean) {
		this.g_mean = g_mean;
	}

	public int getB_mean() {
		return b_mean;
	}

	public void setB_mean(int b_mean) {
		this.b_mean = b_mean;
	}

        public void setH_mean(float h_mean){
            this.h_mean = h_mean;
        }
        
        public float getH_mean()
        {
            return h_mean;
        }
        
        public void setS_mean(float s_mean){
            this.s_mean = s_mean;
        }
        
        public float getS_mean()
        {
            return s_mean;
        }
        
        public void setV_mean(float v_mean){
            this.v_mean = v_mean;
        }
        
        public float getV_mean()
        {
            return v_mean;
        }
	
        public void setH_max(float h_max){
            this.h_max = h_max;
        }
        
        public float getH_max()
        {
            return h_max;
        }
        
        public void setS_max(float s_max){
            this.s_mean = s_max;
        }
        
        public float getS_max()
        {
            return s_max;
        }
        
        public void setV_max(float v_max){
            this.v_mean = v_max;
        }
        
        public float getV_max()
        {
            return v_max;
        }
	
}