package algorithms;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import objects.BVector;
import objects.Butterfly;
import objects.Pixel;

import db.ButterflysClient;
import db.PixelsClient;
import db.VectorsClient;

/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
public class Manager {
    //private DBClinet DBObject = new DBClinet();
    private HSV hsv = new HSV();
    private K_nearest kNear = new K_nearest();
    
    public void AddToDB(String imgPath, String name, String description) throws ClassNotFoundException, SQLException, IOException
    {
        Vector <Integer> RGB = hsv.getRGB(imgPath);
        Vector <Float> HSV = hsv.getHSV(RGB);
        float Vsum = kNear.vectorSum(HSV);
        float Vavg = kNear.VectorAvg(HSV, Vsum);
       
        ButterflysClient butterflysClient = new ButterflysClient();
        VectorsClient vectorsClient = new VectorsClient();
        PixelsClient pixelsClient = new PixelsClient();
        
        butterflysClient.insertButterfly(new Butterfly(name, imgPath, description));
        // need to get butterfly id from insert 
  //    vectorsClient.insertVector(new BVector(, Vsum, Vavg));
        pixelsClient.insertVector(RGB);
    }
    
    public Butterfly Detector(String imgPath) throws ClassNotFoundException, SQLException, IOException
    { // suppose to run on the alghorithms and return a match
        Butterfly ans ;
        Vector <Integer> RGB = hsv.getRGB(imgPath);
        ans = kNear.KNearestNeighborsDistance(RGB);
        return ans;
    }
    

}
