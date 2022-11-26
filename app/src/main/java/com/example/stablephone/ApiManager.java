package com.example.stablephone;

import static android.os.Looper.getMainLooper;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.Executors;
//import com.fasterxml.jackson;

public class ApiManager {

    private ImageView imageView;
    private String urlSt;
    private String id;


    protected String postText(HashMap<String,Object> jsonMap) throws IOException {
        String ret = "";
        HttpURLConnection urlConnection = null;
        String argStrApiUrl = "https://api.replicate.com/v1/predictions";
        try {
            //ステップ1.接続URLを決める。
            URL url = new URL(argStrApiUrl);

            //ステップ2.URLへのコネクションを取得する。
            urlConnection = (HttpURLConnection) url.openConnection();

            //ステップ3.接続設定(メソッドの決定,タイムアウト値,ヘッダー値等)を行う。
            //接続タイムアウトを設定する。
            urlConnection.setConnectTimeout(100000);
            //レスポンスデータ読み取りタイムアウトを設定する。
            urlConnection.setReadTimeout(100000);

            //ヘッダーにUser-Agentを設定する。
//            urlConnection.setRequestProperty("User-Agent", "Android");
            //ヘッダーにAccept-Languageを設定する。
//            urlConnection.setRequestProperty("Accept-Language", Locale.getDefault().toString());

            //ヘッダーにContent-Typeを設定する
            urlConnection.addRequestProperty("Content-Type", "application/json");
            //ヘッダーにAccess-Tokenを設定する
            urlConnection.addRequestProperty("Authorization", "Token ACCESS_TOKEN");

            //HTTPのメソッドをPOSTに設定する。
            urlConnection.setRequestMethod("POST");
            //リクエストのボディ送信を許可する
            urlConnection.setDoOutput(true);
            //レスポンスのボディ受信を許可する
            urlConnection.setDoInput(true);

            //ステップ4.コネクションを開く
            urlConnection.connect();

            //ステップ5:リクエストボディの書き出しを行う。
            OutputStream outputStream = urlConnection.getOutputStream();
            if (jsonMap.size() > 0) {
                //JSON形式の文字列に変換する。
                JSONObject responseJsonObject = new JSONObject(jsonMap);
                String jsonText = responseJsonObject.toString();
                PrintStream ps = new PrintStream(urlConnection.getOutputStream());
                ps.print(jsonText);
                ps.close();
            }
            outputStream.close();

            //ステップ6.レスポンスボディの読み出しを行う。
            int responseCode = urlConnection.getResponseCode();
            ret = convertToString(urlConnection.getInputStream());
            Log.d("execute", "URL:" + argStrApiUrl);
            Log.d("execute", "HttpStatusCode:" + responseCode);
            Log.d("execute", "ResponseData:" + ret);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                //7.コネクションを閉じる。
                urlConnection.disconnect();
            }
        }
        return ret;
    }

    public String convertToString(InputStream stream) throws IOException {
        StringBuffer sb = new StringBuffer();
        String line = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        try {
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    protected String getImgUrl(String jsonString) throws IOException {
        String ret = "";
        HttpURLConnection urlConnection = null;

        //require create id string
        try {
            JSONObject json = new JSONObject(jsonString);
            id = json.getString("id");
        }
        catch (org.json.JSONException e) {

        }

        String argStrApiUrl = "https://api.replicate.com/v1/predictions/" + id;
        try {
            //ステップ1.接続URLを決める。
            URL url = new URL(argStrApiUrl);

            //ステップ2.URLへのコネクションを取得する。
            urlConnection = (HttpURLConnection) url.openConnection();

            //ステップ3.接続設定(メソッドの決定,タイムアウト値,ヘッダー値等)を行う。
            //接続タイムアウトを設定する。
            urlConnection.setConnectTimeout(100000);
            //レスポンスデータ読み取りタイムアウトを設定する。
            urlConnection.setReadTimeout(100000);

            //ヘッダーにUser-Agentを設定する。
//            urlConnection.setRequestProperty("User-Agent", "Android");
            //ヘッダーにAccept-Languageを設定する。
//            urlConnection.setRequestProperty("Accept-Language", Locale.getDefault().toString());

            //ヘッダーにContent-Typeを設定する
            urlConnection.addRequestProperty("Content-Type", "application/json");
            //ヘッダーにAccess-Tokenを設定する
            urlConnection.addRequestProperty("Authorization", "Token ACCESS_TOKEN");

            //HTTPのメソッドをGETに設定する。
            urlConnection.setRequestMethod("GET");
            //リクエストのボディ送信を許可する
            urlConnection.setDoOutput(false);
            //レスポンスのボディ受信を許可する
            urlConnection.setDoInput(true);

            //ステップ4.コネクションを開く
            urlConnection.connect();

            //ステップ5:リクエストボディの書き出しを行う。
//            OutputStream outputStream = urlConnection.getOutputStream();
//            if (jsonMap.size() > 0) {
//                //JSON形式の文字列に変換する。
//                JSONObject responseJsonObject = new JSONObject(jsonMap);
//                String jsonText = responseJsonObject.toString();
//                PrintStream ps = new PrintStream(urlConnection.getOutputStream());
//                ps.print(jsonText);
//                ps.close();
//            }
//            outputStream.close();

            //ステップ6.レスポンスボディの読み出しを行う。
            int responseCode = urlConnection.getResponseCode();
            ret = convertToString(urlConnection.getInputStream());
            Log.d("execute", "URL:" + argStrApiUrl);
            Log.d("execute", "HttpStatusCode:" + responseCode);
            Log.d("execute", "ResponseData:" + ret);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                //7.コネクションを閉じる。
                urlConnection.disconnect();
            }
        }
        return ret;
    }

    protected void setImgUrl(String urlJson, ImageView imageView) {

        // Singleの別スレッドを立ち上げる
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                try {
                    JSONObject json = new JSONObject(urlJson);
                    JSONArray jsonArray = json.getJSONArray("output");
                    urlSt = jsonArray.getString(0);
                }
                catch (org.json.JSONException e) {

                }
                URL url = new URL(urlSt);
                HttpURLConnection urlCon =  (HttpURLConnection) url.openConnection();

                // タイムアウト設定
                urlCon.setReadTimeout(10000);
                urlCon.setConnectTimeout(20000);

                // リクエストメソッド
                urlCon.setRequestMethod("GET");

                // リダイレクトを自動で許可しない設定
                urlCon.setInstanceFollowRedirects(false);

                InputStream is = urlCon.getInputStream();
                Bitmap bmp = BitmapFactory.decodeStream(is);

                // 別スレッド内での処理を管理し実行する
                HandlerCompat.createAsync(getMainLooper()).post(() ->
                        // Mainスレッドに渡す
                        imageView.setImageBitmap(bmp)
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
