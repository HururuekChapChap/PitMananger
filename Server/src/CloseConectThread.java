import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CloseConectThread extends Thread{
	
	DB_Connect DB = null;
	
	CloseConectThread(DB_Connect DB){
			
		this.DB = DB;
		
		try {
			DB.InitContent_DB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(server_main.RoomData_list.size());
	}
	
	
	public void run() {
		boolean flag = false;
		SimpleDateFormat f= new SimpleDateFormat("HH:mm:ss");
		
		while(true) {
			System.out.println(server_main.RoomData_list.size() + " " + server_main.Connect_list.size());
			Date current = new Date();
			long time = 0;
			long RoomData_Time = 0;
			
			try {
				 time = f.parse(f.format(current)).getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(server_main.Connect_list.size() == 0){
				
				for(int i = 0; i< server_main.RoomData_list.size(); i++) {
					
					int check = 0;
					
					try {
						RoomData_Time = f.parse(server_main.RoomData_list.get(i).Time).getTime();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(time - RoomData_Time >0) {
						flag = true;
						System.out.println(server_main.RoomData_list.get(i).number + "삭제 ");
						
						try {
							check = DB.DeleteContent_DB(server_main.RoomData_list.get(i).number);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if(check == 1) {
							server_main.RoomData_list.remove(i);
						}
						
					}
					
				}
				
				if(flag == true) {
					
					for(int i =0; i<server_main.RoomData_list.size(); i++) {
						
						try {
							DB.ChangeContent_DB(i+1, server_main.RoomData_list.get(i).number);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						server_main.RoomData_list.get(i).setRoomData_number(i+1);
						System.out.println(server_main.RoomData_list.get(i).number);
					}
					
				flag = false;
				
				}
				
			}
			else {
				
				for(int i = 0; i< server_main.RoomData_list.size(); i++) {
					
					try {
						RoomData_Time = f.parse(server_main.RoomData_list.get(i).Time).getTime();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(time - RoomData_Time >0) {
						
						try {
							DB.CloseContent_DB(server_main.RoomData_list.get(i).number);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						server_main.RoomData_list.get(i).setRoomData_door();
						
					}
					
				}
				
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
