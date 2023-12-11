package com.example.btlandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlandroid.Interface.ItemClickListener;
import com.example.btlandroid.Model.DonHang;
import com.example.btlandroid.Model.EventBus.DonHangEvent;
import com.example.btlandroid.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ItemViewHoler> {

    private Context context;
    private List<DonHang> listDonHang;

    public DonHangAdapter(Context context, List<DonHang> listDonHang) {
        this.context = context;
        this.listDonHang = listDonHang;
    }

    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }

    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.onButtonClickListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_don_hang, parent, false);
        return new ItemViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHoler holder, int position) {
        DonHang donHang = listDonHang.get(position);
        holder.txtDonHang.setText("Đơn hàng: " + donHang.getId());
        //holder.trangthai.setText(trangThaiDon((donHang.getTrangthai())));

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rcvChiTiet.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        ChiTietAdapter chiTietAdapter = new ChiTietAdapter(context, donHang.getItem());
        holder.rcvChiTiet.setLayoutManager(layoutManager);
        holder.rcvChiTiet.setAdapter(chiTietAdapter);
        holder.rcvChiTiet.setRecycledViewPool(new RecyclerView.RecycledViewPool());

//        holder.setListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int pos, boolean isLongClick) {
//                EventBus.getDefault().postSticky(new DonHangEvent(donHang));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return listDonHang.size();
    }

    public class ItemViewHoler extends RecyclerView.ViewHolder {

        TextView txtDonHang, trangthai;
        RecyclerView rcvChiTiet;
        ImageView imgArrow;

        private ItemClickListener listener;

        public ItemViewHoler(@NonNull View itemView) {
            super(itemView);
            txtDonHang = itemView.findViewById(R.id.iddonhang);
            //trangthai = itemView.findViewById(R.id.tinhtrang);4
            imgArrow = itemView.findViewById(R.id.imgArrow);
            rcvChiTiet = itemView.findViewById(R.id.recycleview_chitiet);

            imgArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onButtonClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onButtonClickListener.onButtonClick(position);
                        }
                    }
                }
            });
        }

        public void setListener(ItemClickListener listener){
            this.listener = listener;
        }

    }



}
