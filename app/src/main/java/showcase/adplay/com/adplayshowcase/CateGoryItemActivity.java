package showcase.adplay.com.adplayshowcase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import showcase.adplay.com.adplayshowcase.adapter.ShowItemsAdapter;
import showcase.adplay.com.adplayshowcase.util.AddItem;

public class CateGoryItemActivity extends AppCompatActivity {

    InsLoadingView mainProgressView;
    private AddItem item;
    private List<AddItem> itemList = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    public static String id , title;
    private ImageView imageBackground;
    private TextView txtHeader;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cate_gory_item);


        mainProgressView = (InsLoadingView) findViewById(R.id.mainProgressView);
        txtHeader = (TextView) findViewById(R.id.Txtheader);
        imageBackground = (ImageView) findViewById(R.id.imgBackgrounds);
        //Glide.with(this).load(R.drawable.adplaybg).into(imageBackground);
        Glide.with(this).load(R.drawable.ezgif).into(imageBackground);

        txtHeader.setText(title);

        parseItems("http://adplaydemo.adplay-mobile.com/adplaydemoad/addata?cat_id="+id);

        initRecycler();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(CateGoryItemActivity.this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                AddItem urlItem = itemList.get(position);
                String url = urlItem.getAdUrl();
                Intent intent = new Intent(CateGoryItemActivity.this, AdViewActivity.class);
                intent.putExtra("ad",url);
                startActivity(intent);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void initRecycler() {
        adapter = new ShowItemsAdapter(CateGoryItemActivity.this, itemList);
        recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        int numberOfColumns = 1;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CateGoryItemActivity.this, numberOfColumns);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void parseItems(String s) {

        JsonArrayRequest request = new JsonArrayRequest(s, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d("FromServer", jsonArray.toString());
                mainProgressView.setVisibility(View.GONE);
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {

                        JSONObject obj = jsonArray.getJSONObject(i);
                        item = new AddItem();
                        item.setId(obj.getString("id"));
                        item.setAdTitle(obj.getString("title"));
                        item.setIcon(obj.getString("icon").replaceAll(" ","%20"));
                        item.setAdUrl(obj.getString("url"));
                        item.setAdIsActive(obj.getString("is_active"));
                        String isActive = obj.getString("is_active");
                        Log.d("ICON","Ad Title"+item.getAdTitle());

                        if (isActive.equals("1")){

                            itemList.add(item);
                        }

//                        Log.d("IsActive", obj.getString("is_active"));

//                        if (obj.getString("is_active").equals("1")) {
//                            itemList.add(addItem);
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //swipeRefreshLayoutGift.setRefreshing(false);
                }

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //swipeRefreshLayoutGift.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(CateGoryItemActivity.this);

        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);

    }


}


