import java.awt.image.*;
import java.io.File;

public class Main {
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("add file path");
			System.exit(0);
		}
		File file = new File(args[0]);
		if(!file.exists()) {
			System.out.println("not exist file");
			System.exit(0);
		}
		
		ConsoleImage ci = new ConsoleImage();
		BufferedImage original = ci.readImage(file);
		BufferedImage resizeImage = ci.resizeImage(original, 50, 50);
		BufferedImage greyScaleImage = ci.grayScaleImage(resizeImage);
		char[][] consoleImage = ci.fromImageToConsoleImage(greyScaleImage);
		
		System.out.println(ci.printConsoleImage(consoleImage));
		
	}
}






















