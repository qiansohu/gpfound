//该文件废弃不用

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

public class gpgdfileProcess {

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
		
		HashMap gpdm = new HashMap();
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
				String ss = (String)fs.getName().substring(0,6);
				gpdm.put(ss, "");		     
		    
			}
		}
		
		Collection cl = gpdm.keySet();
	      Iterator itr = cl.iterator();
	      String gpdmList= new String();
	      while (itr.hasNext()) {
	    	  gpdmList=gpdmList+"\r\n"+itr.next();  
	     }
        
	     
	            bw.append(gpdmList);
	            bw.newLine();
	       
	     
				
		  bw.close();		
		  System.out.println( "所有文件处理结束，all.txt已经生成 "); 
    }
	
	
}
