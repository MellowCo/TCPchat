package talk_client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;

public class TalkClient extends JFrame {

    private JPanel contentPane;
    private JTextField ipText;
    private JTextField portText;
    private JTextField nameText;
    private JTextField msgText;
    Socket s = null;
    BufferedWriter bw = null;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TalkClient frame = new TalkClient();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public TalkClient() {
        setTitle("客户端");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 630, 538);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblip = new JLabel("服务器IP");

        ipText = new JTextField();
        ipText.setColumns(10);
        ipText.setText("localhost");

        JLabel label = new JLabel("端口");

        portText = new JTextField();
        portText.setColumns(10);
        portText.setText("9999");

        JLabel label_1 = new JLabel("用户名");

        nameText = new JTextField();
        nameText.setColumns(10);

        JButton startBut = new JButton("连接服务器");

        JLabel label_2 = new JLabel("输入消息");

        msgText = new JTextField();
        msgText.setColumns(10);

        JButton send = new JButton("发送");

        JScrollPane scrollPane = new JScrollPane();
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
                                        .addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
                                                .addGap(20)
                                                .addComponent(scrollPane))
                                        .addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
                                                .addGap(18)
                                                .addComponent(lblip)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                .addComponent(label_2)
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addComponent(msgText, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(37)
                                                                .addComponent(send))
                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                .addComponent(ipText, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18)
                                                                .addComponent(label)
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addComponent(portText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(35)
                                                                .addComponent(label_1)
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18)
                                                                .addComponent(startBut)))))
                                .addContainerGap(14, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblip)
                                        .addComponent(ipText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label)
                                        .addComponent(portText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_1)
                                        .addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(startBut))
                                .addGap(28)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_2)
                                        .addComponent(msgText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(send))
                                .addGap(22)
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                                .addContainerGap())
        );

        final JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        scrollPane.setViewportView(textArea);
        contentPane.setLayout(gl_contentPane);

        //开启服务器
        startBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                String ip = ipText.getText();
                int port = Integer.parseInt(portText.getText());
                String name = nameText.getText();

                //name 为空，提示输入名字
                if ("".equals(name)) {
                    textArea.append("请输入名字！！！！\n");
                } else {
                    try {
                        s = new Socket(ip, port);
                        bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                        //发送姓名
                        send(name);
                    } catch (IOException ex) {
                    	System.out.println("客户端连接服务器错误");
                        textArea.append("客户端连接服务器错误\n");
                    }

                    new Thread(new ClientReceive(s, textArea)).start();
                }
            }
        });

        //发送信息
        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String msg = msgText.getText();

                //服务器未连接，无法发送信息
                if (s == null) {
                    textArea.append("服务器未连接，请连接服务器！！！\n");
                } else {
                	
                	if(!"".equals(msg)){
                		 //发送信息
                        try {
                        	send(msg);
                            msgText.setText("");
                            msgText.requestFocus();      
                        } catch (IOException e1) {                        
                            System.out.println("客户端发送信息错误");
                            textArea.append("客户端发送信息错误\n");
                        }
                	}else{
                		textArea.append("发送消息为空!!!\n");
                	}
                }
            }

        });


    }

    //发送信息
    void send(String msg) throws IOException{
    	 bw.write(msg);
         bw.newLine();
         bw.flush();
    }
    

}
