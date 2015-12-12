package nfc.example.hce;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;

import org.nfctools.examples.TerminalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.nfctools.scio.*;

public class HceDemo {

	private Logger log = LoggerFactory.getLogger(getClass());

	
	protected static String terminalName;
	protected static JTextArea id_txt;
	protected static JTextArea status_txt;
	protected static JTextArea doctor_txt;
	protected static JTextArea division_txt;
	protected static JTextArea day_txt;
	protected static JTextArea time_txt;
	protected static JTextArea num_txt;

	public void startNFCTerminal() {		
		try {
			CardTerminal cardTerminal = TerminalUtils.getAvailableTerminal().getCardTerminal();
			terminalName = cardTerminal.getName();
			HostCardEmulationTagScanner tagScanner = new HostCardEmulationTagScanner(cardTerminal);
			//status_txt.setText("Terminal Founded!\n");
			//status_txt.append("Terminal Name: " + cardTerminal.getName() + "\n");
			tagScanner.run();
		} catch (RuntimeException e) {
			Font statusFont = new Font("SansSerif", Font.BOLD, 18);
			HceDemo.status_txt.setForeground(Color.RED);
			HceDemo.status_txt.setFont(statusFont);
			status_txt.setText(
					"There is no Terminal Discovered!" + "\n" + "Please replug the device and restart the program");
		}

	}

	protected void initUI() {
		JFrame jf = new JFrame("HceDemo");

		jf.setSize(800, 400);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLayout(new BorderLayout());

		JPanel northPanel = new JPanel(new FlowLayout());
		JPanel centerPanel = new JPanel(new FlowLayout());
		JPanel westPanel = new JPanel(new FlowLayout());

		JLabel id_label = new JLabel("ID: ");

		id_txt = new JTextArea("");
		id_txt.setPreferredSize(new Dimension(100, 18));
		id_txt.setEditable(false);
		
		JLabel doctor_label = new JLabel("Doctor: ");
		
		doctor_txt = new JTextArea("");
		doctor_txt.setPreferredSize(new Dimension(50, 18));
		doctor_txt.setEditable(false);
		
		JLabel division_label = new JLabel("Division: ");
		
		division_txt = new JTextArea("");
		division_txt.setPreferredSize(new Dimension(25, 18));
		division_txt.setEditable(false);
		
		JLabel day_label = new JLabel("Date: ");
		
		day_txt = new JTextArea("");
		day_txt.setPreferredSize(new Dimension(100, 18));
		day_txt.setEditable(false);
		
		JLabel time_label = new JLabel("time: ");
		
		time_txt = new JTextArea("");
		time_txt.setPreferredSize(new Dimension(25, 18));
		time_txt.setEditable(false);
		
		JLabel num_label = new JLabel("Number: ");
		
		num_txt = new JTextArea("");
		num_txt.setPreferredSize(new Dimension(50, 18));
		num_txt.setEditable(false);
		
		


		JLabel status_label = new JLabel("Status: ");

		status_txt = new JTextArea("");
		Font statusFont = new Font("SansSerif", Font.BOLD, 100);
		HceDemo.status_txt.setFont(statusFont);
		
		
		
		

		//JScrollPane jsp = new JScrollPane(status_txt);
		JPanel jp = new JPanel();
		jp.add(status_txt);
		jp.setPreferredSize(new Dimension(650, 300));

		status_txt.setLineWrap(true);
		status_txt.setWrapStyleWord(true);

		status_txt.setPreferredSize(new Dimension(650, 300));
		status_txt.setEditable(false);

		//jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		northPanel.add(id_label);
		northPanel.add(id_txt);
		northPanel.add(doctor_label);
		northPanel.add(doctor_txt);
		northPanel.add(division_label);
		northPanel.add(division_txt);
		northPanel.add(day_label);
		northPanel.add(day_txt);
		northPanel.add(time_label);
		northPanel.add(time_txt);
		northPanel.add(num_label);
		northPanel.add(num_txt);

		westPanel.add(status_label);
		centerPanel.add(jp);

		jf.add(northPanel, BorderLayout.NORTH);
		jf.add(westPanel, BorderLayout.WEST);
		jf.add(centerPanel, BorderLayout.CENTER);

		jf.setVisible(true);

	}

	public static void main(String[] args) {

		HceDemo hd = new HceDemo();

		hd.initUI();

		hd.startNFCTerminal();

	}
	
//	public static void appendTxt(String txt)
//	{
//		status_txt.append(txt+"\n");
//		status_txt.setCaretPosition(status_txt.getDocument().getLength());
//	}
	public static void appendInfo(String[] txt)
	{
		id_txt.setText(txt[1]);
		doctor_txt.setText(txt[3]);
		division_txt.setText(txt[2]);
		day_txt.setText(txt[4]+"年"+txt[5]+"月"+txt[6]+"日");
		time_txt.setText(txt[7]);
		num_txt.setText(txt[8]);
	}
	
}
