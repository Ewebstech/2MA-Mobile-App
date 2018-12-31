package com.avardonigltd.mobilemedicalaid.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.utilities.WebViewClientImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WebActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String EXTRA_URL = "url";
//    public static final String EXTRA_REQUEST_TYPE = "request_type";
//    public static final String EXTRA_POST_DATA = "post_data";
    //private String postData;
    private String url;
   // private String request = "GET";

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.webview)
    WebView webView;
    private Unbinder unbinder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        unbinder = ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setDistanceToTriggerSync(100);
        swipeRefreshLayout.setColorSchemeResources(R.color.appBlue, R.color.lightBlue, R.color.black);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        if (bundle.containsKey(EXTRA_URL)) {
            url = bundle.getString(EXTRA_URL);
        }

//        if (bundle.containsKey(EXTRA_REQUEST_TYPE)) {
//            request = bundle.getString(EXTRA_REQUEST_TYPE);
//            postData = bundle.getString(EXTRA_POST_DATA);
//        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClientImpl(this));
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);

//        if (TextUtils.equals(request, "POST")) {
//            try {
//                //webView.postUrl(url, postData.getBytes());
//                webView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        } else{
//            webView.loadUrl(url);
//        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    public void showProgress() {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(true);
    }

    public void hideProgress() {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRefresh() {
        // webView.loadUrl( "javascript:window.location.reload( true )" );
        webView.reload();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    public void successful(){
        Intent intent = new Intent();
        intent.putExtra("result", "Transaction successful...");
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
