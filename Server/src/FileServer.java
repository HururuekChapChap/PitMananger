
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class FileServer extends Thread {
	
	DB_Connect DB;
	FileServer(DB_Connect DB) throws IOException{
		this.DB = DB;
	}
	
	public void run() {
		
		try {
			while(true) {
		ServerSocket file_Server = new ServerSocket(7777); 
		System.out.println("파일 소켓 연결 대기 중 ");
		Socket file_Socket = file_Server.accept();
		System.out.println("파일 소켓 " + file_Socket + "연결 ");
		
		InputStream InputByte = file_Socket.getInputStream();
		BufferedReader br=new BufferedReader(new InputStreamReader(InputByte));
		
		FileOutputStream out = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		
		String st = System.getProperty("user.dir");
		
		String fileName = br.readLine();
		
		String[] word = fileName.split("/");
		
		if(!word[0].equals("receiver")) {
			System.out.println(fileName);
		File file = new File(st+"/img",fileName);
		out = new FileOutputStream(file);
		
		int i =0;
		while((i = InputByte.read()) != -1) {
			out.write((char)i);
		}
		 out.close();
		System.out.println(fileName + "저장된 ");
		}
		else {
			System.out.println("receiver");
			
			String FileIMG = DB.ImgFile_DB(word[1]);
			
			System.out.println(FileIMG);
			
			if(FileIMG.equals("NOimg")) {
				break;
			}
			
			dis = new DataInputStream(new FileInputStream(new File(st+"/img/"+FileIMG)));
			dos = new DataOutputStream(file_Socket.getOutputStream());
			int data = 0;
			
			while( (data = dis.read()) != -1) {
				dos.writeByte(data);
				dos.flush();
			}
			dis.close(); dos.close();
			System.out.println("전송완료 ");
		}
		
		InputByte.close(); file_Socket.close(); file_Server.close();
		
		}
			
		}
		catch(IOException | SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	
}
