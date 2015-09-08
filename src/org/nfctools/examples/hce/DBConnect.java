package org.nfctools.examples.hce;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DBConnect {

	protected void updateStatus(String nfcCardNum) throws IOException {

		URL url = new URL("http://140.136.150.92/nfcReport.php");
		String result = "";
		String data = "NfcCardNum=" + nfcCardNum;
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try {

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			// Send the POST data
			DataOutputStream dataOut = new DataOutputStream(connection.getOutputStream());
			dataOut.writeBytes(data);
			dataOut.flush();
			dataOut.close();

			BufferedReader in = null;
			try {
				String line;
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} finally {
				if (in != null) {
					in.close();
				}
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			connection.disconnect();
			//if(Integer.parseInt(result)==0);
			System.out.println(result);
			//0:�w����
			//1:������
			//2:����
			//3:�L��
			//4:������
			switch(Integer.parseInt(result))
			{
			case 0:
				HceDemo.status_txt.append("������\n");
				HceDemo.status_txt.setCaretPosition(HceDemo.status_txt.getDocument().getLength());
				break;
			case 1:
				HceDemo.status_txt.append("�w����\n");
				HceDemo.status_txt.setCaretPosition(HceDemo.status_txt.getDocument().getLength());
				break;
			case 2:
				HceDemo.status_txt.append("�ݶE����\n");
				HceDemo.status_txt.setCaretPosition(HceDemo.status_txt.getDocument().getLength());
				break;
			case 3:
				HceDemo.status_txt.append("�L��\n");
				HceDemo.status_txt.setCaretPosition(HceDemo.status_txt.getDocument().getLength());
				break;
			case 4:
				HceDemo.status_txt.append("������\n");
				HceDemo.status_txt.setCaretPosition(HceDemo.status_txt.getDocument().getLength());
				break;
			}
		
			
		}
	}

}
