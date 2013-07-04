import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class SoundEngine {
	private HashMap<String,SE> seMap = new HashMap<String,SE>();
	private static final SoundEngine soundEngine = new SoundEngine();
	private SoundEngine(){
		seMap.put("SE1",new SE("se_maoudamashii_system23.mp3"));
		seMap.put("SE0",new SE("se_maoudamashii_onepoint30.mp3"));
		seMap.put("damegeSE",new SE("se_maoudamashii_battle18.mp3"));
		seMap.put("hBgm", new SE("bgm_maoudamashii_orchestra03.mp3"));
		seMap.put("gBgm", new SE("gBgm.mp3"));
		seMap.put("loseSE",new SE("se_maoudamashii_jingle13.mp3"));
		seMap.put("winSE",new SE("1 - 05 The Winner.mp3"));
		seMap.put("evenSE", new SE("se_maoudamashii_jingle12.mp3"));
		seMap.put("qSE", new SE("se_maoudamashii_onepoint28.mp3"));
	}
	public static void playSE(String key){
		soundEngine.seMap.get(key).thread 
				= new Thread(soundEngine.seMap.get(key));
		soundEngine.seMap.get(key).thread.start();
	}
	public static void stopSE(String key){
		soundEngine.seMap.get(key).player.close();
	}
	
	private class SE implements Runnable{
		
		private Player player;
		private BufferedInputStream stream;
		private Thread thread;
		private String fileName;
		
		private SE(String filename){
			try{
				this.fileName = filename;
				this.stream = new BufferedInputStream(new FileInputStream(fileName));
				this.player = new Player(stream);
			}catch(JavaLayerException e){
				e.printStackTrace();
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}
		}
		public void run(){
			try{
				stream = new BufferedInputStream(new FileInputStream(fileName));
				player = new Player(stream);
			}catch(JavaLayerException e){
				e.printStackTrace();
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}
			try{
				player.play();
			}catch(JavaLayerException e){
				e.printStackTrace();
			}
			
			if(player!=null){
				player.close();
			}
			if(stream!=null){
				player.close();
			}
		}
	}
}
