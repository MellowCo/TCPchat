package talk_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextArea;

/**
 * 开启服务器
 */
public class ServerOpen implements Runnable{
	private int port;
	private JTextArea textArea;
	private ServerSocket ss = null;
	
	
	public ServerOpen(int port, JTextArea textArea) {
		this.port = port;
		this.textArea = textArea;
	}
	
	public ServerOpen() {
	}

	public void run() {
			try {
				ss = new ServerSocket(port);
				textArea.append("开启服务\n");

				while(true){
					Socket s = ss.accept();
					new Thread(new ServerSend(s,textArea)).start();
				}

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("服务器开启失败");
				textArea.append("服务器开启失败\n");
			}
		
	}

}
