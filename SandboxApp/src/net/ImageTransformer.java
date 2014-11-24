package net;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class ImageTransformer {

	
	public static ImageSerializable bitMapToIS(Bitmap bi) { 
		ImageSerializable imageSerializable = new ImageSerializable();
		imageSerializable.width = bi.getWidth(); 
		imageSerializable.height = bi.getHeight(); 
		int[] pixels = new int[bi.getWidth() * bi.getHeight()]; 
		bi.getPixels(pixels, 0, bi.getWidth(), 0, 0, bi.getWidth(), bi.getHeight());//bi.getRGB(0,0,width,height,pixels,0,width); 
		imageSerializable.pixels = pixels;
		return imageSerializable;
	}	
	
	
	public static Bitmap ISToBitMap(ImageSerializable IS){
		Bitmap bi = Bitmap.createBitmap(IS.pixels, IS.width, IS.height, Config.ARGB_4444);
		return bi; 
	} 
}
