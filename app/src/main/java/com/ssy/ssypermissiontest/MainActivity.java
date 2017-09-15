package com.ssy.ssypermissiontest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ssy.ssypermission.SSYPms;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Button btnStorageCheck;
    private Button btnStorageReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        btnStorageCheck = (Button) findViewById(R.id.btnStorageCheck);
        btnStorageCheck.setOnClickListener(clickListener);
        btnStorageReq = (Button) findViewById(R.id.btnStorageReq);
        btnStorageReq.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnStorageCheck:
                    if (SSYPms.hasPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        Toast.makeText(mContext, "有---存储读写权限", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(mContext, "无---存储读写权限", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnStorageReq:
                    String[] ps = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA};
                    SSYPms.reqPermission((Activity) mContext, ps, 111);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions == null || permissions.length <= 0)
            return;
        if (grantResults == null || grantResults.length <= 0)
            return;

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("permission---", "permission---存储-同意===");
        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Log.d("permission---", "permission---存储-拒绝===");
        } else {
            Log.d("permission---", "permission---存储-拒绝===");
        }

        if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d("permission---", "permission---相机-同意===");
        } else if (grantResults[1] == PackageManager.PERMISSION_DENIED) {
            Log.d("permission---", "permission---相机-拒绝===");
        } else {
            Log.d("permission---", "permission---相机-拒绝===");
        }
    }
}
