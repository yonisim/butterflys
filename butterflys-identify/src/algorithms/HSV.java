package algorithms;

/**
 *
 * @author Shlomit Rozen(Gilboa) Haim Gilboa, Mayyan Simkins
 */
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class HSV {
    
    protected Vector<Float> getHSV(Vector<Integer> RGB) {
    	Vector <Float> HSV = new Vector <Float>();
    	try {
            float[] hsv = new float[3];
            int[] rgb;
            for (int i = 0; i < RGB.size(); i = i + 3)
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

    protected Vector<Integer> getRGB(String imgPath){
        try {
            return getRGB(ImageIO.read(new File(imgPath)));
        } catch (IOException ex) {
            Logger.getLogger(HSV.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    protected Vector<Integer> getRGB(BufferedImage img){
        try{
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
    
    protected float[] getHSVMean(Vector<Float> HSV){
        float[] WHITE = new float[3];
        Color.RGBtoHSB(255, 255, 255, WHITE);
        float[] ans = {0,0,0};
        int divider = HSV.size() / 3;
        double H = 0;
        double S = 0;
        double V = 0;
        
        for (int i = 0; i < HSV.size(); i = i + 3) {
            H = HSV.elementAt((i));
            S = HSV.elementAt((i+1));
            V = HSV.elementAt((i+2));
            if (H == WHITE[0] && S == WHITE[1] && V == WHITE[2])
            {
                continue;
            }
            else{
                ans[0] = ans[0] + (float)H;
                ans[1] = ans[1] + (float)S;
                ans[2] = ans[2] + (float)V;
            }
        }
        ans[0] = ans[0] / divider;
        ans[1] = ans[1] / divider;
        ans[2] = ans[2] / divider;
        return ans;
    }
    
    protected float getHSVnorm2(Vector<Float> HSV){
        float[] WHITE = new float[3];
        Color.RGBtoHSB(255, 255, 255, WHITE);
        double sum = 0;
        double H = 0;
        double S = 0;
        double V = 0;
        int divider = HSV.size() / 3;
        for (int i = 0; i < HSV.size(); i = i + 3) {
            H = HSV.elementAt((i));
            S = HSV.elementAt((i+1));
            V = HSV.elementAt((i+2));
            if (H == WHITE[0] && S == WHITE[1] && V == WHITE[2])
            {
                continue;
            }
            else
            {
                sum = sum + Math.sqrt(Math.pow(H, 2) + Math.pow(S, 2) + Math.pow(V, 2));
            }
        }
        return (float)sum / divider;
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
    
    protected float[] getMaxCountFromHistogram(Vector <Integer> RGB){
        int[][][] ch = new int[8][8][8];
        int R,G,B;
        for (int i = 0; i < RGB.size(); i = i+3)
        {
            R = RGB.elementAt(i);
            G = RGB.elementAt(i+1);
            B = RGB.elementAt(i+2);
            if (R == 255 & G == 255 && B == 255)
            {
                continue;
            }
            else
            {
                ch[RGB.elementAt(i) / 32][RGB.elementAt(i+1) / 32][RGB.elementAt(i+2) / 32]++;
            }
        }

        int maxR, maxG, maxB, maxCount;
        maxCount = maxR = maxG = maxB = -1;
        for(int i = 0; i < ch.length; i++)
        {
            for(int j = 0; j < ch[i].length; j++)
            {
                for(int p = 0; p < ch[i][j].length; p++)
                {
                    if (ch[i][j][p] > maxCount)
                    {
                        maxCount = ch[i][j][p];
                        maxR = i;
                        maxG = j;
                        maxB = p;
                    }
                }
            }
        }
        float[] ans = new float[3];
        Color.RGBtoHSB((maxR * 32) + 16,(maxG * 32) + 16, (maxB * 32) + 16, ans);
        return ans;
    }
}