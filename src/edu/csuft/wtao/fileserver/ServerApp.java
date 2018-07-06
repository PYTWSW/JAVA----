package edu.csuft.wtao.fileserver;

/**
 * 网盘服务端
 * 
 * @author wtao
 *
 */
public class ServerApp {

	public static void main(String[] args) {
		
		FileServer fileServer = new FileServer();
		fileServer.start();
	}
}
