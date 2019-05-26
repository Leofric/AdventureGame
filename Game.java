package AdventureGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;
import java.lang.Runnable;
import java.lang.Thread;
import javax.swing.JFrame;
import javax.imageio.ImageIO;

public class Game extends JFrame implements Runnable {
	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
	private BufferedImage testImage;
	
	public Game(){
		//Program shutdown on exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//set position and size of the frame
		setBounds(0, 0, 1000, 800);
		
		//centralize frame 
		setLocationRelativeTo(null);
		
		//Add graphics component
		add(canvas);
		
		//visibility
		setVisible(true);
		
		//create object for buffer strategy
		canvas.createBufferStrategy(3);
		
		renderer = new RenderHandler(getWidth(), getHeight());
		
		testImage = loadImage("dark-blue.jpg"); //replace with image file. Might need to import into eclipse first
	}
	
	public void update(){
		
	}
	
	private BufferedImage loadImage(String path){
		try{
			BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
			
			return loadedImage; 
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}

	public void render(){
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);
		
		renderer.renderImage(testImage, 0, 0);
		renderer.render(graphics);
		
		graphics.dispose();
		bufferStrategy.show();
	}
	
	public void run(){
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		int i = 0;
		int x = 0;
		
		long lastTime = System.nanoTime();
		double nanoSecondConversion = 1000000000.0 / 60; //60 frames/s
		double changeInSeconds = 0;
		
		while(true){
			Long now = System.nanoTime();
			
			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			while(changeInSeconds >= 1){
				update();
				changeInSeconds = 0;
			}
			
			render();
			lastTime = now;
		}
	}
	
	public static void main(String [] args){
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}
	
}
