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
 * Created by toukirul on 6/8/2017.
 */

public class ShowItemsAdapter  extends RecyclerView.Adapter<ShowItemsAdapter.MyViewHolder> {

    private Context mContext;
    private List<AddItem> addItemList;

    public ShowItemsAdapter(Context context, List<AddItem> items){
        this.mContext = context;
        this.addItemList = items;
    }

    @Override
    public ShowItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_picasso,parent,false);

        return new ShowItemsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowItemsAdapter.MyViewHolder holder, int position) {

        AddItem primaryClass = addItemList.get(position);
        String id = primaryClass.getId();
        Log.d("ImageIcon",primaryClass.getAdTitle());
        holder.addTitle.setText(primaryClass.getAdTitle());
        Picasso.with(mContext).load(primaryClass.getIcon()).into(holder.imgAd);

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