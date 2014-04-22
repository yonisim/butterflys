package algorithms;

import java.util.Vector;

/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
public class Manager {
    //private DBClinet DBObject = new DBClinet();
    private HSV hsv = new HSV();
    private K_nearest kNear = new K_nearest();
    
    public void AddToDB(String imgPath, String details, String description)
    {
        Vector <Integer> RGB = hsv.getRGB(imgPath);
        Vector <Float> HSV = hsv.getHSV(RGB);
        float Vsum = kNear.VectorSum(HSV);
        float Vavg = kNear.VectorAvg(HSV, Vsum);
        
        //DBObject.AddToDataBase(hsv.getHSV(imgPath),details, imgPath);
    }
    
    public String [] Detector(String imgPath)
    { // suppose to run on the alghorithms and return a match
        String [] ans ;
        Vector <Integer> RGB = hsv.getRGB(imgPath);
        ans = kNear.KNearestNeighbors(hsv.getHSV(RGB));
        return ans;
    }
}
