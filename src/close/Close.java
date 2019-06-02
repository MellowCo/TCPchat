package close;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

public class Close {
	
	/*
     *关闭实现Closeable的相关对象
     *jdk 1.8
	 */
    public static void closeAll(Closeable... closeables){
        for (Closeable closeable : closeables) {
            if (closeable != null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("close异常");
                }
            }
        }
    }

    /*
     *关闭相关对象
     *jdk 1.6
	 */
	public static void closeAll(Socket s, BufferedWriter bw, BufferedReader br) {
		
			try {
				if(s != null){
					s.close();
				}
				
				if(bw != null){
					bw.close();
				}
				
				if(br != null){
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
	}

	

}
