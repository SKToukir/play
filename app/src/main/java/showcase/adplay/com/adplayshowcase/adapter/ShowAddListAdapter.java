package showcase.adplay.com.adplayshowcase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import showcase.adplay.com.adplayshowcase.R;
import showcase.adplay.com.adplayshowcase.util.AddItem;

/**
 * Created by toukirul on 2/7/2017.
 */

public class ShowAddListAdapter extends RecyclerView.Adapter<ShowAddListAdapter.MyViewHolder> {

    private Context mContext;
    private List<AddItem> addItemList;

    public ShowAddListAdapter(Context context, List<AddItem> items){
        this.mContext = context;
        this.addItemList = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_picasso,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        AddItem primaryClass = addItemList.get(position);
        String id = primaryClass.getId();
        Log.d("ImageIcon",primaryClass.getIcon());
        holder.addTitle.setText(primaryClass.getCategoryName());
        Picasso.with(mContext).load(primaryClass.getIcon()).into(holder.imgAd);
//
//        if (id.equals("55")){
//            holder.imgAd.setImageResource(R.drawable.paz);
//        }else if (id.equals("54")){
//            holder.imgAd.setImageResource(R.drawable.multitab);
//        }else if (id.equals("53")){
//            holder.imgAd.setImageResource(R.drawable.ipng);
//        }else if (id.equals("52")){
//            holder.imgAd.setImageResource(R.drawable.coc);
//        }else if (id.equals("51")){
//            holder.imgAd.setImageResource(R.drawable.messenger);
//        }else if (id.equals("50")){
//            holder.imgAd.setImageResource(R.drawable.shake);
//        }else if (id.equals("49")){
//            holder.imgAd.setImageResource(R.drawable.multitab);
//        }else if (id.equals("48")){
//            holder.imgAd.setImageResource(R.drawable.multitab);
//        }else if (id.equals("47")){
//            holder.imgAd.setImageResource(R.drawable.guessnwin);
//        }else if (id.equals("46")){
//            holder.imgAd.setImageResource(R.drawable.cocacola);
//        }else if (id.equals("45")){
//            holder.imgAd.setImageResource(R.drawable.livestatus);
//        }else if (id.equals("44")){
//            holder.imgAd.setImageResource(R.drawable.multitab);
//        }else if (id.equals("43")){
//            holder.imgAd.setImageResource(R.drawable.reveal);
//        }else if (id.equals("42")){
//            holder.imgAd.setImageResource(R.drawable.guessnwin);
//        }else if (id.equals("41")){
//            holder.imgAd.setImageResource(R.drawable.ipng);
//        }else if (id.equals("40")){
//            holder.imgAd.setImageResource(R.drawable.mobilebannericon);
//        }else if (id.equals("39")){
//            holder.imgAd.setImageResource(R.drawable.adplayadicon);
//        }else if (id.equals("38")){
//            holder.imgAd.setImageResource(R.drawable.adplayadicon);
//        }else if (id.equals("37")){
//            holder.imgAd.setImageResource(R.drawable.natives);
//        }else if (id.equals("36")){
//            holder.imgAd.setImageResource(R.drawable.natives);
//        }else if (id.equals("35")){
//            holder.imgAd.setImageResource(R.drawable.ipng);
//        }else if (id.equals("34")){
//            holder.imgAd.setImageResource(R.drawable.mobilebannericon);
//        }else {
//            holder.imgAd.setImageResource(R.drawable.ipng);
//        }
//
//        holder.addTitle.setText(primaryClass.getAddTitle());



    }

    @Override
    public int getItemCount() {

        return addItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView addTitle;
        ImageView imgAd;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgAd = (ImageView) itemView.findViewById(R.id.imgAd);
            addTitle = (TextView) itemView.findViewById(R.id.txtAddTitle);
        }
    }
}