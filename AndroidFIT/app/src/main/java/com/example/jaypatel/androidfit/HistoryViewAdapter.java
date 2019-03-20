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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryViewAdapter extends RecyclerView.Adapter<HistoryViewAdapter.MyViewHolder> {

    Context mContext;
    private ArrayList<DataWrapper.Workout> mData;
    private ArrayList<DataWrapper.Exercise> mData2;
    Dialog myDialog;
    Button btnEdit,btnDel;
    DataWrapper wrapper;



    public HistoryViewAdapter(Context mContext, ArrayList<DataWrapper.Workout> mData, ArrayList<DataWrapper.Exercise> mData2,DataWrapper wrapper){
        this.mContext = mContext;
        this.mData = mData;
        this.wrapper = wrapper;
        this.mData2 = mData2;
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
        view = LayoutInflater.from(mContext).inflate(R.layout.workout_list,viewGroup,false);
        final MyViewHolder viewHolder =  new MyViewHolder(view);


//        viewHolder.workout_list.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DataWrapper.Workout e = mData.get(viewHolder.getAdapterPosition());
//                //WorkoutView workoutView = new WorkoutView(wrapper, e.uuid);
//                Intent startWorkoutIntent = new Intent(mContext,WorkoutView.class);
//                startWorkoutIntent.putExtra("exerciseID", e.uuid);
//                startWorkoutIntent.putExtra("exerciseName", e.name);
//                mContext.startActivity(startWorkoutIntent);
//
//
//            }
//        });

        viewHolder.workout_list.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
//                        myDialog = new Dialog(mContext);
//                        myDialog.setContentView(R.layout.dialog_exercise);
//                        DataWrapper.Exercise e = mData.get(viewHolder.getAdapterPosition());
//
//                        Log.e("START EDITING PROCESS......", e.name);
//
//                        //TextView dialog_exe = (TextView) myDialog.findViewById(R.id.exe_id).se;
//                        ((EditText)myDialog.findViewById(R.id.exe_id)).setText(e.name);
//
//                        final String IDToEdit = e.uuid;
//
//                        btnDel = (Button)myDialog.findViewById(R.id.btn_delete);
//                        btnEdit = (Button)myDialog.findViewById(R.id.btn_edit);
//
//                        btnEdit.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                String text =  ((EditText) myDialog.findViewById(R.id.exe_id)).getEditableText().toString();
//                                Log.e("THE EDIT INFO:...." , IDToEdit + " " + text);
//                                wrapper.editExercise(IDToEdit,text);
//                                myDialog.dismiss();
//
//                            }
//                        });
//

//                        btnDel.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //wrapper.deleteExercise(IDToEdit);
//                                myDialog.dismiss();
//
//
//                            }
//                        });
                        myDialog.show();

                        return false;
                    }
                }
        );


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {


        holder.exe.setText(mData.get(i).exerciseUUID);


        holder.setnum.setText(String.valueOf(mData.get(0).sets));



        holder.amountofWeight.setText(String.valueOf(mData.get(i).sets.get(0).weight));



        //displayes the date
        Calendar calendar = Calendar.getInstance();
        String currentdate = DateFormat.getDateInstance().format(calendar.getTime());
        holder.dateval.setText(currentdate);

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
        private LinearLayout workout_list;
        TextView exe;
        TextView dateval;
        TextView setnum;
        TextView amountofWeight;


;



        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            workout_list = (LinearLayout) itemView.findViewById(R.id.workout_list_id);
            exe = (TextView) itemView.findViewById(R.id.exe_id);
            dateval = (TextView) itemView.findViewById(R.id.date);
            setnum = (TextView) itemView.findViewById(R.id.sets);
            amountofWeight =(TextView) itemView.findViewById(R.id.pounds);

        }


    }


}
