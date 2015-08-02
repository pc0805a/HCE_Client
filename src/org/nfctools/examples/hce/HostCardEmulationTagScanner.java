package org.nfctools.examples.hce;

import java.awt.Color;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;

import org.nfctools.api.TagType;
import org.nfctools.scio.TerminalStatus;
import org.nfctools.spi.acs.AbstractTerminalTagScanner;
import org.nfctools.spi.acs.ApduTagReaderWriter;
import org.nfctools.spi.tama.TamaException;

public class HostCardEmulationTagScanner extends AbstractTerminalTagScanner {

	protected HostCardEmulationTagScanner(CardTerminal cardTerminal) {
		super(cardTerminal);
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			notifyStatus(TerminalStatus.WAITING);
			try {
				Card card = cardTerminal.connect("direct");
				ApduTagReaderWriter readerWriter = new ApduTagReaderWriter(new AcsDirectChannelTag(TagType.ISO_DEP,
						null, card));
				try {
					IsoDepTamaCommunicator tamaCommunicator = new IsoDepTamaCommunicator(readerWriter, readerWriter);
					tamaCommunicator.connectAsInitiator();
				}
				catch (Exception e1) {
					card.disconnect(true);
					e1.printStackTrace();
					HceDemo.status_txt.append("Time Out!\n");
					HceDemo.status_txt.setCaretPosition(HceDemo.status_txt.getDocument().getLength());

					try {
						Thread.sleep(1000);
					}
					catch (InterruptedException e) {
						break;
					}
				}
				finally {
					waitForCardAbsent();
				}
			}
			catch (CardException e) {
				e.printStackTrace();
				HceDemo.status_txt.setForeground(Color.RED);
				HceDemo.status_txt.setText("Device has been removed" + "\n"
						+ "Please replug the device and restart the program");
				HceDemo.status_txt.setCaretPosition(HceDemo.status_txt.getDocument().getLength());
				break;
			}
		}
	}
}
