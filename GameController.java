import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class GameController extends JPanel{
	private static final int WIDTH=MainFrame.WIDTH;
	private static final int HEIGHT=MainFrame.HEIGHT;
    private AnswerPanel ansPanel;
	private GamePanel gamePanelLeft,gamePanelRight;
	private int STATE;
	private int QSTATE=0,ASTATE=1;
	
	public BufferedImage background;
	public GameController(){
		MainFrame.opponent = new User("Opponent",User.OPPONENT);
		
		try{
			background = ImageIO.read(getClass().getResource("img_980681_27657870_0.jpg"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setLayout(new BorderLayout());
		
		gamePanelLeft = new GamePanel(GamePanel.LEFT);
		gamePanelRight = new GamePanel(GamePanel.RIGHT);
		ansPanel = new AnswerPanel();
		
		add(gamePanelLeft,BorderLayout.WEST);
		add(ansPanel,BorderLayout.CENTER);
		add(gamePanelRight,BorderLayout.EAST);
		STATE = QSTATE;
	}
	public void setup(){
		gamePanelLeft.gameLoop.start();
		gamePanelRight.gameLoop.start();
	}
	public String gameUpdate(String[] data){
		ServerData[] sData = new ServerData[2];
		for(int i=0;i<2;i++){
			sData[i] = new ServerData();
			sData[i].decode(data[i]);
		}
		ClientData cData = new ClientData();
		
		if(STATE==QSTATE){
			while(true){
				if(ansPanel.STATE!=ansPanel.QSTATE){
					try{
						Thread.sleep(500);//0.5sec毎に確認
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}else break;
			}
		}
		else if(STATE==ASTATE){
			while(true){
				if(ansPanel.STATE!=ansPanel.ASTATE){
					try{
						Thread.sleep(500);//0.5sec毎に確認
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}else break;
			}
		}
		MainFrame.user.statusUpdate(sData[0].status);
		MainFrame.opponent.statusUpdate(sData[1].status);
		gamePanelLeft.newHPUpdate(sData[0].HP);
		gamePanelRight.newHPUpdate(sData[1].HP);
		ansPanel.update(sData[0].selections, sData[0].question);
			
		while(true){
			if(ansPanel.STATE==ansPanel.ASTATE) {
				if(STATE==QSTATE){
					STATE=ASTATE;
				}
				else {
					STATE=QSTATE;
				}
				cData.set(MainFrame.user.Ans,MainFrame.user.Remain);
				return cData.encode();
			}
			try{
				Thread.sleep(500);//0.5sec毎に確認
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	public void toHome(){
		gamePanelLeft.loopStop();
		gamePanelRight.loopStop();
		MainFrame.cl.show(MainFrame.cardPanel, "Home");
	}
}
