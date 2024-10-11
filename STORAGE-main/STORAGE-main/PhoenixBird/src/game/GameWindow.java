package game; 
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import javax.swing.*;

import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.File;

//
//GameWindow inherits JPanel 
//Implementing Action listener for movements in game and KeyListener for Key movements by player
public class GameWindow extends JPanel implements ActionListener,KeyListener{
	int ScreenWidth = 360;  //Attributes of the GameWindow aka Game Screen the width and height
	int ScreenHeight= 640;	//
	
	Image Background;  //Declaring Images that used to 
	Image phoenix; 
	// new characters
	Image birdA;
	Image birdB;
	Image birdC;
	Image birdD;
	 Image Volcano2;
	
	
	//will be used to make game
	Image NorthPole;
	Image SouthPole;
	Image Ground;
	Image GameOverBg;
	Image PauseGame;
	Image Play;
	Image CoverPage;
	
	
	int phoenix1=ScreenWidth/8; //Bird  position x
	int phoenix2=ScreenHeight/2; //y
	int PWidth=94; //Bird size attributes
	int PHeight=94;
	boolean GamePaused=false; //Boolean to Check if Game is Paused
	boolean GameStart=false;
	Timer FlashText;
	boolean On = true;
	boolean speedup= false;
	// Gambling with my Life 
	boolean ChooseChar=true;
	Image character;


	
	class Phoenix{
		//Storing attributes for the bird
		int A= phoenix1;
		int B=phoenix2;
		
		int Width=PWidth;
		int Height=PHeight;
		Image pic; //Image of the bird phoenix
		
		Phoenix(Image pic){   //Constructor which takes in the picture of the bird
			this.pic=pic;
			
		}
	}
		//Characteristics of our poles
		int PoleRT=ScreenWidth; //Right Side Pole (Top of Right Pole)
		int PoleRB=0; //Pole starts from the top of screen (Bottom of right Pole)
		int PoleLength=400; //Size and dimensions of the Poles
		int PoleWidth=60;
		
		//Class for the Poles
		  class Poles{
			int T=PoleRT;
			int B=PoleRB;
			int width=PoleWidth;
			int height=PoleLength;
			Image pic;
			boolean skipped =false; //Check if the bird has passed a pole so it can increment the Points
			Poles(Image pic){ //Constructor in which we pass our pole image
				this.pic=pic;
					
			}
			
		}
	
	 
	Phoenix bird; //Create a field for our bird class
	Timer gameLoop; 
	int Speed=0; //Birds initial speed,starts at 0
	
	int SpeedX=-10; 
							//Speed at which bird moves vertically
	
	int gravity= 1; //The rate at which the bird falls 
	Timer PoleTimer;  //rate at which the poles appear
	
	ArrayList<Poles>poles;  //Array List for our poles
	Random random=new Random();
	 boolean GameOver = false; //Checks if the game is over(Weather bird hit pole or fell)
	 double Points =0.00;
	
	
	 
	
	GameWindow(){
		setPreferredSize(new Dimension (ScreenWidth,ScreenHeight));  
		setBackground(Color.blue); //testing background 
		setFocusable(true); //Make sure to take in our key events
		addKeyListener(this); //Make sure we check our key functions
		try {
		//Loading images from the computer
			
		Background = new ImageIcon(getClass().getResource("Sunny background.jpg")).getImage();
		phoenix = new ImageIcon(getClass().getResource("Flappy-Bird-Pixel-Art-Transparent-Background-PNG.png")).getImage();
			//new characters
			birdA = new ImageIcon(getClass().getResource("NewMonster.png")).getImage();
			birdB = new ImageIcon(getClass().getResource("Eggshell.png")).getImage();
			birdC = new ImageIcon(getClass().getResource("Bobby2.png")).getImage();
			birdD = new ImageIcon(getClass().getResource("redbird.png")).getImage();

		// Lets gooooooooo its time to Ntsumbedzeni
		Volcano2 = new ImageIcon(getClass().getResource("Volcano2.jpg")).getImage();
		NorthPole=new ImageIcon(getClass().getResource("Upsidedown.png")).getImage();
		SouthPole=new ImageIcon(getClass().getResource("flappy-bird-pipe-png-7.png")).getImage();
		Ground = new ImageIcon(getClass().getResource("base.png")).getImage();
		GameOverBg = new ImageIcon(getClass().getResource("GameOverImgFinished.jpg")).getImage();
		PauseGame = new ImageIcon(getClass().getResource("PauseIMG.jpeg")).getImage();
		Play=new  ImageIcon(getClass().getResource("PlayButton.png")).getImage();
		CoverPage =new ImageIcon(getClass().getResource("Wallpaper Phoenix2.PNG")).getImage();
		character = phoenix;

		bird = new Phoenix(character); //Creating bird Object
		poles=new ArrayList<Poles>(); //Array list of pipes,every second a pipe is added to the list

		int framerate=1500;
		if(Points%40==0 && Points<=150){
			framerate-=50;
		}

		PoleTimer =new Timer(framerate,new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent e) {
				poleposition(); //call the function poleposition every second to place new poles in the game
				}
		});
		
		PoleTimer.start();
		gameLoop= new Timer(60,this); //The frame rate
		gameLoop.start(); //Game starts running/drawing the frames rapidly
		PlayMusic();
		
		}
		catch( UnsupportedAudioFileException uae){
			System.out.println("The music dude");
		}
		catch(IOException ioe){
			System.out.println("The music dude");

		}
		catch(LineUnavailableException loe){
			System.out.println("The music dude");
		}
		catch(NullPointerException npe){
			System.out.println("THe pictures dude");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		new Thread(()->{
			try {
				PlayMusic();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		
		
		FlashText =new Timer(500,e ->{
			On=!On;
			repaint();
		});
		FlashText.start();
		}
	
			
	
	public void poleposition() { 
		//Function is going to allocate positions for our pipes,randomly
		int randomNP=(int)(PoleRB-PoleLength/4-Math.random()*(PoleLength/2)); //Random shift upwards 
		
		int path= ScreenHeight/2; //Path for the bird to fly through
		if(Points>=50){
			path= ScreenHeight/3;
		}else{}


		
        Poles NorthP= new Poles(NorthPole); //Pole at the Top
        NorthP.B=randomNP;
        
        Poles SouthP=new Poles(SouthPole);
        SouthP.B=NorthP.B+PoleLength +path;
        
        poles.add(NorthP); //add it to list
        poles.add(SouthP);
        
	}
		
	
	 
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g); //super calls to the Parent class Jpanel
		draw(g);
		
	} 
	//Function to draw our components of the game
	public void draw(Graphics g) { 
		super.paintComponent(g);
		//System.out.println("running");//testing,show game is running
		
		if(ChooseChar){
			//g.drawImage(Background,0,0,ScreenWidth,ScreenHeight,null);
			g.drawImage(Volcano2,0,0,ScreenWidth,ScreenHeight,null);

			g.setColor(Color.white);
			g.setFont(new Font("Arial",Font.PLAIN,32));
			g.drawString("Choose a character :",40,100);
			g.drawString("1 <Phoenix>",50,200);
			g.drawImage(phoenix,200,100 , bird.Width,bird.Height,null);
			g.drawString("2 <Monster>",50,250);
			g.drawImage(birdA,200,190 , bird.Width,80,null);
			g.drawString("3 <Pinky>",50,300);
			g.drawImage(birdB,200,250 , bird.Width,bird.Height,null);
			g.drawString("4 <Dragon>",50,350);
			g.drawImage(birdC,200,300 , bird.Width,bird.Height,null);
			g.drawString("5 <Fly>",50,400);
			g.drawImage(birdD,180,370 , 50,50,null);


		}else{


		if(!GameStart) {
			
			g.drawImage(CoverPage,0,0,ScreenWidth,ScreenHeight,null);
			g.setColor(Color.white);
			g.setFont(new Font("Arial Bold",Font.PLAIN,20));
			if(On) {
			g.drawString("Press Enter to Start",20,625);
  			}

			
		}else {
				
		
		//Create the Background image
		g.drawImage(Background,0,0,ScreenWidth,ScreenHeight,null);
		//Create Bird image
		//g.drawImage(bird.pic, bird.A, bird.B+gravity, bird.Width,bird.Height,null);
		g.drawImage(character, bird.A, bird.B+gravity, bird.Width,bird.Height,null);
		//for loop that will draw poles in the game
		for(int i=0;i<poles.size();i++) {
			Poles pole=poles.get(i);
			g.drawImage(NorthPole,pole.T,pole.B,pole.width,pole.height,null);
			//g.drawImage(SouthPole,pole.B,pole.T,pole.width,pole.height,null);
			
		}
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.PLAIN,32));
		if (GameOver) {
			g.drawString("Total Score : "+ String.valueOf((int )Points),10,35); //Display the total score of the player when GameOver is True
			g.drawImage(GameOverBg, 100, 250, 160,140,null); //Display Game Over Image
			g.setFont(new Font("Arial",Font.PLAIN,15));
			g.drawString("Press Space to Play",110,405);
		}
		if(GamePaused) {
			g.drawImage(PauseGame,100,25,160,140,null);
			g.setFont(new Font("Arial",Font.PLAIN,20));
			g.drawString("Press R to Resume ",20,600);
		}else {
			g.drawString(String.valueOf((int )Points),10,35);  //Else keep printing the Players points
			g.setFont(new Font("Arial",Font.PLAIN,15));
			g.drawString("Press P to Pause",20,600);
			//g.setFont(new Font("Arial",Font.PLAIN,15));
			//g.drawString("Press R to Resume ",20,620);
			
		}
		}
	}
		//repaint();
	}
	
	public void move() {
		//This function will be responsible for the movements in the game
		Speed+=gravity; //Bird is continuously under the influence of gravity[always falling]
		bird.B+=Speed;
		
		bird.B=Math.max(bird.B,0);	//Stops bird from going above the Screen
		//for loop which will generate our poles
		for(int i=0;i<poles.size();i++) {
			Poles pole=poles.get(i);
			pole.T+=SpeedX;
			
			if(EndGame(bird,pole)) { //If bird hits pole
				GameOver=true; //then GameOver == True
			}
			if(!pole.skipped && bird.A > pole.T + pole.width) { 
				pole.skipped=true;
				Points += 1; //Add points as the bird passes each pole
			}
			if(pole.T+pole.width<0) {
				poles.remove(i);
			}
		}
		
		if(Points >= 20 && !speedup) {
			SpeedX-=8;
			speedup =true;
		}
		if(Points >= 40 && !speedup) {
			SpeedX-=8;
			speedup =true;
		}
		if(Points >= 60 && !speedup) {
			SpeedX-=8;
			speedup =true;
		}if(Points >= 80 && !speedup) {
			SpeedX-=8;
			speedup =true;
		}
		
	
		
	
		
		if (bird.B>ScreenHeight) {
			GameOver = true;
		}

		
	}
	//The actions that are performed every frame rate
	public void  actionPerformed(ActionEvent e) {
		if(!ChooseChar){
		move();
		repaint();
		if(GameOver) { 
			PoleTimer.stop(); //Stop generating pipes if game is over
			gameLoop.stop(); //Stop Game when game over is true
			
		}
	
	}
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
    public void keyPressed(KeyEvent e) {
		if(ChooseChar){
			if(e.getKeyCode()==KeyEvent.VK_1){
				character= phoenix;
				ChooseChar =false;
			}else if(e.getKeyCode() == KeyEvent.VK_2){
				character=birdA;
				ChooseChar= false;
			}else if(e.getKeyCode()==KeyEvent.VK_3){
				character = birdB;
				ChooseChar = false;
			}else if(e.getKeyCode()==KeyEvent.VK_4){
				character = birdC;
				ChooseChar = false;
			}else if(e.getKeyCode()==KeyEvent.VK_5){
				character = birdD;
				ChooseChar = false;
			}

		}else{

		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			GameStart=true;
			gameLoop.start();
			PoleTimer.start();
			
		}else {
		//Function will monitor the Keys that are pressed by player
    	if(e.getKeyCode()==KeyEvent.VK_SPACE){ //if player presses space
    		Speed=-9;					//bird will move upwards
    		if (GameOver) { //If GameOver is true ,Reset the game variables
    			bird.B=phoenix2;
    			Speed =0;
    			poles.clear();
    			Points =0.00;
    			GameOver=false;
    			gameLoop.start();
    			PoleTimer.start();
    		}
    	}
    		if(e.getKeyCode()==KeyEvent.VK_P) {
    			if(!GameOver) {
    				gameLoop.stop();
    				PoleTimer.stop();
    				GamePaused=true;
    				
    			}else {
    				gameLoop.start();
    				PoleTimer.stop();
    				GamePaused = false;
    			}
    		}
    		
    		if(e.getKeyCode()==KeyEvent.VK_R) {
    			if(!GameOver) {
    				gameLoop.start();
    				PoleTimer.start();
    				GamePaused=false;
    			}
    		}
		}
	}
}
    		
    		
 
    public void keyReleased(KeyEvent e) {
    	
    }
   

    public boolean EndGame(Phoenix tim,Poles pipe) {
    	int buffer =20;
    	//Monitor  if the bird has collided with the poles
    	int birdLeft=tim.A+buffer;
    	int birdRight =tim.A+tim.Width-buffer;
    	int birdTop=tim.B+buffer;
    	int birdBottom=tim.B+tim.Height - buffer;
    	
    	
    	
    	
    	return birdLeft<pipe.T+pipe.width &&
    		   birdRight > pipe.T && 
    			birdTop<pipe.B+pipe.height &&
    			birdBottom >pipe.B;
    	
    }
    
    public  void  PlayMusic() throws UnsupportedAudioFileException,IOException,LineUnavailableException{
    	//Scanner scanner = new Scanner(System.in);
    	File file = new File("../pixel-fight-8-bit-arcade-music-background-music-for-video-208775.wav");
    	AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
    	Clip clip = AudioSystem.getClip();
    	clip.open(audioStream);
    	clip.loop(Clip.LOOP_CONTINUOUSLY);
    	clip.start();
    	//System.out.println("music");
    }
    	
    }







