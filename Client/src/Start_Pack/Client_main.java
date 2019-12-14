package Start_Pack;
import java.io.IOException;
import java.net.Socket;

public class Client_main {
	
	public static String UserID = "one";
	public Frame frame;
	
	
	Client_main(){
		
		try {
			Socket socket = new Socket("localhost", 8888);
			
			frame = new Frame(socket);
			//SendThread send = new SendThread(socket, UserID, frame);
			ReadThread read = new ReadThread(socket, frame);
			//send.start();
			read.start();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		
		new Client_main();
	}
	
}
