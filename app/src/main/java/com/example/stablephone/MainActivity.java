package com.example.stablephone;

import static android.os.Looper.getMainLooper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.navigation.ui.AppBarConfiguration;
//import com.example.stablephone.databinding.ActivityMainBinding;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private ImageButton retryButton;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            // 取得したテキストを TextView に張り付ける
            textView.setText(text);
            apiManager.getJson(text, imageView);
            // Singleの別スレッドを立ち上げる
//            Executors.newSingleThreadExecutor().execute(() -> {
//                try {
//                    URL url = new URL(text);
//                    HttpURLConnection urlCon =  (HttpURLConnection) url.openConnection();
//
//                    // タイムアウト設定
//                    urlCon.setReadTimeout(10000);
//                    urlCon.setConnectTimeout(20000);
//
//                    // リクエストメソッド
//                    urlCon.setRequestMethod("GET");
//
//                    // リダイレクトを自動で許可しない設定
//                    urlCon.setInstanceFollowRedirects(false);
//
//                    InputStream is = urlCon.getInputStream();
//                    Bitmap bmp = BitmapFactory.decodeStream(is);
//
//                    // 別スレッド内での処理を管理し実行する
//                    HandlerCompat.createAsync(getMainLooper()).post(() ->
//                            // Mainスレッドに渡す
//                            imageView.setImageBitmap(bmp)
//                    );
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
        });

        retryButton.setOnClickListener( v-> {
            //ここを関数かしよう！！！

            // エディットテキストのテキストを取得
            String text = editText.getText().toString();
            // 取得したテキストを TextView に張り付ける
            textView.setText("");
            editText.setText("https://replicate.delivery/pbxt/MPaqiul8V9ZiJlzEzElR7UL43kDPxfJe2yEfTGgvLVZ1U3GgA/out-0.png");
        });
    }
}