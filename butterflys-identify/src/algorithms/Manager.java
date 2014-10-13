package algorithms;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;

import objects.BVector;
import objects.Butterfly;
import objects.Pixel;

import db.ButterflysClient;
import db.PixelsClient;
import db.VectorsClient;
import detectors.ButterflyIdentifier;
import detectors.RGBDetector;

/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
public class Manager {
    //private DBClinet DBObject = new DBClinet();
    private HSV hsv = new HSV();
    private K_nearest kNear = new K_nearest();
    ButterflysClient butterflysClient = new ButterflysClient();
    VectorsClient vectorsClient = new VectorsClient();
    
    public void AddToDB(String imgPath, String name, String description) throws ClassNotFoundException, SQLException, IOException
    {

       
        Butterfly butterfly = new Butterfly(name, imgPath, description);
        int butterfly_id;
        List<Butterfly> butterflys = butterflysClient.selectButterfly(butterflysClient.getColName() + " = " + "'" + butterfly.name + "'");
        if(butterflys.size() != 0){
        	butterfly_id = butterflys.get(0).id;
        } else{
        	butterfly_id = butterflysClient.insertButterfly(butterfly);
        }
        
        BufferedImage image = ImageIO.read(new File(imgPath));
        
        ButterflyIdentifier bi = new ButterflyIdentifier();
        BufferedImage filteredImage = bi.getFilteredImage(image);
        
        int[] rgbMean = bi.getRGBMean(filteredImage);
        
        System.out.println("inserted butterfly with id: "+ butterfly_id);
        BVector bvector = new BVector(butterfly_id, imgPath , rgbMean[1] , rgbMean[2] , rgbMean[3] , rgbMean[4]);
        addVectorToDb(bvector);
    }
    
    public void addVectorToDb(BVector bvector) throws ClassNotFoundException, SQLException, IOException{
    	int vector_id = vectorsClient.insertVector(bvector);
        System.out.println("Inserted vector with id: " + vector_id);
    }
    
    public Butterfly Detector(String imgPath) throws ClassNotFoundException, SQLException, IOException
    { // suppose to run on the alghorithms and return a match
        Butterfly ans ;
        Vector <Integer> RGB = hsv.getRGB(imgPath);
        Vector <Float> HSV = hsv.getHSV(RGB);
        float Vsum = kNear.vectorSum(HSV);
        float Vavg = kNear.VectorAvg(HSV, Vsum);
        ans = kNear.KNearestNeighborsAvgH(Vavg);
        return ans;
    }
    
    
    public Butterfly getClosestImage(int[] rgbMeanData){
		// get closest neighbour
    	detectors.Detector detector = new RGBDetector();
		try {
			List<BVector> vectorList = vectorsClient.selectVectors("");
			int distanceFromImage = 255*3;
			BVector closest = null;
			for(BVector vector : vectorList){
				System.out.println(vector.toString());
				int currentDistance = detector.calcDistanceMeansRGB(rgbMeanData, new int[]{1,1,vector.getR_mean() , vector.getG_mean() , vector.getB_mean()});
				System.out.println("distance: " + currentDistance);

				if(currentDistance < distanceFromImage){
					distanceFromImage = currentDistance;
					closest = vector;
				}
			}
			return butterflysClient.selectSingleButterfly(butterflysClient.getColId() + " = " + closest.butterfly_id);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
