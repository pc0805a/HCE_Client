package org.nfctools.examples.hce;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import org.nfctools.examples.TerminalUtils;

public class HceDemo {

	static boolean isDataIn = false;

	protected static JTextArea id_txt = new JTextArea("None");
	protected static JTextArea status_txt = new JTextArea("None\n");

	public void run() {
		CardTerminal cardTerminal = TerminalUtils.getAvailableTerminal()
				.getCardTerminal();
		HostCardEmulationTagScanner tagScanner = new HostCardEmulationTagScanner(
				cardTerminal);
		tagScanner.run();
	}

	public static void main(String[] args) {
		
		DefaultCaret caret = (DefaultCaret)status_txt.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JFrame jf = new JFrame();

		jf.setSize(400, 400);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLayout(new BorderLayout());

		JPanel northPanel = new JPanel(new FlowLayout());
		JPanel centerPanel = new JPanel(new FlowLayout());
		JPanel westPanel = new JPanel(new FlowLayout());

		JLabel id_label = new JLabel("ID: ");
		
		id_txt.setPreferredSize(new Dimension(100, 18));
		id_txt.setEditable(false);

		JLabel status_label = new JLabel("Status: ");
		
		status_txt.setPreferredSize(new Dimension(300, 300));
		status_txt.setEditable(false);

		northPanel.add(id_label);
		northPanel.add(id_txt);

		westPanel.add(status_label);
		centerPanel.add(status_txt);

		jf.add(northPanel, BorderLayout.NORTH);
		jf.add(westPanel, BorderLayout.WEST);
		jf.add(centerPanel, BorderLayout.CENTER);

		jf.setVisible(true);

		new HceDemo().run();
	}
}
