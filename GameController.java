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
		MainFrame.enemy = new User("enemy",User.ENEMY);
		
		try{
			background = ImageIO.read(getClass().getResource("image04.jpg"));
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
		STATE=QSTATE;
	}
	public void setup(){
		gamePanelLeft.gameLoop.start();
		gamePanelRight.gameLoop.start();
	}
	public void gameUpdate(){
		ServerData sData = new ServerData();
		String data = "";//サーバーからのデータは文字列で取得される。
		sData.decode(data);//サーバーから受け取ったデータをデコードして、データセットする。
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
		MainFrame.user.statusUpdate(sData.status);
		//MainFrame.enemy.statusUpdate(ServerData.getInstance().status2);
		gamePanelLeft.newHPUpdate(sData.HP);
		//gamePanelRight.newHPUpdate(sData.HP);

		// シングルトンをやめたので以下の書き方を修正
		// ansPanel.update(sData.Selections, sData.question);
		// MainFrame.user.statusUpdate(ServerData.getInstance().status1);
		// MainFrame.enemy.statusUpdate(ServerData.getInstance().status2);
		// gamePanelLeft.newHPUpdate(ServerData.getInstance().HP1);
		// gamePanelRight.newHPUpdate(ServerData.getInstance().HP2);
		// ansPanel.update(ServerData.getInstance().selections,ServerData.getInstance().question);
			
		
		while(true){
			if(ansPanel.STATE==ansPanel.ASTATE) {
				ClientData.getInstance().set(MainFrame.user.Ans,MainFrame.user.Remain);
				break;
			}
			try{
				Thread.sleep(500);//0.5sec毎に確認
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		if(STATE==QSTATE){
			STATE=ASTATE;
		}
		else {
			STATE=QSTATE;
		}
	}
	public void toHome(){
		MainFrame.cl.show(MainFrame.cardPanel, "Home");
	}
}
