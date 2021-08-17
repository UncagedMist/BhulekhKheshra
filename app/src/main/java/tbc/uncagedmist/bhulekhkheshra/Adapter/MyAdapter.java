package tbc.uncagedmist.bhulekhkheshra.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tbc.uncagedmist.bhulekhkheshra.Common.Common;
import tbc.uncagedmist.bhulekhkheshra.Fragments.ResultFragment;
import tbc.uncagedmist.bhulekhkheshra.Interface.ItemClickListener;
import tbc.uncagedmist.bhulekhkheshra.Model.Item;
import tbc.uncagedmist.bhulekhkheshra.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Item> items;

    private InterstitialAd mInterstitialAd;

    public MyAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.my_item_list,parent, false);

        loadFullscreen();

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {

        holder.progressBar.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(items.get(position).getStateImage())
                .into(holder.stateImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.stateName.setText(items.get(position).getStateName());
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        holder.stateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show((Activity) context);
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
    public int getItemCount() {
        return items.size();
    }

    private void loadFullscreen() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(
                context,
                context.getString(R.string.ADMOB_FULL),
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Log.d("TAG", "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Log.d("TAG", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                mInterstitialAd = null;
                                Log.d("TAG", "The ad was shown.");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView stateImage;
        TextView stateName;
        CardView stateCard;
        ProgressBar progressBar;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            stateImage = itemView.findViewById(R.id.state_image);
            stateName= itemView.findViewById(R.id.state_name);
            stateCard = itemView.findViewById(R.id.state_card);
            progressBar = itemView.findViewById(R.id.progressBar);

            Typeface face = Typeface.createFromAsset(context.getAssets(),
                    "fonts/sport.ttf");
            stateName.setTypeface(face);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view,getAdapterPosition());
        }
    }
}
