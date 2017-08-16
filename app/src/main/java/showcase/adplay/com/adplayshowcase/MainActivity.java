package showcase.adplay.com.adplayshowcase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.qintong.library.InsLoadingView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import showcase.adplay.com.adplayshowcase.adapter.ShowAddListAdapter;
import showcase.adplay.com.adplayshowcase.util.AddItem;

import static java.lang.Thread.sleep;

//public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
public class MainActivity extends AppCompatActivity {

    InsLoadingView mainProgress;
    LinearLayout activity_main;
    //private SwipeRefreshLayout swipeRefreshLayoutGift;
    AddItem addItem;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<AddItem> itemList = new ArrayList<AddItem>();
    WebView mWebView;
    Context mContext;
    private Activity mActivity;
    private ImageView imgBackground;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainProgress = (InsLoadingView) findViewById(R.id.mainProgress);
        imgBackground = (ImageView) findViewById(R.id.imgBackground);
        //Glide.with(this).load(R.drawable.adplaybg).into(imgBackground);
        Glide.with(this).load(R.drawable.ezgif).into(imgBackground);
        mContext = getApplicationContext();

        mActivity = MainActivity.this;

        inittRecycler();

        if (isConnected()) {
            getLinkSpeed();
            parseAdds("http://adplaydemo.adplay-mobile.com/adplaydemoad/categoryapi");
            //splash();
        } else {
            showConnectivityAlert();
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                AddItem item = itemList.get(position);
                //showView("https://rtb.adplay-mobile.com/adplay-uat");
                CateGoryItemActivity.id = item.getId();
                CateGoryItemActivity.title = item.getCategoryName().replaceAll("_"," ");

                startActivity(new Intent(MainActivity.this,CateGoryItemActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

//        swipeRefreshLayoutGift.post(new Runnable() {
//            @Override
//            public void run() {
//                parseAdds("http://adplaydemo.adplay-mobile.com/adplaydemoad/addata");
//            }
//        });
    }

    private void getLinkSpeed() {
        WifiManager myWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
        int linkSpeed = myWifiInfo.getLinkSpeed();

        Log.v("linkSpeed", "Link speed:" + linkSpeed);
    }

    private void splash() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    Log.d("Internet", String.valueOf(isConnected()));
//                    parseAdds("http://adplaydemo.adplay-mobile.com/adplaydemoad/addata");
                    parseAdds("http://adplaydemo.adplay-mobile.com/adplaydemoad/categoryapi");

                }

            }
        });
        thread.start();

    }

    private void showConnectivityAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Adplay Ad-showcase");
        builder.setMessage("Internet connection failure!");

// Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                MainActivity.this.finish();
            }
        });

// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void parseAdds(String s) {
        //swipeRefreshLayoutGift.setRefreshing(true);
        itemList.clear();
        JsonArrayRequest request = new JsonArrayRequest(s, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d("FromServer", jsonArray.toString());
                mainProgress.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                getLinkSpeed();
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {

                        JSONObject obj = jsonArray.getJSONObject(i);
                        addItem = new AddItem();
                        addItem.setId(obj.getString("id"));
                        addItem.setCategoryName(obj.getString("category_name"));
                        addItem.setIcon(obj.getString("icon").replaceAll(" ","%20"));

                        itemList.add(addItem);
//                        Log.d("IsActive", obj.getString("is_active"));

//                        if (obj.getString("is_active").equals("1")) {
//                            itemList.add(addItem);
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //swipeRefreshLayoutGift.setRefreshing(false);
                }

                recyclerView.swapAdapter(adapter, true);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //swipeRefreshLayoutGift.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);

    }

    private void inittRecycler() {

//        swipeRefreshLayoutGift = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_gift);
//        swipeRefreshLayoutGift.setOnRefreshListener(this);

        adapter = new ShowAddListAdapter(MainActivity.this, itemList);
        recyclerView = (RecyclerView) findViewById(R.id.rvContacts);
        int numberOfColumns = 1;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, numberOfColumns);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

//    @Override
//    public void onRefresh() {
//
//        try{
//            parseAdds("http://adplaydemo.adplay-mobile.com/adplaydemoad/addata");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.d("error",e.getMessage());
//        }


    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

}


class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private ClickListener clickListener;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}


//    public class AppWebViewClients extends WebViewClient {
//        private ProgressBar progressBar;
//
//        public AppWebViewClients(ProgressBar progressBar) {
//            this.progressBar=progressBar;
//            progressBar.setVisibility(View.VISIBLE);
//        }
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView myWebView, String url) {
//            // TODO Auto-generated method stub
//
//            WebSettings webSettings = myWebView.getSettings();
//            webSettings.setJavaScriptEnabled(true);
//            webSettings.setLoadsImagesAutomatically(true);
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////            webSettings.setAllowFileAccessFromFileURLs(true);
////        }
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////            webSettings.setAllowUniversalAccessFromFileURLs(true);
////        }
//
//            try {
//                Method m = WebSettings.class.getMethod("setMixedContentMode", int.class);
//                if ( m == null ) {
//                    Log.e("WebSettings", "Error getting setMixedContentMode method");
//                }
//                else {
//                    m.invoke(myWebView.getSettings(), 2); // 2 = MIXED_CONTENT_COMPATIBILITY_MODE
//                    Log.i("WebSettings", "Successfully set MIXED_CONTENT_COMPATIBILITY_MODE");
//                }
//            }
//            catch (Exception ex) {
//                Log.e("WebSettings", "Error calling setMixedContentMode: " + ex.getMessage(), ex);
//            }
//
//            webSettings.setUseWideViewPort(true);
//            webSettings.setLoadWithOverviewMode(true);
//            myWebView.setWebChromeClient(new WebChromeClient());
//            //myWebView.setWebViewClient(new WebViewClient());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                myWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                myWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//            }
//
//            view.loadUrl(url);
//            return true;
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            // TODO Auto-generated method stub
//            super.onPageFinished(view, url);
//            progressBar.setVisibility(View.GONE);
//        }
//    }
//}
