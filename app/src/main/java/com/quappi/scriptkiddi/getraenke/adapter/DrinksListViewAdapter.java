package com.quappi.scriptkiddi.getraenke.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.quappi.scriptkiddi.getraenke.Constants;
import com.quappi.scriptkiddi.getraenke.R;
import com.quappi.scriptkiddi.getraenke.controller.DrinkBuyAction;
import com.quappi.scriptkiddi.getraenke.utils.Drink;
import com.quappi.scriptkiddi.getraenke.utils.Person;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by fritz on 08.07.17.
 */

public class DrinksListViewAdapter extends RecyclerView.Adapter<DrinksListViewAdapter.ViewHolder> {

    private final Context context;

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    private final Person person;
    private ArrayList<Drink> mDataset;
    private static final String TAG = "DrinksListViewAdapter";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView drinkTitle;
        public TextView drinkPrice;
        public TextView drinkVolume;
        public ImageView drinkImage;

        public ViewHolder(View v) {
            super(v);
            drinkTitle = (TextView) v.findViewById(R.id.drinkTitle);
            drinkPrice = (TextView) v.findViewById(R.id.drinkPrice);
            drinkVolume = (TextView) v.findViewById(R.id.drinkVolume);
            drinkImage = (ImageView) v.findViewById(R.id.drinkImage);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DrinksListViewAdapter(ArrayList<Drink> myDataset, Person person, Context context) {
        this.context = context;
        mDataset = myDataset;
        this.person = person;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DrinksListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drink_card, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.drinkTitle.setText(mDataset.get(position).getName());
        final NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        final double price = mDataset.get(position).getResellPrice();
        final String priceString = formatter.format(price);

        holder.drinkPrice.setText(priceString);
        holder.drinkVolume.setText(String.format("%.2f l", mDataset.get(position).getVolume()));
        new DownloadImageTask(holder.drinkImage).execute(Constants.baseURL+mDataset.get(position).getImgURL());

        holder.drinkImage.setImageDrawable(mDataset.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DrinkBuyAction drinkBuyAction = new DrinkBuyAction(person, mDataset.get(position));
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(holder.drinkTitle.getText() + " " + holder.drinkVolume.getText() + " (" + priceString + ") bestellt");

                builder.setMessage(String.format("Neues Guthaben: %.2f €", person.getCredit() - price));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        drinkBuyAction.setValid(true);
                        Log.d(TAG, "send by ok: " + drinkBuyAction);
                        drinkBuyAction.putOrder(context);
                    }
                });
                builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        drinkBuyAction.setValid(false);
                        Log.d(TAG, "aborted order: " + drinkBuyAction);
                        drinkBuyAction.putOrder(context);
                    }
                });

                final AlertDialog alert = builder.create();
                alert.show();
                Button abortButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                abortButton.setTextColor(Color.RED);
                final Handler handler = new Handler();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (alert.isShowing()) {
                            alert.dismiss();
                            Log.d(TAG, "send by timeout: " + drinkBuyAction);
                            drinkBuyAction.putOrder(context);
                        }
                    }
                };

                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        handler.removeCallbacks(runnable);
                        drinkBuyAction.setValid(true);
                    }
                });

                handler.postDelayed(runnable, 10000);
            }
        });


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
