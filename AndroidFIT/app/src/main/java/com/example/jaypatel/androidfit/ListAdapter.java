//package com.example.jaypatel.androidfit;
//
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView.ViewHolder;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//public class ListAdapter extends RecyclerView.Adapter {
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//
//    private class ListViewHolder extends ViewHolder implements View.OnClickListener{
//
//
//        private TextView exe;
//        public ListViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            exe = (TextView) itemView.findViewById(R.id.exe_name);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            Log.d("onclick","onClick" +getLayoutPosition()+ " "+ exe.getText());
//        }
//
//
//    }
//}
