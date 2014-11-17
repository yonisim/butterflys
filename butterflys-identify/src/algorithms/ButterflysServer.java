package algorithms;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import objects.Butterfly;



public class ButterflysServer  extends Thread{


	private static final int port = 7776;
	private ConnectionHelper connectionHelper;
	private Manager manager;

	public ButterflysServer(ConnectionHelper player) {
		this.connectionHelper = player;
		this.manager = new Manager();
	}

	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(port);

			boolean waiting = true;

			while(waiting){
				System.out.println("Server is listening on port: " + serverSocket.getLocalPort());

				Socket socket = serverSocket.accept();
				ConnectionHelper player = new ConnectionHelper(socket);	

				ButterflysServer t = new ButterflysServer(player);
				t.start();
			}


		}catch(IOException e){
			System.out.println("cannot create server socket at port " + port);
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}

	@Override
	public void run(){

		Object o = connectionHelper.readMessage();
		if(o instanceof ImageSerializable){

			BufferedImage bufferedImage = ImageTransformer.ISToBufferedImage((ImageSerializable)o);
			File imgFile = new File("C:/Users/simkiy/Documents/temp/butterfls/resources/not_in_db/tempImage.jpg");
			//File imgFile = new File("/home/ec2-user/images/tempImage.jpg");
			
			BufferedImage imageAns = null;
			try {
				imgFile.createNewFile();
				ImageIO.write(bufferedImage, "jpg" , imgFile);
				
				// take the buffered image and insert to the algorithm
				
				
				if(((ImageSerializable)o).messageType == MessageType.SEARCH){
					Butterfly ans;

					ans = manager.getMatch(imgFile.getCanonicalPath());
					//manager.getClosestImage(bi.getRGBMean(filteredImage));

					imageAns = ImageIO.read(new File(ans.linkToPicture));
					ImageSerializable IS = ImageTransformer.bufferedImageToIS(imageAns);
					IS.butterflyName = ans.name;
					IS.butterflyDescription = ans.description;
					
					connectionHelper.sendMessage(IS);

					imgFile.delete();
				}

				else if(((ImageSerializable)o).messageType == MessageType.ADD_TO_DB){
					imgFile.delete();
					String fileName = ((ImageSerializable)o).butterflyName.replaceAll(" ", "_");
					String pathToFile = "/home/ec2-user/images/" + fileName +  ".jpg";
					File newImgFile = new File(pathToFile);
					while(newImgFile.exists())
						newImgFile = new File(newImgFile.getCanonicalPath().replace(".jpg", "_1.jpg"));
					newImgFile.createNewFile();
					ImageIO.write(bufferedImage, "jpg", newImgFile);
					
					manager.AddToDB(newImgFile.getCanonicalPath() , ((ImageSerializable)o).butterflyName , ((ImageSerializable)o).butterflyDescription);
					connectionHelper.sendMessage(new OkMessage());
				}
			} catch (ClassNotFoundException | SQLException | IOException | NullPointerException
					| InterruptedException e) {
				imgFile.delete();
				connectionHelper.close();
				e.printStackTrace();
			}



			connectionHelper.close();
			this.interrupt();

			
		}


	}


}
