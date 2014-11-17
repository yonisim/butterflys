package algorithms;

import java.io.Serializable;

public class ImageSerializable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int width; int height; int[] pixels;
	
	public MessageType messageType;
	public String butterflyName;
	public String butterflyDescription;

}