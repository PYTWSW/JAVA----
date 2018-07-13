package edu.csuft.wsw.mywangpan;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 网盘服务器
 *
 * @author wsw
 *
 */
public class Server extends Thread {

    /**
     * 服务端套接字：
     */
    ServerSocket serverSocket;

    HashMap<String, String> map = new HashMap<>();

    /**
     * 服务端端口号
     */
    int port = 9000;

    /**
     * 线程池
     */
    ExecutorService pool;

    public void run() {
        // 线程池
        pool = Executors.newFixedThreadPool(9);
        pool = Executors.newSingleThreadExecutor();
        pool = Executors.newCachedThreadPool();

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务器启动了...");

            while (true) {
                Socket socket = serverSocket.accept();
                // 异步
                pool.execute(new FileUploadTask(socket, map));
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}


