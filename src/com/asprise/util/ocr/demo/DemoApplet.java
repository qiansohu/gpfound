package com.asprise.util.ocr.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.asprise.util.ocr.OCR;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 * How to sign the jar files:
 * 
 * To generate key & sign jars:
 * 
 * keytool -genkey -alias asprise -keypass asprise -keystore asprise.keystore
 * 
 * jarsigner -keystore asprise.keystore -storepass asprise -keypass asprise aspriseOCR.jar asprise
 * 
 * jarsigner -keystore asprise.keystore -storepass asprise -keypass asprise demo.jar asprise
 * 
 * 
 */

public class DemoApplet extends JApplet {
  private boolean isStandalone = false;
  JPanel panelMain = new JPanel();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField textImagePath = new JTextField();
  JButton buttonBrowse = new JButton();
  JButton buttonOCR = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea textLog = new JTextArea();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  //Get a parameter value
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //Construct the applet
  public DemoApplet() {
  }

  //Initialize the applet
  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception {
    this.setContentPane(panelMain);
    this.setSize(new Dimension(400,300));
    panelMain.setLayout(gridBagLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 16));
    jLabel1.setForeground(SystemColor.desktop);
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setText("Asprise OCR v4.0 Demo Applet");
    jLabel2.setText("Image File");
    buttonBrowse.setText("Browse ...");
    buttonBrowse.addActionListener(new DemoApplet_buttonBrowse_actionAdapter(this));
    buttonOCR.setText("Perform OCR");
    buttonOCR.addActionListener(new DemoApplet_buttonOCR_actionAdapter(this));
    textLog.setFont(new java.awt.Font("Dialog", 0, 14));
    textLog.setForeground(SystemColor.desktop);
    textLog.setEditable(true);
    textLog.setText("");
    textImagePath.setText("");
    //this.getContentPane().add(panelMain, BorderLayout.CENTER);
    panelMain.add(jLabel1,   new GridBagConstraints(0, 0, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), 0, 13));
    panelMain.add(jLabel2,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
    panelMain.add(textImagePath,   new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 10), 0, 0));
    panelMain.add(buttonBrowse,   new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 0, 10), 0, 0));
    panelMain.add(buttonOCR,   new GridBagConstraints(0, 2, 3, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), 0, 0));
    panelMain.add(jScrollPane1,   new GridBagConstraints(0, 3, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(15, 10, 10, 10), 0, 0));
    jScrollPane1.getViewport().add(textLog, null);
  }

  //Get Applet information
  public String getAppletInfo() {
    return "Applet Information";
  }

  //Get parameter info
  public String[][] getParameterInfo() {
    return null;
  }

  //Main method
  public static void main(String[] args) {
    DemoApplet applet = new DemoApplet();
    applet.isStandalone = true;
    JFrame frame = new JFrame();
    //EXIT_ON_CLOSE == 3
    frame.setDefaultCloseOperation(3);
    frame.setTitle("Applet Frame");
    frame.getContentPane().add(applet, BorderLayout.CENTER);
    applet.init();
    applet.start();
    frame.setSize(400,320);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
    frame.setVisible(true);
  }

  //static initializer for setting look & feel
  static {
    try {
      //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch(Exception e) {
    }
  }
  
  void log(String mesg) {
  	textLog.append(mesg);
  	textLog.append("\n");
  	textLog.setSelectionStart(textLog.getText().length());
  	textLog.setSelectionEnd(textLog.getText().length());
  }

  void buttonBrowse_actionPerformed(ActionEvent e) {
  	JFileChooser chooser = new JFileChooser();
  	if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
  		textImagePath.setText(chooser.getSelectedFile().getAbsolutePath());
  		log("File chosen: " + textImagePath.getText());
  	}else{
  		log("File choosing cancelled.");
  	}
  }

  void buttonOCR_actionPerformed(ActionEvent e) {
  	File file = new File(textImagePath.getText());
	
		log("Trying to perform OCR on image: " + file.getAbsolutePath());
		
		//OCR.setLibraryPath("E:/Twain/OCR/OCR+i/Release/AspriseOCR.dll");

		try {
			BufferedImage image =
				ImageIO.read(file);

			String s = new OCR().recognizeEverything(image);
			log("\n---- RESULTS: ------- \n" + s + "\n\n");
		} catch (Throwable e1) {
			log(e1.toString());
			if(e1 instanceof UnsatisfiedLinkError) {
				log("java.libary.path = " + System.getProperty("java.library.path"));
				log("Please put AspriseOCR.dll in any of the directory listed above." + "\n");
			}
		}
  }
}

class DemoApplet_buttonBrowse_actionAdapter implements java.awt.event.ActionListener {
  DemoApplet adaptee;

  DemoApplet_buttonBrowse_actionAdapter(DemoApplet adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.buttonBrowse_actionPerformed(e);
  }
}

class DemoApplet_buttonOCR_actionAdapter implements java.awt.event.ActionListener {
  DemoApplet adaptee;

  DemoApplet_buttonOCR_actionAdapter(DemoApplet adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.buttonOCR_actionPerformed(e);
  }
}
