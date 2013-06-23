import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{
	public static int WIDTH;
	public static int HEIGHT;
	
	public static JPanel cardPanel;
	public static CardLayout cl;
	public static String userName;
	public static TitlePanel titlePanel;
	public static LoginPanel loginPanel;
	public static HomePanel homePanel;
	public static GameController gameController;
	public static User user;
	public static User opponent;
	public static int FIRST=0,START=1;
	public static int STATE=FIRST;
	public MainFrame(){
		cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout());
		setTitle("MainFrame");
		setResizable(false);
		titlePanel = new TitlePanel();
		loginPanel = new LoginPanel();
		homePanel = new HomePanel();
		gameController=new GameController();
		cardPanel.add(titlePanel,"Title");
		cardPanel.add(loginPanel,"Login");
		cardPanel.add(homePanel,"Home");
		cardPanel.add(gameController,"Controller");
		cl = (CardLayout)(cardPanel.getLayout());
		getContentPane().add(cardPanel);
		titlePanel.title.FadeIn();
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args){
		new DisplaySize().setLocationRelativeTo(null);
		//以下テスト用
		String cData ;
		String[] selection = new String[4];
		for(int i=0;i<4;i++){
			selection[i]= "選択肢"+i;
		}
		String[] selection2 = new String[4];
		for(int i=0;i<4;i++){
			selection2[i] = "";
		}
		ServerData[] sData = new ServerData[2];
		sData[0] = new ServerData();
		sData[1] = new ServerData();
		sData[0].set("1+1",selection,"ダメージ30",80);
		sData[1].set(" ",selection2,"ダメージ40",70);
		String[] data = new String[2];
		data[0] = sData[0].encode();
		data[1] = sData[1].encode();
		
		
		while(true){
			if(STATE==START)break;
			try{
				Thread.sleep(500);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		cData = MainFrame.gameController.gameUpdate(data);
		System.out.println(cData);
	}
}
