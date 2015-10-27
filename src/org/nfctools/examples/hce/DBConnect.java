package org.nfctools.examples.hce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class DBConnect {

	protected void updateStatus(String[] data) throws IOException {
		
		String postURL = "http://140.136.150.92/fetchDBInfo.php";
		
		HashMap<String, String> postDataParams = new HashMap<String, String>();

        postDataParams.put("id", data[1]);
        postDataParams.put("division", data[2]);
        postDataParams.put("doctor", data[3]);
        postDataParams.put("year", data[4]);
        postDataParams.put("month", data[5]);
        postDataParams.put("day", data[6]);
        postDataParams.put("num", data[8]);

        String result = getInfo(postURL, postDataParams);

//		URL url = new URL("http://140.136.150.92/nfcReport.php");
//		String result = "";
//		String data = "NfcCardNum=" + nfcCardNum;
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//		try {
//
//			connection.setDoInput(true);
//			connection.setDoOutput(true);
//			connection.setUseCaches(false);
//			connection.setRequestMethod("POST");
//			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//
//			// Send the POST data
//			DataOutputStream dataOut = new DataOutputStream(connection.getOutputStream());
//			dataOut.writeBytes(data);
//			dataOut.flush();
//			dataOut.close();
//
//			BufferedReader in = null;
//			try {
//				String line;
//				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//				while ((line = in.readLine()) != null) {
//					result += line;
//				}
//			} finally {
//				if (in != null) {
//					in.close();
//				}
//			}
//
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		finally {
//			connection.disconnect();
//			//if(Integer.parseInt(result)==0);
//			System.out.println(result);
//			
//			switch(Integer.parseInt(result))
//			{
//			case 0:
//				HceDemo.status_txt.append("未到\n");
//				HceDemo.status_txt.setCaretPosition(HceDemo.status_txt.getDocument().getLength());
//				break;
//			case 1:
//				HceDemo.status_txt.append("已到\n");
//				HceDemo.status_txt.setCaretPosition(HceDemo.status_txt.getDocument().getLength());
//				break;
//			case 2:
//				HceDemo.status_txt.append("看診完畢\n");
//				HceDemo.status_txt.setCaretPosition(HceDemo.status_txt.getDocument().getLength());
//				break;
//			case 3:
//				HceDemo.status_txt.append("過號\n");
//				HceDemo.status_txt.setCaretPosition(HceDemo.status_txt.getDocument().getLength());
//				break;
//			case 4:
//				HceDemo.status_txt.append("未掛號\n");
//				HceDemo.status_txt.setCaretPosition(HceDemo.status_txt.getDocument().getLength());
//				break;
//			}
//		
//			
//		}
	}
	
	protected String getInfo(String requestURL,
            HashMap<String, String> postDataParams)
	{
		 URL url;//140.136.150.92/fetchDBInfo.php
	        String response = "";
	        try {
	            url = new URL(requestURL);

	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setReadTimeout(15000);
	            conn.setConnectTimeout(15000);
	            conn.setRequestMethod("POST");
	            conn.setDoInput(true);
	            conn.setDoOutput(true);


	            OutputStream os = conn.getOutputStream();
	            BufferedWriter writer = new BufferedWriter(
	                    new OutputStreamWriter(os, "UTF-8"));
	            writer.write(getPostDataString(postDataParams));

	            writer.flush();
	            writer.close();
	            os.close();
	            int responseCode=conn.getResponseCode();

	            if (responseCode == HttpsURLConnection.HTTP_OK) {
	                String line;
	                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
	                while ((line=br.readLine()) != null) {
	                    response+=line;
	                }
	            }
	            else {
	                response="";

	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return response;
	}
	
	private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
