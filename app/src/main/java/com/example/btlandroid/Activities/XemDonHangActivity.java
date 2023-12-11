package com.example.btlandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlandroid.Adapter.DonHangAdapter;
import com.example.btlandroid.Model.DonHang;
import com.example.btlandroid.Model.DonHangModel;
import com.example.btlandroid.Model.EventBus.DonHangEvent;
import com.example.btlandroid.R;
import com.example.btlandroid.Retrofit.ApiShop;
import com.example.btlandroid.Utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class XemDonHangActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rcvDonHang;
    private ImageView imgDonHangTrong;
    private DonHang donHang;
    private Disposable disposable;
    private DonHangAdapter adapter;
    private List<DonHang> listDonHang = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don_hang);
        initView();
        initToolbar();
        getOrder();
    }

    private void getDetailOrder() {
        adapter.setOnButtonClickListener(new DonHangAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(int position) {
                DonHang donHang1 = listDonHang.get(position);
                Intent intent = new Intent(XemDonHangActivity.this, ChiTietDhActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("donHang", donHang1);
                intent.putExtra("DATA", bundle);
                startActivity(intent);
            }
        });
    }

    private void getOrder() {
        ApiShop.getApiShop.xemDonHang(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DonHangModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull DonHangModel donHangModel) {
                        listDonHang = donHangModel.getResult();
                        if (listDonHang.size() == 0) {
                            imgDonHangTrong.setVisibility(View.VISIBLE);
                        }
                        adapter = new DonHangAdapter(getApplicationContext(), listDonHang);
                        rcvDonHang.setAdapter(adapter);
                        getDetailOrder();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("XemDonHangActivity", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("XemDonHangActivity", "Lấy Đơn hàng thành công!");
                    }
                });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        imgDonHangTrong = findViewById(R.id.imgDonHangTrong);
        rcvDonHang = findViewById(R.id.recycleview_donhang);
        toolbar = findViewById(R.id.toobar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvDonHang.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventDonHang(DonHangEvent event){
        if(event != null){
            donHang = event.getDonHang();
        }
    }


}