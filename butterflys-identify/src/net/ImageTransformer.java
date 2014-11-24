package algorithms;

import java.awt.image.BufferedImage;



public class ImageTransformer {

	public static ImageSerializable bufferedImageToIS(BufferedImage bi) { 
		ImageSerializable imageSerializable = new ImageSerializable();
		imageSerializable.width = bi.getWidth(); 
		imageSerializable.height = bi.getHeight(); 
		int[] pixels = new int[bi.getWidth() * bi.getHeight()]; 
		int[] temp = bi.getRGB(0,0, bi.getWidth(), bi.getHeight(), pixels, 0, bi.getWidth()); 
		imageSerializable.pixels = pixels;
		return imageSerializable;
	}

	public static BufferedImage ISToBufferedImage(ImageSerializable IS) { 
		BufferedImage bi = new BufferedImage(IS.width, IS.height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0,0, IS.width, IS.height, IS.pixels, 0, IS.width);
		return bi; 
		
	} 
}
