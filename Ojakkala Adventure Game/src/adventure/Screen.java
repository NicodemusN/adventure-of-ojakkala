package adventure;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Screen extends JFrame implements ActionListener{
	//Variables
	int x = 800;
	int y = 600;
	
	Screen(){
	//Frame base structure
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(x, y));
    this.pack();
    this.setLocationRelativeTo(null);
    
    this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}