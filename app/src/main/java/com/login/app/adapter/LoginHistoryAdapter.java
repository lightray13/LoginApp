package com.login.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.login.app.R;
import com.login.app.data.db.model.LoginHistoryEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LoginHistoryAdapter extends RecyclerView.Adapter<LoginHistoryAdapter.LoginHistoryViewHolder> {

    private List<LoginHistoryEntity> items;

    public LoginHistoryAdapter(List<LoginHistoryEntity> items) {
        this.items = items;
    }

    @Override
    public LoginHistoryAdapter.LoginHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.login_history_item, parent, false);
        return new LoginHistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final LoginHistoryAdapter.LoginHistoryViewHolder holder, final int position) {
        final LoginHistoryEntity item = items.get(position);
        holder.loginHistoryEntityUid.setText(item.uid);
        holder.loginHistoryEntityUserName.setText("Login: " + item.user);
        holder.loginHistoryEntityUserPass.setText("Password: " + item.pass);
        holder.loginHistoryEntityUserImei.setText("IMEI: " + item.imei);
        holder.loginHistoryEntityUserDate.setText(item.date);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class LoginHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView loginHistoryEntityUid;
        TextView loginHistoryEntityUserName;
        TextView loginHistoryEntityUserPass;
        TextView loginHistoryEntityUserImei;
        TextView loginHistoryEntityUserDate;

        public LoginHistoryViewHolder(View itemView) {
            super(itemView);
            loginHistoryEntityUid = itemView.findViewById(R.id.loginHistoryEntityUid);
            loginHistoryEntityUserName = itemView.findViewById(R.id.loginHistoryEntityUserName);
            loginHistoryEntityUserPass = itemView.findViewById(R.id.loginHistoryEntityUserPass);
            loginHistoryEntityUserImei = itemView.findViewById(R.id.loginHistoryEntityUserImei);
            loginHistoryEntityUserDate = itemView.findViewById(R.id.loginHistoryEntityUserDate);
        }
    }
}
