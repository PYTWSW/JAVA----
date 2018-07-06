package edu.csuft.wtao.fileserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 定义一个交给线程/线程池处理的任务【文件上传】
 * 
 * @author wtao
 *
 */
public class FileUploadTask implements Runnable {
	
	/**
	 * 套接字
	 */
	Socket socket;

	public FileUploadTask(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		String path = "/Users/wtao/files";
		// 使用文件的摘要值（散列值 - SHA-256）做文件名
		String fileName = "";
		
		// 具体的上传操作
		
		// 长度可变的内存数组
		ByteArrayOutputStream dataUpload = new ByteArrayOutputStream();
		
		// 接收数据
		try (InputStream in = socket.getInputStream()){
			byte[] buf = new byte[1024 * 8];
			int size;
			while (-1 != (size = in.read(buf))) {
				dataUpload.write(buf, 0, size);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获得了所有数据
		byte[] data = dataUpload.toByteArray();
		
		// 获得文件的散列值
		try {
			byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);
			fileName = new BigInteger(1, hash).toString(16);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		try (FileOutputStream out 
				= new FileOutputStream(new File(path, fileName))){
			
			out.write(data);
			out.close();
			System.out.println("接收完成：" + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
