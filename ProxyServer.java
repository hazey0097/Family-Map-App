package com.example.client.Proxy;


import com.example.client.DataCache.DataCache;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import Request.LoginReq;
import Request.RegisterReq;
import Result.Login;
import Result.PersonRes;
import Result.Register;
import Result.EventRes;


public class ProxyServer {
    private static DataCache myDataCache = DataCache.getInstance();
    // LOGIN PROXY
   public static Login loginProxy(LoginReq login){
        try{
            URL url = new URL("http://" + myDataCache.getServerHost() + ":" + myDataCache.getServerPort()  + "/user/login"); //login URL
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            String reqData = toJson(login);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            System.out.println("Before If Statements");

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("SUCCESS");
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                Login response = jsonToObject(respData,Login.class);
                return response;
            }
            else {
                System.out.println("Sign in was unsuccessful");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
       return null;
    }

    // REGISTER PROXY
    public static Register registerProxy(RegisterReq registerReq){
        try{
            URL url = new URL("http://" + myDataCache.getServerHost() + ":" + myDataCache.getServerPort()  + "/user/register"); //login URL
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            String reqData = toJson(registerReq);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("SUCCESS");
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                Register response = jsonToObject(respData,Register.class);
                return response;
            }
            else {
                System.out.println("ERROR: registration was unsuccessful");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static PersonRes getPeopleProxy(String authToken){
        try{
            URL url = new URL("http://" + myDataCache.getServerHost() + ":" + myDataCache.getServerPort()  + "/person"); //login URL
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("SUCCESS");
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                PersonRes response = jsonToObject(respData,PersonRes.class);
                return response;
            }
            else {
                System.out.println("ERROR: retrieving people  was unsuccessful");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static EventRes getEventsProxy(String authToken){
        try{
            URL url = new URL("http://" + myDataCache.getServerHost() + ":" + myDataCache.getServerPort()  + "/event"); //login URL
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("SUCCESS");
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                EventRes response = jsonToObject(respData,EventRes.class);
                return response;
            }
            else {
                System.out.println("ERROR: retrieving people  was unsuccessful");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //HELPER METHODS
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    private static String toJson(Object o){
        return new Gson().toJson(o);
    }

    private static <T> T jsonToObject(String s, Class<T> returnObject){
        return (new Gson()).fromJson(s,returnObject);
    }

}
