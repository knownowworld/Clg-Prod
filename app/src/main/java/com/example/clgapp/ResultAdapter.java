package com.example.clgapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>
{
    private List<Result> listdata;
    private boolean mA;
    public ResultAdapter(List<Result> listdata,boolean mA)
    {
        this.mA = mA;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(mA) {
            listItem = layoutInflater.inflate(R.layout.result_list_items, parent, false);
        }else
        {
            listItem = layoutInflater.inflate(R.layout.attendence_list_items, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(listItem,mA);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.ViewHolder holder, int position) {
        final Result myListData = listdata.get(position);
        if(mA) {
            holder.subject.setText(myListData.retSub());
            holder.marks.setText(myListData.retMarks());
        }else
        {
            holder.sub.setText(myListData.retSub());
            holder.attended.setText(myListData.retMarks());
        }
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject;
        TextView marks;
        LinearLayout resLay;

        TextView sub,attended;
        LinearLayout atnLay;
        public ViewHolder(@NonNull View itemView,boolean mA) {
            super(itemView);
            //For Marks
            if(mA) {
                this.subject = (TextView) itemView.findViewById(R.id.subject);
                this.marks = (TextView) itemView.findViewById(R.id.secured);
                this.resLay = (LinearLayout) itemView.findViewById(R.id.resLay);
            }
            //For Attendence
            else
            {
                this.sub = (TextView) itemView.findViewById(R.id.sub);
                this.attended = (TextView) itemView.findViewById(R.id.attended);
                this.atnLay = (LinearLayout) itemView.findViewById(R.id.atnLay);
            }
        }
    }
}
