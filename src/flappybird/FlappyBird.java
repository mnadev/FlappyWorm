package flappybird;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;


public class FlappyBird extends PApplet {
	
	/**
	 * Class that represents pipe objects
	 * @author mohammednadeem
	 *
	 */
	public class Pipe{
		
		int y;
		int x;
		
		public Pipe() {
			this.y = (int) Math.ceil(Math.random() * 200 + 1);
			this.x = 800;
		}
		
		public void draw() {
			fill(50,205,50);
			rect(x, 600 - y, 100, y);
			
			fill(50,205,50);
			rect(x, 0, 100, 600 - 250  - y);
		}
		
		public void move() {
			x -= 5;
		}
	}
	
	/**
	 * Class that represents a worm object.
	 * We can create a worm, draw it and make it jump
	 * and fall.
	 * @author mohammednadeem
	 *
	 */
	private class Worm {
		
		int x;
		int y;
		
		public Worm() {
			this.x = 200;
			this.y = 350;
		}
		
		public void draw() {
			fill(color(165,42,42));
			rect(x, y, 150,50);
	    
			fill(color(0, 0, 0));
			ellipse(x + 120, y + 10, 10, 10);
			
			fill(color(0, 0, 0));
			ellipse(x + 120, y + 30, 10, 10);
		}
		
		public void jump() {
			this.y -= 10;
			
			if(this.y < 0) {
				this.y = 750 + this.y;
			}
			
			this.y = this.y % 750;
		}
		
		public void fall() {
			this.y += 6;
			this.y = this.y % 750;
		}
		
	}
	
	/**
	 * This integer holds the x-coordinate at which the worm is located.
	 */
	public static int x = 200;
	
	/**
	 * This integer holds the y-coordinate of the worm.
	 */
	public static int y = 350;
	
	/**
	 * This boolean lets the program know that the user wants
	 * to start the game
	 */
	public static boolean start = false;
	
	/**
	 * This is the worm object that is created and referenced through the gameplay. 
	 */
	public static Worm worm;
	
	/**
	 * This boolean states if the worm hit an obstacle.
	 */
	public static boolean didHit = false;
	
	/** 
	 * This integer keeps track of how long the worm lived.
	 */
	public static int sum = 0;
	
	/**
	 * This list holds all the pipes that are currently in scope.
	 */
	public static ArrayList<Pipe> pipes = new ArrayList<Pipe>();
	
	public static void main(String[] args) {
		//run applet
		PApplet.main("flappybird.FlappyBird");
	}
	
	public void settings() {
		// set constrained size
		size(1000, 750);
		
		// create worm
		worm = new Worm();
	}
	
	public void setup() {
		
		// create worm
		worm = new Worm();
	}

	public void draw() {
		    
		// first draw grass as dark green rectangle
		background(color(164, 255, 51));
	    fill(color(0,100,0));
	    rect(0, 600, 1000, 400);
	    
	    if(!didHit) {
		    // check if the user wants to start. if not, we are at start screen
		    if(!start) {
			    textSize(32);
			    text("Press any key to start and any key to jump", 50, 450);
		    } else {
		    	
		    		for(Pipe p: pipes) {
		    			if(p.x - worm.x < 100 && worm.x - p.x < 150) {
		    				if(!(worm.y > y &&  worm.y < 250 + y)) {
		    					didHit = true;
		    				}
		    			}
		    		}
		    	
		    		if(worm.y > 600) {
		    			didHit = true;
		    		}
		    		
		    		sum++;
		    		//otherwise draw the worm
		    		worm.draw();
		    		
		    		// draw the pipes/obstacles
				if(sum % 60 == 0) {
					Pipe p = new Pipe();
					pipes.add(p);
				}
				
				for(Pipe p: pipes) {
		    			p.move();
		    			p.draw();
				}
			}
			
		    if(keyPressed) {
	    			start = true;
	    			worm.jump();
		    } else {
		    		worm.fall();;
		    }
		    
	    } else {
	    		int sec = sum/60;
	    		textSize(32);
	    		text("You lasted " + sec + " seconds", 50, 450);
	    }
	    
	    if(pipes.size() > 5) {
	    		pipes.remove(0);
	    }
	}
}
