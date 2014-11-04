/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
public class Helper {
     private double _distance;
     private int _butterflyID;
     
     public Helper(double distance, int ID)
     {
         _distance = distance;
         _butterflyID = ID;
     }
     
     protected double getDistance()
     {
         return _distance;
     }
     
     protected int getButterflyID()
     {
         return _butterflyID;
     }
     
     protected void setDistance(double distance){
         _distance = distance;
     }
}
