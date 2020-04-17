package com.wgz.test;

import java.io.File;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.wgz.utils.FTPUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class FtpTest {
	public static void main(String[] args) throws Exception {
		String server = "39.106.254.228";
	    int port = 50021;
	    String userName = "xgjncc_ro";
	    String userPassword = "KFJH239hjkhf3HksO3H";
	    String merId = "111261";
	    String localPath = "D:\\slzx\\业财一体化\\新高教\\NC\\测试数据\\ftp\\";
	    String fileName = "111261_XS_SS_20191212.txt";
		FTPUtil ftp = new FTPUtil(server, port, userName, userPassword);
		if (!ftp.open()) {
            log.info("====链接ftp报错====");
        }
//		FTPClient fc = ftp.getFtpClient();
//		Boolean flag = fc.changeWorkingDirectory("/"+merId+"/");
//		log.info(flag.toString());
//		FTPFile[] listFiles = fc.listFiles();
//		for(int i=0;i<listFiles.length;i++) {
//			System.out.println(i);
//			log.info(listFiles[i].getName());
//		}
//		Boolean upFlag = ftp.upload("D:\\slzx\\业财一体化\\新高教\\NC\\测试数据\\new 1.txt", "new 1.txt", "/111261");
//		log.info(upFlag.toString());
		boolean b = ftp.get("/"+merId+"/"+fileName, localPath+fileName);
		log.info(b+"");
//		if(ftp.open()) {
//			ftp.changeDir(merId);
//			ftp.get(fileName, localPath+fileName);
//		}
		ftp.close();
	}
}
