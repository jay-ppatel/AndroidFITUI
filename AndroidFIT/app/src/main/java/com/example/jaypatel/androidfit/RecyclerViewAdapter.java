package com.example.jaypatel.androidfit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    private ArrayList<DataWrapper.Exercise> mData;
    Dialog myDialog;
    Button btnEdit,btnDel;
    DataWrapper wrapper;


    public RecyclerViewAdapter(Context mContext,ArrayList<DataWrapper.Exercise> mData, DataWrapper wrapper){
        this.mContext = mContext;
        this.mData = mData;
        this.wrapper = wrapper;
    }

    /*public RecyclerViewAdapter(Context mContext,ArrayList<DataWrapper.Exercise> mData, DataWrapper wrapper){
        this.mContext = mContext;
        this.mData = mData;
        this.wrapper = wrapper;
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,  int i) {
        final View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.items_exercise,viewGroup,false);
        final MyViewHolder viewHolder =  new MyViewHolder(view);



         viewHolder.item_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataWrapper.Exercise e = mData.get(viewHolder.getAdapterPosition());
                //WorkoutView workoutView = new WorkoutView(wrapper, e.uuid);
                Intent startWorkoutIntent = new Intent(mContext,WorkoutView.class);
                startWorkoutIntent.putExtra("exerciseID", e.uuid);
                startWorkoutIntent.putExtra("exerciseName", e.name);
                mContext.startActivity(startWorkoutIntent);


            }
        });

        viewHolder.item_exercise.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        myDialog = new Dialog(mContext);
                        myDialog.setContentView(R.layout.dialog_exercise);
                        DataWrapper.Exercise e = mData.get(viewHolder.getAdapterPosition());

                        Log.e("START EDITING PROCESS......", e.name);

                        //TextView dialog_exe = (TextView) myDialog.findViewById(R.id.exe_id).se;
                        ((EditText)myDialog.findViewById(R.id.exe_id)).setText(e.name);

                        final String IDToEdit = e.uuid;

                        btnDel = (Button)myDialog.findViewById(R.id.btn_delete);
                        btnEdit = (Button)myDialog.findViewById(R.id.btn_edit);

                        btnEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String text =  ((EditText) myDialog.findViewById(R.id.exe_id)).getEditableText().toString();
                                Log.e("THE EDIT INFO:...." , IDToEdit + " " + text);
                                wrapper.editExercise(IDToEdit,text);
                                myDialog.dismiss();

                            }
                        });
//

                        btnDel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                wrapper.deleteExercise(IDToEdit);
                                myDialog.dismiss();


                            }
                        });
                        myDialog.show();

                        return false;
                    }
                }
        );


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.exe.setText(mData.get(i).name);

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setWrapper(DataWrapper wrapper)
    {
        this.wrapper = wrapper;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        private LinearLayout item_exercise;
        TextView exe;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            item_exercise = (LinearLayout) itemView.findViewById(R.id.exe_item_id);
            exe = (TextView) itemView.findViewById(R.id.exe_name);
        }


    }


}
