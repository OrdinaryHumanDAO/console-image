import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ConsoleImage {
	
	public BufferedImage resizeImage(BufferedImage original,int destWidth,int destHeight) {
		double originalWidth  = (double)original.getWidth();
		double originalHeight = (double)original.getHeight();

		// Determine the image size after resizing while maintaining the aspect ratio	
		double widthScale = (double) destWidth / (double) originalWidth;
		double heightScale = (double) destHeight / (double) originalHeight;
		double scale = widthScale < heightScale ? widthScale : heightScale;		
		int width = (int)(originalWidth * scale);
		int height = (int)(originalHeight * scale);
					
		BufferedImage resizeImage = new BufferedImage(width, height, original.getType());
					
		Graphics2D g2d = resizeImage.createGraphics();
		g2d.drawImage(original, 0, 0, width, height, null);
		g2d.dispose();
		return resizeImage;
	}
	
	
	public BufferedImage grayScaleImage(BufferedImage original) {
		int w = original.getWidth();
		int h = original.getHeight();
		
		BufferedImage grayScaleImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g2d = grayScaleImage.createGraphics();
		g2d.drawImage(original, 0, 0, w, h, null);
		return grayScaleImage;
	}
	
	
	public BufferedImage readImage(File file) {
		BufferedImage image;
		try {
			image = ImageIO.read(file);
			//ImageIO becomes null when reading anything other than an image file
			if(image == null) {
				System.out.println("not image file");
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
			image = new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
		}
		return image;
	}
	
	
	public void writeImage(BufferedImage image, String ex, String filePath) {
		try {
			ImageIO.write(image, ex, new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public char[][] ImageToConsoleImage(BufferedImage image) {
		int width  = image.getWidth();
		int height = image.getHeight();
		char[][] consoleImage = new char[height][width];
		char[] consoleImageFont         = new char[] {' ', '.', ',', '-', '~', ':', ';', '=', '!', '*', '#', '$', '@'};
		
		for(int Y = 0; Y < height; Y++) {
			for(int X = 0; X < width; X++) {
				Color color = new Color(image.getRGB(X, Y));
				 
				double GreyScaleColor = (color.getRed() + color.getGreen() + color.getBlue()) / 3; 
				consoleImage[Y][X] = consoleImageFont[(int)(GreyScaleColor / 21.25)];
			}
		}
 		
		return consoleImage;
	}
	
	public String printConsoleImage(char[][] consoleImage) {
		String result = "";
		
		for(int Y = 0; Y < consoleImage.length; Y++) {
			for(int X = 0; X < consoleImage[Y].length; X++) {
				result += consoleImage[Y][X] + " ";
			}
			result += "\n";
		}
		
		return result;
	}
}
