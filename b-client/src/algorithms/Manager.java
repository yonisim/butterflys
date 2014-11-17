package algorithms;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;





/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
public class Manager {

	Socket socket;
	ConnectionHelper connectionHelper;

	public Manager() throws IOException {
		initConnection();
	}

	private void initConnection(){
		try {
			//"localhost";//
			String ipAddress = "ec2-54-148-5-231.us-west-2.compute.amazonaws.com";
			int port = 7776;
			socket = new Socket(ipAddress, port);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(socket != null){
			connectionHelper = new ConnectionHelper(socket);
			
		}
	}

	public boolean AddToDB(BufferedImage image, String name, String description){
		initConnection();
		// send add to db request to server with image
		
		ImageSerializable IS = ImageTransformer.bufferedImageToIS(image);
		IS.messageType = MessageType.ADD_TO_DB;
		IS.butterflyName = name;
		IS.butterflyDescription = description;
		
		connectionHelper.sendMessage(IS);
		Object o = connectionHelper.readMessage();
		if(o instanceof OkMessage){
			return true;
		}
		return false;
		
	}


	public ImageSerializable search(BufferedImage image){
		initConnection();
		ImageSerializable IS = ImageTransformer.bufferedImageToIS(image);
		IS.messageType = MessageType.SEARCH;
		connectionHelper.sendMessage(IS);
		Object o = connectionHelper.readMessage();
		if(o instanceof ImageSerializable){
			return (ImageSerializable)o;
		}
		return new ImageSerializable();
	}




}
