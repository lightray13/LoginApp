package com.login.app.ui;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.login.app.R;
import com.login.app.data.db.model.UsersListEntity;
import com.login.app.mvp.presenter.MainPresenter;
import com.login.app.mvp.view.MainView;
import com.login.app.utils.NetworkUtils;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends MvpActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;

    @BindView(R.id.editTextPass)
    EditText editTextPass;

    @BindView(R.id.buttonEnterUser)
    Button buttonEnterUser;

    @BindView(R.id.spinnerUsersList)
    NiceSpinner spinnerUsersList;

    @Optional
    @OnClick(R.id.buttonEnterUser)
    void onClick() {
        logIn();
    }

    String uid = "";
    private ProgressDialog mProgressDialog;

    private static final int REQUEST_READ_PHONE_STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (!checkPermission()) {
            requestPermission();
        } else {
            loadData();
        }
    }

    public static String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;
    }

    private boolean checkPermission()
    {
        return (checkSelfPermission(Manifest.permission.READ_PRECISE_PHONE_STATE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_PHONE_STATE && grantResults.length > 0) {
            loadData();
        } else {
            showError("Permission denied");
        }
    }

    private void loadData() {
        if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
            mainPresenter.loadUsersListIntoDatabase(getDeviceId(this));
        } else {
            mainPresenter.loadUsersList();
        }
    }

    public void logIn() {
        Editable pass = editTextPass.getText();
        if (!TextUtils.isEmpty(pass) && !pass.toString().equals("")
        && !uid.equals("")) {
            mainPresenter.authentication(spinnerUsersList.getText().toString(),
                    getDeviceId(this),
                    uid,
                    pass.toString());
        } else {
            showError("Enter password");
        }
    }

    @Override
    public void showUsersList(List<UsersListEntity> list) {
        List<String> names = new ArrayList<>();
        for (UsersListEntity entity: list) {
            names.add(entity.user);
        }
        List<String> dataset = new LinkedList<>(names);
        spinnerUsersList.attachDataSource(dataset);
        uid = list.get(0).uid;
        spinnerUsersList.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                uid = list.get(position).uid;
            }
        });
    }

    @Override
    public void startLoading() {
        mProgressDialog = showLoadingDialog();
    }

    @Override
    public void endLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginResponse() {
        startActivity(new Intent(this, LoginHistoryActivity.class));
    }

    public ProgressDialog showLoadingDialog() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}