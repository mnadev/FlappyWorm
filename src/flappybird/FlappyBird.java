package flappybird;

import java.util.ArrayList;

import processing.core.PApplet;
//import processing.core.PGraphics;
import processing.serial.*;

public class FlappyBird extends PApplet {
	
	/**
	 * Class that represents pipe objects
	 * @author mohammednadeem
	 *
	 */
	public class Pipe{
		
		int height;
		int x;
		
		public Pipe() {
			this.height = (int) Math.ceil(Math.random() * 200 + 1);
			this.x = 800;
		}
		
		public void draw() {
			fill(50,205,50);
			rect(x, height + 300, 100, 600 - 300 - height);
			
			fill(50,205,50);
			rect(x, 0, 100, height);
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
	 * This boolean lets the program know that the we are
	 * at the start screen
	 */
	public static boolean startScreen = false;
	
	/**
	 * This boolean lets the program know that the we are
	 * running the actual gameplay.
	 */
	public static boolean gameRun = false;
	
	/**
	 * This boolean states if game is over.
	 */
	public static boolean gameOver = false;
	
	/**
	 * This boolean lets us know if we are at the high scores screen.
	 */
	public boolean seeScores = false;
	
	
	
	/**
	 * This is the worm object that is created and referenced through the gameplay. 
	 */
	public static Worm worm;
	/**
	 * This list holds all the pipes that are currently in scope.
	 */
	public static ArrayList<Pipe> pipes = new ArrayList<Pipe>();
	/**
	 * This list holds all users/players.
	 */
	public static ArrayList<User> users = new ArrayList<User>();
	
	
	
	/** 
	 * This integer keeps track of how long the worm lived.
	 */
	public static int score = 0;
	
	/**
	 * This array holds the start screen menu strings.
	 */
	public static String[] startArr = {"Start Game", "Scores", "Exit Game"};

	/**
	 * This int lets us know which menu item
	 */
	public static int startInd = 0;

	
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
		frameRate(60);
	}

	public void draw() {
		    
		// first draw grass as dark green rectangle
		background(color(164, 255, 51));
	    fill(color(0,100,0));
	    rect(0, 600, 1000, 400);
	    
	    if (gameRun) {
	    		
	    		//draw worm
	    		worm.draw();
	    	
	    		// draw the pipes/obstacles
			if(score % 60 == 0) {
				Pipe p = new Pipe();
				pipes.add(p);
			}
		
			for(Pipe p: pipes) {
	    			p.move();
	    			p.draw();
			}
	    		
			// if worm.y > 800, then worm hits ground, it's not in air and thus game is over
			if(worm.y > 600) {
				System.out.println("1");
				gameOver = true;
				gameRun = false;
    			}
			
	    		// check if worm hit pipe
			
			// height of the pipe is stored in height
			// x value is stored in x. width of pipe is 100
			
			//width of worm is 150, height is 50
	    		for(Pipe p: pipes) {
	    			
	    			//check if worm is in x range of pipe
	    			// it's 150 because that's the max width that can be between pipe and
	    			// worm since worm is 150 wide.
	    			if(worm.x - p.x <= 150 && worm.x - p.x >= 0) {
	    				
	    				// check if worm hit top pipe 
	    				// worm.y must be < p.height
	    				if(worm.y < p.height) {
	    					System.out.println("2");
		    				gameOver = true;
		    				gameRun = false;
	    				}
	    				
	    				// check if worm hit bottom pipe 
	    				// worm.y must be > p.height + 300
	    				
	    				if(worm.y > (p.height + 300)) {
	    					System.out.println("3");
		    				gameOver = true;
		    				gameRun = false;
	    				}
	    			}
	    		}
    	
	    		//increase score
	    		score++;
	
		    // make the worm jump only if the key is pressed.
		    if(keyPressed) {
				worm.jump();
		    } else {
		    		worm.fall();;
		    }
		    
		    // when there are more than 5 pipes, then we remove the first one
		    if(pipes.size() > 5) {
				pipes.remove(0);
		    }
	    } else if (gameOver) {

	    		// if the user hits, then game over
	    		// then we ask the user to 
	    		textSize(32);
	    		text("Press r to reset", 400,400);
	    		
	    		textSize(32);
	    		text("Your score was" + score % 60, 400,450);
	    		if(keyPressed) {
	    			if(key == 'r' || key == 'R') {
	    				score = 0;
			    		gameOver = false;
			    		gameRun = true;
			    		pipes = new ArrayList<Pipe>();
	    			}
	    		}
	    } else if(seeScores) {
	    		
	    } else {
	    		textSize(32);
	    		
	    		// print the menu
	    		// the if statement changes the color of the option we currently have selected.
		    for(int i = 0; i < startArr.length; i++) {
		    		
		    		if(startInd == i) {
		    			fill(0,50,0);
		    		} else {
		    			fill(0,100,0);
		    		}
		    		
		    		text(startArr[i], 400, 400 + i * 50);
		    }
		    
		    // detect if key is pressed
		    if(keyPressed) {
		    		// if they hit enter, then check the current index that is chosen
		    		// and proceed respectively
		    		if(key == ENTER) {
		    			switch(startInd) {
			    			case 0:
			    				gameRun = true;
			    				startScreen = false;
			    				break;
			    			case 1:
			    				seeScores = true;
			    				startScreen = false;
			    				break;
			    			case 2:
			    				System.exit(0);
			    				break;
			    			default:
			    				System.exit(0);
			    				break;
		    			}
		    		}
		    	
		    		// if they hit up, decrease the chosen index
		    		//if they hit down, increase it
		    		// use the modulus operator to loop around the menu
		    		if(key == CODED) {
		    			if(keyCode == UP) {
		    				System.out.println("UP");
		    				startInd = (startInd - 1) % startArr.length;
		    				delay(200);
		    			}
		    			if(keyCode == DOWN) {
		    				System.out.println("UP");
		    				startInd = (startInd + 1) % startArr.length;
		    				delay(200);
		    			}
		    		}
		    }
	    }
	    
	}
}
