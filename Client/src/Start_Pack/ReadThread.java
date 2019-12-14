package Start_Pack;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.ParseException;

import javax.swing.JOptionPane;



public class ReadThread extends Thread {

	private Socket socket;
	
	Frame frame;
	
	public ReadThread(Socket socket, Frame frame) {
		this.socket = socket;
		this.frame = frame;
	}
	
	public void run() {
		
		try {
			BufferedReader tempBuff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String[] receive;
			String word;
			
			while(true) {
				
				word = tempBuff.readLine();
				
				receive = word.split("/");
				System.out.println(word);
				
				if(receive[0].equals("CONNECT")) {
					int num = Integer.parseInt(receive[5]);
					
					if(num >0) {
					new Main_Pack.ReceiveData(1);
					Main_Pack.Board2.getInfo(num,receive[3],receive[4], receive[2], receive[6],receive[8], receive[9]);
					frame.ChangeToMain(receive[1], 1, receive[7]);
					}
					else {
						frame.ChangeToMain(receive[1], 0,receive[7]);
					}
					System.out.println(receive[1]);
				}
				else if(receive[0].equals("DIS_CONNECT")) {
					JOptionPane.showMessageDialog(null, "아이디 및 비밀번호가 틀렸습니다 ");
				}
				else if(receive[0].equals("ID_overwrite")) {
					JOptionPane.showMessageDialog(null, "아이디 중복 입니다 ");
				}
				else if(receive[0].equals("ADMIT")) {
					JOptionPane.showMessageDialog(null, "회원가입 완료 했습니다.");
					frame.ChangeToLogin();
				}
				else if(receive[0].equals("Board_Fail")) {
					JOptionPane.showMessageDialog(null, "한개의 아이디에 한개만 생성 가능합니다 .");
				}
				else if(receive[0].equals("Board_Make")) {
					JOptionPane.showMessageDialog(null, "방을 개설하였습니다.");
					Main_Pack.Board2.PathToMain(receive[1],receive[2],receive[3]);
				}
				else if(receive[0].equals("setINFO")) {
					int num = Integer.parseInt(receive[4]);
					Main_Pack.Board2.getInfo(num,receive[2],receive[3],receive[1],receive[5], receive[6], receive[7]);
					
					System.out.println("yes");
					
				}
				else if(receive[0].equals("INSA")) {
					new Main_Pack.ShowFriends(socket,receive[1],receive.length, word);
				}
				else if(receive[0].equals("OUTSIDER")) {
					new Main_Pack.ShowFriends(socket,receive[1],3, "");
				}
				else if(receive[0].equals("MakeFriend")) {
					JOptionPane.showMessageDialog(null, "친구가 등록 되었습니다.");
				}
				else if(receive[0].equals("NOFriend")) {
					JOptionPane.showMessageDialog(null, "그런 친구는 없습니다.");
				}
				else if(receive[0].equals("LOGOUT")) {
					
					try {
						socket.close();
						System.exit(0);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else if(receive[0].equals("STATE_ON")) {
					JOptionPane.showMessageDialog(null, "접속 중 입니다. ");
				}
				else if(receive[0].equals("STATE_OFF")) {
					JOptionPane.showMessageDialog(null, "접속 중이  아닙니다. ");
				}
				else if(receive[0].equals("Single_Chat")) {
					
					if(Main_Pack.Chat_Panel.name.equals(receive[2]) && Main_Pack.Chat_Panel.user.equals(receive[1])){
					Main_Pack.Chat_Panel.Chat_Panel_TextArea.append(receive[2] + " >> " + receive[3] +"\n");
					}
					else {
						JOptionPane.showMessageDialog(null, receive[2]+"님께서 "+ receive[3] +"를 보내셨습니다.");
					}
				}
				else if(receive[0].equals("GROUP_IN_SUCC")) {
					JOptionPane.showMessageDialog(null, "방에 접속하였습니다. ");
				}
				else if(receive[0].equals("YESYOUCAN")) {
					Main_Pack.Board2.PathTo_ChangeToRoom();
				}
				else if(receive[0].equals("NOYOUCAN")) {
					JOptionPane.showMessageDialog(null, "방이 마감되었습니다. ");
				}
				else if(receive[0].equals("CALLMONEY_SUCC")) {
					JOptionPane.showMessageDialog(null, receive[1] + "방에" + receive[2] +"님께서 " + receive[3] +"원이 입력되었습니다. " );
					Main_Pack.Group_Chat.Price.setText("");	
					Main_Pack.Group_Chat.Price.append(receive[2] + " | " + receive[3]);
				}
				else if(receive[0].equals("GROUP_CHAT")) {
					Main_Pack.Group_Chat.textArea.append(receive[1] + " >> " + receive[2] + "\n" );
				}
				else if(receive[0].equals("REFRESH")) {
					
					int num = Integer.parseInt(receive[6]);
					
					if(num >0) {
					Main_Pack.Board2.CloseMain();
					new Main_Pack.ReceiveData(1);
					Main_Pack.Board2.getInfo(num,receive[4],receive[5], receive[3], receive[7],receive[8], receive[9]);
					new Main_Pack.Main(socket, receive[1], 1, receive[2]);
					}
					else {
						JOptionPane.showMessageDialog(null, "변함이 없습니다. ");
					}
					
					
				}
				
				
			}
			
			
		}
		catch(IOException | ParseException e) {
			e.printStackTrace();
		}
		
	}
	
}
