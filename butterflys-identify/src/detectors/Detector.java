package detectors;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;


public abstract  class Detector {

	protected double maxImageStrength = 0;
	protected BufferedImage strongestImage;
	protected static final int WHITE = Color.WHITE.getRGB();
	protected static final int AREA_RADIUS = 2;
	protected static final double RELEVANT_PERCENTAGE = 0.34;
	protected static final int FRAME_WIDTH = 20;

	public abstract void detectButterfly(BufferedImage image);


	protected void checkImageStrength(BufferedImage image){
		double strength = getImageStrength(image);
		System.out.println("Image strength: " + strength);
		if(strength > maxImageStrength){
			maxImageStrength = strength;
			strongestImage = image;
		}
	}

	private double getImageStrength(BufferedImage image){
		int frameFillPercent = getFrameFillPercent(image);
		int centerFillPercent = getCenterFillPercent(image);

		if(frameFillPercent < 1){
			System.out.println(centerFillPercent + " / " + frameFillPercent + " = NAN");
			return centerFillPercent;
		}
		// image strength = frame is whiter & center is more colorful
		double imageStrengthSub = centerFillPercent - frameFillPercent; 
		double imageStrengthDivide = ((double)centerFillPercent / (double)frameFillPercent);

		System.out.println(centerFillPercent + " / " + frameFillPercent + " = " + imageStrengthDivide);
		return imageStrengthSub;
	}

	private int getFrameFillPercent(BufferedImage image){
		int[] frameFill = new int[2];
		// get north frame
		for(int i = 0 ; i < FRAME_WIDTH ; i++){
			int[] lineFill = getLineFill(image , 0 , image.getWidth() , i);
			frameFill = addArray(frameFill, lineFill);
		}
		// get south frame
		for(int i = image.getHeight() - FRAME_WIDTH ; i < image.getHeight() ; i++){
			int[] lineFill = getLineFill(image , 0 , image.getWidth() , i);
			frameFill = addArray(frameFill, lineFill);
		}
		//get west and east frame
		for(int i = FRAME_WIDTH ; i < image.getHeight() - FRAME_WIDTH ; i++){
			int[] lineFill = getLineFill(image , 0 , FRAME_WIDTH , i);
			frameFill = addArray(frameFill, lineFill);
			lineFill = getLineFill(image , image.getWidth() - FRAME_WIDTH , image.getWidth() , i);
			frameFill = addArray(frameFill, lineFill);
		}

		int fillPercent = (int)(((float)frameFill[1] / (float)frameFill[0]) * 100 );

		return fillPercent;


	}

	private int getCenterFillPercent(BufferedImage image){
		int[] centerFill = new int[2];
		for(int i = image.getHeight()/8 ; i < image.getHeight() - image.getHeight()/8 ; i++){
			int[] lineFill = getLineFill(image , image.getWidth()/8 , image.getWidth() - image.getWidth()/8 , i);
			centerFill = addArray(centerFill, lineFill);
		}

		int fillPercent = (int)(((float)centerFill[1] / (float)centerFill[0]) * 100);

		return fillPercent;
	}

	private int[] getLineFill(BufferedImage image , int start , int end , int fixed){
		int[] lineFill = new int[2];
		for(int i = start; i < end ; i++){
			try{
				int rgb = image.getRGB(i, fixed);
				lineFill[0]++;
				if(rgb != WHITE)
					lineFill[1]++;
			} catch(Exception e){}
		}
		return lineFill;
	}

	protected int[] getLineRgb(BufferedImage image , int start , int end , int fixed){
		int[] rgbSum = new int[5];
		for(int i = start; i < end ; i++){
			try{
				int rgb = image.getRGB(i, fixed);
				if(rgb == WHITE)
					continue;
				int[] pixelData = getPixelData(rgb);
				//System.out.println("("+ i + "," + fixed + ") pixel data: "+ rgb +" ["+ pixelData[0] + "," + pixelData[1] + "," + pixelData[2] +"]");
				rgbSum[0]++;
				rgbSum[1] += rgb;
				rgbSum[2] += pixelData[0];
				rgbSum[3] += pixelData[1];
				rgbSum[4] += pixelData[2];

			} catch(Exception e){}
		}
		return rgbSum;
	}

	protected int[] getPixelData(int rgb) {

		int pixelData[] = new int[]{
				(rgb >> 16) & 0xff, //red
				(rgb >> 8) & 0xff, //green
				(rgb) & 0xff //blue
		};
		/*
		System.out.println(rgb);
		for(int i = 0 ; i < pixelData.length ; i++){
			System.out.print(pixelData[i] + ",");
		}
		System.out.println();*/

		float[] hsv = new float[3];
		Color.RGBtoHSB(pixelData[0], pixelData[1], pixelData[2], hsv);
		/*		System.out.println("hsv");
		for(int i = 0 ; i < pixelData.length ; i++){
			System.out.print(hsv[i] + ",");
		}
		System.out.println();*/

		return pixelData;
	}


	protected int[] addArray(int[] original , int[] addition){
		for(int i=0 ; i < original.length && i < addition.length ; i++){
			original[i] += addition[i];
		}
		return original;

	}

	protected BufferedImage paintSquare(BufferedImage original , BufferedImage res , int x , int y , Color color){
		for(int i = x ; i <= x+AREA_RADIUS ; i++){
			for(int j = y ; j <= y+AREA_RADIUS ; j++){
				try{
					int rgb = original.getRGB(i, j);
					if(color == null)
						res.setRGB(i, j, rgb);
					else
						res.setRGB(i, j, color.getRGB());

				}catch(Exception e ){}
			}
		}
		return res;
	}

	public int calcDistanceMeansRGB(int[] source , int[] objective){
		int res = 0;
		for(int i = 2 ; i < source.length && i < objective.length ; i++){
			if(objective[1] == 0)
				return -1;
			res += Math.abs(source[i] - objective[i]);
		}
		return res;
	}

	protected BufferedImage getImageAfterOutline(Image image , BufferedImage originalImage){
		BufferedImage image2 = (BufferedImage) image;
		BufferedImage res = new BufferedImage(originalImage.getWidth(), originalImage.getHeight() , originalImage.getType());

		for(int i =0 ; i < res.getWidth() ; i++){
			for(int j = 0; j < res.getHeight() ; j++){

				res.setRGB(i, j, -1);
			}
		}

		for(int i =0 ; i < res.getWidth() ; i = i + AREA_RADIUS){
			for(int j = 0; j < res.getHeight() ; j = j + AREA_RADIUS){
				if(isRelevantArea(image2, i, j)){
					res = paintNeighbourhoud(originalImage , res , i , j);
					//	res.setRGB(i, j, -1);
				}
			}
		}
		return res;
	}

	private boolean isRelevantArea(BufferedImage image , int x , int y){
		double numNeighbours = 0 , numColoured = 0;
		for(int i = x-AREA_RADIUS ; i <= x+AREA_RADIUS ; i++){
			for(int j = y-AREA_RADIUS ; j <= y+AREA_RADIUS ; j++){
				try{
					//		System.out.println(image.getRGB(i, j));
					if(image.getRGB(i, j) != -1)
						numColoured++;
					numNeighbours++;
				}catch(Exception e ){
				}
			}
		}
		return numColoured / numNeighbours > RELEVANT_PERCENTAGE;
	}

	private BufferedImage paintNeighbourhoud(BufferedImage original , BufferedImage res , int x , int y){
		for(int i = x-AREA_RADIUS ; i <= x+AREA_RADIUS ; i++){
			for(int j = y-AREA_RADIUS ; j <= y+AREA_RADIUS ; j++){
				try{
					int rgb = original.getRGB(i, j);
					res.setRGB(i, j, rgb);
				}catch(Exception e ){}
			}
		}
		return res;
	}
	
	
	protected int[] getMeansFromSum(int[] sums){
		if(sums[0] == 0)
			return sums;
		int[] means = {sums[0], sums[1] / sums[0] , sums[2] / sums[0] ,
				sums[3] / sums[0] , sums[4] / sums[0]};
		return means;
	}
}
