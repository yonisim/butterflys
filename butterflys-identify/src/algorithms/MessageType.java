package algorithms;

public enum MessageType {

	OK ("200"),
	SEARCH (""),
	ADD_TO_DB (""),
	SANITY ("");
	
	private String value;
	
	MessageType(String value) {
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
	
	
	
	
}
