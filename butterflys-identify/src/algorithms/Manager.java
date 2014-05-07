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
        System.out.println("rgb size: " + RGB.size());
        Vector <Float> HSV = hsv.getHSV(RGB);
        System.out.println("hsv size: " + HSV.size());
        float Vsum = kNear.vectorSum(HSV);
        float Vavg = kNear.VectorAvg(HSV, Vsum);
       
        ButterflysClient butterflysClient = new ButterflysClient();
        
        Butterfly butterfly = new Butterfly(name, imgPath, description);
        int butterfly_id;
        List<Butterfly> butterflys = butterflysClient.selectButterfly(butterflysClient.getColName() + " = " + "'" + butterfly.name + "'");
        if(butterflys.size() != 0){
        	butterfly_id = butterflys.get(0).id;
        } else{
        	butterfly_id = butterflysClient.insertButterfly(butterfly);
        }
        
        System.out.println("inserted butterfly with id: "+ butterfly_id);
        BVector bvector = new BVector(butterfly_id, imgPath , Vsum, Vavg);
        addVectorToDb(bvector);
    }
    
    public void addVectorToDb(BVector bvector) throws ClassNotFoundException, SQLException, IOException{
    	VectorsClient vectorsClient = new VectorsClient();
    	int vector_id = vectorsClient.insertVector(bvector);
        System.out.println("Inserted vector with id: " + vector_id);
    }
    
    public Butterfly Detector(String imgPath) throws ClassNotFoundException, SQLException, IOException
    { // suppose to run on the alghorithms and return a match
        Butterfly ans ;
        Vector <Integer> RGB = hsv.getRGB(imgPath);
        System.out.println("rgb vector size: " + RGB.size());
        ans = kNear.KNearestNeighborsDistance(RGB);
        return ans;
    }
    

}
