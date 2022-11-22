package com.g7tech.guribile.HelperClasses;

import android.app.Activity;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.g7tech.guribile.Activities.DashboardActivity;
import com.g7tech.guribile.Model.ApprovedRequests;
import com.g7tech.guribile.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateRequestsAdapter extends RecyclerView.Adapter<RateRequestsAdapter.RateRequestsViewHolder> {
    private Context mCtx;
    private List<ApprovedRequests> RateRequestsList;
    public SessionManager session;
    View itemView;

    public RateRequestsAdapter(Context mCtx, List<ApprovedRequests> RateRequestsList) {
        this.mCtx = mCtx;
        this.RateRequestsList = RateRequestsList;
        session = new SessionManager(mCtx);
    }

    @Override
    public RateRequestsAdapter.RateRequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.accepted_request_list, null);
        return new RateRequestsAdapter.RateRequestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RateRequestsAdapter.RateRequestsViewHolder holder, int position) {
        ApprovedRequests ApprovedRequests = RateRequestsList.get(position);

        holder.id.setText(Integer.valueOf(ApprovedRequests.getId()).toString());
        holder.title.setText(String.valueOf(ApprovedRequests.getTitle()));
        holder.message.setText(String.valueOf(ApprovedRequests.getMessage()));
        holder.status.setText(String.valueOf(ApprovedRequests.getStatus()));
        holder.created_at.setText(String.valueOf(ApprovedRequests.getCreated_at()));
    }

    @Override
    public int getItemCount() {
        return RateRequestsList.size();
    }


    class RateRequestsViewHolder extends RecyclerView.ViewHolder {

        TextView id,title,message,status,created_at,accept;
        ImageView info;
        LinearLayout linear4;


        public RateRequestsViewHolder(View itemView) {
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
                accept.setText("Qiimee Shaqada");
            }
            else {
                accept.setText("Rate Work");
            }
            accept.setTextColor(ContextCompat.getColor(mCtx, R.color.colorGreen));

            info.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        ApprovedRequests clickedDataItem = RateRequestsList.get(pos);

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

            accept.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        ApprovedRequests clickedDataItem = RateRequestsList.get(pos);
                        session = new SessionManager(mCtx);
                        session.setWork(String.valueOf(((ApprovedRequests)clickedDataItem).getId()));

                        final Dialog dialog = new Dialog(v.getContext());
                        dialog.setContentView(R.layout.rating_dialog);
                        if (session.getLanguage().length() == 6){
                            dialog.setTitle("Qiimaha Adeegga");
                        }
                        else {
                            dialog.setTitle("Rate Service");
                        }
                        Button buttonOk = dialog.findViewById(R.id.done);
                        final RatingBar stars = dialog.findViewById(R.id.stars);
                        dialog.show();
                        buttonOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                submitrating(Integer.valueOf(session.getWork()),(int) stars.getRating());
                            }
                        });

                    }
                }
            });
        }
    }

    private void submitrating(final int id,final int rating) {

        String tag_string_req = "Accept Request";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SUBMIT_RATING, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        if (session.getLanguage().length() == 6){
                            Toast.makeText(mCtx, "Ku guuleystey si ku-guuleysan ..!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(mCtx, "Rated Succesfully..!", Toast.LENGTH_LONG).show();
                        }

                        mCtx.startActivity(new Intent(mCtx, DashboardActivity.class));
                        ((Activity)mCtx).finish();
                    }
                    else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(mCtx,
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mCtx,
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                params.put("rating", String.valueOf(rating));
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

}
