package utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;
import java.util.Collections;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCVComparisonUtil {

    public static float[] getTemplateImageCoord(String sourcePath, String templatePath) {
        Mat source = null;
        Mat template = null;

        source = Imgcodecs.imread(sourcePath);
        template = Imgcodecs.imread(templatePath);

        Mat outputImage = new Mat();
        int machMethod = Imgproc.TM_CCOEFF_NORMED;
        //Template matching method
        Imgproc.matchTemplate(source, template, outputImage, machMethod);

        Core.MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        Point matchLoc = mmr.maxLoc;

        //Draw rectangle on result image
        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(46, 204, 113), 20);
        Imgcodecs.imwrite("dist/templateImageCoord.jpg", source);
        float[] coords = {(float) matchLoc.x, (float) matchLoc.y, template.cols(), template.rows()};
        System.out.println(Arrays.toString(coords));
        return coords;
    }

    public static boolean matchingImage(String sourcePath, String templatePath) {
        boolean matchImage = true;

        Mat source = null;
        Mat template = null;

        source = Imgcodecs.imread(sourcePath);
        template = Imgcodecs.imread(templatePath);

        Mat outputImage = new Mat();
        int machMethod = Imgproc.TM_CCOEFF_NORMED;
        //Template matching method
        Imgproc.matchTemplate(source, template, outputImage, machMethod);

        Core.MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        Point matchLoc = mmr.maxLoc;

        //Change minVal and maxVal to 0 and 1
        Core.normalize(outputImage, outputImage, 0, 1, Core.NORM_MINMAX, -1, new Mat());
        System.out.println("Min val: " + mmr.minVal + "Max val: " + mmr.maxVal);

        //Draw rectangle on result image
        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(46, 204, 113), 10);
        Imgcodecs.imwrite("src/main/resources/images/matchimage/matchImage1.jpg", source);
        if (mmr.maxVal < 0.9) {
            matchImage = false;
            System.out.println("Image doesn't match");
        }
        return matchImage;
    }

    public static boolean compare_image(BufferedImage img_1, BufferedImage img_2) {
        boolean equalImage = true;

        Mat mat_1 = conv_Mat(img_1);
        Mat mat_2 = conv_Mat(img_2);

        Mat hist_1 = new Mat();
        Mat hist_2 = new Mat();

        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        MatOfInt histSize = new MatOfInt(25);

        Imgproc.calcHist(Collections.singletonList(mat_1), new MatOfInt(0),
                new Mat(), hist_1, histSize, ranges);
        Imgproc.calcHist(Collections.singletonList(mat_2), new MatOfInt(0),
                new Mat(), hist_2, histSize, ranges);

        double similarityDiff = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
        System.out.println("Similarity different: " + similarityDiff);

        if (similarityDiff < 0.95) {
            equalImage = false;
        }
        return equalImage;
    }

    /**
     * Represents an image with 8-bit RGB color components, corresponding
     * to a Windows-style BGR color model) with the colors Blue, Green,
     * and Red stored in 3 bytes.  There is no alpha.  The image has a
     * {@code ComponentColorModel}.
     * When data with non-opaque alpha is stored
     * in an image of this type,
     * the color data must be adjusted to a non-premultiplied form
     * and the alpha discarded,
     * as described in the
     * {@link java.awt.AlphaComposite} documentation.
     */

    private static Mat conv_Mat(BufferedImage img) {
        // covert to 3 bytes BGR
        BufferedImage convertedImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);
        convertedImage.getGraphics().drawImage(img, 0, 0, null);

        byte[] data = ((DataBufferByte) convertedImage.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(convertedImage.getHeight(), convertedImage.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);

        Mat mat1 = new Mat(convertedImage.getHeight(), convertedImage.getWidth(), CvType.CV_8UC3);
        Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2HSV);

        return mat;
    }

}