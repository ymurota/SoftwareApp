import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class SE {
	
	private Player player;
	private BufferedInputStream stream;
	private static SE se = new SE("se_maoudamashii_system44.mp3");
	
	private SE(String filename){
		try{
			this.stream = new BufferedInputStream(new FileInputStream(filename));
			this.player = new Player(stream);
		}catch(JavaLayerException e){
			e.printStackTrace();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	public void playSE(int seNum){
		setSE(seNum);
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
	private void setSE(int seNum){
		switch(seNum){
		case 1:se = new SE("se_maoudamashii_system44.mp3");
		}
	}
	public static SE getSE(){
		return se;
	}
}
