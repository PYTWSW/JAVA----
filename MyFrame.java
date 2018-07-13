package edu.csuft.wsw.mywangpan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

/**
 * 程序窗口
 *
 * @author wsw
 */
public class MyFrame extends JFrame {

    JButton button1;
    JButton button2;
    JLabel label1;

    static JFileChooser jfc = new JFileChooser();

    public MyFrame() {

        this.setTitle("网盘文件传输");

        label1 = new JLabel("点击按钮即可上传文件");
        label1.setBounds(100, 100, 10, 5);


        Server server = new Server();
        button1 = new JButton("启动服务器");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                server.start();  //服务器启动
            }
        });



        button2 = new JButton("启动客服端");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Client().start();  //客户端启动
            }
        });


        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel1.setLayout(new GridLayout(2,1));
        panel2.setLayout(new FlowLayout());
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER));

        panel2.add(button1);
        panel2.add(button2);

        panel3.add(label1);

        panel1.add(panel3);
        panel1.add(panel2);

        this.add(panel1);

        this.setResizable(false);
        this.setBounds(100, 100, 700, 250);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    /**
     * 文件选择框的设置
     *
     */

    public  static File chooseFile() {

        FileSystemView fsv = FileSystemView.getFileSystemView();
        jfc.setCurrentDirectory(fsv.getHomeDirectory());
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.showDialog(new JLabel(),"确定");
        File f = jfc.getSelectedFile();
        return f;
    }

    public static void main(String[] args) {
        new MyFrame();

    }


}
