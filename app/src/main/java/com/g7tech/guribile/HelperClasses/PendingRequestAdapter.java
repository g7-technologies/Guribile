package com.g7tech.guribile.HelperClasses;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.g7tech.guribile.Model.CompletedRequests;
import com.g7tech.guribile.R;

import java.util.List;

public class PendingRequestAdapter extends RecyclerView.Adapter<PendingRequestAdapter.PendingRequestViewHolder> {
    private Context mCtx;
    private List<CompletedRequests> CompletedRequestsList;
    public SessionManager session;
    View itemView;

    public PendingRequestAdapter(Context mCtx, List<CompletedRequests> CompletedRequestsList) {
        this.mCtx = mCtx;
        this.CompletedRequestsList = CompletedRequestsList;
        session = new SessionManager(mCtx);
    }

    @Override
    public PendingRequestAdapter.PendingRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.accepted_request_list, null);
        return new PendingRequestAdapter.PendingRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PendingRequestAdapter.PendingRequestViewHolder holder, int position) {
        CompletedRequests CompletedRequests = CompletedRequestsList.get(position);

        holder.id.setText(Integer.valueOf(CompletedRequests.getId()).toString());
        holder.title.setText(String.valueOf(CompletedRequests.getTitle()));
        holder.message.setText(String.valueOf(CompletedRequests.getMessage()));
        holder.status.setText(String.valueOf(CompletedRequests.getStatus()));
        holder.created_at.setText(String.valueOf(CompletedRequests.getCreated_at()));
    }

    @Override
    public int getItemCount() {
        return CompletedRequestsList.size();
    }


    class PendingRequestViewHolder extends RecyclerView.ViewHolder {

        TextView id,title,message,status,created_at,accept;
        ImageView info;
        LinearLayout linear4;


        public PendingRequestViewHolder(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            status = itemView.findViewById(R.id.status);
            created_at = itemView.findViewById(R.id.created_at);
            info = itemView.findViewById(R.id.info);
            linear4 = itemView.findViewById(R.id.linear44);
            accept = itemView.findViewById(R.id.accept);
            if (session.getLanguage().length() == 6){
                accept.setText("Inta la sugayo");
            }
            else {
                accept.setText("Pending");
            }

            accept.setTextColor(ContextCompat.getColor(mCtx, R.color.colorPrimary));


            info.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        CompletedRequests clickedDataItem = CompletedRequestsList.get(pos);

                        final Dialog dialog = new Dialog(v.getContext());
                        dialog.setContentView(R.layout.custom_dialog);
                        if (session.getLanguage().length() == 6){
                            dialog.setTitle("Faahfaahin");
                        }
                        else {
                            dialog.setTitle("Description");
                        }
                        TextView dialog_title = dialog.findViewById(R.id.dialog_title);
                        TextView dialog_message = dialog.findViewById(R.id.dialog_message);
                        TextView dialog_status = dialog.findViewById(R.id.dialog_status);
                        TextView dialog_created_at = dialog.findViewById(R.id.dialog_created_at);
                        Button buttonOk = dialog.findViewById(R.id.buttonOk);
                        if (session.getLanguage().length() == 6){
                            dialog_title.setText("Adeeg: "+((CompletedRequests)clickedDataItem).getTitle());
                            dialog_message.setText("Faahfaahin:\n"+((CompletedRequests)clickedDataItem).getMessage());
                            dialog_status.setText("Waqtiga: "+((CompletedRequests)clickedDataItem).getStatus());
                            dialog_created_at.setText("Taariikh: "+((CompletedRequests)clickedDataItem).getCreated_at());
                        }
                        else {
                            dialog_title.setText("Service: "+((CompletedRequests)clickedDataItem).getTitle());
                            dialog_message.setText("Description:\n"+((CompletedRequests)clickedDataItem).getMessage());
                            dialog_status.setText("Time: "+((CompletedRequests)clickedDataItem).getStatus());
                            dialog_created_at.setText("Date: "+((CompletedRequests)clickedDataItem).getCreated_at());
                        }

                        dialog.show();
                        buttonOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                }
            });
        }
    }
}
