package com.example.administrator.monpss;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class HttpUtils {

    public HttpUtils() {
        // TODO Auto-generated constructor stub
    }

    public static String SendRequest(String adress_Http, String strJson) {

        String returnLine = "";
        try {

            Log.e("POST","**************开始http通讯**************");
            Log.e("POST","**************调用的接口地址为**************" + adress_Http);
            Log.e("POST","**************请求发送的数据为**************" + strJson);
            URL my_url = new URL(adress_Http);
            HttpURLConnection connection = (HttpURLConnection) my_url.openConnection();
            connection.setDoOutput(true);

            connection.setDoInput(true);

            connection.setRequestMethod("POST");

            connection.setUseCaches(false);

            connection.setInstanceFollowRedirects(true);

            connection.setRequestProperty("Content-Type", "application/json");

            connection.connect();


            DataOutputStream out = new DataOutputStream(connection
                    .getOutputStream());

            byte[] content = strJson.getBytes("utf-8");

            out.write(content, 0, content.length);
            out.flush();
            out.close(); // flush and close

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

            //StringBuilder builder = new StringBuilder();

            String line = "";

            Log.e("POST","Contents of post request start");

            while ((line = reader.readLine()) != null) {
                // line = new String(line.getBytes(), "utf-8");
                returnLine += line;

                System.out.println(line);

            }

            Log.e("POST", "Contents of post request ends");

            reader.close();
            connection.disconnect();
            Log.e("POST","========返回的结果的为========" + returnLine);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnLine;

    }


    public static String getJsonContent(String url_path) {
        String returnLine = "";
        try {
            URL url = new URL(url_path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000); // 请求超时时间3s
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            Log.e("GET", "**************开始http通讯**************");
            Log.e("GET", "**************调用的接口地址为**************" + url_path);
            /*
            int code = connection.getResponseCode(); // 返回状态码
            if (code == 200) {
                // 或得到输入流，此时流里面已经包含了服务端返回回来的JSON数据了,此时需要将这个流转换成字符串
                Log.e("GET","========返回的结果的为========" + changeInputStream(connection.getInputStream()));
                return changeInputStream(connection.getInputStream());
            }
            */
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

            //StringBuilder builder = new StringBuilder();

            String line = "";

            Log.e("GET", "Contents of get request start");

            while ((line = reader.readLine()) != null) {
                // line = new String(line.getBytes(), "utf-8");
                returnLine += line;

                System.out.println(line);

            }

            Log.e("GET", "Contents of post request ends");

            reader.close();
            connection.disconnect();
            Log.e("GET", "========返回的结果的为========" + returnLine);
            returnLine = returnLine.replace("\\","");
            returnLine = returnLine.substring(1,returnLine.length()-1);
            returnLine+="";
            Log.e("result",returnLine);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return returnLine;
    }

    private static String changeInputStream(InputStream inputStream) {
        // TODO Auto-generated method stub
        String jsonString = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length = 0;
        byte[] data = new byte[1024];
        try {
            while (-1 != (length = inputStream.read(data))) {
                outputStream.write(data, 0, length);
            }
            // inputStream流里面拿到数据写到ByteArrayOutputStream里面,
            // 然后通过outputStream.toByteArray转换字节数组，再通过new String()构建一个新的字符串。
            jsonString = new String(outputStream.toByteArray());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return jsonString;
    }
}
