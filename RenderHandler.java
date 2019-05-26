package AdventureGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {
	private BufferedImage view;
	private int[] pixels;
	
	public RenderHandler(int width, int height){
		//Create BufferedImage that will represent the view
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//Create an array for pixels
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
	}
	
	//render array of pixels to the screen
	public void render(Graphics graphics){
		for(int i=0; i<pixels.length; i++){
			pixels[i] = (int)(Math.random()*0xFFFFFF);
		}
		
		graphics.drawImage(view,  0, 0, view.getWidth(), view.getHeight(), null);
	}
	
	//Render image to array of pixels
	public void renderImage(BufferedImage image, int xPosition, int yPosition){
		int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				pixels[(x + xPosition) + (y + yPosition) * view.getWidth()] = imagePixels[x + y * image.getWidth()];
			}
		}
	}
}
