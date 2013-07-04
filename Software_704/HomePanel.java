import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Font;

public class HomePanel extends JPanel implements ActionListener{
	private static final int WIDTH=MainFrame.WIDTH;
	private static final int HEIGHT=MainFrame.HEIGHT;
	private JButton[] button;
	private BufferedImage icon,back;
	private JLabel message;
	private Font f1,f2;
	public HomePanel(){
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setLayout(null);
		f1 = FontCreator.getFont(1);
		f2 = FontCreator.getFont(2);
		icon = ImageEngine.getImage("button2");
		back = ImageEngine.getImage("hBack");
		button = new JButton[2];
		button[0] = new JButton("START",new ImageIcon(icon));
		button[1] = new JButton("EXIT",new ImageIcon(icon));
		for(int i=0;i<2;i++){
			button[i].setFont(f1.deriveFont(23.0f));	
			button[i].setForeground(Color.WHITE);
			button[i].setBounds(700+i*160,430+i*75,icon.getWidth(),icon.getHeight());
			button[i].setHorizontalTextPosition(JButton.CENTER);
			add(button[i]);
			button[i].addActionListener(this);
		}
	}
	public void paintComponent(Graphics g){
		g.drawImage(back, 0,0,null);
		g.setFont(f2.deriveFont(30.0f));
		g.setColor(Color.WHITE);
		g.drawString("Your Name : ", 850, 600);
		g.setFont(f1.deriveFont(25.0f));
		g.drawString(MainFrame.user.userName,970,600);
	}
	public void actionPerformed(ActionEvent e){
		Object action = e.getSource();
		if(action==button[0]){
			SoundEngine.playSE("SE1");
			SoundEngine.stopSE("hBgm");
			SoundEngine.playSE("gBgm");
			MainFrame.gameController.setup();
			MainFrame.STATE=MainFrame.START;
			MainFrame.cl.show(MainFrame.cardPanel, "Controller");
		}
		else if(action==button[1]){
			SoundEngine.playSE("SE1");
			try{
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				SwingUtilities.updateComponentTreeUI(this);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			message = new JLabel("EXIT?");
			message.setFont(f1.deriveFont(23.0f));
			String[] option = {"YES","NO"};
			int OPTION =JOptionPane.showOptionDialog(this,
					message,
					"EXIT",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					option,option[0]);
			if(OPTION==0){
				System.exit(0);
			}else{
				try{
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					SwingUtilities.updateComponentTreeUI(this);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
}
