import java.io.*;
import java.lang.reflect.*;

class ClientData{
	private static final ClientData instance = new ClientData();
	private ClientData(){}
	int selected;
	double time;

	public static ClientData getInstance() {
		return ClientData.instance;
	}
	
	public void set(int selected, double time) {
		this.selected = selected;
		this.time = time;
	}

	public String encode() {
		String data;

		data = "selected:" + this.selected + ",";
		data += "time:" + this.time;

		return data;
	}

	public void decode(String str) {
		try{
			String[] data = str.split(",");
			for (int i = 0; i < data.length; i++ ) {
				String[] tmp = data[i].split(":");

				if (tmp[0].equals("selected")) {
					int selected = Integer.parseInt(tmp[1]);
					this.selected = selected;
				} else if (tmp[0].equals("time")) {
					double time = Double.parseDouble(tmp[1]);
					this.time = time;
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
