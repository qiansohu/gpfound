
/*���ڶ�ͨ���ŵ����Ĺ�Ʊ���������ļ�����
     ȡ���ļ�����Ȼ���ÿ���ļ��е�ÿһ�м����Ʊ���룬�Ա㵼�뵽gprx���ݻ�gprxfuquan���ݱ���
  param1 D:/gpnew/fuquan Ϊ��Ҫ�����txt���ڵ�Ŀ¼
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
			System.out.println("------------------��������-----------------------------------------");
			for (int i = 1; i < args.length; i++)
				System.out.println(args[i]+"\r\n");
			System.out.println("---------------- -------------------------------------------\n");
		 } else {			 
			 appendChar(args);
		 }

		
	}
	
	public  static void appendChar( String[]  args ) throws IOException {
		
		
		String path = args[0] ; // ·�� "D:/20170928"
		File f = new File(path);
		if (!f.exists()) {
			System.out.println(path + " not exists");
			return;
		}		
		
		File outFile = new File(args[0]+"/"+"all.txt");
			
		if ( outFile.exists() ) { outFile.delete();
		   System.out.println(  " all.txt ���ļ�����");
		}
		
		outFile.createNewFile();
		
		  Writer ow = new FileWriter(outFile);
		  BufferedWriter bw = new BufferedWriter(ow);

		File fa[] = f.listFiles();
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];	
		
						
			if (fs.isDirectory()) {
				System.out.println(fs.getName() + " [Ŀ¼]");
			} else if ( !fs.getName().equals("all.txt")  ) {
				String fileName =(String)fs.getName().substring(0,6);
				//File file = new File(args[0]);
				File inFile =fs;
				System.out.println( "�ļ�����=" + i  +  " , �����ļ�= " + fs.getName() );
			     
		        Reader ir = new FileReader(inFile);                 
		      
		         
		        BufferedReader br = new BufferedReader(ir);
		      
		         
		        while(br.ready()) {
		            String line = br.readLine();
		            line = fileName+ "," + line;
		            bw.append(line);
		            bw.newLine();
		        }
		        System.out.println( "�ļ�����= " + fs.getName() ); 
		        br.close();
		      
				
			}
		}
				
		  bw.close();		
		  System.out.println( "�����ļ�������� ,ȫ�����˹�Ʊ���룬���ϲ���all��txt����ļ��У�ֻҪ����gprxfuquan�м��ɡ�"); 
    }
	
	
}
