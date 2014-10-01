package detectors;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;



public class SimpleDetector extends RGBDetector {

	public void detectButterfly(BufferedImage image){
		BufferedImage image2 = analyzeImage(image);
		BufferedImage image2_5 = analyzeImage2(image2);  
		BufferedImage image3 = getImageAfterOutline(image2, image);
		BufferedImage image4 = getImageAfterOutline(image2_5, image);


		checkImageStrength(image2_5);
		checkImageStrength(image3);
		checkImageStrength(image4);
	}

	
	private BufferedImage analyzeImage(BufferedImage image){
		BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		List<Integer> rgbList = getBordersRgb(image);

		for(int i =0 ; i < image.getWidth() ; i++){
			for(int j = 0; j < image.getHeight() ; j++){
				int rgb = image.getRGB(i, j);
				int[] pixelData = getPixelData(rgb);

				image2.setRGB(i, j, rgb);
				for(Integer integer : rgbList){
					int[] pixelData2 = getPixelData(integer);
					if( pixelData[2] == pixelData2[2] )
						image2.setRGB(i, j, WHITE);
				}
			}
		}

		return image2;
	}

	private List<Integer> getBordersRgb(BufferedImage image){
		List<Integer> rgbList = new LinkedList<>();
		getBordersRgb(rgbList, image, 1, image.getWidth(), 0);
		getBordersRgb(rgbList, image, 1, image.getWidth(), image.getHeight()-1);
		getBordersRgb(rgbList, image, 2, image.getHeight(), 0);
		getBordersRgb(rgbList, image, 2, image.getHeight(), image.getWidth()-1);
		return rgbList;
	}

	private void getBordersRgb(List<Integer> rgbList , BufferedImage image , int direction , int size , int fixed){
		for(int i = 0; i < size ; i++){
			int rgb;
			if(direction == 1)
				rgb = image.getRGB(i, fixed);
			else
				rgb = image.getRGB(fixed, i);

			int count = 0;
			for(Integer integer : rgbList){
				if(rgb == integer){
					break;
				}
				count++;
			}
			if(count == rgbList.size())
				rgbList.add(rgb);
		}
	}
}
