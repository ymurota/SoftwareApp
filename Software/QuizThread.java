import java.io.*;
import java.net.*;

public class QuizThread extends Thread
{
	final int player_number;
	final Socket socket;
	BufferedReader in;
	BufferedWriter out;

	QuizThread(int _number, Socket _socket) throws IOException
	{
		player_number = _number;
		socket = _socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	public void run()
	{
		try
		{
			for(int i=0; i < ServerSystem.client_number; ++i)
			{
				//out.write(ServerSystem.server_data[player_number].encode());
				out.write(ServerSystem.server_data[i].encode());
				out.newLine();
				out.flush();
			}

			String line;

			line = in.readLine();

			ServerSystem.client_data[player_number].decode(line);
			ServerSystem.data_valid_number += 1;
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
}
