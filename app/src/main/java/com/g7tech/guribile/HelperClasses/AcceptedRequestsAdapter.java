package com.g7tech.guribile.HelperClasses;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.g7tech.guribile.Activities.RatingActivity;
import com.g7tech.guribile.Model.ApprovedRequests;
import com.g7tech.guribile.Model.PendingRequests;
import com.g7tech.guribile.R;

import java.util.List;

public class AcceptedRequestsAdapter extends RecyclerView.Adapter<AcceptedRequestsAdapter.ApprovedRequestsViewHolder> {
    private Context mCtx;
    private List<ApprovedRequests> ApprovedRequestsList;
    public SessionManager session;
    View itemView;

    public AcceptedRequestsAdapter(Context mCtx, List<ApprovedRequests> ApprovedRequestsList) {
        this.mCtx = mCtx;
        this.ApprovedRequestsList = ApprovedRequestsList;
        session = new SessionManager(mCtx);
    }

    @Override
    public AcceptedRequestsAdapter.ApprovedRequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.accepted_request_list, null);
        return new AcceptedRequestsAdapter.ApprovedRequestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AcceptedRequestsAdapter.ApprovedRequestsViewHolder holder, int position) {
        ApprovedRequests ApprovedRequests = ApprovedRequestsList.get(position);

        holder.id.setText(Integer.valueOf(ApprovedRequests.getId()).toString());
        holder.title.setText(String.valueOf(ApprovedRequests.getTitle()));
        holder.message.setText(String.valueOf(ApprovedRequests.getMessage()));
        holder.status.setText(String.valueOf(ApprovedRequests.getStatus()));
        holder.created_at.setText(String.valueOf(ApprovedRequests.getCreated_at()));
    }

    @Override
    public int getItemCount() {
        return ApprovedRequestsList.size();
    }


    class ApprovedRequestsViewHolder extends RecyclerView.ViewHolder {

        TextView id,title,message,status,created_at,accept;
        ImageView info;
        LinearLayout linear4;


        public ApprovedRequestsViewHolder(View itemView) {
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
                accept.setText("Waa la aqbalay");
            }
            else {
                accept.setText("Accepted");
            }
            accept.setTextColor(ContextCompat.getColor(mCtx, R.color.colorGreen));

            info.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        ApprovedRequests clickedDataItem = ApprovedRequestsList.get(pos);

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
                            dialog_title.setText("Adeeg: "+((ApprovedRequests)clickedDataItem).getTitle());
                            dialog_message.setText("Faahfaahin:\n"+((ApprovedRequests)clickedDataItem).getMessage());
                            dialog_status.setText("Waqtiga: "+((ApprovedRequests)clickedDataItem).getStatus());
                            dialog_created_at.setText("Taariikh: "+((ApprovedRequests)clickedDataItem).getCreated_at());
                        }
                        else {
                            dialog_title.setText("Service: "+((ApprovedRequests)clickedDataItem).getTitle());
                            dialog_message.setText("Description:\n"+((ApprovedRequests)clickedDataItem).getMessage());
                            dialog_status.setText("Time: "+((ApprovedRequests)clickedDataItem).getStatus());
                            dialog_created_at.setText("Date: "+((ApprovedRequests)clickedDataItem).getCreated_at());
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
