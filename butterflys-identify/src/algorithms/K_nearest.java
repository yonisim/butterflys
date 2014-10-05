package algorithms;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import db.*;
import objects.*;

/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
public class K_nearest {

	private ButterflysClient butterflysClient = new ButterflysClient();

	protected Butterfly KNearestNeighborsDistance(Vector <Integer> original) throws ClassNotFoundException, SQLException, IOException
	{
		Butterfly result = new Butterfly();
		long min = Long.MAX_VALUE;
		VectorsClient vectorsClient = new VectorsClient();
		PixelsClient pixelsClient = new PixelsClient();
		for (BVector vector : vectorsClient.selectVectors(""))
		{
			Vector <Integer> current = pixelsClient.getVectorFromDb(vector.vector_id);

			long distance = vectorsDistance(current, original);

			if (distance < min)
			{
				min = distance;
				List<Butterfly> results = butterflysClient.selectButterfly(butterflysClient.getColId() + " = " + vector.butterfly_id);
				if(results.size() > 1)
					System.out.println("ERROR: more than 1 butterfly matches the id " + vector.butterfly_id);
				result = results.get(0);
			}
		}
		if(result.id == 0)
			System.out.println("No match was found");
		return result;
	}

	protected Butterfly KNearestNeighborsSum(Vector<Integer> original) throws ClassNotFoundException, SQLException, IOException
	{
		Butterfly result = new Butterfly();
		float min = Float.MAX_VALUE;
		float originalSum = vectorSumRGB(original);
		float res;
		VectorsClient vectorsClient = new VectorsClient();
		for (BVector vector : vectorsClient.selectVectors("")){
			res = Math.abs(vector.vector_max - originalSum);
			if(res < min){
				min = res;
				List<Butterfly> results = butterflysClient.selectButterfly(butterflysClient.getColId() + " = " + vector.butterfly_id);
				if(results.size() > 1)
					System.out.println("ERROR: more than 1 butterfly matches the id " + vector.butterfly_id);
				result = results.get(0);

			}
		}
		return result;
	}
	
	protected Butterfly KNearestNeighborsAvg(Vector<Integer> original) throws ClassNotFoundException, SQLException, IOException
	{
		Butterfly result = new Butterfly();
		float min = Float.MAX_VALUE;
		float originalAvg = VectorAvgRGB(original);
		float res;
		VectorsClient vectorsClient = new VectorsClient();
		for (BVector vector : vectorsClient.selectVectors("")){
			res = vector.r_mean - originalAvg;
			if(res < min){
				min = res;
				List<Butterfly> results = butterflysClient.selectButterfly(butterflysClient.getColId() + " = " + vector.butterfly_id);
				if(results.size() > 1)
					System.out.println("ERROR: more than 1 butterfly matches the id " + vector.butterfly_id);
				result = results.get(0);

			}
		}
		return result;
	}

        protected Butterfly KNearestNeighborsAvgH(float Vavg) throws ClassNotFoundException, SQLException, IOException
        {
            Butterfly result = new Butterfly();
            float min = Float.MAX_VALUE;
            float res;
            VectorsClient vectorsClient = new VectorsClient();
            for (BVector vector : vectorsClient.selectVectors("")){
                res = Math.abs(vector.r_mean - Vavg);
                if(res < min){
				min = res;
				List<Butterfly> results = butterflysClient.selectButterfly(butterflysClient.getColId() + " = " + vector.butterfly_id);
				if(results.size() > 1)
					System.out.println("ERROR: more than 1 butterfly matches the id " + vector.butterfly_id);
				result = results.get(0);
                }
            }
            return result;
        }

	protected long vectorSumRGB(Vector<Integer> vector){
		long sum = 0;
		for (int i = 0 ; i< vector.size(); i = i++)
		{
			sum += vector.elementAt((i));
		}
		return sum;
	}

	protected float VectorAvgRGB(Vector<Integer> vector){
		return vectorSumRGB(vector) / vector.size();
	}

	
	
	protected float vectorSum(Vector<Float> HSV)
	{ // for now it's only the sum of h
		float Hsum = 0;
		for (int i = 0 ; i< HSV.size(); i = i + 3)
		{
			Hsum += HSV.elementAt((i));
		}
		return Hsum;
	}

	protected float VectorAvg(Vector<Float> HSV, float Hsum)
	{// for now it's only the avg of h
		return Hsum / (HSV.size()/3);
	}

	private long vectorsDistance(Vector<Integer> vector_1 , Vector<Integer> vector_2){
		long res = 0;
		for (int i = 0; i < vector_1.size(); i += 3)
		{
			res += Math.abs(vector_1.elementAt(i) - vector_2.elementAt(i));
		}
		return res;
	}
}
