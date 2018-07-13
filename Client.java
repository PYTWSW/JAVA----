package edu.csuft.wsw.mywangpan;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 网盘客户端
 *
 * @author wsw
 *
 */
public class Client extends Thread{

    /**
     * 套接字：封装了网络通信中的底层细节（插座）
     * 输出流：发送数据
     * 输入流：接收数据
     */
    Socket socket;

    /**
     * 服务器的地址
     */
    String address = "127.0.0.1";

    // 核心类
    InetAddress address2;

    /**
     * 端口号
     */
    int port = 9000;

    /**
     * 建立数据库的连接
     */
    private static String url = "jdbc:mysql://localhost:3306/wsw";
    private static String user = "root";
    private static String pass = "pyt19980801";
    private static String mysql = "com.mysql.jdbc.Driver";
    private static String addsql = "insert into file_name values(?,?)";

    /**
     * 启动客户端
     */
    public void run() {
        // 通信协议（契约）
        // TCP/IP
        // ------------
        // 应用层	HTTP、FTP、POP/SMTP、XMPP、MQTT
        // 传输层	TCP、UDP
        // 网络层	IP
        // 物理层	电气接口的相关规范
        // ------------
        // 创建 TCP 套接字

        try {
            socket = new Socket(address, port);
            System.out.println("客户端建立连接");


            // 发送文件
            OutputStream out = socket.getOutputStream();

            //传送选择到的文件
            File file = MyFrame.chooseFile();
            //
            ByteArrayOutputStream ram = new ByteArrayOutputStream();
            byte[] buf = new byte[1024 * 4];
            int size;
            try (FileInputStream in = new FileInputStream(file)) {

                while (-1 != (size = in.read(buf))) {
                    ram.write(buf, 0, size);
                }
            } catch (Exception e) {
            }
            byte[] data = ram.toByteArray();
            String hashString;
            try {
                /**
                 * 设置文件的散列值和文件名并上传到数据库
                 */
                byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);
                hashString = new BigInteger(1, hash).toString(16);
                add(hashString,file.getName());

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            // 获得所有数据
            BufferedInputStream in=new BufferedInputStream(new FileInputStream(file));
            while (-1 != (size = in.read(buf))) {
                // 使用从套接字获得输出流【发送】数据
                out.write(buf, 0, size);
                // 刷新缓冲区
                out.flush();
            }
            System.out.println("上传完成");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    socket = null;
                }
            }
        }


    }

    public static boolean add(String hashName,String fileName) {
        boolean isSuccess=true;
        try {
            Class.forName(mysql);
            Connection connection = DriverManager.getConnection(url,user,pass);
            PreparedStatement ps = connection.prepareStatement(addsql);
            ps.setString(1, hashName);
            ps.setString(2, fileName);
            int row=ps.executeUpdate();
            System.out.println(isSuccess);
            if(row<=0) {
                isSuccess=false;
            }
            connection.close();
            ps.close();
        }catch (ClassNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        }catch(SQLException e) {
            e.printStackTrace();
            isSuccess=false;
        }
        return isSuccess;
    }


}
