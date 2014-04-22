package algorithms;

import java.util.Vector;

/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
public class K_nearest {
    
    private DBClinet DBObject = new DBClinet();
    
    protected Butterfly KNearestNeighbors(Vector <Float> V)
    {
        String [] ans = new String[2];
        float min = Float.MAX_VALUE;
        for (int i = 0; i < DBObject.size(); i++)
        {
            Vector <Float> cur = DBObject.getVector(i);
            float curSum = 0;
            for (int j = 0; j < cur.size(); j = j + 3)
            {
                curSum = curSum + Math.abs(cur.elementAt(j) - V.elementAt(j));
            }
            if (curSum < min)
            {
                min = curSum;
                ans[0] = DBObject.getButterflyInfo(i);
                ans[1] = DBObject.getImagePath(i);
            }
        }
        return ans;
    }
    
    protected float VectorSum(Vector<Float> HSV)
    { // for now it's only the sum of h
        float Hsum = 0;
        for (int i = 0 ; i< HSV.size(); i = i + 3)
        {
            Hsum = Hsum + HSV.elementAt((i));
        }
        return Hsum;
    }
    
    protected float VectorAvg(Vector<Float> HSV, float Hsum)
    {// for now it's only the avg of h
        return Hsum / (HSV.size()/3);
    }
}
