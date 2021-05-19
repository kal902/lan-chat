import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LeftPanel extends JPanel implements ActionListener{
	private JTextArea ta;
	private JButton btnstop;
	public LeftPanel(){
		setLayout(new BorderLayout());
		ta = new JTextArea();
		ta.setEditable(false);

		btnstop = new JButton("stop");
		btnstop.addActionListener(this);


		add(ta,BorderLayout.CENTER);
		add(btnstop,BorderLayout.SOUTH);
	}
	@Override
	public void actionPerformed(ActionEvent event){

	}
}