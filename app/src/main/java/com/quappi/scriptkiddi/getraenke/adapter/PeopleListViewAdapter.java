package com.quappi.scriptkiddi.getraenke.adapter;

import android.content.Intent;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.quappi.scriptkiddi.getraenke.ListViewDrinks;
import com.quappi.scriptkiddi.getraenke.ManagePersonActivity;
import com.quappi.scriptkiddi.getraenke.utils.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fritz on 08.07.17.
 */

public class PeopleListViewAdapter extends RecyclerView.Adapter<PeopleListViewAdapter.ViewHolder> implements Filterable {
    private final SortedList.Callback<Person> mCallback = new SortedList.Callback<Person>() {

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public int compare(Person a, Person b) {
            return a.getFirstName().compareTo(b.getFirstName());
        }

        @Override
        public boolean areContentsTheSame(Person oldItem, Person newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(Person item1, Person item2) {
            return item1.getFirstName().equals(item2.getFirstName()) && item1.getLastName().equals(item2.getLastName());
        }
    };

    final SortedList<Person> mSortedList = new SortedList<>(Person.class, mCallback);


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;

        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PeopleListViewAdapter(ArrayList<Person> myDataset) {
        this.add(myDataset);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PeopleListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mSortedList.get(position).getFirstName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListViewDrinks.class);
                intent.putExtra("Person", mSortedList.get(position));
                v.getContext().startActivity(intent);
            }
        });
        holder.itemView.setLongClickable(true);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(view.getContext(), ManagePersonActivity.class);
                intent.putExtra("Person", mSortedList.get(position));
                view.getContext().startActivity(intent);
                return true;
            }
        });
    }

    public void add(Person model) {
        mSortedList.add(model);
    }

    public void remove(Person model) {
        mSortedList.remove(model);
    }

    public void add(List<Person> models) {
        mSortedList.addAll(models);
    }

    public void remove(List<Person> models) {
        mSortedList.beginBatchedUpdates();
        for (Person model : models) {
            mSortedList.remove(model);
        }
        mSortedList.endBatchedUpdates();
    }

    public void replaceAll(List<Person> models) {
        mSortedList.beginBatchedUpdates();
        for (int i = mSortedList.size() - 1; i >= 0; i--) {
            final Person model = mSortedList.get(i);
            if (!models.contains(model)) {
                mSortedList.remove(model);
            }
        }
        mSortedList.addAll(models);
        mSortedList.endBatchedUpdates();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mSortedList.size();
    }
}