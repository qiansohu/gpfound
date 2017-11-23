
/*用于对通达信导出的股票日线数据文件处理。
     取得文件名，然后对每个文件中的每一行加入股票代码，以便导入到gprx数据或gprxfuquan数据表中
  param1 D:/gpnew/fuquan 为需要处理的txt所在的目录
*/


package com.asprise.util.ocr.demo;


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

public class tdxFileProcess {

	public static void main(String[] args) throws IOException {

		if (args.length <= 0) {
			System.out.println("------------------参数不够-----------------------------------------");
			for (int i = 1; i < args.length; i++)
				System.out.println(args[i]+"\r\n");
			System.out.println("---------------- -------------------------------------------\n");
		 } else {			 
			 appendChar(args);
		 }

		
	}
	
	public  static void appendChar( String[]  args ) throws IOException {
		
		
		String path = args[0] ; // 路径 "D:/20170928"
		File f = new File(path);
		if (!f.exists()) {
			System.out.println(path + " not exists");
			return;
		}		
		
		File outFile = new File(args[0]+"/"+"all.txt");
			
		if ( outFile.exists() ) { outFile.delete();
		   System.out.println(  " all.txt 该文件存在");
		}
		
		outFile.createNewFile();
		
		  Writer ow = new FileWriter(outFile);
		  BufferedWriter bw = new BufferedWriter(ow);

		File fa[] = f.listFiles();
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];	
		
						
			if (fs.isDirectory()) {
				System.out.println(fs.getName() + " [目录]");
			} else if ( !fs.getName().equals("all.txt")  ) {
				String fileName =(String)fs.getName().substring(0,6);
				//File file = new File(args[0]);
				File inFile =fs;
				System.out.println( "文件序列=" + i  +  " , 处理文件= " + fs.getName() );
			     
		        Reader ir = new FileReader(inFile);                 
		      
		         
		        BufferedReader br = new BufferedReader(ir);
		      
		         
		        while(br.ready()) {
		            String line = br.readLine();
		            line = fileName+ "," + line;
		            bw.append(line);
		            bw.newLine();
		        }
		        System.out.println( "文件结束= " + fs.getName() ); 
		        br.close();
		      
				
			}
		}
				
		  bw.close();		
		  System.out.println( "所有文件处理结束 ,全部加了股票代码，并合并到all。txt这个文件中，只要导入gprxfuquan中即可。"); 
    }
	
	
}
