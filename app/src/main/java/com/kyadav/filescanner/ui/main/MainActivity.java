package com.kyadav.filescanner.ui.main;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyadav.filescanner.R;
import com.kyadav.filescanner.base.BaseActivity;
import com.kyadav.filescanner.datamodel.FileFrequencyModel;
import com.kyadav.filescanner.util.GridSpacingItemDecoration;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMvpView, View.OnClickListener {

    @Inject
    MainMvpPresenter<MainMvpView> presenter;


    @Inject
    RecyclerView.LayoutManager layoutManager;


    @Inject
    RecyclerView.LayoutManager biggestLayoutManager;

    @Inject
    FileFrequencyAdapter adapter;


    @Inject
    BiggestFileAdapter biggestFileAdapter;


    Button startScanButton,shareDataButton;
    TextView averageSize, maxSizeText, frequencyText;
    RecyclerView frequencyRecyclerView, biggestFileRecyclerView;
    LinearLayout noDataLayout;

    double averageSizeinMB;
    List<FileFrequencyModel> fileFrequencyModelList;
    List<File> fileList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        getActivityComponent().inject(this);
        presenter.onViewAttach(this);
        setScreenTitle("File Scanner");
        findViewById();
        setOnClickListener();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        frequencyRecyclerView.setLayoutManager(layoutManager);
        //frequencyRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        //frequencyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        frequencyRecyclerView.setAdapter(adapter);


        biggestFileRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //biggestFileRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        //biggestFileRecyclerView.setItemAnimator(new DefaultItemAnimator());
        biggestFileRecyclerView.setAdapter(biggestFileAdapter);
    }

    @Override
    protected void findViewById() {
        super.findViewById();
        startScanButton = findViewById(R.id.startScanButton);
        averageSize = findViewById(R.id.averageSize);
        frequencyRecyclerView = (RecyclerView) findViewById(R.id.mostFrequentRecyclerView);
        biggestFileRecyclerView = (RecyclerView) findViewById(R.id.biggestRecylerView);
        maxSizeText = findViewById(R.id.biggestText);
        frequencyText = findViewById(R.id.mostFrequentText);
        noDataLayout = findViewById(R.id.noDataLayout);
        shareDataButton=findViewById(R.id.shareData);


    }

    @Override
    protected void setOnClickListener() {
        super.setOnClickListener();
        startScanButton.setOnClickListener(this);
        shareDataButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.startScanButton:
                presenter.onStartScanClicked();
                break;

            case R.id.shareData:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                Bundle bundle=new Bundle();
                sendIntent.putExtra("AVERAGE_FILE_SIZE",averageSizeinMB);
                sendIntent.putExtra("BIGGEST_FILE", (Serializable) fileList);
                sendIntent.putExtra("FREQUENCY_DATA", (Serializable) fileFrequencyModelList);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDetach();
    }

    @Override
    public void onAverageSizeCalculated(double size) {
        noDataLayout.setVisibility(View.GONE);
        averageSize.setVisibility(View.VISIBLE);
        maxSizeText.setVisibility(View.VISIBLE);
        frequencyText.setVisibility(View.VISIBLE);
        frequencyRecyclerView.setVisibility(View.VISIBLE);
        biggestFileRecyclerView.setVisibility(View.VISIBLE);
        averageSize.setText("Average File Size : " + String.format("%.2f", size) + " MB");

    }

    @Override
    public void onFrequencyCalculated(List<FileFrequencyModel> list) {
        adapter.addItems(list);
    }


    @Override
    public void onBiggestFilesCalculated(List<File> fileList) {
        biggestFileAdapter.addItems(fileList);
        shareDataButton.setVisibility(View.VISIBLE);

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
