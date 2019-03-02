package com.example.jaypatel.androidfit;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Exercises> mData;
    Dialog myDialog;

    public RecyclerViewAdapter(Context mContext, List<Exercises> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.items_exercise,viewGroup,false);
        final MyViewHolder viewHolder =  new MyViewHolder(view);
        //dialog stuff




        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_exercise);


        viewHolder.item_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_exe = (TextView) myDialog.findViewById(R.id.exe_id);

                dialog_exe.setText(mData.get(viewHolder.getAdapterPosition()).getName());
               Toast.makeText(mContext,"Test Click"+String.valueOf(viewHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                myDialog.show();
            }
        });



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.exe.setText(mData.get(i).getName());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder  implements View.OnClickListener{
        private LinearLayout item_exercise;
        private TextView exe;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            item_exercise = (LinearLayout) itemView.findViewById(R.id.exe_item_id);
            exe = (TextView) itemView.findViewById(R.id.exe_name);
        }

        @Override
        public void onClick(View v) {
            //gets postion of item clicked
            int mPosition = getLayoutPosition();
            Exercises element = mData.get(mPosition);
            //mData.set(mPosition,"Clicked" + element);

        }
    }


}
