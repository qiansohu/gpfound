/*
 * $Id$
 * 
 */
package com.asprise.util.ocr.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import com.asprise.util.ocr.OCR;
import com.asprise.util.ui.JImageDialog;
import com.asprise.util.ui.JImageDialogListener;

/**
 * Sample JImageDialog application.
 * 
 */
public class DemoUI extends JPanel implements JImageDialogListener {

	BufferedImage image;

	public DemoUI() {
		super();
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JImageDialog dialog = new JImageDialog();
				if (image != null)
					dialog.setImage(image);
				dialog.addImageDialogListener(DemoUI.this);
				dialog.showDialog();
			}
		});
		setToolTipText("Click to edit/view image in JImageDialog ...");
	}

	/**
	 * Display image if any.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Paint background.
		if (image != null)
			g.drawImage(image, 0, 0, null);
		else
			g.drawString("Click to set image ...", 20, 20);
	}

	public Dimension getMinimumSize() {
		if (image == null)
			return new Dimension(600, 400);
		else
			return new Dimension(image.getWidth(), image.getHeight());
	}

	public Dimension getPreferredSize() {
		return getMinimumSize();
	}

	public void setImage(BufferedImage image) {
		this.image = image;

		setPreferredSize(getPreferredSize());
		revalidate();
		repaint();
	}

	public static void main(String[] args) {

		System.out.println("Native library path is: " + System.getProperty("java.library.path"));

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (NoClassDefFoundError er) {
			// do nothing
		} catch (Exception e) {
			// e.printStackTrace();
		}

		final JFrame frame = new JFrame("Asprise OCR Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel left = new JPanel();
		left.setLayout(new BorderLayout());

		final DemoUI panel = new DemoUI();

		JButton button = new JButton("Click here to set image ...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JImageDialog dialog = new JImageDialog(frame, "Sample", true);
				panel.setImage(dialog.showDialog());
			}
		});

		final JTextArea textArea = new JTextArea();

		JButton buttonOCR = new JButton("Perform OCR");
		buttonOCR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (panel.image == null) {
					textArea.append("\nPlease set an image first ...\n");
					return;
				}
				textArea.append("\n--- OCR starts ---\n");
				BufferedImage image1 = panel.image.getSubimage(1525, 58, 71, 21);
				String s = new OCR().recognizeCharacters(image1);
				textArea.append(s);
				System.out.println("\n---- eagleResult: ------- \n" + s);
				textArea.append("--- END ---");
			}
		});

		left.add(button, BorderLayout.NORTH);
		left.add(new JScrollPane(panel), BorderLayout.CENTER);
		left.add(buttonOCR, BorderLayout.SOUTH);

		frame.getContentPane().setLayout(new GridLayout());
		frame.getContentPane().add(left);
		frame.getContentPane().add(new JScrollPane(textArea));

		frame.pack();
		// Utility.centerWindow(frame);
		frame.setVisible(true);

		try {
			if (VMSupported("1.4")) {
				File file = new File("./sample-images/ocr.gif");
				BufferedImage image = ImageIO.read(file);
				panel.setImage(image);
			}
		} catch (Exception e) {

		}

		// if(! Utility.VMSupported("1.3")) {
		// System.err.println("Your Java VM version is " +
		// System.getProperty("java.vm.version") + ", version 1.3 or above is
		// required!");
		// }
	}

	/**
	 * Test whether the java runtime meets the minimium requirement.
	 * 
	 * @param minimiumVersion
	 *            mim version to run the application. eg. "1.2.1", "1.3".
	 * @return
	 */
	public static boolean VMSupported(String minimiumVersion) {
		if (minimiumVersion.compareTo(System.getProperty("java.vm.version")) <= 0)
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asprise.util.UI.JImageDialogListener#onCancel()
	 */
	public void onCancel() {
		setImage(null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asprise.util.UI.JImageDialogListener#onImageSet(java.awt.image.
	 * BufferedImage)
	 */
	public void onImageSet(BufferedImage image) {
		setImage(image);

	}

}