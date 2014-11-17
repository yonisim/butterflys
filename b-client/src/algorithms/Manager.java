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
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Properties properties;

	public Manager() throws IOException {
		//properties = new Properties();
		//FileInputStream inputStream = new FileInputStream("src/resources/server.properties");
		//properties.load(inputStream);
		initConnection();
	}

	private void initConnection(){
		try {
			String ipAddress = "localhost";//"ec2-54-148-5-231.us-west-2.compute.amazonaws.com";//properties.getProperty("server.ip");
			int port = 7776;//Integer.parseInt(properties.getProperty("server.port"));
			socket = new Socket(ipAddress, port);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(socket != null){
			try {
				System.out.println("creating new player instance");
				setOos(new ObjectOutputStream(socket.getOutputStream()));
				oos.flush();

				setOis(new ObjectInputStream(socket.getInputStream()));

				System.out.println("Player instance created");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean AddToDB(BufferedImage image, String name, String description){
		initConnection();
		// TODO - send add to db request to server with image
		
		ImageSerializable IS = ImageTransformer.bufferedImageToIS(image);
		IS.messageType = MessageType.ADD_TO_DB;
		IS.butterflyName = name;
		IS.butterflyDescription = description;
		
		sendMessage(IS);
		Object o = readMessage();
		if(o instanceof OkMessage){
			return true;
		}
		return false;
		
	}


	public ImageSerializable search(BufferedImage image){
		initConnection();
		ImageSerializable IS = ImageTransformer.bufferedImageToIS(image);
		IS.messageType = MessageType.SEARCH;
		sendMessage(IS);
		Object o = readMessage();
		if(o instanceof ImageSerializable){
			return (ImageSerializable)o;
		}
		return new ImageSerializable();
	}


	public Object readMessage(){
		try {
			System.out.println("Reading message");
			return ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void sendMessage(Object o){
		try {
			System.out.println("Sending message");
			oos.writeObject(o);
			System.out.println("Message sent");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void sendOkMessage(){
		System.out.println("Sending OK message");
		//sendMessage(OK_MESSAGE);

	}


	public void close(){
		try {
			ois.close();
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void flush(){
		try {
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ObjectInputStream getOis() {
		return ois;
	}
	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}
	public ObjectOutputStream getOos() {
		return oos;
	}
	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

}
