package com.login.app.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.widget.Toast;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.login.app.R;
import com.login.app.adapter.LoginHistoryAdapter;
import com.login.app.data.db.model.LoginHistoryEntity;
import com.login.app.mvp.presenter.LoginHistoryPresenter;
import com.login.app.mvp.view.LoginHistoryView;

import java.util.List;

public class LoginHistoryActivity extends MvpActivity implements LoginHistoryView {

    @InjectPresenter
    LoginHistoryPresenter loginHistoryPresenter;

    @BindView(R.id.loginHistoryListRecyclerView)
    RecyclerView loginHistoryListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_history);

        ButterKnife.bind(this);

        loginHistoryPresenter.loadLoginHistoryList();
    }

    @Override
    public void showLoginHistoryList(List<LoginHistoryEntity> list) {
        loginHistoryListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loginHistoryListRecyclerView.setAdapter(new LoginHistoryAdapter(list));
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}