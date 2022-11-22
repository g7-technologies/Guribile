package com.g7tech.guribile.HelperClasses;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.g7tech.guribile.Activities.Appointment_fix;
import com.g7tech.guribile.Model.PendingRequests;
import com.g7tech.guribile.R;
import java.util.List;

public class PendingRequestsAdapter extends RecyclerView.Adapter<PendingRequestsAdapter.PendingRequestsViewHolder> {
    private Context mCtx;
    private List<PendingRequests> PendingRequestsList;
    public SessionManager session;

    public PendingRequestsAdapter(Context mCtx, List<PendingRequests> PendingRequestsList) {
        this.mCtx = mCtx;
        this.PendingRequestsList = PendingRequestsList;
        session = new SessionManager(mCtx);
    }

    @Override
    public PendingRequestsAdapter.PendingRequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.pending_request_list, null);
        return new PendingRequestsAdapter.PendingRequestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PendingRequestsAdapter.PendingRequestsViewHolder holder, int position) {
        PendingRequests PendingRequests = PendingRequestsList.get(position);

        double diss = distance(Double.valueOf(session.getLat()),Double.valueOf(session.getLongt()),Double.valueOf(PendingRequests.getLatitude()),Double.valueOf(PendingRequests.getLongitude()));
        holder.id.setText(Integer.valueOf(PendingRequests.getId()).toString());
        holder.title.setText(String.valueOf(PendingRequests.getTitle()));
        holder.message.setText(String.valueOf(PendingRequests.getMessage()));
        holder.status.setText(String.valueOf(PendingRequests.getStatus()));
        if (session.getLanguage().length() == 6){
            holder.distancee.setText(String.valueOf(diss)+" Km fog.");
        }
        else {
            holder.distancee.setText(String.valueOf(diss)+" Km Away From you.");
        }

        holder.created_at.setText(String.valueOf(PendingRequests.getCreated_at()));
        ImageLoader imgLoader = new ImageLoader(mCtx);
        int loader = R.drawable.add_user;
        imgLoader.DisplayImage("http://guribile.cert-pro.net/profileImages/"+PendingRequests.getCreated_at(), loader, holder.info);

    }

    @Override
    public int getItemCount() {
        return PendingRequestsList.size();
    }


    class PendingRequestsViewHolder extends RecyclerView.ViewHolder {

        TextView id,title,message,status,created_at,distancee;
        ImageView info;
        LinearLayout linear4;

        public PendingRequestsViewHolder(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            status = itemView.findViewById(R.id.status);
            created_at = itemView.findViewById(R.id.created_at);
            distancee = itemView.findViewById(R.id.distance);
            info = itemView.findViewById(R.id.info);
            linear4 = itemView.findViewById(R.id.linear44);


            info.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        PendingRequests clickedDataItem = PendingRequestsList.get(pos);

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
                            dialog_title.setText("Adeeg: "+((PendingRequests)clickedDataItem).getTitle());
                            dialog_message.setText("Faahfaahin:\n"+((PendingRequests)clickedDataItem).getMessage());
                            dialog_status.setText("Waqtiga: "+((PendingRequests)clickedDataItem).getStatus());
                            dialog_created_at.setText("Taariikh: "+((PendingRequests)clickedDataItem).getCreated_at());
                        }
                        else {
                            dialog_title.setText("Service: "+((PendingRequests)clickedDataItem).getTitle());
                            dialog_message.setText("Description:\n"+((PendingRequests)clickedDataItem).getMessage());
                            dialog_status.setText("Time: "+((PendingRequests)clickedDataItem).getStatus());
                            dialog_created_at.setText("Date: "+((PendingRequests)clickedDataItem).getCreated_at());
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

            linear4.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        PendingRequests clickedDataItem = PendingRequestsList.get(pos);

                        Intent intent = new Intent(mCtx, Appointment_fix.class);
                        intent.putExtra("Worker_id",String.valueOf(((PendingRequests)clickedDataItem).getId()));
                        mCtx.startActivity(intent);
                        session.setWorker(String.valueOf(((PendingRequests)clickedDataItem).getId()));
                        Log.e("worker_id",session.getWorker());
                    }
                }
            });
        }
    }

    private int distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist / 0.62137;
        int distt = (int)dist;
        return (distt);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}