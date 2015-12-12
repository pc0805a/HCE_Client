package nfc.example.hce;

import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;

import org.nfctools.api.TagType;
import org.nfctools.scio.TerminalStatus;
import org.nfctools.spi.acs.AbstractTerminalTagScanner;
import org.nfctools.spi.acs.ApduTagReaderWriter;
import org.nfctools.spi.tama.TamaException;

public class HostCardEmulationTagScanner extends AbstractTerminalTagScanner {
	
	protected static Timer mtimer = new Timer();;

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
					HceDemo.status_txt.setForeground(Color.RED);
					HceDemo.status_txt.append("\n已離開感應器\n");
					
					HceDemo.status_txt.setCaretPosition(HceDemo.status_txt.getDocument().getLength());
					
					mtimer = new Timer();
					mtimer.schedule(new StatusCleanTask(), 5000);

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
				Font statusFont = new Font("SansSerif", Font.BOLD, 18);
				HceDemo.status_txt.setForeground(Color.RED);
				HceDemo.status_txt.setFont(statusFont);
				HceDemo.status_txt.append("Device has been removed" + "\n"
						+ "Please replug the device and restart the program");
				break;
			}
		}
	}
	
	private class StatusCleanTask extends TimerTask{
		public void run(){
			HceDemo.status_txt.setText("");
			HceDemo.status_txt.setForeground(Color.BLACK);
		}
		public boolean cancel()
		{
			return false;
		}
	}
	
}


