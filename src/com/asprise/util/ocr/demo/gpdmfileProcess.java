package com.asprise.util.ocr.demo;

/* eagleqian submit to github 20180223
 * 该java用于对每日采集后的文件分开sc文件或 股票代码的文件
 * 进行每499个文件一个目录的归档，以便上载百度云
 * 
 * 该java不负责OCR文件内容，所以不会对文件进行扫描识别更名动作
 * 
 * param1  源目录
 * param2 目标目录
 */

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;

import com.asprise.util.ocr.OCR;

public class gpdmfileProcess {

	public static void main(String[] args) throws IOException {

		if (args.length <= 1) {
			System.out.println("------------------参数不够-----------------------------------------");
			for (int i = 1; i < args.length; i++)
				System.out.println(args[i]+"\r\n");
			System.out.println("---------------- -------------------------------------------\n");
		 } else {	
			 System.out.println("程序用途：该java用于对每日采集后的文件分开sc文件或 股票代码的文件进行每499个文件一个目录的归档，以便上载百度云 -\n");
			 appendChar(args);
		 }

		
	}
	
	/* 创建目录 */
	 public static boolean createDir(String destDirName) {  
	        File dir = new File(destDirName);  
	        if (dir.exists()) {  
	            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");  
	            return false;  
	        }  
	        if (!destDirName.endsWith(File.separator)) {  
	            destDirName = destDirName + File.separator;  
	        }  
	        //创建目录  
	        if (dir.mkdirs()) {  
	            System.out.println("创建目录" + destDirName + "成功！");  
	            return true;  
	        } else {  
	            System.out.println("创建目录" + destDirName + "失败！");  
	            return false;  
	        }  
	    }  
	     
	
	public  static void appendChar( String[]  args ) throws IOException {
		
		HashMap gpdm = new HashMap();
		String path = args[0] ; // 路径 "D:/20170928"
		File f = new File(path);
		if (!f.exists()) {
			System.out.println(path + " not exists");
			return;
		}		
		
		File outFile = new File(args[0] + "/" + "all.txt");

		if (outFile.exists()) {
			outFile.delete();
			System.out.println(" all.txt 该文件存在");
		}

		outFile.createNewFile();

		Writer ow = new FileWriter(outFile);
		BufferedWriter bw = new BufferedWriter(ow);

		File fa[] = f.listFiles();
		//创建如干个目录，分别用于存放499个文件
		for (int j = 0; j < 15 ; j++) {
		createDir(args[1] + "/" + j + "/");
		}
		
		for (int i = 0; i < fa.length; i++) {
			
			File fs = fa[i];
			if (fs.isDirectory()) {
				System.out.println(fs.getName() + " [目录]");
			} else if (!fs.getName().equals("all.txt")) {
				String ss = (String) fs.getName().substring(0, 6);
				if ( ( ss != null ) && !ss.substring(0,2).equals("sc")) 
					gpdm.put(ss, "");/*这里收集股票代码，用于加入到all.txt  */
				
				File newfile=null;
				if (i<500 ){
				 newfile = new File(args[1] + "/0/" + fs.getName());}
				else if (i>=500 && i<1000  ){
					 newfile = new File(args[1] + "/1/" + fs.getName());}
				else if (i>=1000 && i<1500  ){
					 newfile = new File(args[1] + "/2/" + fs.getName());}
				else if (i>=1500 && i<2000  ){
					 newfile = new File(args[1] + "/3/" + fs.getName());}
				else if (i>=2000 && i<2500  ){
					 newfile = new File(args[1] + "/4/" + fs.getName());}
				else if (i>=2500 && i<3000  ){
					 newfile = new File(args[1] + "/5/" + fs.getName());}
				else if (i>=3000 && i<3500  ){
					 newfile = new File(args[1] + "/6/" + fs.getName());}
				else if (i>=3500 && i<4000  ){
					 newfile = new File(args[1] + "/7/" + fs.getName());}
				else if (i>=4000 && i<4500  ){
					 newfile = new File(args[1] + "/8/" + fs.getName());}
				else if (i>=4500 && i<5000  ){
					 newfile = new File(args[1] + "/9/" + fs.getName());}
				else if (i>=5000 && i<5500  ){
					 newfile = new File(args[1] + "/10/" + fs.getName());}
				else if (i>=5500 && i<6000  ){
					 newfile = new File(args[1] + "/11/" + fs.getName());}
				else if (i>=6000 && i<6500  ){
					 newfile = new File(args[1] + "/12/" + fs.getName());}
				else if (i>=6500 && i<7000 ){
					 newfile = new File(args[1] + "/13/" + fs.getName());}	
				else if (i>=7000  && i<7500 ){
					 newfile = new File(args[1] + "/14/" + fs.getName());}	
				else if (i>=7500  ){
					 newfile = new File(args[1] + "/15/" + fs.getName());}	
                //  这里对文件进行目录的更名
				fs.renameTo(newfile);

			}
		}
		
        // 根据前面的数据集，对这些不重复的gpdm数据输出到all.txt文件，以便导入到软件或数据中。		
		Collection cl = gpdm.keySet();
		Iterator itr = cl.iterator();
		String gpdmList = new String();
		while (itr.hasNext()) {
			gpdmList = gpdmList + "\r\n" + itr.next();
		}

		bw.append(gpdmList);
		bw.newLine();

		bw.close();
		System.out.println("所有文件分目录存放好，包含gpdm的all.txt已经生成 ");
    }
	
	
}
