
package game;
import javax.swing.*;

public class Main {

	public static void main(String[] args) {    
		int ScreenWidth=360; // set the screen width and height
		int ScreenHeight=640;  //
		
		
		JFrame frame = new JFrame("Phoenix Bird"); //Name of the Window 
		//frame.setVisible(true);  //make window visible   
		frame.setSize(ScreenWidth,ScreenHeight); //Setting the size of the Screen
		frame.setLocationRelativeTo(null); //Positions the Screen in the center of the computer
		frame.setResizable(false);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //When user press x on the window ,it will exit the window
		
		
		GameWindow phoenix =new GameWindow(); 
		frame.add(phoenix);  
		frame.pack();
		phoenix.requestFocus(); 
		frame.setVisible(true);
		
				
		// TODO Auto-generated method stub

	}

}
