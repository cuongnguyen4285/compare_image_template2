package utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;
import net.serenitybdd.core.pages.WebElementFacade;
import com.assertthat.selenium_shutterbug.core.Shutterbug;

public class ShutterBugImageUtil {

    public static BufferedImage takeScreenshot(WebDriver driver, String outputFilePath, String imageName) {
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        String outputFilePath2 = outputFilePath + imageName + ".png";

        FileUtil.delete(outputFilePath2);
        Shutterbug.shootPage(driver, true).withName(imageName).save(outputFilePath);
        resizeImage(outputFilePath2, width, height);
        return getBufferImage(outputFilePath2);
    }

    public static BufferedImage takeElementScreenshot(WebDriver driver, String outputFilePath, WebElementFacade webElementFacade, String imageName) {
        int width = webElementFacade.getSize().getWidth();
        int height = webElementFacade.getSize().getHeight();
        String outputFilePath2 = outputFilePath + imageName + ".png";
        FileUtil.delete(outputFilePath2);
        Shutterbug.shootElement(driver, webElementFacade, true).withName(imageName).save(outputFilePath);

        resizeImage(outputFilePath2, width, height);
        return getBufferImage(outputFilePath2);
    }

    public static BufferedImage getBufferImage(WebDriver driver, WebElementFacade webElementFacade) {
        return Shutterbug.shootElement(driver, webElementFacade, true).getImage();
    }

    public static BufferedImage getBufferImage(String inputFilePath) {
        // reads input image
        BufferedImage outputImage = null;
        File inputFile = new File(inputFilePath);

        try {
            outputImage = ImageIO.read(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputImage;
    }

    public static BufferedImage getResizeBufferImage(String inputFilePath, int scaledWidth,
                                                     int scaledHeight) {
        // reads input image
        BufferedImage inputImage = null;
        BufferedImage outputImage = null;

        File inputFile = new File(inputFilePath);

        try {
            inputImage = ImageIO.read(inputFile);
            // creates output image
            outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        return outputImage;
    }

    public static BufferedImage getResizeBufferImage(BufferedImage inputBufferedImage, int scaledWidth,
                                                     int scaledHeight) {
        BufferedImage outputImage = null;

        // creates output image
        outputImage = new BufferedImage(scaledWidth, scaledHeight, inputBufferedImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputBufferedImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        return outputImage;
    }

    public static void resizeImage(String inputFilePath, int scaledWidth, int scaledHeight) {
        // reads input image
        BufferedImage inputImage = null;
        BufferedImage outputImage = null;

        File inputFile = new File(inputFilePath);

        try {
            inputImage = ImageIO.read(inputFile);
            // creates output image
            outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);
        // writes to output file
        try {
            ImageIO.write(outputImage, formatName, new File(inputFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
