package detectors;


import java.awt.image.BufferedImage;


public class ButterflyIdentifier {


	private double maxImageStrength = 0;
	private BufferedImage strongestImage;


	public BufferedImage getFilteredImage(BufferedImage image){

		maxImageStrength = 0;
		strongestImage = image;

		SimpleDetector simpleDetector = new SimpleDetector();
		simpleDetector.detectButterfly(image);
		checkImageStrengthAndSubstitute(simpleDetector);

		RGBDetector rgbDetector = new RGBDetector();
		rgbDetector.detectButterfly(image);
		checkImageStrengthAndSubstitute(rgbDetector);

		HSVDetector hsvDetector = new HSVDetector();
		hsvDetector.detectButterfly(image);
		checkImageStrengthAndSubstitute(hsvDetector);


		strongestImage = rgbDetector.getImageAfterOutline(strongestImage, image);

		return strongestImage;
	}

	private void checkImageStrengthAndSubstitute(Detector detector){
		if(detector.maxImageStrength > maxImageStrength){
			maxImageStrength = detector.maxImageStrength;
			strongestImage = detector.strongestImage;
		}
	}

}
