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
import com.g7tech.guribile.Model.DeclinedRequests;
import com.g7tech.guribile.R;

import java.util.List;

public class DeclinedRequestsAdapter extends RecyclerView.Adapter<DeclinedRequestsAdapter.DeclinedRequestsViewHolder> {
    private Context mCtx;
    private List<DeclinedRequests> DeclinedRequestsList;
    public SessionManager session;
    View itemView;

    public DeclinedRequestsAdapter(Context mCtx, List<DeclinedRequests> DeclinedRequestsList) {
        this.mCtx = mCtx;
        this.DeclinedRequestsList = DeclinedRequestsList;
        session = new SessionManager(mCtx);
    }

    @Override
    public DeclinedRequestsAdapter.DeclinedRequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.accepted_request_list, null);
        return new DeclinedRequestsAdapter.DeclinedRequestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeclinedRequestsAdapter.DeclinedRequestsViewHolder holder, int position) {
        DeclinedRequests DeclinedRequests = DeclinedRequestsList.get(position);

        holder.id.setText(Integer.valueOf(DeclinedRequests.getId()).toString());
        holder.title.setText(String.valueOf(DeclinedRequests.getTitle()));
        holder.message.setText(String.valueOf(DeclinedRequests.getMessage()));
        holder.status.setText(String.valueOf(DeclinedRequests.getStatus()));
        holder.created_at.setText(String.valueOf(DeclinedRequests.getCreated_at()));
    }

    @Override
    public int getItemCount() {
        return DeclinedRequestsList.size();
    }


    class DeclinedRequestsViewHolder extends RecyclerView.ViewHolder {

        TextView id,title,message,status,created_at,accept;
        ImageView info;
        LinearLayout linear4;


        public DeclinedRequestsViewHolder(View itemView) {
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
                accept.setText("Waa diiday");
            }
            else {
                accept.setText("Declined");
            }
            accept.setTextColor(ContextCompat.getColor(mCtx, R.color.red));


            info.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        DeclinedRequests clickedDataItem = DeclinedRequestsList.get(pos);

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
                            dialog_title.setText("Adeeg: "+((DeclinedRequests)clickedDataItem).getTitle());
                            dialog_message.setText("Faahfaahin:\n"+((DeclinedRequests)clickedDataItem).getMessage());
                            dialog_status.setText("Waqtiga: "+((DeclinedRequests)clickedDataItem).getStatus());
                            dialog_created_at.setText("Taariikh: "+((DeclinedRequests)clickedDataItem).getCreated_at());
                        }
                        else {
                            dialog_title.setText("Service: "+((DeclinedRequests)clickedDataItem).getTitle());
                            dialog_message.setText("Description:\n"+((DeclinedRequests)clickedDataItem).getMessage());
                            dialog_status.setText("Time: "+((DeclinedRequests)clickedDataItem).getStatus());
                            dialog_created_at.setText("Date: "+((DeclinedRequests)clickedDataItem).getCreated_at());
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
