package objects;


public class Butterfly{
	
	public int id;
	public String name;
	public String linkToPicture;
	public String description;
	
	public Butterfly(){
		
	}
	
	public Butterfly(String name , String linkToPicture, String description){
		this.name = name;
		this.linkToPicture = linkToPicture;
		this.description = description;
	}
	
	@Override
	public String toString() {
		String string = "id: " + id;
		string += " , name: " + name;
		string += " , link to image: " + linkToPicture;
		string += " , description: " + description;
		return string;
	}

	
}