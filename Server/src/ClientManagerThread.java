import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

public class ClientManagerThread extends Thread {

	private Socket socket;
	private DB_Connect DB;
	
	ClientManagerThread(Socket socket, DB_Connect DB){
		this.socket = socket;
		this.DB = DB;
	}
	
	public void run() {
		
		try {
			BufferedReader socket_buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			
			//
			InputStream InputByte = socket.getInputStream();
			String fileName = null;
			String directory = System.getProperty("user.dir");
			//File file = null;
			FileOutputStream out;
			int data =0;
			//
			
			String text;
			String message_from_DB=null;
			//String[] send_commend;
			String[] word = null;
			
			
			while(true) {
			
			text = socket_buffer.readLine();
			
			System.out.println(text);

			
			if(text == null) {
				System.out.println("소켓을 종료합니다. ");
				break;	
			}
		
			word = text.split("/!1@2#3/");
			
			if(word[0].equals("ADMIN")) {
				message_from_DB = DB.Write_DB(word);
			}
			
			else if(word[0].equals("LOGIN")) {
				message_from_DB = DB.Login_DB(socket, word);
			}
			
			else if(word[0].equals("Make_Board")) {
				message_from_DB = DB.MakeBoard_DB(word);
			}
			
			else if(word[0].equals("getINFO")) {
				message_from_DB = DB.TakeINFO_DB(word[1]);
			}
			else if(word[0].equals("Friends_INFO")) {
				message_from_DB = DB.TakeFriends_DB(word[1]);
			}
			else if(word[0].equals("MAKE_Friend")) {
				message_from_DB = DB.MakeFriend_DB(word[1], word[2]);
			}
			else if(word[0].equals("EXIT")) {
				message_from_DB = DB.EXIT_DB(socket,word[1]);
			}
			else if(word[0].equals("STATE_INFO")) {
				message_from_DB = DB.GET_STATE(word[1]);
			}
			else if(word[0].equals("Single_Chat")) {
				PrintWriter For_Chat = null;
				for(int i = 0; i< server_main.Connect_list.size(); i++) {
					
					if(word[1].equals(server_main.Connect_list.get(i).ID)) {
						For_Chat = new PrintWriter(server_main.Connect_list.get(i).socket.getOutputStream());
						For_Chat.println("Single_Chat/"+ word[1] +"/"+word[2] +"/" + word[3]);
						For_Chat.flush();
						break;
					}
					
				}
				
				continue;
				
			}
			else if(word[0].equals("GROUP_IN")) {
				message_from_DB = DB.GROUPIN_DB(word[1], word[2]);
			}
			else if(word[0].equals("GROUP_OUT")) {
				message_from_DB = DB.GROUP_OUT_DB(word[1], word[2]);
			}
			else if(word[0].equals("ISENTER")) {
				message_from_DB = DB.ISENTER_DB(word[1]);
			}
			else if(word[0].equals("CALLMONEY")) {
				
				String temp_DB = DB.GROUPUSER_DB(word[1]);
				message_from_DB = DB.CALLMONEY_DB(word[1], word[2], word[3]);
				String[] users = temp_DB.split("@");
				PrintWriter For_Chat = null;
				
				for(int i = 0; i <users.length; i++) {
					
					for(int j = 0; j< server_main.Connect_list.size(); j++) {
						
						if(users[i].equals(server_main.Connect_list.get(j).ID)) {
							For_Chat = new PrintWriter(server_main.Connect_list.get(j).socket.getOutputStream());
							For_Chat.println(message_from_DB);
							For_Chat.flush();
							break;
						}
						
					}
					
				}
				continue;
				
			}
			else if(word[0].equals("GROUP_CHAT")) {
				String temp_DB = DB.GROUPUSER_DB(word[1]);
				String[] users = temp_DB.split("@");
				PrintWriter For_Chat = null;
				
				for(int i = 0; i <users.length; i++) {
					
					for(int j = 0; j< server_main.Connect_list.size(); j++) {
						
						if(users[i].equals(server_main.Connect_list.get(j).ID)) {
							For_Chat = new PrintWriter(server_main.Connect_list.get(j).socket.getOutputStream());
							For_Chat.println("GROUP_CHAT/"+ word[2] +"/" + word[3]);
							For_Chat.flush();
							break;
						}
						
					}
					
				}
				continue;
			}
			else if(word[0].equals("Refresh")) {
				message_from_DB = DB.REFRESH_DB(word[1], word[2]);
			}
			
			
			System.out.println(message_from_DB);
			
			writer.println(message_from_DB);
			writer.flush();
			message_from_DB = "NO";
			
			
			}
			
			
		}
		catch(IOException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
