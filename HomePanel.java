import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
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
	private BufferedImage icon;
	private JLabel message;
	private Font font;
	public HomePanel(){
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setLayout(null);
		font = FontCreator.getFont(1);
		try{
			icon = ImageIO.read(getClass().getResource("Button3.png"));	
		}catch(IOException e){
			e.printStackTrace();
		}
		button = new JButton[3];
		button[0] = new JButton("START",new ImageIcon(icon));
		button[1] = new JButton("EXIT",new ImageIcon(icon));
		for(int i=0;i<2;i++){
			button[i].setFont(font.deriveFont(23.0f));	
			button[i].setForeground(Color.WHITE);
			button[i].setBounds(WIDTH/2-100,2*HEIGHT/3+i*50,icon.getWidth(),icon.getHeight());
			button[i].setHorizontalTextPosition(JButton.CENTER);
			add(button[i]);
			button[i].addActionListener(this);
		}
	}
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setFont(font.deriveFont(20.0f));
		g.setColor(Color.WHITE);
		g.drawString("Your Name : "+MainFrame.user.userName, 100, 100);
	}
	public void actionPerformed(ActionEvent e){
		Object action = e.getSource();
		if(action==button[0]){
			SE.getSE().playSE(1);
			MainFrame.gameControllerPanel.setup();
			MainFrame.cl.show(MainFrame.cardPanel, "Controller");
			MainFrame.clientTest.thread.start();
		}
		else if(action==button[1]){
			MainFrame.cl.show(MainFrame.cardPanel, "Manual");
		}
		else if(action==button[2]){
			SE.getSE().playSE(1);
			try{
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				SwingUtilities.updateComponentTreeUI(this);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			message = new JLabel("‚Ù‚ñ‚Æ‚ÉH");
			message.setFont(font.deriveFont(23.0f));
			String[] option = {"‚Ù‚ñ‚Æ","‚¤‚»"};
			int OPTION =JOptionPane.showOptionDialog(this,
					message,
					"‚¨‚í‚é",
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
