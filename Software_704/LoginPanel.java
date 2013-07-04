import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class LoginPanel extends JPanel implements ActionListener,KeyListener{
	private static final int WIDTH=MainFrame.WIDTH;
	private static final int HEIGHT=MainFrame.HEIGHT;
	private JTextField textField;
	private JButton button;
	private BufferedImage icon,back;
	private Font font,font2;
	
	public LoginPanel(){
		icon = ImageEngine.getImage("button1");
		back = ImageEngine.getImage("lBack");
		
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setLayout(null);
		
		textField = new JTextField(20){
			protected void paintComponent(Graphics g) {
		    if(!isOpaque()) {
		    	int w = getWidth(),h=getHeight();
		        Graphics2D g2 = (Graphics2D)g.create();
		        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                            RenderingHints.VALUE_ANTIALIAS_ON);
		        g2.setColor(Color.BLACK);
		        g2.fillRoundRect(0, 0, w-1, h-1, h, h);
		        g2.setColor(Color.WHITE);
		        g2.drawRoundRect(0, 0, w-1, h-1, h, h);
		        g2.dispose();
		      }
		      super.paintComponent(g);
		    }
		};
		textField.setOpaque(false);
		textField.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
		textField.setEditable(true);
		textField.setForeground(Color.WHITE);
		font = FontCreator.getFont(2);
		font2 = FontCreator.getFont(1);
		
		textField.setFont(font2.deriveFont(23.0f));
		textField.setBounds(WIDTH/2-10,HEIGHT/2-15,200,30);
		textField.addKeyListener(this);
		
		button = new JButton(new ImageIcon(icon));
		button.setBounds(WIDTH/2-10,
						HEIGHT/2+25,
						icon.getWidth(),
						icon.getHeight());
		button.setFont(font2.deriveFont(23.0f));
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		add(textField);
		add(button);
		button.addActionListener(this);
		addKeyListener(this);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(back, 
				WIDTH/2-330, 
				HEIGHT/2-190,
				null);
		g.setFont(font.deriveFont(27.0f));
		g.setColor(Color.WHITE);
		g.drawString("USER NAME :", 
					WIDTH/2-130, 
					HEIGHT/2+10);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==button){
			SoundEngine.playSE("SE1");
			if(textField.getText().length()!=0){
				MainFrame.user = new User(textField.getText(),
						User.USER);
				SoundEngine.playSE("hBgm");
				MainFrame.cl.show(MainFrame.cardPanel, "Home");
			}
		}
	}
	public void keyReleased(KeyEvent e){
		if(textField.getText().length()>8){
			textField.setText(textField.getText().substring(0,8));
		}
	}
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_ESCAPE){
			MainFrame.cl.show(MainFrame.cardPanel, "Title");
		}
	}
	public void keyTyped(KeyEvent e){}
}
