package algorithms;

import db.AlgoWeightsClient;
import db.ButterflysClient;
import db.VectorsClient;
import detectors.ButterflyIdentifier;
import detectors.RGBDetector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.imageio.ImageIO;
import objects.BVector;
import objects.Butterfly;

/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
public class Manager {
    //private DBClinet DBObject = new DBClinet();
    private HSV hsv = new HSV();
    private K_nearest kNear = new K_nearest();
    private ButterflysClient butterflysClient = new ButterflysClient();
    private VectorsClient vectorsClient = new VectorsClient();
    private AlgoWeightsClient algoWeightsClient = new AlgoWeightsClient();
    
    public void addToDB(String imgPath, String name, String description) throws ClassNotFoundException, SQLException, IOException
    {
    	imgPath = convertBackslashes(imgPath);
        Butterfly butterfly = new Butterfly(name, imgPath, description);
        int butterfly_id;
        List<Butterfly> butterflys = butterflysClient.selectButterfly(butterflysClient.getColName() + " = " + "'" + butterfly.name + "'");
        if(butterflys.size() != 0){
        	butterfly_id = butterflys.get(0).id;
        } else{
        	butterfly_id = butterflysClient.insertButterfly(butterfly);
        }
        
        BVector bvector = createBVector(butterfly_id, imgPath);
        System.out.println("inserted butterfly with id: "+ butterfly_id);
        addVectorToDb(bvector);
    }
    
    private void addVectorToDb(BVector bvector) throws ClassNotFoundException, SQLException, IOException{
    	int vector_id = vectorsClient.insertVector(bvector);
        System.out.println("Inserted vector with id: " + vector_id);
    }
    
    private BVector createBVector(int butterfly_id, String imgPath) throws IOException{
        BufferedImage image = ImageIO.read(new File(imgPath));
        
        ButterflyIdentifier bi = new ButterflyIdentifier();
        BufferedImage filteredImage = bi.getFilteredImage(image);
        
        int[] rgbMean = bi.getRGBMean(filteredImage);
        Vector <Integer> RGB = hsv.getRGB(filteredImage);
        Vector <Float> HSV = hsv.getHSV(RGB);
        float[] hsvMean = hsv.getHSVMean(HSV);
        float[] MaxCountFromHistogram = hsv.getMaxCountFromHistogram(RGB);
        
        BVector bvector = new BVector(butterfly_id, imgPath , rgbMean[1] , rgbMean[2] , rgbMean[3] , rgbMean[4], hsvMean[0], 
                hsvMean[1], hsvMean[2], MaxCountFromHistogram[0], MaxCountFromHistogram[1], MaxCountFromHistogram[2]);
        return bvector;
    }
    
    public void createAdaboostWeights() throws ClassNotFoundException, SQLException, IOException{
        List<BVector> DBVectorsList = vectorsClient.selectVectors("");
        compareFunction func;
        BVector currVector;
        float[] vectorsWeights = new float[DBVectorsList.size()];
        boolean[] currAlgoAns = new boolean[DBVectorsList.size()];
        float[] algoWeights = new float[22];
        
        // initialize the algo weights
        for (int i = 0; i < algoWeights.length; i++)
        {
            algoWeights[i] = 0;
        }
        
        // initialize each vector weight
        for (int i = 0; i < vectorsWeights.length; i++)
        {
            vectorsWeights[i] = (float) (1.0 / (vectorsWeights.length - 1));
        }
        
        for (int i = 0; i < algoWeights.length; i++)
        {
            for (int j = 0; j < DBVectorsList.size(); j++)
            {
                currVector = DBVectorsList.remove(j);
                int currAns;
                    switch (i)
                    {
                        case 0:
                            func = new compareByMostCountedColorHSVandMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, 0.25, 5, false);
                            break;
                        case 1:
                            func = new compareByMostCountedColorHSVandMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, Double.MAX_VALUE, 1, false);
                            break;
                        case 2:
                            func = new compareByMostCountedColorHSVandMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, 0.3, 11, true);
                            break;
                        case 3:
                            func = new compareByMostCountedColorHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, 0.3, 11, true);
                            break;
                        case 4:
                            func = new compareByMostCountedColorHSVandMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, 0.15, 3, false);
                            break;
                        case 5:
                            func = new compareByMostCountedColorHSVandMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, Double.MAX_VALUE, 5, false);
                            break;
                        case 6:
                            func = new compareByMostCountedColorHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, Double.MAX_VALUE, 5, false);
                            break;
                        case 7:
                            func = new compareByMostCountedColorHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, 0.25, 5, false);
                            break;
                        case 8:
                            func = new compareByMostCountedColorHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, Double.MAX_VALUE, 1, false);
                            break;
                        case 9:
                            func = new compareByMostCountedColorHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, Double.MAX_VALUE, Integer.MAX_VALUE, true);
                            break;
                        case 10:
                            func = new compareByMostCountedColorHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, 0.15, 3, false);
                            break;
                        case 11:
                            func = new compareByMostCountedColorHSVandMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, 0.25, 5, true);
                            break;
                        case 12:
                            func = new compareByMostCountedColorHSVandMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, Double.MAX_VALUE, Integer.MAX_VALUE, true);
                            break;
                        case 13:
                            func = new compareByMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, Double.MAX_VALUE, Integer.MAX_VALUE, true);
                            break;
                        case 14:
                            func = new compareByMostCountedColorHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, 0.25, 5, true);
                            break;
                        case 15:
                            func = new compareByMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, 0.25, 5, true);
                            break;
                        case 16:
                            func = new compareByMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, Double.MAX_VALUE, 1, false);
                            break;
                        case 17:
                            func = new compareByMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, 0.3, 11, true);
                            break;
                        case 18:
                            func = new compareByMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, 0.15, 3, false);
                            break;
                        case 19:
                            func = new compareByMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, 0.25, 5, false);
                            break;
                        case 20:
                            func = new compareByMeanHSV();
                            currAns = kNear.KNearestNeighborsGeneric(func, currVector, DBVectorsList, Double.MAX_VALUE, 5, false);
                            break;
                        default:
                            int[] rgbMean = {currVector.r_mean, currVector.g_mean, currVector.b_mean};
                            currAns = getClosestImage(rgbMean, DBVectorsList);
                            break;
                    }
                    
                if (currAns == -1 || currVector.butterfly_id != currAns) {
                    currAlgoAns[j] = false;
                    algoWeights[i] = algoWeights[i] + vectorsWeights[j];
                } else {
                    currAlgoAns[j] = true;
                }
                DBVectorsList.add(j, currVector);
            }
            if (algoWeights[i] < 0.5)
            {
                // calculating the algorithem weight, from the error rate it has until now
                algoWeights[i] = (float)(0.5 * Math.log((1 - algoWeights[i])/algoWeights[i]));
                // updating each vector weight for the next round
                double mult;
                float weightsSum = 0;
                for (int p = 0; p < vectorsWeights.length; p++)
                {
                    if (currAlgoAns[p] == true)
                    {
                        mult = Math.exp(-1 * algoWeights[i]);
                    }
                    else
                    {
                        mult = Math.exp(algoWeights[i]);
                    }
                    vectorsWeights[p] = vectorsWeights[p] * (float)mult;
                    weightsSum = weightsSum + vectorsWeights[p];
                }
                for (int p = 0; p < vectorsWeights.length; p++)
                {
                    vectorsWeights[p] = vectorsWeights[p] / weightsSum;
                }
            }
            else
            {
                algoWeights[i] = 0;
            }
        }
        //add the new weights to the DB
        algoWeightsClient.CleanAlgoWeightsTable();
        for (int i = 0; i < algoWeights.length; i ++)
        {
            algoWeightsClient.insertAlgoWeight(i+1, algoWeights[i]);
        }
    }
    
    public Butterfly getMatch(String imgPath) throws ClassNotFoundException, SQLException, IOException, InterruptedException {
        //run on the alghorithms and return a match
        List<Float> algoWeights = algoWeightsClient.selectAlgoWeightsSorted("");
        final List<BVector> DBVectorsList = vectorsClient.selectVectors("");
        Vector<Helper> res = new Vector<Helper>();
        Thread[] algoThreads = new Thread[algoWeights.size()];
        final int[] butterflyAns = new int[algoWeights.size()];
        final BVector bvector = createBVector(-1, imgPath);
        Butterfly ans;

        for (int i = 0; i < algoWeights.size(); i++) {
            if (algoWeights.get(i) == 0)
            {
                continue;
            }
            switch (i) {
                case 0:
                    final compareFunction func0 = new compareByMostCountedColorHSVandMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[0] = kNear.KNearestNeighborsGeneric(func0, bvector, DBVectorsList, 0.25, 5, false);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 1:
                    final compareFunction func1 = new compareByMostCountedColorHSVandMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[1] = kNear.KNearestNeighborsGeneric(func1, bvector, DBVectorsList, Double.MAX_VALUE, 1, false);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 2:
                    final compareFunction func2 = new compareByMostCountedColorHSVandMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[2] = kNear.KNearestNeighborsGeneric(func2, bvector, DBVectorsList, 0.3, 11, true);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 3:
                    final compareFunction func3 = new compareByMostCountedColorHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[3] = kNear.KNearestNeighborsGeneric(func3, bvector, DBVectorsList, 0.3, 11, true);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 4:
                    final compareFunction func4 = new compareByMostCountedColorHSVandMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[4] = kNear.KNearestNeighborsGeneric(func4, bvector, DBVectorsList, 0.15, 3, false);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 5:
                    final compareFunction func5 = new compareByMostCountedColorHSVandMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[5] = kNear.KNearestNeighborsGeneric(func5, bvector, DBVectorsList, Double.MAX_VALUE, 5, false);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 6:
                    final compareFunction func6 = new compareByMostCountedColorHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[6] = kNear.KNearestNeighborsGeneric(func6, bvector, DBVectorsList, Double.MAX_VALUE, 5, false);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 7:
                    final compareFunction func7 = new compareByMostCountedColorHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[7] = kNear.KNearestNeighborsGeneric(func7, bvector, DBVectorsList, 0.25, 5, false);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 8:
                    final compareFunction func8 = new compareByMostCountedColorHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[8] = kNear.KNearestNeighborsGeneric(func8, bvector, DBVectorsList, Double.MAX_VALUE, 1, false);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 9:
                    final compareFunction func9 = new compareByMostCountedColorHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[9] = kNear.KNearestNeighborsGeneric(func9, bvector, DBVectorsList, Double.MAX_VALUE, Integer.MAX_VALUE, true);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 10:
                    final compareFunction func10 = new compareByMostCountedColorHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[10] = kNear.KNearestNeighborsGeneric(func10, bvector, DBVectorsList, 0.15, 3, false);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 11:
                    final compareFunction func11 = new compareByMostCountedColorHSVandMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[11] = kNear.KNearestNeighborsGeneric(func11, bvector, DBVectorsList, 0.25, 5, true);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 12:
                    final compareFunction func12 = new compareByMostCountedColorHSVandMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[12] = kNear.KNearestNeighborsGeneric(func12, bvector, DBVectorsList, Double.MAX_VALUE, Integer.MAX_VALUE, true);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 13:
                    final compareFunction func13 = new compareByMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[13] = kNear.KNearestNeighborsGeneric(func13, bvector, DBVectorsList, Double.MAX_VALUE, Integer.MAX_VALUE, true);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 14:
                    final compareFunction func14 = new compareByMostCountedColorHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[14] = kNear.KNearestNeighborsGeneric(func14, bvector, DBVectorsList, 0.25, 5, true);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 15:
                    final compareFunction func15 = new compareByMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[15] = kNear.KNearestNeighborsGeneric(func15, bvector, DBVectorsList, 0.25, 5, true);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 16:
                    final compareFunction func16 = new compareByMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[16] = kNear.KNearestNeighborsGeneric(func16, bvector, DBVectorsList, Double.MAX_VALUE, 1, false);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 17:
                    final compareFunction func17 = new compareByMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[17] = kNear.KNearestNeighborsGeneric(func17, bvector, DBVectorsList, 0.3, 11, true);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 18:
                    final compareFunction func18 = new compareByMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[18] = kNear.KNearestNeighborsGeneric(func18, bvector, DBVectorsList, 0.15, 3, false);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 19:
                    final compareFunction func19 = new compareByMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[19] = kNear.KNearestNeighborsGeneric(func19, bvector, DBVectorsList, 0.25, 5, false);
                        }
                    });
                    algoThreads[i].start();
                    break;
                case 20:
                    final compareFunction func20 = new compareByMeanHSV();
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[20] = kNear.KNearestNeighborsGeneric(func20, bvector, DBVectorsList, Double.MAX_VALUE, 5, false);
                        }
                    });
                    algoThreads[i].start();
                    break;
                default:
                    final int[] rgbMean = {bvector.r_mean, bvector.g_mean, bvector.b_mean};
                    algoThreads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            butterflyAns[21] = getClosestImage(rgbMean, DBVectorsList);
                        }
                    });
                    algoThreads[i].start();
                    break;
            }
        }
        for (Thread t : algoThreads) {
            if (t != null)
            {
                t.join();
            }
        }

        for (int i = 0; i < butterflyAns.length; i++) {
            if (butterflyAns[i] != -1) {
                boolean exist = false;
                for (int j = 0; j < res.size(); j++) {
                    if (res.elementAt(j).getButterflyID() == butterflyAns[i]) {
                        exist = true;
                        res.elementAt(j).setDistance(res.elementAt(j).getDistance() + algoWeights.get(i));
                        break;
                    }
                }
                if (exist == false) {
                    res.add(new Helper(algoWeights.get(i), butterflyAns[i]));
                }
            }
        }

        int ansID = -1;
        double maxScore = -1;
        for (int i = 0; i < res.size(); i++) {
            if (res.elementAt(i).getDistance() > maxScore) {
                maxScore = res.elementAt(i).getDistance();
                ansID = res.elementAt(i).getButterflyID();
            }
        }
        ans = butterflysClient.selectSingleButterfly(butterflysClient.getColId() + " = " + ansID);
        return ans;
    }
    
    public int getClosestImage(int[] rgbMeanData, List<BVector> vectorList){
		// get closest neighbour
    	detectors.Detector detector = new RGBDetector();
		try {
			int distanceFromImage = 255*3;
			BVector closest = null;
			for(BVector vector : vectorList){
				int currentDistance = detector.calcDistanceMeansRGB(rgbMeanData, new int[]{1,1,vector.getR_mean() , vector.getG_mean() , vector.getB_mean()});

				if(currentDistance < distanceFromImage){
					distanceFromImage = currentDistance;
					closest = vector;
				}
			}
			return closest.butterfly_id;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

    public class compareByMeanHSV implements compareFunction
    {
        @Override
        public double compareVector(BVector current, BVector butterflyToSearch) {
            float h,s,v;
            h = Math.abs( current.h_mean - butterflyToSearch.h_mean);
            s = Math.abs( current.s_mean - butterflyToSearch.s_mean);
            v = Math.abs( current.v_mean - butterflyToSearch.v_mean);
            return Math.sqrt(Math.pow(h, 2) + Math.pow(s, 2) + Math.pow(v, 2));
        }  
    }
    
    public class compareByMostCountedColorHSV implements compareFunction
    {
        @Override
        public double compareVector(BVector current, BVector butterflyToSearch) {
            float h,s,v;
            h = Math.abs( current.h_max - butterflyToSearch.h_max);
            s = Math.abs( current.s_max - butterflyToSearch.s_max);
            v = Math.abs( current.v_max - butterflyToSearch.v_max);
            return Math.sqrt(Math.pow(h, 2) + Math.pow(s, 2) + Math.pow(v, 2));
        }  
    }
    
    public class compareByMostCountedColorHSVandMeanHSV implements compareFunction
    {
        @Override
        public double compareVector(BVector current, BVector butterflyToSearch) {
            float h,s,v;
            h = (Math.abs( current.h_max - butterflyToSearch.h_max) + Math.abs( current.h_mean - butterflyToSearch.h_mean)) / 2;
            s = (Math.abs( current.s_max - butterflyToSearch.s_max) + Math.abs( current.s_mean - butterflyToSearch.s_mean)) / 2;
            v = (Math.abs( current.v_max - butterflyToSearch.v_max) + Math.abs( current.v_mean - butterflyToSearch.v_mean)) / 2;
            return Math.sqrt(Math.pow(h, 2) + Math.pow(s, 2) + Math.pow(v, 2));
        }  
    }
    
	public static String convertBackslashes(String s){
		String res = s.replace("\t", "/t");
		res = res.replace("\r", "/r");
		res = res.replace("\n", "/n");
		res = res.replace("\b", "/b");
		res = res.replace("\f", "/f");
		res = res.replace("\\", "/");
		System.out.println(res);
		return res;
	}
}
