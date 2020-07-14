package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.github.sarxos.webcam.Webcam;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import javax.swing.JScrollPane;

public class MainApp extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp frame = new MainApp();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setResizable(false);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	Webcam w;
	JLabel video;
	JTextArea textArea;
	JLabel img;
	public MainApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 899, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Bg = new JLabel("");
		Bg.setBounds(0, 0, 883, 471);
		contentPane.add(Bg);
		Bg.setIcon(new ImageIcon("C:\\Users\\bhand\\eclipse-workspace\\TextXtracter\\Images\\B.png"));
		
		video = new JLabel("");
		video.setHorizontalAlignment(SwingConstants.CENTER);
		video.setBounds(20, 52, 451, 337);
		Bg.add(video);
		video.setIcon(new ImageIcon("C:\\Users\\bhand\\eclipse-workspace\\TextXtracter\\Images\\Camera.png"));
		
		JLabel Camera = new JLabel("Camera");
		Camera.setHorizontalAlignment(SwingConstants.CENTER);
		Camera.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 18));
		Camera.setBounds(10, 11, 158, 30);
		Bg.add(Camera);
		
		JLabel Text = new JLabel("Text");
		Bg.add(Text);
		Text.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
		Text.setHorizontalAlignment(SwingConstants.CENTER);
		Text.setBounds(399, 405, 123, 38);
		
		JButton getText = new JButton("Press");
		Bg.add(getText);
		getText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Playvideo().start();
				}
		});
		getText.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 18));
		getText.setBounds(506, 135, 109, 40);
		
		JLabel lblNewLabel = new JLabel("");
		Bg.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 23));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(630, 23, 232, 159);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\bhand\\eclipse-workspace\\TextXtracter\\Images\\Logo.png"));
		
		
	
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBackground(new Color(0, 153, 204));
		textArea.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 13));
		textArea.setBounds(545, 196, 304, 264);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(545, 195, 308, 265);
		contentPane.add(scrollPane);
		
		
		JButton pdf = new JButton("Import PDF");
		pdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
				fileChooser.setBounds(0, 79, 625, 340);
				contentPane.add(fileChooser);
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();
					String address = f.getAbsolutePath();
					// this is the path of the pdf files
					//use api here to open them
					try {
						PDDocument doc = PDDocument.load(new File(address));
						if(!doc.isEncrypted())
						{
							PDFTextStripperByArea stripper = new PDFTextStripperByArea();
							stripper.setSortByPosition(true);
							PDFTextStripper pstripper = new PDFTextStripper();
							String str = pstripper.getText(doc);
							Scanner sc = new Scanner(str);
							String res = "";
							while(sc.hasNextLine())
							{
								res+=sc.nextLine()+"\n";
							}
							sc.close();
							doc.close();
							img.setIcon(null);
							textArea.setText(res);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		Bg.add(pdf);
		pdf.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 16));
		pdf.setBounds(503, 60, 136, 40);
		
		img = new JLabel("");
		img.setBounds(545, 195, 308, 265);
		Bg.add(img);
		img.setForeground(Color.WHITE);
		img.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 16));
		img.setHorizontalAlignment(SwingConstants.CENTER);
		img.setIcon(new ImageIcon("C:\\Users\\bhand\\eclipse-workspace\\TextXtracter\\Images\\Text.png"));
		
		
		
	
			
	}
	class Playvideo extends Thread{
		Image i;
		boolean flag = false;
		public void run()
		{
			if(flag == false) {
			w = Webcam.getDefault();
			w.setViewSize(new Dimension(640,480));
			int start  = ((int) System.currentTimeMillis()/1000)%60;
			System.out.println(start);
			w.open();
			int elapsed = 0;
			while(elapsed<=7) {
				int stop =  ((int)System.currentTimeMillis()/1000)%60;
				elapsed = stop - start;
				 i = w.getImage();
				 video.setIcon(new ImageIcon(i));
				
			}
			w.close();
			try {
				ImageIO.write((RenderedImage) i,"PNG",new File("pdftoconvert.tiff"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//enhance image
			
			
			BufferedImage  image;
			   int width;
			   int height;
			   File finalimg;
			  try {
			         File input = new File("C:\\Users\\bhand\\eclipse-workspace\\TextXtracter\\pdftoconvert.tiff");
			         image = ImageIO.read(input);
			         width = image.getWidth();
			         height = image.getHeight();
			         
			         //change to  grayscale
			         for(int i=0; i<height; i++) {
			         
			            for(int j=0; j<width; j++) {
			            
			               Color c = new Color(image.getRGB(j, i));
			               int red = (int)(c.getRed() * 0.299);
			               int green = (int)(c.getGreen() * 0.587);
			               int blue = (int)(c.getBlue() *0.114);
			               Color newColor = new Color(red+green+blue,red+green+blue,red+green+blue);
			               image.setRGB(j,i,newColor.getRGB());
			            }
			         }
			         
			         finalimg = new File("C:\\Users\\bhand\\eclipse-workspace\\TextXtracter\\grayscale.tiff");
			         ImageIO.write(image, "tiff", finalimg);
			         
			         
			      } catch (Exception e) {
			    	  System.out.println("grayScale conversion error");
			      }
			  
			  //remove noise
			  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			  Mat src = Highgui.imread("C:\\Users\\bhand\\eclipse-workspace\\TextXtracter\\pdftoconvert.tiff",0);
			  Mat dst = new Mat();
			  Imgproc.GaussianBlur(src, dst,new Size(5,5), 0);
			  Imgproc.adaptiveThreshold(dst, dst,300, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 15,10);
			  Highgui.imwrite("C:\\Users\\bhand\\eclipse-workspace\\TextXtracter\\thresh.tiff",dst);
			  
			  
			  
			ITesseract imagefinal = new Tesseract();
			try {
				String res = imagefinal.doOCR(new File("C:\\Users\\bhand\\eclipse-workspace\\TextXtracter\\thresh.tiff"));
				textArea.setText(res);
			} catch (TesseractException e) {
				// TODO Auto-generated catch block
				System.out.println("Bad Image Quality");
				e.printStackTrace();
			}
			flag = true;
		}
		}
}
}
