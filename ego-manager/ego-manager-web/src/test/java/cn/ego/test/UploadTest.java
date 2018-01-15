package cn.ego.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class UploadTest {

	public static void main(String[] args) {

		FTPClient ftpClient = new FTPClient();
		
		try {
			ftpClient.connect("192.168.35.129");
			ftpClient.login("ftpuser", "ftpuser");
			FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Sully\\Desktop\\img\\2.jpg"));
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			boolean b = ftpClient.storeFile("2.jpg", inputStream);//存在user里面
			System.out.println(b);
			inputStream.close();

			ftpClient.logout();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
}
