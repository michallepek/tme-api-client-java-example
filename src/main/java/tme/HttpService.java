package tme;

import java.io.*;
import java.net.*;
import java.util.Map;

public class HttpService {

    public static String sendPost(URL url, Map<String, String> params) throws IOException {
        byte[] postDataBytes = paramsToStringBytes(params);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();

        for (int item; (item = in.read()) >= 0;)
            stringBuilder.append((char)item);

        return stringBuilder.toString();
    }

    private static byte[] paramsToStringBytes(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder postData = new StringBuilder();

        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0)
                postData.append('&');

            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        return postData.toString().getBytes("UTF-8");
    }

}
