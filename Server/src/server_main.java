import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Data{
	 Socket socket;
	 String ID;
	
	Data(Socket socket, String ID){
		this.socket = socket;
		this.ID = ID;
	}
	
}

class RoomData{
	String Time;
	int number;
	int door;
	
	public RoomData(String Time, int number, int door) {
		this.Time = Time;
		this.number = number;
		this.door = door;
	}
	
	public void setRoomData_number(int number) {
		this.number = number;
	}
	
	public void setRoomData_door() {
		this.door = 0;
	}

}

public class server_main {

	public static ArrayList<Socket> list = new ArrayList<Socket>();
	public static ArrayList<Data> Connect_list = new ArrayList<Data>();
	public static ArrayList<RoomData> RoomData_list = new ArrayList<RoomData>();
	
	server_main(){
		
		try {
			
			System.out.println("start");
			
			ServerSocket socket_Server = new ServerSocket(8888);
			DB_Connect DB = new DB_Connect();
			FileServer file = new FileServer(DB);
			CloseConectThread Close = new CloseConectThread(DB);
			file.start();
			Close.start();
		
			while(true) {
					Socket socket = socket_Server.accept();
					System.out.println("1번 소켓 : "+ socket);
					list.add(socket);
					ClientManagerThread client = new ClientManagerThread(socket, DB);
					
					client.start();
				}
				
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		new server_main();
		
	}

}
