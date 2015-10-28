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

	protected int updateStatus(String[] data) throws IOException {

		URL url = new URL("http://140.136.150.92/nfcReport.php");
		String result = "";
		
		String id = "id=" + data[1];
		String division = "division=" + data[2];
		String doctor = "doctor=" + data[3];
		String year = "year=" + data[4];
		String month = "month=" + data[5];
		String day = "day=" + data[6];
		String time = "time=" + data[7];
		String num = "num=" + data[8];
		String imei = "imei=" + data[9];
		
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try {

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			// Send the POST data
			DataOutputStream dataOut = new DataOutputStream(connection.getOutputStream());
			
			String  tempData = id + "&" +
								division + "&" +
								doctor + "&" +
								year + "&" +
								month + "&" +
								day + "&" +
								time + "&" +
								num  + "&" +
								imei;
			
			dataOut.write(tempData.getBytes("utf-8"));
			
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
			
			switch(Integer.parseInt(result))
			{
			case 0:
				HceDemo.appendTxt("未到");
				break;
			case 1:
				HceDemo.appendTxt("已到");
				break;
			case 2:
				HceDemo.appendTxt("看診完畢");
				break;
			case 3:
				HceDemo.appendTxt("過號");
				break;
			case 4:
				HceDemo.appendTxt("未掛號");
				break;
			case -1:
				HceDemo.appendTxt("尚未建立資料手機尚未綁定");
				break;
			}
		
			
		}
		
		return Integer.parseInt(result);
	}

}
