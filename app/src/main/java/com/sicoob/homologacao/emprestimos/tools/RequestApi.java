package com.sicoob.homologacao.emprestimos.tools;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.List;


public class RequestApi {

    private static RequestApi instance;
    private HttpClient httpclient;
    private String retorno = null;

    private RequestApi() {

        httpclient = new DefaultHttpClient();
        httpclient.getParams().setIntParameter("http.socket.timeout", 15000);
        httpclient.getParams().setIntParameter("http.connection.timeout", 15000);
        httpclient.getParams().setParameter("connection", "close");


    }

    public static RequestApi getInstance() {
        if (instance == null) {
            instance = new RequestApi();
        }
        return instance;
    }


    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String requistarApiPost(String url, List<NameValuePair> params) {
        String retorno = null;
        Integer i = 0;
        for (i = 0; i <= 6; i++) {
            try {
                return getApiHttpPost(url, params);
            } catch (Exception e) {

                e.printStackTrace();

            }
        }
        if (i == 7) {
            throw new IllegalAccessError("Sem conexao ou acesso 3g " + System.getProperty("line.separator") +
                    System.getProperty("line.separator") +
                    "Favor verificar sua conectividade no menu configuração do Android.");
        } else {
            return retorno;
        }

    }

    public String requisitarApiGET(String url) throws Exception {
        Integer i = 0;
        for (i = 0; i <= 15; i++) {
            try {
                return getApiHttpGet(url);
            } catch (UnknownHostException u) {
                u.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (i == 16) {
            throw new Exception("Sem conexao ou acesso 3g lento . " + System.getProperty("line.separator") +
                    System.getProperty("line.separator") +
                    "Favor verificar sua conectividade no menu configuração do Android.");
        } else {
            return retorno;
        }
    }

    public String requisitarPostJson(String url, StringEntity json) {


        String retorno = null;
        Integer i = 0;
        for (i = 0; i <= 6; i++) {
            try {
                return getApiHttpPost(url, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (i == 17) {
            throw new IllegalAccessError("Sem conexao ou acesso 3g " + System.getProperty("line.separator") +
                    System.getProperty("line.separator") +
                    "Favor verificar sua conectividade no menu configuração do Android.");
        } else {
            return retorno;
        }


    }


    String getApiHttpGet(String uri) throws Exception {

        HttpGet httpget = new HttpGet(uri);
        httpget.setHeader("user-agent", "tracklock-mobile-android");
        httpget.setHeader("Content-type", "application/json");
        HttpResponse response = httpclient.execute(httpget);

        HttpEntity entity = response.getEntity();
        int codigoResp = response.getStatusLine().getStatusCode();
        if (entity != null && (codigoResp >= 200 && codigoResp < 300)) {
            InputStream instream = entity.getContent();
            String result = convertStreamToString(instream);
            result = result.replace("\n", "").replace("\r", "");
            instream.close();
            response.getEntity().consumeContent();
            return result;
        }

        return String.valueOf(codigoResp);

    }

    public byte[] getArrayByteApiHttpGet(String url) throws Exception {
        Integer i = 0;
        for (i = 0; i <= 15; i++) {
            try {
                return _getArrayByteApiHttpGet(url);
            } catch (UnknownHostException u) {
                u.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (i == 16) {
            throw new Exception("Sem conexao ou acesso 3g lento . " + System.getProperty("line.separator") +
                    System.getProperty("line.separator") +
                    "Favor verificar sua conectividade no menu configuração do Android.");
        } else {
            return null;
        }
    }

    private byte[] _getArrayByteApiHttpGet(String uri) throws Exception {

        HttpGet httpget = new HttpGet(uri);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        int codigoResp = response.getStatusLine().getStatusCode();
        if (entity != null && codigoResp == HttpStatus.SC_OK) {
            InputStream instream = entity.getContent();
            byte[] result = readFully(instream);
            instream.close();
            response.getEntity().consumeContent();
            return result;
        }

        return null;

    }

    public static byte[] readFully(InputStream input) throws IOException {
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }



    private String getApiHttpPost(String url, List<NameValuePair> params) throws Exception {
        String result = null;
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        int codigoResp = response.getStatusLine().getStatusCode();
        if (entity != null && (codigoResp >= 200 && codigoResp < 300)) {
            InputStream instream = entity.getContent();
            result = convertStreamToString(instream);
            instream.close();
        }
        response.getEntity().consumeContent();
        return result;
    }

    private String getApiHttpPost(String url, StringEntity params) throws Exception {

        String result = null;
        HttpPost httppost = new HttpPost(url);

        httppost.setHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httppost.setHeader(new BasicHeader("Accept", "application/json"));
        httppost.setEntity(params);

        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        int codigoResp = response.getStatusLine().getStatusCode();
        LogApp.debug("getApiHttpPost codigoResp: " + codigoResp);
        if (entity != null && (codigoResp >= 200 && codigoResp < 300)) {
            InputStream instream = entity.getContent();
            result = convertStreamToString(instream);
            instream.close();
        } else if (codigoResp == 400 ) {
            InputStream instream = entity.getContent();
            result = codigoResp + " ";
            result += convertStreamToString(instream);
            instream.close();
        } else {
            result = codigoResp + "";
        }
        if (response != null && response.getEntity() != null)
            response.getEntity().consumeContent();
        return result;
    }

    public Bitmap getBitmapFromURL(String link) {
    /*--- this method downloads an Image from the given URL,
     *  then decodes and returns a Bitmap object
     ---*/
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("getBmpFromUrl error: ", e.getMessage().toString());
            return null;
        }
    }


}
