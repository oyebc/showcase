package com.fivehundredpx.showcase.services;

import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;


import com.fivehundredpx.showcase.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by Samuel Oyediran on 12/12/2016.
 */

public class NetworkManager {

    private static final String TAG = "NetworkManager";
    //http data read timeout
    private static final int READ_TIMEOUT=10000;
    //http connection timeout
    private static  final int CONNECT_TIMEOUT=15000;

    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";



    @Nullable
    public static HttpResponse makeHttpRequest(String url, String requestMethod, String requestBody, Map<String, String> requestProperties)
    {
        HttpURLConnection httpUrlConnection = null;
        HttpResponse httpResponse = new HttpResponse(new byte[0], "", -1, "");
        String responseMessage = null;
        int responseCode= -1;
        try
        {
            //set http request properties
            InputStream inStream = null;
            URL urlObj = new URL(url);
            httpUrlConnection = (HttpURLConnection)urlObj.openConnection();
            httpUrlConnection.setRequestMethod(requestMethod);
            //set timeout properties
            httpUrlConnection.setConnectTimeout(NetworkManager.CONNECT_TIMEOUT);
            httpUrlConnection.setReadTimeout(NetworkManager.READ_TIMEOUT);
            //set request headers
            if(requestProperties != null)
            {
                for (Map.Entry<String, String> property: requestProperties.entrySet())
                {
                    httpUrlConnection.setRequestProperty(property.getKey(), property.getValue());
                }
            }
            //if this is a post/put http request then there might be a request body, add it to the
            //request
            if(requestBody != null && !requestBody.isEmpty())
            {
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setChunkedStreamingMode(0);

                OutputStream outStream = new BufferedOutputStream(httpUrlConnection.getOutputStream());
                outStream.write(requestBody.getBytes());
                outStream.flush();
            }
            //get request response
            responseCode = httpUrlConnection.getResponseCode();
            responseMessage = httpUrlConnection.getResponseMessage();
            Log.i(TAG, url + ": Connection status " + responseCode + " " + responseMessage);
            //turn request response into byte array
            inStream = new BufferedInputStream(httpUrlConnection.getInputStream());
            byte[] responseData = Utils.getByteArray(inStream);
            //create a response object which will be returned to calling method
            httpResponse = new HttpResponse(responseData, httpUrlConnection.getResponseMessage(), httpUrlConnection.getResponseCode(), null);
            inStream.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            if(httpUrlConnection != null && httpUrlConnection.getErrorStream() !=null)
            {
                String errorMessage= new String(Utils.getByteArray(httpUrlConnection.getErrorStream()));
                httpResponse = new HttpResponse(null, responseMessage, responseCode, errorMessage);
            }

        }
        finally
        {
            if(httpUrlConnection != null)
            {
                httpUrlConnection.disconnect();
            }
        }

        return httpResponse;
    }



    /**
     * Representation of the response of an HTTP call. The http status code, the response message,
     * the body of the response in byte array form and error message if available.
     */
    public static class HttpResponse
    {
        private final byte[] responseData;
        private final String responseMessage;
        private final int responseCode;
        private final String errorMessage;

        public HttpResponse(byte[] responseData, String responseMessage, int responseCode, String errorMessage) {
            this.responseData = responseData;
            this.responseMessage = responseMessage;
            this.responseCode = responseCode;
            this.errorMessage = errorMessage;
        }

        public byte[] getResponseData() {
            return responseData;
        }

        public String getResponseMessage() {
            return responseMessage;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public String getErrorMessage(){
            return errorMessage;
        }
    }
}
