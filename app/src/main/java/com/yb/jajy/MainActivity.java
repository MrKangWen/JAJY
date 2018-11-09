package com.yb.jajy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;


import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;

/**
 * @author kang
 */
public class MainActivity extends AppCompatActivity {


    private BridgeWebView mWebView;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.mainWebView);
        mWebView.loadUrl("https://weixin.juaijiayuan.com");

        //添加js监听 这样html就能调用客户端
        //  mWebView.addJavascriptInterface(this, "getLiveIdFromObjC");

        mWebView.registerHandler("getLiveIdFromObjC", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("mytest", "handler = submitFromWeb, data from web = " + data);
                function.onCallBack("submitFromWeb exe, response data from Java");

                Intent intent = new Intent(MainActivity.this, LiveActivity.class);
                intent.putExtra("LiveData", data);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
