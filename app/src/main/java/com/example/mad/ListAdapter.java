package com.example.mad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ListAdapter extends FirebaseRecyclerAdapter<Reviews,ListAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ListAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Reviews> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ListAdapter.myViewHolder holder, int position, @NonNull @NotNull Reviews model) {

        holder.cus_neme.setText(model.getName());
        holder.rev_date.setText(model.getDate());
        holder.rev_mess.setText(model.getMessage());

    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView cus_neme,rev_date,rev_mess;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            cus_neme = (TextView)itemView.findViewById(R.id.cus_name);
            rev_date = (TextView)itemView.findViewById(R.id.rev_date);
            rev_mess = (TextView)itemView.findViewById(R.id.rev_mess);

        }
    }


}
