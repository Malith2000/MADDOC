package com.example.mad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyListAdapter extends FirebaseRecyclerAdapter<Reviews,MyListAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyListAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Reviews> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull MyListAdapter.myViewHolder holder, final int position, @NonNull @NotNull Reviews model) {

        holder.mrev_date.setText(model.getDate());
        holder.mrev_mess.setText(model.getMessage());

        holder.buttn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.mrev_mess.getContext())
                        .setContentHolder(new ViewHolder(R.layout.edit_review))
                        .setExpanded(true,1400)
                        .create();

                //dialogPlus.show();

                View view1 = dialogPlus.getHolderView();

                EditText mess = view1.findViewById(R.id.edit_mess);
                EditText date = view1.findViewById(R.id.editDate);

                Button sub_buttn = view1.findViewById(R.id.edit_subbttn);

                mess.setText(model.getMessage());

                dialogPlus.show();


                sub_buttn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("date" ,date.getText().toString());
                        map.put("message",mess.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Reviews")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.mrev_mess.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(holder.mrev_mess.getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

            }
        });

        //delete function
        holder.buttn_dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.mrev_mess.getContext());
                builder.setTitle("Delete Review");
                builder.setMessage("Are you sure want to delete this review?");

                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Reviews")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.mrev_mess.getContext(), "Cancelled.",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @NotNull
    @Override
    public MyListAdapter.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_review_list,parent,false);
        return new MyListAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView mrev_date,mrev_mess;
        Button buttn_edit,buttn_dlt;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mrev_date = (TextView)itemView.findViewById(R.id.mrev_date);
            mrev_mess = (TextView)itemView.findViewById(R.id.mrev_mess);

            buttn_edit = (Button)itemView.findViewById(R.id.buttn_edit);
            buttn_dlt = (Button)itemView.findViewById(R.id.buttn_dlt);

        }
    }

}
