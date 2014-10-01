package detectors;

import java.awt.Color;
import java.awt.image.BufferedImage;


public class RGBDetector extends Detector {

	public void detectButterfly(BufferedImage image){
		BufferedImage image2 = analyzeImage2(image);
		BufferedImage image2_5 = analyzeImage2(image2);
		
		checkImageStrength(image2_5);
	}
	
	protected BufferedImage analyzeImage2(BufferedImage image){
		return analyzeImage2(image, 0);
	}

	private BufferedImage analyzeImage2(BufferedImage image , int mode){
		BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		int[] frameMean = getFrameMean(image);

		//System.out.println("!!!!!!!  FRAME !!!!!!!!");
		//printMeans(frameMean);

		int[] centerMean = getCenterMean(image);
		if(mode == 1)
			centerMean = getCenterMean(image.getSubimage(0, 0, image.getWidth()/2, image.getHeight()));
		else if(mode == 2)
			centerMean = getCenterMean(image.getSubimage(image.getWidth()/2, 0, image.getWidth()/2, image.getHeight()));

		//System.out.println("==== CENTER ====");
		//printMeans(centerMean);

		for(int i =0 ; i < image.getWidth() ; i += AREA_RADIUS){
			for(int j = 0; j < image.getHeight() ; j += AREA_RADIUS){
				int[] areaMean = getSquareAreaMean(image, i, j);
				//System.out.println("(" + i + "," + j + ")");
				//printMeans(areaMean);
				int distanceFrame = calcDistanceMeansRGB(frameMean, areaMean);
				//System.out.println("Distance FRAME: " + distanceFrame);
				int distanceCenter = calcDistanceMeansRGB(centerMean, areaMean);
				//System.out.println("Distance CENTER: " + distanceCenter);
				if(distanceFrame < distanceCenter || distanceFrame == -1){
					image2 = paintSquare(image, image2, i, j, Color.WHITE);
				}
				else if(distanceCenter < distanceFrame){
					image2 = paintSquare(image, image2, i, j, null);
				}
			}
		}
		return image2;
	}
	
	
	private int [] getSquareAreaMean(BufferedImage image , int i , int j){
		int[] areaSumRgb = new int[5];
		for(int y = j ; y < j + AREA_RADIUS ; y++){
			int[] lineMean = getLineRgb(image , i , i + AREA_RADIUS , y);
			areaSumRgb = addArray(areaSumRgb, lineMean);
		}

		int[] mean = getMeansFromSum(areaSumRgb);

		return mean;

	}


	private int[] getFrameMean(BufferedImage image){
		int[] frameSumRgb = new int[5];
		// get north frame
		for(int i = 0 ; i < FRAME_WIDTH ; i++){
			int[] lineMean = getLineRgb(image , 0 , image.getWidth() , i);
			frameSumRgb = addArray(frameSumRgb, lineMean);
		}
		// get south frame
		for(int i = image.getHeight() - FRAME_WIDTH ; i < image.getHeight() ; i++){
			int[] lineMean = getLineRgb(image , 0 , image.getWidth() , i);
			frameSumRgb = addArray(frameSumRgb, lineMean);
		}
		//get west and east frame
		for(int i = FRAME_WIDTH ; i < image.getHeight() - FRAME_WIDTH ; i++){
			int[] lineMean = getLineRgb(image , 0 , FRAME_WIDTH , i);
			frameSumRgb = addArray(frameSumRgb, lineMean);
			lineMean = getLineRgb(image , image.getWidth() - FRAME_WIDTH , image.getWidth() , i);
			frameSumRgb = addArray(frameSumRgb, lineMean);
		}

		int[] mean = getMeansFromSum(frameSumRgb);

		return mean;

	}

	private int[] getCenterMean(BufferedImage image){
		int[] centerSumRgb = new int[5];
		for(int i = image.getHeight()/8 ; i < image.getHeight() - image.getHeight()/8 ; i++){
			int[] lineRgb = getLineRgb(image , image.getWidth()/8 , image.getWidth() - image.getWidth()/8 , i);
			centerSumRgb = addArray(centerSumRgb, lineRgb);
		}

		int[] mean = getMeansFromSum(centerSumRgb);

		return mean;
	}


}
