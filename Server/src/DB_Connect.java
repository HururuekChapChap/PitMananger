import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Connect {
	
	Connection conn = null;
	Statement stmt = null;
	
	
	DB_Connect(){
	
		try {
			String sql_name = "jdbc:mysql://localhost:3306/testdb?serverTimezone=UTC";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(sql_name, "root", "@taesoo4239");
			
			System.out.println("connect");
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void  InitContent_DB() throws SQLException {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from contents");
		
		while(rs.next()) {
			int cnt = rs.getInt("cnt");
			String date = rs.getString("date");
			int door = rs.getInt("door");
			
			server_main.RoomData_list.add(new RoomData(date,cnt,door));
		}
		
	}
	
	public int DeleteContent_DB(int cnt) throws SQLException {
		stmt = conn.createStatement();
		String command = String.format("delete from contents where cnt = ('%d');",cnt);
		int rowNum = stmt.executeUpdate(command);
		if(rowNum < 1) {
			System.out.println("error");
			return 0;
		}
		
		return 1;
	}
	
	public void ChangeContent_DB(int to,int cnt) throws SQLException {
		stmt = conn.createStatement();
		String command = String.format("update contents set cnt = ('%d') where cnt = ('%d');",to,cnt);
		int rowNum = stmt.executeUpdate(command);
		if(rowNum < 1) {
			System.out.println("error");
		}
		
	}
	
	public void CloseContent_DB(int cnt) throws SQLException {
		
		stmt = conn.createStatement();
		String command = String.format("update contents set door = 0 where cnt = ('%d');",cnt);
		int rowNum = stmt.executeUpdate(command);
		if(rowNum < 1) {
			System.out.println("error");
		}
	}
	
	public	String Write_DB(String[] word) throws SQLException {
		
		int code = 0;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from userinfo");
		
		
		while(rs.next()) {
			
			if(rs.getString("id").equals(word[1])) {
				return "ID_overwrite";
			}
			
			code = rs.getInt("code");
		}
		
		for(int i=0; i<word.length; i++) {
			System.out.println(word[i]);
		}
		
		
		String command = String.format("insert into userinfo"
				+ "(name, id, password, code, pic)"
				+ "values ('%s','%s','%s','%d','%d');",
				word[3], word[1], word[2], code +1, Integer.parseInt(word[4]));
		
		
		int rowNum = stmt.executeUpdate(command);
		if(rowNum < 1) {
			System.out.println("error");
		}
		
		System.out.println("ADMIT");
		
		return "ADMIT";
		
	}
	
	public String Login_DB(Socket socket,String[] word) throws SQLException {
		
		stmt = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from userinfo");
		ResultSet rs2 = stmt2.executeQuery("select * from contents");
		
		String id = null ,product = null , date = null , MAX = null, text = null, Picture = null, MCP = null, price = null;
		int total = 0, pic = 0;
		
		while(rs2.next()) {
			
			if(total == 0) {
				id = rs2.getString("id");
				product = rs2.getString("product");
				text = rs2.getString("text");
				date = rs2.getString("date");
				
				if(rs2.getString("MCP") == null) {
					MCP = "비어있음";
					price = "0";
				}
				else {
					MCP = rs2.getString("MCP");
					price = rs2.getString("price");
				}
			}
			
			total++;
		}
		
		while(rs.next()) {
			
			if(rs.getString("id").equals(word[1]) && rs.getString("password").equals(word[2])) {
				
				MAX = Integer.toString(total);
				Picture = Integer.toString(rs.getInt("pic"));
				String command = String.format("update userinfo set state = 1 where id = ('%s');",word[1]);
				
				int rowNum = stmt.executeUpdate(command);
				if(rowNum < 1) {
					System.out.println("error");
				}
				System.out.println("2번 소켓 : "+ socket + " " + word[1]);
				server_main.Connect_list.add(new Data(socket, word[1]));
				
				return "CONNECT/" + word[1] + "/" + id + "/"+ product + "/" + date + "/" + MAX +"/" + text + "/" + Picture + "/" + MCP + "/" + price;
			}
			
		}
		
		
		return "DIS_CONNECT";
	}
	
	public String MakeBoard_DB(String[] word) throws SQLException {
		
		int cnt = 0;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from contents");
		
		while(rs.next()) {
			
			if(rs.getString("id").equals(word[1])) {
				return "Board_Fail";
			}
			cnt = rs.getInt("cnt");
		}
		
		String command = String.format("insert into contents"
				+ "(id, product, text, cnt, img, date)"
				+ "values ('%s','%s','%s','%d','%s', '%s');",
				word[1], word[2], word[4], cnt +1, word[3], word[5]);
		
		int rowNum = stmt.executeUpdate(command);
		if(rowNum < 1) {
			System.out.println("error");
		}
		
		System.out.println("Board_Make");
		
		server_main.RoomData_list.add(new RoomData(word[5], cnt + 1,1));
		
		return "Board_Make/"+word[1]+"/"+word[4]+"/"+word[5];
	}
	
	public String ImgFile_DB(String word) throws SQLException {
		
		int cnt = 0;
		int num = Integer.parseInt(word);
		Statement stmt3 = conn.createStatement();
		ResultSet rs = stmt3.executeQuery("select * from contents");
		
		
		while(rs.next()) {
			int number = rs.getInt("cnt");
			if(number == num ) {
				String IMG_NAME = rs.getString("img");
				System.out.println(number+ " " + IMG_NAME);
				return IMG_NAME;
			}
			
		}
		
		return "NOimg";
	}
	
	public String TakeINFO_DB(String word) throws SQLException{
		
		int num = Integer.parseInt(word);
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from contents");
		
		String id = null, product = null, date = null, text = null, MCP = null, price = null;
		int total = 0;
		
		
		while(rs.next()) {
			
			if(rs.getInt("cnt") == num) {
				id =  rs.getString("id");
				product = rs.getString("product");
				text = rs.getString("text");
				date = rs.getString("date");
				
				if(rs.getString("MCP") == null) {
					MCP = "비어있음";
					price = "0";
				}
				else {
					MCP = rs.getString("MCP");
					price = rs.getString("price");
				}
				
			}
			
			total++;
		}
		
		return "setINFO/"+ id +"/" + product +"/" + date +"/" + Integer.toString(total) +"/"+ text +"/" + MCP +"/" + price;
		
	}
	
	public String TakeFriends_DB(String name) throws SQLException {
		stmt = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		String command = String.format("select friend from FriendINFO where id = ('%s');",name);
		ResultSet rs = stmt.executeQuery(command);
		ResultSet rs2;
		/*
		rs.next();
		
		if(rs.getString("friend") != null) {
			
			String friends_name = rs.getString("friends");
			
			String Pic_name = "";
			
			String[] split_friends = friends_name.split("/");
			
		
			
			for(int i = 0; i< split_friends.length; i++) {
				
				
				
				String command2 = String.format("select pic from userinfo where id = ('%s');",split_friends[i]);
				rs2 = stmt2.executeQuery(command2);
				rs2.next();
				
				String temp =  Integer.toString(rs2.getInt("pic"));
				
				Pic_name =	 Pic_name + temp +"@";
			}
			
			
			return "INSA/"+name +"/"+Pic_name +"/"+friends_name;
		}
		*/
		
		boolean flag = false;
		String friends_name = "";
		String Pic_name = "";
		while(rs.next()) {
			flag = true;
			friends_name = friends_name+ rs.getString("friend") +"/";
			String command2 = String.format("select pic from userinfo where id = ('%s');",rs.getString("friend"));
			rs2 = stmt2.executeQuery(command2);
			rs2.next();
			
			String temp =  Integer.toString(rs2.getInt("pic"));
			
			Pic_name =	 Pic_name + temp +"@";
			
		}
		
		if(flag == true) {
			return "INSA/"+name +"/"+Pic_name +"/"+friends_name;
		}
		
		return "OUTSIDER/"+name;
	}
	
	public String MakeFriend_DB(String user,String name) throws SQLException {
		stmt = conn.createStatement();
		/*
		Statement stmt2 = conn.createStatement();
		
		String command = String.format("select * from userinfo where id = ('%s');",name);
		ResultSet rs = stmt.executeQuery(command);
		
		String command2 = String.format("select friends from userinfo where id = ('%s');",user);
		ResultSet rs2 = stmt2.executeQuery(command2);
		rs2.next();
		
		
		if(rs.next()) {
			String command3;
			Statement stmt3 = conn.createStatement();
			int rowNum;
			
			if(rs2.getString("friends") != null) {
				command3 = String.format("update userinfo set friends = ('%s') where id = ('%s');",rs2.getString("friends")+"/"+name ,user);
				rowNum = stmt3.executeUpdate(command3);
				if(rowNum < 1) {
					System.out.println("error");
				}
			}
			else {
				command3 = String.format("update userinfo set friends = ('%s') where id = ('%s');",name ,user);
				rowNum = stmt3.executeUpdate(command3);
				if(rowNum < 1) {
					System.out.println("error");
				}
			}
			
			return "MakeFriend";
			
		}
		*/
		
		String command = String.format("insert into FriendINFO"
				+ "(id, friend)"
				+ "values ('%s','%s');",
				user, name);
		
		int rowNum = stmt.executeUpdate(command);
		if(rowNum < 1) {
			System.out.println("error");
			return "NOFriend";
		}
		
		return "MakeFriend";
		
	}
	public String EXIT_DB(Socket socket,String user) throws SQLException {
		
		stmt = conn.createStatement();
		String command = String.format("update userinfo set state = 0 where id = ('%s');",user);
		int rowNum = stmt.executeUpdate(command);
		
		if(rowNum < 1) {
			System.out.println("error");
		}
		
		
		//server_main.Connect_list.remove(new Data(socket,user));
		
		for(int i =0; i< server_main.Connect_list.size(); i++) {
			
			if(server_main.Connect_list.get(i).ID.equals(user)) {
				server_main.Connect_list.remove(i);
			}
			
		}
		
		//System.out.println(server_main.Connect_list.size()+ " "+server_main.Connect_list.toString());
		
		return "LOGOUT";
	}
	
	public String GET_STATE(String name) throws SQLException {
		stmt = conn.createStatement();
		String command = String.format("select state from userinfo where id = ('%s');",name);
		ResultSet rs = stmt.executeQuery(command);
		rs.next();
		
		if(rs.getInt("state") == 1) {
			return "STATE_ON";
		}
		
		return "STATE_OFF";
	}
	
	public String GROUPIN_DB(String roomID, String user) throws SQLException {
		
		Statement stmt2 = conn.createStatement();
		//Statement stmt2 = conn.createStatement();
		String command = String.format("select users from contents where id = ('%s');",roomID);
		ResultSet rs = stmt2.executeQuery(command);
		rs.next();
		
		if(rs.getString("users") == null) {
			command = String.format("update contents set users = ('%s') where id = ('%s')",user +"@",roomID);
			stmt2.executeUpdate(command);
		}
		else {
			command = String.format("update contents set users = ('%s') where id = ('%s')", rs.getString("users") +user +"@",roomID);
			stmt2.executeUpdate(command);
		}
		
		return "GROUP_IN_SUCC";
		
	}
	
	public String GROUP_OUT_DB(String roomID, String user) throws SQLException {
		
		Statement stmt2 = conn.createStatement();
		String command = String.format("select users from contents where id = ('%s');",roomID);
		ResultSet rs = stmt2.executeQuery(command);
		rs.next();
		String users = rs.getString("users");
		
		if(users == null) {
			return "NO_USER_DELETE";
		}
		else {
			String[] SP_users = users.split(user+"@");
			
			if(SP_users.length == 0) {
				System.out.println("lengh : 0");
				command = String.format("update contents set users = ('%s') where id = ('%s')", "" ,roomID);
			}
			else if(SP_users.length == 1) {
				
				System.out.println("lengh : 1 "+SP_users[0]);
				command = String.format("update contents set users = ('%s') where id = ('%s')",SP_users[0],roomID);
				
			}
			else {
				
				System.out.println("lengh : 2 "+SP_users[0] + " "+ SP_users[1]);
			command = String.format("update contents set users = ('%s') where id = ('%s')",SP_users[0] + SP_users[1],roomID);
			
			}
			
			stmt2.executeUpdate(command);
			
		}
		
		return "USER_DELTE";
	}
	
	public String ISENTER_DB(String roomID) throws SQLException {
		stmt = conn.createStatement();
		String command = String.format("select door from contents where id = ('%s');",roomID);
		ResultSet rs = stmt.executeQuery(command);
		rs.next();
		
		int check = rs.getInt("door");
		
		if(check == 1) {
			return "YESYOUCAN";
		}
		
		return "NOYOUCAN";
	}
	
	public String CALLMONEY_DB(String roomID, String user, String price) throws SQLException {
		Statement stmt5 = conn.createStatement();
		String command = String.format("update contents set MCP = ('%s'), price = ('%s') where id = ('%s')", user, price ,roomID);
		int rowNUM = stmt5.executeUpdate(command);
		if(rowNUM < 1) {
			System.out.println("ERROR");
		}
		return "CALLMONEY_SUCC/"+ roomID +"/" +user + "/" + price;
	}
	
	public String GROUPUSER_DB(String roomID) throws SQLException {
		Statement stmt6 = conn.createStatement();
		String command = String.format("select users from contents where id = ('%s');",roomID);
		ResultSet rs = stmt6.executeQuery(command);
		rs.next();
		
		String users = rs.getString("users");
		
		return users;
	}
	
	public String REFRESH_DB(String user, String pic) throws SQLException {
		
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from contents");
		
		String id = null ,product = null , date = null , MAX = null, text = null, Picture = null, MCP = null, price = null;
		int total = 0;
		
		while(rs.next()) {
			
			if(total == 0) {
				id = rs.getString("id");
				product = rs.getString("product");
				text = rs.getString("text");
				date = rs.getString("date");
				
				if(rs.getString("MCP") == null) {
					MCP = "비어있음";
					price = "0";
				}
				else {
					MCP = rs.getString("MCP");
					price = rs.getString("price");
				}
			}
			
			total++;
		}
		
		
		return "REFRESH/"+ user +"/" + pic + "/" +id +"/" + product +"/" + date +"/" + Integer.toString(total) +"/"+ text +"/" + MCP +"/" + price;
	}
	
}

//한번에 명령을 보내줘서 같은 DB에 접속한다면, 중복이 이뤄져서 데이터가 없어지는 현상이 발생한다. 그래서 DB연결이 CLOSE ㅠㅠ
//따라서 DB 접속 클래스인 Statement를 달리 해줘야한다. 아니면 중복이 안 이뤄지게, 시간차를 줘야하는데, 만약에 여러명이 접속했을 때의 상황은,,,
