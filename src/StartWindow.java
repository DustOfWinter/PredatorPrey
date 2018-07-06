import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class StartWindow extends JFrame
{
	JPanel panel;
	JPanel preyPanel;
	JPanel predatorPanel;
	JButton start;
	JTextField prey1;
	JTextField prey2;
	JTextField prey3;
	JTextField prey4;
	JTextField predator1;
	JTextField predator2;
	JLabel prey1Label, prey2Label, prey3Label, prey4Label, predator1Label, predator2Label;
	
	public StartWindow()
	{
		super("Predator and Prey Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		buildPanel();
		pack();
		
		setVisible(true);
	}
	public void buildPanel()
	{
		panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		
		preyPanel = new JPanel();
		preyPanel.setLayout(new GridLayout(2,4));
		preyPanel.setBorder(BorderFactory.createTitledBorder("Prey"));
		
		predatorPanel = new JPanel();
		predatorPanel.setLayout(new GridLayout(2,2));
		predatorPanel.setBorder(BorderFactory.createTitledBorder("Predators"));
		
		start = new JButton("Start!");
		start.addActionListener(new StartListener());
		
		prey1 = new JTextField();
		prey2 = new JTextField();
		prey3 = new JTextField();
		prey4 = new JTextField();
		
		predator1 = new JTextField();
		predator2 = new JTextField();
		
		prey1Label = new JLabel("Rabbits:");
		prey2Label = new JLabel("Deers:");
		prey3Label = new JLabel("Squirrels:");
		prey4Label = new JLabel("Raccoons:");
		
		predator1Label = new JLabel("Wolves:");
		predator2Label = new JLabel("Super Raccoons:");
		
		preyPanel.add(prey1Label);
		preyPanel.add(prey1);
		preyPanel.add(prey2Label);
		preyPanel.add(prey2);
		preyPanel.add(prey3Label);
		preyPanel.add(prey3);
		preyPanel.add(prey4Label);
		preyPanel.add(prey4);
		
		predatorPanel.add(predator1Label);
		predatorPanel.add(predator1);
		predatorPanel.add(predator2Label);
		predatorPanel.add(predator2);
		
		panel.add(preyPanel);
		panel.add(predatorPanel);
		panel.add(start);
		
		add(panel);
	}
	private class StartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int rabbits = 0;
			int deer = 0;
			int squirrels = 0;
			int raccoons = 0;
			int wolves = 0;
			int superRaccoons = 0;
			
			try
			{
				rabbits = Integer.parseInt(prey1.getText());
				deer = Integer.parseInt(prey2.getText());
				squirrels = Integer.parseInt(prey3.getText());
				raccoons = Integer.parseInt(prey4.getText());
				wolves = Integer.parseInt(predator1.getText());
				superRaccoons = Integer.parseInt(predator2.getText());
				
				//if counts are successful, create new window for game
				PredatorPrey game = new PredatorPrey(rabbits, deer, squirrels, raccoons, wolves, superRaccoons);
				setVisible(false);
			}
			catch(NumberFormatException a)
			{
				JOptionPane.showMessageDialog(null, "You may only enter whole numbers!");
				prey1.setText("");
				prey2.setText("");
				prey3.setText("");
				prey4.setText("");
				predator1.setText("");
				predator2.setText("");
			}
		}
	}
}