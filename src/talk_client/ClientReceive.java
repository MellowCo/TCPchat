package talk_client;

import close.Close;

import java.awt.TexturePaint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;

public class ClientReceive implements Runnable{
	Socket s = null;
	String msg;
	JTextArea textArea;
	BufferedReader br = null;
	boolean flag = true;
		
	public ClientReceive(Socket s,JTextArea textArea) {
		this.s = s;
		this.textArea = textArea;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ClientReceive 初始化错误");
			textArea.append("ClientReceive 初始化错误\n");
			close();
		}
	}

	//客户端接收消息
	void receive(){
			try {
				msg = br.readLine();
				textArea.append(msg+"\n");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("ClientReceive receive 错误");
				textArea.append("服务器异常关闭\n");
				close();
			}
	}

	//关闭
	void close(){
		flag = !flag;
		Close.closeAll(s,null,br);
	}

	public void run() {
		while(flag){
			receive();
		}
	}

}
