package com.yb.jajy;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.FormBody;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author kang
 */
public class LiveActivity extends AppCompatActivity {
    public String TAG = "LiveActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        String liveData = getIntent().getStringExtra("LiveData");

        LiveBean liveBean = new Gson().fromJson(liveData, LiveBean.class);
        getLiveUrl(liveBean);
        ImageView imageView =  findViewById(R.id.liveIvFace);

      //  Drawable drawablegetResources().getDrawable(R.mipmap.ic_launcher);
       Glide.with(this).load(R.mipmap.ic_launcher).transform(new GlideCircleTransform(getApplicationContext())).into(imageView);
        findViewById(R.id.liveIvClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void getLiveUrl(LiveBean liveBean) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("streamName ", liveBean.getLiveId())
                .add("appName ", liveBean.getLiveName())
                .build();
        Request request = new Request.Builder()
                .url("https://wx.juaijiayuan.com/api/UnionTV/GetPlayUrl")
                .post(requestBody)
                .addHeader("livetoken", liveBean.getLiveToken())
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }
}
