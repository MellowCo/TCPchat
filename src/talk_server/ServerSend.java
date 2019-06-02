package talk_server;

import close.Close;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JTextArea;

/**
 * 服务器 接送信息 转发信息
 */
public class ServerSend implements Runnable{
	Socket s = null;
	static CopyOnWriteArrayList<ServerSend> ss = new CopyOnWriteArrayList<ServerSend>();
	String name;
	BufferedWriter bw = null;
	BufferedReader br = null;
	
	BufferedWriter fw = null;
			
	String msg = null;
	JTextArea textArea;
	boolean flag = true;
	Long startTime;
	Long endTime;
	static File file = new File("li.txt"); 

	/**
	 *
	 * @param s 客户端Socket
	 * @param textArea 客户端的textArea
	 */
	public ServerSend(Socket s,JTextArea textArea) {
		startTime = System.currentTimeMillis();
		
		this.s = s;
		this.textArea = textArea;
		addSocket();

		try {
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
			receName();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ServerSend 初始化失败");
			//textArea.append("ServerSend 初始化失败\n");
			close();
		}
		welcome();	
	}

	public ServerSend() {
	}


	public void run() {
		while(flag){
			receive();
			sendOther(msg);
		}
	}
	
	//添加socket
	void addSocket(){
		ss.add(this);
	}
	
	//向客户端转发消息
	void send(String msg){
		try {
			bw.write(msg);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("server send fail");
			//textArea.append("server send fail\n");
			close();
		}
	}

	//向其他客户转发信息
	void sendOther(String msg){
		if(msg != null && msg !=""){
			for(ServerSend s:ss){
				s.send(msg);
			}
		}
	}
	
	//接受客户端的消息
	void receive(){
		try {
			msg = br.readLine();
			msg = name +":"+ msg + "-->" + new Date();
			textArea.append(msg+"\n");
			save(msg);
		} catch (IOException e) {
			close();
			e.printStackTrace();
			System.out.println("server receive fail");
			//textArea.append("server receive fail\n");
		}
	}

	//接送名字
	void receName(){
		try {
			name = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("receName fail");
			//textArea.append("receName fail\n");
			close();
		}
	}
	
	//欢迎客户端
	void welcome(){
		sendOther("欢迎"+name+"来到聊天室");
		textArea.append("欢迎"+name+"来到聊天室"+"\n");
	}


	//异常时 关闭对象
	void close(){
		leave();
		msg = null;
		flag = !flag;
		Close.closeAll(s,bw,br);
		Close.closeAll(null,fw,null);
	}

	//客户端关闭，转发离开信息
	void leave(){
		endTime = System.currentTimeMillis();
		textArea.append(name + "在线时间：" + (endTime - startTime)/1000 + "秒\n");
		ss.remove(this);
		sendOther(name + "离开了聊天室");
		textArea.append(name + "离开了聊天室\n");
	}
	
	//保存信息到file
	void save(String msg){
		try {
			fw.write(msg);
			fw.newLine();
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
