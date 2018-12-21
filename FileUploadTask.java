package edu.csuft.wsw.mywangpan;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.util.Date;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 定义文件上传的具体操作，交给线程池执行的一个任务
 *
 * @author wsw
 *
 */
public class FileUploadTask implements Runnable {

    /**
     * 服务器与客户端通信的连接（管道）
     */
    Socket socket;

    /**
     *建立数据库的连接
     *
     */
    private static String url = "jdbc:mysql://localhost:3306/wsw";
    private static String user = "root";
    private static String pass = "";
    private static String mysql = "com.mysql.jdbc.Driver";
    private static String addsql = "insert into file_date values(?,?)";

    Map<String, String> map;
    public FileUploadTask(Socket socket, Map<String, String> map) {
        this.socket = socket;
        this.map = map;
    }

    @Override
    public void run() {
        // 存储文件的路径
        String path = "/Users/wsw/Desktop/大二.WhiteNight/WswJavaTest";
        // 根据文件内容的散列值生成
        String fileName = "";

        // 接收数据
        // 容量可变的内存数组
        ByteArrayOutputStream ram = new ByteArrayOutputStream();
        byte[] buf = new byte[1024 * 4];
        int size;
        try (InputStream in = socket.getInputStream()) {

            while (-1 != (size = in.read(buf))) {
                ram.write(buf, 0, size);
            }
        } catch (Exception e) {
        }
        // 获得所有数据
        byte[] data = ram.toByteArray();

        // 生产文件的消息摘要（SHA-256）,使用摘要信息做文件名
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);
            fileName = new BigInteger(1, hash).toString(16);

            map.put(fileName, "");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 写入新的文件
        try (FileOutputStream out = new FileOutputStream(new File(path, fileName))){
            out.write(data);
            out.flush();
            System.out.println("成功接收：" + fileName);

            /**
             *设置文件的散列值和日期并上传到数据库
             */
            FileDate myfile=new FileDate();
            myfile.setHashvalue(fileName);
            myfile.setDate(new Date().toString());
            add(myfile);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public boolean add(FileDate file) {
        boolean isSuccess=true;
        try {
            Class.forName(mysql);
            Connection connection = DriverManager.getConnection(url,user,pass);
            PreparedStatement ps = connection.prepareStatement(addsql);
            ps.setString(1, file.getHashvalue());
            ps.setString(2, file.getDate());
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
