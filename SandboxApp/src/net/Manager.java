package net;



import java.io.IOException;
import java.net.Socket;

import android.graphics.Bitmap;
import android.os.StrictMode;
import android.util.Log;

/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
public class Manager {

	private Socket socket;
	private ConnectionHelper connectionHelper;
	private boolean isConnectionInitiated = false;
	

	public Manager() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		initConnection();
	}

	private void initConnection(){
		if(isConnectionInitiated)
			return;
		try {
			//"localhost";//
			//ec2-54-148-5-231.us-west-2.compute.amazonaws.com
			String ipAddress = "10.0.2.2";
			int port = 7776;
			Log.i(getClass().getName() , "Trying to connect to: " + ipAddress + ":" + port);
			socket = new Socket(ipAddress, port);
			Log.i(getClass().getName() ,"Connection initiated");
			isConnectionInitiated = true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(socket != null){
			connectionHelper = new ConnectionHelper(socket);
			
		}
	}

	public boolean AddToDB(Bitmap image, String name, String description){
		initConnection();
		// send add to db request to server with image
		
		ImageSerializable IS = ImageTransformer.bitMapToIS(image);
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


	public Bitmap search(Bitmap image){
		initConnection();
		ImageSerializable IS = ImageTransformer.bitMapToIS(image);
		IS.messageType = MessageType.SEARCH;
		connectionHelper.sendMessage(IS);
		Object o = connectionHelper.readMessage();
		Log.i(this.getClass().getName() , "Finished reading message");
		if(o instanceof ImageSerializable){
			Log.i(this.getClass().getName() , "Transforming message to bit map");
			return ImageTransformer.ISToBitMap((ImageSerializable)o);
		}
		return null;
	}




}
