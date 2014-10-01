package detectors;

import java.awt.Color;
import java.awt.image.BufferedImage;


public class HSVDetector extends Detector{

	
	
	@Override
	public void detectButterfly(BufferedImage image) {
		detectButterfly(image, 1);
		detectButterfly(image, 2);
		detectButterfly(image, 3);
		detectButterfly(image, 4);
		
	}
	
	private void detectButterfly(BufferedImage image , int mode){
		BufferedImage image2 = analyzeImage3(image , mode);
		//BufferedImage image2_5 = analyzeImage2(image2);//analyzeImageSecondary(image2, image, mode);		
	
		checkImageStrength(image2);
	}
	
	private BufferedImage analyzeImage3(BufferedImage image , int mode){
		BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		float[] frameMean = getFrameMeanHSV(image);

		System.out.println("!!!!!!!  FRAME !!!!!!!!");
		printMeansHSV(frameMean);

		float[] centerMean = getCenterMeanHSV(image);

		System.out.println("==== CENTER ====");
		printMeansHSV(centerMean);

		for(int i =0 ; i < image.getWidth() ; i += AREA_RADIUS){
			for(int j = 0; j < image.getHeight() ; j += AREA_RADIUS){
				float[] areaMean = getSquareAreaMeanHSV(image, i, j);
				//System.out.println("(" + i + "," + j + ")");
				//printMeans(areaMean);
				float distanceFrame = calcDistanceMeansHSV(frameMean, areaMean , mode);
				//System.out.println("Distance FRAME: " + distanceFrame);
				float distanceCenter = calcDistanceMeansHSV(centerMean, areaMean , mode);
				//System.out.println("Distance CENTER: " + distanceCenter);
				if(distanceFrame < distanceCenter){
					image2 = paintSquare(image, image2, i, j, Color.WHITE);
				}
				else if(distanceCenter < distanceFrame){
					image2 = paintSquare(image, image2, i, j, null);
				}
			}
		}
		return image2;
	}
	
	private float[] getFrameMeanHSV(BufferedImage image){
		float[] frameSumRgb = new float[5];
		// get north frame
		for(int i = 0 ; i < FRAME_WIDTH ; i++){
			float[] lineMean = getLineHSV(image , 0 , image.getWidth() , i);
			frameSumRgb = addArray(frameSumRgb, lineMean);
		}
		// get south frame
		for(int i = image.getHeight() - FRAME_WIDTH ; i < image.getHeight() ; i++){
			float[] lineMean = getLineHSV(image , 0 , image.getWidth() , i);
			frameSumRgb = addArray(frameSumRgb, lineMean);
		}
		//get west and east frame
		for(int i = FRAME_WIDTH ; i < image.getHeight() - FRAME_WIDTH ; i++){
			float[] lineMean = getLineHSV(image , 0 , FRAME_WIDTH , i);
			frameSumRgb = addArray(frameSumRgb, lineMean);
			lineMean = getLineHSV(image , image.getWidth() - FRAME_WIDTH , image.getWidth() , i);
			frameSumRgb = addArray(frameSumRgb, lineMean);
		}

		float[] mean = getMeansFromSumHSV(frameSumRgb);

		return mean;

	}

	private float[] getCenterMeanHSV(BufferedImage image){
		float[] centerSumRgb = new float[5];
		for(int i = image.getHeight()/8 ; i < image.getHeight() - image.getHeight()/8 ; i++){
			float[] lineRgb = getLineHSV(image , image.getWidth()/8 , image.getWidth() - image.getWidth()/8 , i);
			centerSumRgb = addArray(centerSumRgb, lineRgb);
		}

		float[] mean = getMeansFromSumHSV(centerSumRgb);

		return mean;
	}
	
	private float [] getSquareAreaMeanHSV(BufferedImage image , int i , int j){
		float[] areaSumRgb = new float[5];
		for(int y = j ; y < j + AREA_RADIUS ; y++){
			float[] lineMean = getLineHSV(image , i , i + AREA_RADIUS , y);
			areaSumRgb = addArray(areaSumRgb, lineMean);
		}

		float[] mean = getMeansFromSumHSV(areaSumRgb);

		return mean;

	}


	private float[] getMeansFromSumHSV(float[] sums){
		float[] means = {sums[0], sums[1] / sums[0] , sums[2] / sums[0] ,
				sums[3] / sums[0] , sums[4] / sums[0]};
		return means;
	}

	private float[] getLineHSV(BufferedImage image , int start , int end , int fixed){
		float[] hsvSun = new float[5];
		for(int i = start; i < end ; i++){
			try{
				int rgb = image.getRGB(i, fixed);
				if(rgb == -1)
					continue;
				float[] pixelData = getHsv(i, fixed, image);
				//System.out.println("("+ i + "," + fixed + ") pixel data: "+ rgb +" ["+ pixelData[0] + "," + pixelData[1] + "," + pixelData[2] +"]");
				hsvSun[0]++;
				hsvSun[1] += rgb;
				hsvSun[2] += pixelData[0];
				hsvSun[3] += pixelData[1];
				hsvSun[4] += pixelData[2];

			} catch(Exception e){}
		}
		return hsvSun;
	}

	private float[] addArray(float[] original , float[] addition){
		for(int i=0 ; i < original.length && i < addition.length ; i++){
			original[i] += addition[i];
		}
		return original;

	}


	private float calcDistanceMeansHSV(float[] source , float[] objective , int mode){
		float res = 0;
		switch(mode){
		case 1:
			for(int i = 2 ; i < source.length && i < objective.length ; i++){
				res += Math.abs(source[i] - objective[i]);
			}
			break;
		case 2:
			res = Math.abs(source[2] - objective[2]) + Math.abs(source[3] - objective[3]);
			break;
		case 3:
			res = Math.abs(source[2] - objective[2]);
			break;
		case 4:
			res = Math.abs(source[3] - objective[3]);
			break;
		case 5:
			res = Math.abs(source[4] - objective[4]);
			break;
		}

		return res;
	}


	private float[] getHsv(int x , int y , BufferedImage image){
		float[] hsv = new float[3];
		int rgb = image.getRGB(x, y);
		int[] pixelData = getPixelData(rgb);
		Color.RGBtoHSB(pixelData[0], pixelData[1], pixelData[2], hsv);
		return hsv;
	}
	
	private void printMeansHSV(float[] mean){
		System.out.println("HSV mean: " + mean[1]);
		System.out.println("H mean: " + mean[2]);
		System.out.println("S mean: " + mean[3]);
		System.out.println("V mean: " + mean[4]);
	}

}
