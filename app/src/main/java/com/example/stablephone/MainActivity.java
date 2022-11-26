package com.example.stablephone;

import static android.os.Looper.getMainLooper;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.navigation.ui.AppBarConfiguration;
//import com.example.stablephone.databinding.ActivityMainBinding;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Executors;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private ImageButton retryButton;
    private ImageView imageView;
    private String jsonString;
    private String urlString;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        editText = findViewById(R.id.edit_text);
        textView = findViewById(R.id.text_view);
        imageView = findViewById(R.id.imageView);

        Button button = findViewById(R.id.button);
        retryButton = findViewById(R.id.imageButton3);

        ApiManager apiManager = new ApiManager();

        button.setOnClickListener( v-> {
            //ここを関数かしよう！！！

            // エディットテキストのテキストを取得
            String text = editText.getText().toString();

            if(!text.equals("")){
                if(!text.equals("please write your word")){
                    editText.setEnabled(false);
                    button.setEnabled(false);
                    textView.setText("thank you for waiting.");

                    //@post create image by text
                    HashMap<String, Object> postJsonMap = new HashMap<>();

                    String version = "8abccf52e7cba9f6e82317253f4a3549082e966db5584e92c808ece132037776";
                    postJsonMap.put("version",version);

                    HashMap<String, Object> postJsonMap2 = new HashMap<>();
                    postJsonMap2.put("prompt", text);

                    String inputText = String.format("{\"prompt\": %s}", text);

                    postJsonMap.put("input",postJsonMap2);
                    try {
                        jsonString = apiManager.postText(postJsonMap);
                        try{
                            Thread.sleep(10000); //10000ミリ秒Sleepする
                        }catch(InterruptedException e){}

                    } catch (IOException e) {e.printStackTrace();}

//                  @get image url by responseJson
                    try {
                        urlString = apiManager.getImgUrl(jsonString);

                    } catch (IOException e) {e.printStackTrace();}

                    //@set image Url to imageView
                    apiManager.setImgUrl(urlString, imageView);
                }
            }else{
                editText.setText("please write your word");
            }
        });

        retryButton.setOnClickListener( v-> {
            //ここを関数かしよう！！！

            // エディットテキストのテキストを取得
            String text = editText.getText().toString();
            // 取得したテキストを TextView に張り付ける
            textView.setText("");
            editText.setText("");
            button.setEnabled(true);
            editText.setEnabled(true);
        });
    }
}