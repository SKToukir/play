package showcase.adplay.com.adplayshowcase;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qintong.library.InsLoadingView;

//import im.delight.android.webview.AdvancedWebView;

//public class AdViewActivity extends AppCompatActivity implements AdvancedWebView.Listener {
@SuppressWarnings("ALL")
public class AdViewActivity extends AppCompatActivity {

        WebView mWebView;
   private InsLoadingView mProgressBar;
    private FrameLayout frame;
    private TextView txtLoadCount;

//    private AdvancedWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_view);

//        ImageView imageView = (ImageView) findViewById(R.id.imgBackgroundView);
//        Glide.with(this).load(R.drawable.ezgif).into(imageView);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed(); // Implemented by activity
//            }
//        });

//        txtLoadCount = (TextView) findViewById(R.id.txtLoadCount);
//        frame = (FrameLayout) findViewById(R.id.frame);
//        mProgressBar = (ProgressBar) findViewById(R.id.pb);
//
//        Intent intent = getIntent();
//        String adUrl = intent.getStringExtra("ad");
//
//        mWebView = (AdvancedWebView) findViewById(R.id.webview);
//        mWebView.setListener(this, this);
//                WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setUseWideViewPort(false);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        try{
//            mWebView.loadUrl(adUrl);
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.d("Error",e.getMessage());
//        }
//
//        mWebView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // open in Webview
//                if (url.contains("android_asset")) {
//                    // Can be clever about it like so where myshost is defined in your strings file
//                    // if (Uri.parse(url).getHost().equals(getString(R.string.myhost)))
//                    return false;
//                }
//                // open rest of URLS in default browser
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//                return true;
//            }
//
//        });
//
//        mWebView.setWebChromeClient(new WebChromeClient(){
//
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onPermissionRequest(PermissionRequest request) {
//                request.grant(request.getResources());
//            }
//
//            /*
//                            public void onProgressChanged (WebView view, int newProgress)
//                                Tell the host application the current progress of loading a page.
//
//                            Parameters
//                                view : The WebView that initiated the callback.
//                                newProgress : Current page loading progress, represented by an integer
//                                    between 0 and 100.
//                        */
//            public void onProgressChanged(WebView view, int newProgress){
//                // Update the progress bar with page loading progress
//                txtLoadCount.setText(String.valueOf(newProgress)+"%");
//                Log.d("progress",String.valueOf(newProgress)+"%");
//                mProgressBar.setProgress(newProgress);
//                if(newProgress == 100){
//                    // Hide the progressbar
//                    frame.setVisibility(View.GONE);
//                    mProgressBar.setVisibility(View.GONE);
//                }
//            }
//        });


        txtLoadCount = (TextView) findViewById(R.id.txtLoadCount);
        frame = (FrameLayout) findViewById(R.id.frame);
        mWebView = (WebView) findViewById(R.id.webview);
        mProgressBar = (InsLoadingView) findViewById(R.id.pb);

        Intent intent = getIntent();
        String adUrl = intent.getStringExtra("ad");

        showView(adUrl);
    }

    private void showView(String s) {


        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.d("Urlllll",url);

                // open in Webview
                if (url.contains("android_asset") ){
                    // Can be clever about it like so where myshost is defined in your strings file
                    // if (Uri.parse(url).getHost().equals(getString(R.string.myhost)))
                    return false;
                }else if (!url.contains("http://")){
                    Log.d("Error","No url found");
                    //Toast.makeText(getApplicationContext(),"Try again",Toast.LENGTH_LONG).show();
                }
                // open rest of URLS in default browser
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Do something on page loading started
                // Visible the progressbar
                frame.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url){
                // Do something when page loading finished
                Log.d("something",url);
            }

        });

        /*
            WebView
                A View that displays web pages. This class is the basis upon which you can roll your
                own web browser or simply display some online content within your Activity. It uses
                the WebKit rendering engine to display web pages and includes methods to navigate
                forward and backward through a history, zoom in and out, perform text searches and more.

            WebChromeClient
                 WebChromeClient is called when something that might impact a browser UI happens,
                 for instance, progress updates and JavaScript alerts are sent here.
        */
        mWebView.setWebChromeClient(new WebChromeClient(){
            /*
                public void onProgressChanged (WebView view, int newProgress)
                    Tell the host application the current progress of loading a page.

                Parameters
                    view : The WebView that initiated the callback.
                    newProgress : Current page loading progress, represented by an integer
                        between 0 and 100.
            */
            public void onProgressChanged(WebView view, int newProgress){
                // Update the progress bar with page loading progress
                txtLoadCount.setText(String.valueOf(newProgress)+"%");
                Log.d("progress",String.valueOf(newProgress)+"%");
                //mProgressBar.setProgress(newProgress);
                if(newProgress == 100){
                    // Hide the progressbar
                    frame.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });


        // Enable the javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(false);

        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // Render the web page
        mWebView.loadUrl(s);
    }
//    }
//
//    @Override
//    public void onPageStarted(String url, Bitmap favicon) {
//        frame.setVisibility(View.VISIBLE);
//        mProgressBar.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void onPageFinished(String url) {
//
//    }
//
//    @Override
//    public void onPageError(int errorCode, String description, String failingUrl) {
//
//    }
//
//    @Override
//    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
//
//    }
//
//    @Override
//    public void onExternalPageRequest(String url) {
//
//    }
}
