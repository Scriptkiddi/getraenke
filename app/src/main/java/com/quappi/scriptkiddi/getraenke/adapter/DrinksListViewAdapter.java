package com.quappi.scriptkiddi.getraenke.adapter;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quappi.scriptkiddi.getraenke.R;
import com.quappi.scriptkiddi.getraenke.utils.Drink;
import com.quappi.scriptkiddi.getraenke.utils.Person;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
     * Created by fritz on 08.07.17.
     */

public class DrinksListViewAdapter extends RecyclerView.Adapter<DrinksListViewAdapter.ViewHolder>{
        private final Person person;
        private ArrayList<Drink> mDataset;


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
        public DrinksListViewAdapter(ArrayList<Drink> myDataset, Person person) {
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
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.drinkTitle.setText(mDataset.get(position).getName());
            NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);
            holder.drinkPrice.setText(formatter.format(mDataset.get(position).getResellPrice()));
            holder.drinkVolume.setText(String.format("%.2f l",mDataset.get(position).getVolume()));
            holder.drinkImage.setImageDrawable(mDataset.get(position).getImage());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(person.getFirstName()).show();
                }
            });

        }



        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
   }
