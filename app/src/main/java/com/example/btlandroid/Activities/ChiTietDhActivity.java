package com.example.btlandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlandroid.Adapter.ChiTietDhAdapter;
import com.example.btlandroid.Model.DonHang;
import com.example.btlandroid.Model.Item;
import com.example.btlandroid.R;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class ChiTietDhActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private StepView stepView;
    private TextView tvFullName, tvAddress, tvPhoneNumber, tvTotalPrice, tvOrderId;
    private RecyclerView rvProducts;
    private ChiTietDhAdapter adapter;
    private List<Item> itemList = new ArrayList<>();
    private int trangthai = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dh);
        initView();
        initControl();
        ActionToolbar();
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initControl() {
        ArrayList<String> listTrangThai = new ArrayList<>();
        listTrangThai.add("Đang xử lý");
        listTrangThai.add("Đã chấp nhận");
        listTrangThai.add("Đang giao hàng");
        listTrangThai.add("Giao hàng thành công");
        stepView.setSteps(listTrangThai);
        stepView.go(trangthai, false);
        if(trangthai == 3){
            stepView.done(true);
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbarOrderDetails);
        stepView = findViewById(R.id.stepView);
        tvFullName = findViewById(R.id.tvFullName);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvOrderId = findViewById(R.id.tvOrderId);
        rvProducts = findViewById(R.id.rvProducts);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA");
        DonHang donHang = (DonHang) bundle.getSerializable("donHang");

        itemList = donHang.getItem();
        tvTotalPrice.setText(donHang.getTongtien());
        tvFullName.setText("Mã đơn hàng: "+donHang.getId());
        tvPhoneNumber.setText("SĐT: "+donHang.getSodienthoai());
        tvAddress.setText("Địa chỉ: "+donHang.getDiachi());
        tvOrderId.setText("Chi tiết đơn hàng");
        trangthai = donHang.getTrangthai();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChiTietDhActivity.this, RecyclerView.VERTICAL, false);
        adapter = new ChiTietDhAdapter(ChiTietDhActivity.this, itemList);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.setAdapter(adapter);

    }
}
