import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class DisplaySize extends JFrame implements ActionListener{
	private DisplayPanel displayPanel;
	public DisplaySize(){
		setTitle("DisplaySize");
		displayPanel = new DisplayPanel();
		displayPanel.confirmButton.addActionListener(this);
		getContentPane().add(displayPanel);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==displayPanel.confirmButton){
			if(displayPanel.displaySize[0].isSelected()){
				MainFrame.WIDTH=1024;
				MainFrame.HEIGHT=768;
			}
			else if(displayPanel.displaySize[1].isSelected()){
				MainFrame.WIDTH=1280;
				MainFrame.HEIGHT=800;
			}
			else if(displayPanel.displaySize[2].isSelected()){
				MainFrame.WIDTH=1600;
				MainFrame.HEIGHT=900;
			}
			setVisible(false);
			new MainFrame().setLocationRelativeTo(null);		
		}
	}
	private class DisplayPanel extends JPanel{
		private final static int WIDTH=250;
		private final static int HEIGHT=100;
		private JRadioButton[] displaySize;
		private JButton confirmButton;
		public DisplayPanel(){
			setPreferredSize(new Dimension(WIDTH,HEIGHT));
			displaySize = new JRadioButton[4];
			displaySize[0]=new JRadioButton("1024*768");
			displaySize[1]=new JRadioButton("1280*800");
			displaySize[2]= new JRadioButton("1600*900");
			displaySize[3]= new JRadioButton("フルスクリーン");
			displaySize[0].setSelected(true);
			displaySize[3].setEnabled(false);
			ButtonGroup group = new ButtonGroup();
			for(int i=0;i<4;i++){
				group.add(displaySize[i]);
				add(displaySize[i]);
			}
			confirmButton = new JButton("OK");
			add(confirmButton);
		}
	}
}

