package Main_Pack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReceiveData {

	public ReceiveData(int num){
		try {
			String String_num = Integer.toString(num);
			Socket s = new Socket("localhost", 7777);
			
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			bw.write("receiver/"+String_num+"\n"); 
			bw.flush();
			
			String st = System.getProperty("user.dir");
			
			File file = new File(st+"/img",String_num+".png");
			InputStream InputByte = s.getInputStream();
			FileOutputStream out = new FileOutputStream(file);
			
			int i = 0;
			while( (i = InputByte.read()) !=  -1 ) {
				out.write((char)i);
			}
			System.out.println("저장된 ");
			
			InputByte.close(); out.close(); s.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
