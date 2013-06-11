import java.io.*;
import java.lang.reflect.*;

class ServerData{
	private static final ServerData instance = new ServerData();
	private ServerData(){}
	String question;
	String selections[] = new String[4];
	String status;
	int HP;

	public static ServerData getInstance() {
		return ServerData.instance;
	}
	
	public void set(String question, String selections[], String status, int HP) {
		this.question = question;
		this.selections = selections.clone();
		this.status = status;
		this.HP = HP;
	}

	public String encode() {
		String data;

		data = "question:" + this.question + ",";
		data += "selections:";
		for (int i = 0; i < this.selections.length; i++ ) {
			char d = (i == this.selections.length - 1) ? ',' : '/';
			data += this.selections[i]+d;
		}
		data += "status:" + this.status + ",";
		data += "HP:" + this.HP;

		return data;
	}

	public void decode(String str) {
		try{
			Class cls = Class.forName("ServerData");
			
			String[] data = str.split(",");
			for (int i = 0; i < data.length; i++ ) {
				String[] tmp = data[i].split(":");
				Field f = cls.getDeclaredField(tmp[0]);

				if (tmp[0].equals("selections")) {
					String[] each = tmp[1].split("/");
					f.set(this, each.clone());
				} else if (tmp[0].equals("HP")) {
					int HP = Integer.parseInt(tmp[1]);
					f.set(this, HP);
				} else {
					f.set(this, tmp[1]);
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
