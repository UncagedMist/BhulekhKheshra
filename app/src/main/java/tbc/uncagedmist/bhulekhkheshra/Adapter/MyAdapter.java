package tbc.uncagedmist.bhulekhkheshra.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tbc.uncagedmist.bhulekhkheshra.Ads.GoogleAds;
import tbc.uncagedmist.bhulekhkheshra.Common.Common;
import tbc.uncagedmist.bhulekhkheshra.Common.MyApplicationClass;
import tbc.uncagedmist.bhulekhkheshra.Fragments.ResultFragment;
import tbc.uncagedmist.bhulekhkheshra.Interface.ItemClickListener;
import tbc.uncagedmist.bhulekhkheshra.Model.Item;
import tbc.uncagedmist.bhulekhkheshra.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Item> items;

    public MyAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.my_item_list,parent, false);

        if (MyApplicationClass.getInstance().isShowAds())   {
            GoogleAds.loadGoogleFullscreen(context);
        }

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {

        Picasso.get()
                .load(items.get(position).getStateImage())
                .into(holder.stateImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.stateName.setText(items.get(position).getStateName());
                        holder.stateDesc.setText(items.get(position).getStateDesc());

                        holder.stateCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (GoogleAds.mInterstitialAd != null)  {
                                    GoogleAds.mInterstitialAd.show((Activity) context);
                                }
                                else {
                                    ResultFragment resultFragment = new ResultFragment();
                                    FragmentTransaction transaction = ((AppCompatActivity)context)
                                            .getSupportFragmentManager().beginTransaction();

                                    Common.CurrentStateName = items.get(position).getStateName();
                                    Common.CurrentStateUrl = items.get(position).getStateUrl();

                                    transaction.replace(R.id.main_frame,resultFragment).commit();
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView stateImage;
        TextView stateName,stateDesc;
        RelativeLayout stateCard;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            stateImage = itemView.findViewById(R.id.stateImage);
            stateName= itemView.findViewById(R.id.stateName);
            stateDesc = itemView.findViewById(R.id.stateDesc);
            stateCard = itemView.findViewById(R.id.stateCard);

            stateName.setSelected(true);
            stateDesc.setSelected(true);

            Typeface face = Typeface.createFromAsset(context.getAssets(),
                    "fonts/sport.ttf");
            stateDesc.setTypeface(face);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view,getAdapterPosition());
        }
    }
}
