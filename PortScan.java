import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class PortScan{
	JFrame frame;

	public PortScan(){
		frame = new JFrame("util");
		JPanel panel = new JPanel(new BorderLayout());
		frame.setContentPane(panel);
		

		panel.add(new LeftPanel(),BorderLayout.CENTER);
		panel.add(new RightPanel(),BorderLayout.EAST);

		frame.setSize(900,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public static void main(String args[]){
		new PortScan();
	}
}