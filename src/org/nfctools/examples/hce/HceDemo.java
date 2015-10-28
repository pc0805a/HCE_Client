package org.nfctools.examples.hce;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

	static boolean isDataIn = false;

	protected static String terminalName;
	protected static JTextArea id_txt;
	protected static JTextArea status_txt;

	public void startNFCTerminal() {
		try {
			CardTerminal cardTerminal = TerminalUtils.getAvailableTerminal().getCardTerminal();
			terminalName = cardTerminal.getName();
			HostCardEmulationTagScanner tagScanner = new HostCardEmulationTagScanner(cardTerminal);
			status_txt.setText("Terminal Founded!\n");
			status_txt.append("Terminal Name: " + cardTerminal.getName() + "\n");
			tagScanner.run();
		} catch (RuntimeException e) {
			status_txt.setForeground(Color.RED);
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

		id_txt = new JTextArea("None");
		id_txt.setPreferredSize(new Dimension(100, 18));
		id_txt.setEditable(false);

		JLabel status_label = new JLabel("Status: ");

		status_txt = new JTextArea("None\n");

		JScrollPane jsp = new JScrollPane(status_txt);
		jsp.setPreferredSize(new Dimension(650, 300));

		status_txt.setLineWrap(true);
		status_txt.setWrapStyleWord(true);

		// status_txt.setPreferredSize(new Dimension(650, 300));
		status_txt.setEditable(false);

		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		northPanel.add(id_label);
		northPanel.add(id_txt);

		westPanel.add(status_label);
		centerPanel.add(jsp);

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
	
	public static void appendTxt(String txt)
	{
		status_txt.append(txt+"\n");
		status_txt.setCaretPosition(status_txt.getDocument().getLength());
	}
}
