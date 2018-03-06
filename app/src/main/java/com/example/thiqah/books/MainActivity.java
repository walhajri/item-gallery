package com.example.thiqah.books;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ramotion.foldingcell.FoldingCell;

import java.util.List;
import api.DataSource;
import api.RemoteDataSource;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DataSource {

    private static final String TAG = "MainActivity";
    private static final int MY_PERMISSIONS_REQUEST = 100;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        setView();

        requestStoragePermission();

        initializeLayoutManager();

        initializeAdapter();

        initializeData();
    }

    private void setView() {
        ButterKnife.bind(this);
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }
    }

    private void initializeLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initializeAdapter() {
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initializeData() {
        RemoteDataSource remoteDataSource = new RemoteDataSource();

        remoteDataSource.getCoverPhotosListCall(MainActivity.this);
        remoteDataSource.getBookListCall(MainActivity.this);
        remoteDataSource.getAuthorListCall(MainActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // file-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public void passBookList(final List list) {
        recyclerViewAdapter.setDataList(list);
    }

    @Override
    public void passAuthorList(final List list) {
        recyclerViewAdapter.setAuthorsDataList(list);
    }

    @Override
    public void passCoverPhotoList(final List list) {
        recyclerViewAdapter.setPhotosDataList(list);
    }
}
