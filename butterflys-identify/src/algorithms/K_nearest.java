package algorithms;

import java.util.List;
import java.util.Vector;
import objects.*;

/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
public class K_nearest {

    /**
     * this is a generic K Nearest Neighbors function, that check the distance
     *
     * @param functionCompare the compare function
     * @param newButterfly the butterfly vector we need to get a match for
     * @param butterflyList a vector of butterfly to search Neighbors on
     * @param maxDistance the max distance we will care about,
     * "Double.MAX_VALUE" means don't limit
     * @param maxNumOfNeighbors the number of Neighbors we will care about to
     * decide (closest Neighbors), Integer.MAX_VALUE means dont limit
     * @param avgDistance if true, calc the average distance for each butterfly
     * type (closest Neighbors)
     * @return the resualt Butterfly ID
     */
    protected int KNearestNeighborsGeneric(compareFunction functionCompare, BVector newButterfly, List<BVector> butterflyList, double maxDistance, int maxNumOfNeighbors, boolean avgDistance) {
        try {
            Vector<Helper> compareRes = new Vector<Helper>(); // copareRes will hold the distance for each neighbor
            Vector<Integer> IDs = new Vector<Integer>(); // which butterflies ID's we have resualt for
            Vector<Integer> IDsCount = new Vector<Integer>(); // this vector will match the IDs vector, counting how many times we got each butterfly ID
            for (BVector vector : butterflyList) {
                double res = functionCompare.compareVector(vector, newButterfly);
                if (res <= maxDistance) {
                    addHelperSorted(compareRes, new Helper(res, vector.butterfly_id));
                    AddToIDsAndIDsCount(vector.butterfly_id, IDs, IDsCount);
                    if (compareRes.size() > maxNumOfNeighbors) {
                        removeFromIDsAndIDsCount(compareRes.lastElement().getButterflyID(), IDs, IDsCount);
                        compareRes.removeElementAt(compareRes.size() - 1);
                    }
                }
            }
            if (avgDistance == true) {
                Vector<Helper> AvgDistanceRes = new Vector<Helper>();
                for (int i = 0; i < IDs.size(); i++) {
                    double sum = 0;
                    for (Helper curDistance : compareRes) {
                        if (curDistance.getButterflyID() == IDs.elementAt(i)) {
                            sum = sum + curDistance.getDistance();
                        }
                    }
                    addHelperSorted(AvgDistanceRes, new Helper(sum / IDsCount.elementAt(i), IDs.elementAt(i)));
                }
                if (AvgDistanceRes.isEmpty()) {
                    return -1;
                }
                return AvgDistanceRes.firstElement().getButterflyID();
            } else {
                int maxIndex = -1;
                int maxCount = -1;
                for (int i = 0; i < IDsCount.size(); i++) {
                    if (IDsCount.elementAt(i) > maxCount) {
                        maxCount = IDsCount.elementAt(i);
                        maxIndex = i;
                    }
                }
                if (maxIndex == -1) {
                    return -1;
                }
                return IDs.elementAt(maxIndex);
            }
        } catch (Exception ex) {
            return -1;
        }
    }

    private void addHelperSorted(Vector<Helper> compareRes, Helper res) {
        boolean done = false;
        for (int i = 0; i < compareRes.size(); i++) {
            if (res.getDistance() < compareRes.elementAt(i).getDistance()) {
                compareRes.insertElementAt(res, i);
                done = true;
                break;
            }
        }
        if (done == false) {
            compareRes.add(res);
        }
    }

    private void AddToIDsAndIDsCount(int butterfly_id, Vector<Integer> IDs, Vector<Integer> IDsCount) {
        int index = IDs.indexOf(butterfly_id);
        if (index != -1) {
            IDsCount.set(index, IDsCount.elementAt(index) + 1);
        } else {
            IDs.add(butterfly_id);
            IDsCount.add(1);
        }
    }

    private void removeFromIDsAndIDsCount(int butterfly_id, Vector<Integer> IDs, Vector<Integer> IDsCount) {
        int index = IDs.indexOf(butterfly_id);
        if (IDsCount.elementAt(index) == 1) {
            IDsCount.removeElementAt(index);
            IDs.removeElementAt(index);
        } else {
            IDsCount.set(index, IDsCount.elementAt(index) - 1);
        }
    }
}
