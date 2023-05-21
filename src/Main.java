import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("ファイルパスを指定してください");
			System.exit(0);
		}
		File file = new File(args[0]);
		if(!file.exists()) {
			System.out.println("ファイルが存在しません");
			System.exit(0);
		}
		
		BufferedImage original = readImage(file);
		BufferedImage resizeImage = resizeImage(original, 70, 70);
		BufferedImage greyScaleImage = grayScaleImage(resizeImage);
		char[][] consoleImage = fromImageToConsoleImage(greyScaleImage);
		printConsoleImage(consoleImage);
		
	}
	
	public static BufferedImage resizeImage(BufferedImage original,int destWidth,int destHeight) {
		// 元ファイルの縦横サイズ(pixel)を取得
		double originalWidth  = (double)original.getWidth();
		double originalHeight = (double)original.getHeight();

		// 縦横の比率から、scaleを決める		
		double widthScale = (double) destWidth / (double) originalWidth;
		double heightScale = (double) destHeight / (double) originalHeight;
		double scale = widthScale < heightScale ? widthScale : heightScale;
					
					
		// 縦横比を保ちリサイズ後の画像サイズを決定
		int width = (int)(originalWidth * scale);
		int height = (int)(originalHeight * scale);
					
		BufferedImage resizeImage = new BufferedImage(width, height, original.getType());
					
		Graphics2D g2d = resizeImage.createGraphics();
		g2d.drawImage(original, 0, 0, width, height, null);
		g2d.dispose();
		return resizeImage;
	}
	
	
	public static BufferedImage grayScaleImage(BufferedImage original) {
		int w = original.getWidth();
		int h = original.getHeight();
		
		BufferedImage grayScaleImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g2d = grayScaleImage.createGraphics();
		g2d.drawImage(original, 0, 0, w, h, null);
		return grayScaleImage;
	}
	
	
	public static BufferedImage readImage(File file) {
		BufferedImage image;
		try {
			image = ImageIO.read(file);
			//ImageIOは画像ファイル以外を読み込むとnullになる
			if(image == null) {
				System.out.println("画像ファイルではありません");
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
			image = new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
		}
		return image;
	}
	
	
	public static void writeImage(BufferedImage image, String ex, String filePath) {
		try {
			ImageIO.write(image, ex, new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static char[][] fromImageToConsoleImage(BufferedImage image) {
		int width  = image.getWidth();
		int height = image.getHeight();
		char[][] consoleImage = new char[height][width];
		char[] consoleImageFont = new char[] {'.', ',', '-', '~', ':', ';', '=', '!', '*', '#', '$', '@'};
		
		for(int Y = 0; Y < height; Y++) {
			for(int X = 0; X < width; X++) {
				Color color = new Color(image.getRGB(X, Y));
				 
				double GreyScaleColor = (color.getRed() + color.getGreen() + color.getBlue()) / 3; 
				consoleImage[Y][X] = consoleImageFont[(int)(GreyScaleColor / 21.25)];
			}
		}
 		
		return consoleImage;
	}
	
	public static void printConsoleImage(char[][] consoleImage) {
		for(int Y = 0; Y < consoleImage.length; Y++) {
			for(int X = 0; X < consoleImage[Y].length; X++) {
				System.out.print(consoleImage[Y][X] + " ");
			}
			System.out.print("\n");
		}
	}
}






















