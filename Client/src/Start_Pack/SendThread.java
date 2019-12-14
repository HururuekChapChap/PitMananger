package Start_Pack;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SendThread {

	private Socket socket;
	private String ID = "temp";
	
	/*private Frame frame;
	
	SendThread(Socket socket, Frame frame){
		this.socket = socket;
		this.frame = frame;
		
	}
	*/
	
	
	public SendThread(Socket socket){
		this.socket = socket;
	}
	
	/*
	
	public void sendMessage() {
		
		try {
			//BufferedReader sendBuffer = new BufferedReader(new InputStreamReader(System.in));
			
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			
			String sendString;
		
			//sendString = sendBuffer.readLine();
			
			sendString = frame.ID.getText();
			
			System.out.println(sendString);
			
			writer.println(ID + "//" +sendString);
			writer.flush();
				
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	
	}
	*/
	
	
	
public void sendMessage(String message) {
		
		try {
			//BufferedReader sendBuffer = new BufferedReader(new InputStreamReader(System.in));
			
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			
			String sendString = message;
		
			//sendString = sendBuffer.readLine();
			
			
			System.out.println(sendString);
			
			/*
			String[] word = message.split("/!1@2#3/");
			
			if(word[0].equals("ADMIN")) {
			
			writer.println(sendString);
			
			}
			else if (word[0].equals("LOGIN")){
				writer.println(sendString);
			}
			*/
			
			writer.println(sendString);
			writer.flush();
				
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	
	}

	
	

	
}
