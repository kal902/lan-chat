import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RightPanel extends JPanel implements ActionListener{
	private JLabel label;
	private JTextField tf;
	private JButton startscan;
	public RightPanel(){

		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);

		setBorder(BorderFactory.createTitledBorder("port scan"));

		tf = new JTextField(25);
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 2;

		add(tf,gc);

		gc.gridx = 1;
		gc.gridy = 3;

		add(startscan,gc);

	}
	@Override
	public void actionPerformed(ActionEvent event){

	}
}