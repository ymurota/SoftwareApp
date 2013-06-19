import java.io.*;
import java.net.*;

public class Server
{
	static final int PORT = 8080;
	static final int CLIENT_MAX = 2;

	static void main(String args[]) throws IOException
	{
		ServerSocket server_socket= new ServerSocket(PORT);
		int client_number = 0;
		Socket[] socket = new Socket[CLIENT_MAX];
		QuizThread[] quiz_thread = new QuizThread[CLIENT_MAX];

		System.out.println("Started: " + server_socket);

		try
		{
			while(client_number < CLIENT_MAX)
			{
				socket[client_number] = server_socket.accept();
				quiz_thread[client_number] = new QuizThread(client_number, socket[client_number]);
				System.out.println("Connection accepted: " + socket);

				client_number += 1;
			}

			System.out.println("Game Start");
			ServerSystem.set(client_number);

			while(!ServerSystem.is_dead)
			{
				ServerSystem.selectQuestionData();
				ServerSystem.reset();

				for(int i=0; i<CLIENT_MAX; ++i)
				{
					quiz_thread[i].start();
				}

				while(ServerSystem.data_valid_number < ServerSystem.client_number)
				{

				}

				ServerSystem.judge();
				ServerSystem.reset();

				for(int i=0; i<CLIENT_MAX; ++i)
				{
					quiz_thread[i].start();
				}

				while(ServerSystem.data_valid_number < ServerSystem.client_number)
				{

				}
			}
		}
		finally
		{

		}
	}

}
