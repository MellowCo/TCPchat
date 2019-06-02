package talk_server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TalkServer extends JFrame {

    private JPanel contentPane;
    private JTextField ipText;
    private JTextField portText;
    final JButton startBut;
    final JTextArea textArea;
    

    /**
     * Create the frame.
     */
    public TalkServer() {
        setTitle("聊天服务器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 555, 441);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        JLabel lblip = new JLabel("本机IP");
        lblip.setFont(new Font("宋体", Font.PLAIN, 14));

        ipText = new JTextField();
        ipText.setColumns(10);
        ipText.setText("127.0.0.1");
        
        JLabel label = new JLabel("监听端口");
        label.setFont(new Font("宋体", Font.PLAIN, 14));
        
        portText = new JTextField();
        portText.setColumns(10);
        portText.setText("9999");
        
        startBut= new JButton("开启服务器");
        
        JScrollPane scrollPane = new JScrollPane();
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addComponent(lblip, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(ipText, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
                            .addGap(18)
                            .addComponent(label, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(portText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(46)
                            .addComponent(startBut)
                            .addContainerGap(23, Short.MAX_VALUE))
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addComponent(scrollPane)
                            .addGap(23))))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(28)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblip, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                        .addComponent(ipText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                        .addComponent(portText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(startBut))
                    .addGap(18)
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                    .addGap(12))
        );
        
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        scrollPane.setViewportView(textArea);
        contentPane.setLayout(gl_contentPane);
         
        
        startBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new ServerOpen(Integer.parseInt(portText.getText()),textArea)).start();
			}
		});
        
    }
    

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TalkServer frame = new TalkServer();
                    frame.setVisible(true);
                          
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
    }
}
