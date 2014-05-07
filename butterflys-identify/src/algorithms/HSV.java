package algorithms;

/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;
import javax.imageio.ImageIO;

public class HSV {

    protected Vector<Float> getHSV(Vector<Integer> RGB) {
    	Vector <Float> HSV = new Vector <Float>();
    	try {
            float[] hsv = new float[3];
            int[] rgb;
            for (int i = 10; i < RGB.size() - 3; i = i + 3)
            {
                Color.RGBtoHSB(RGB.elementAt(i),RGB.elementAt(i+1),RGB.elementAt(i+2),hsv);
                for (int k = 0; k < hsv.length; k++) {
                        HSV.add(hsv[k]);
                    }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HSV;
    }

    protected Vector<Integer> getRGB(String imgPath)
    {
        try {
            BufferedImage img = ImageIO.read(new File(imgPath));
            Vector <Integer> RGB = new Vector <Integer>();
            int[] rgb = new int[3];
            int pixelIndex = 0;
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    rgb = getPixelData(img, i, j);
                    for (int k = 0; k < rgb.length; k++) {
                        RGB.add(rgb[k]);
                    }
                    pixelIndex++;
                }
            }
            return RGB;
        } catch (Exception e) {
            return null;
        }
    }
    
    private int[] getPixelData(BufferedImage img, int x, int y) {
        int argb = img.getRGB(x, y);

        int rgb[] = new int[]{
            (argb >> 16) & 0xff, //red
            (argb >> 8) & 0xff, //green
            (argb) & 0xff //blue
        };
        return rgb;
    }

    /*public static void main(String Args[]) {
        HSV b = new HSV();
        Vector <Float> bla = b.getHSV("C:\\Temp\\green.jpg");
    }*/
}