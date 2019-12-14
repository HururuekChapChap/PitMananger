package Main_Pack;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendData {
	
	String directory_file;
	String filename;
	Socket socket;

	SendData(String directory_file,String filename ,Socket socket) throws IOException{
		
		this.directory_file = directory_file;
		this.filename = filename;
		this.socket = socket;
	}
	
	public void SendDataFunc() {
		
		DataInputStream dis;
		try {
			Socket s = new Socket("localhost", 7777);
			
			Date d= new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd:hh");
			
			
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			bw.write(sdf.format(d)+"_"+filename+"\n"); //현재시간을 더하는 방식으로 변경 => 이름 중복 방
			bw.flush();
			
			dis = new DataInputStream(new FileInputStream(new File(directory_file)));
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			int data = 0;
			
			while( (data = dis.read()) != -1) {
				dos.writeByte(data);
				dos.flush();
			}
			dis.close();
			dos.close();
			s.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
