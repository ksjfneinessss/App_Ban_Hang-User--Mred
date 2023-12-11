package com.example.btlandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btlandroid.Interface.ItemClickListener;
import com.example.btlandroid.Model.EventBus.DonHangEvent;
import com.example.btlandroid.Model.Item;
import com.example.btlandroid.Model.SanPhamMoi;
import com.example.btlandroid.R;
import com.example.btlandroid.Utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ChiTietDhAdapter extends RecyclerView.Adapter<ChiTietDhAdapter.ItemViewHolder>{

    private Context context;
    private List<Item> itemList;

    public ChiTietDhAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chi_tiet_dh, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        //Glide.with(context).load(item.getHinhanh()).into(holder.imageCartProduct);

        if(item.getHinhanh().contains("http")){
            Glide.with(context).load(item.getHinhanh()).into(holder.imageCartProduct);
        }else{
            String img = Utils.BASE_URL+"images/"+item.getHinhanh();
            Glide.with(context).load(img).into(holder.imageCartProduct);
        }

        holder.tvBillingProductQuantity.setText("Số lượng: "+item.getSoluong());
        holder.tvProductCartName.setText(item.getTensp());
        holder.tvProductCartPrice.setText("Giá: "+item.getGia());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProductCartName, tvProductCartPrice, tvBillingProductQuantity;
        private ImageView imageCartProduct;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCartProduct = itemView.findViewById(R.id.imageCartProduct);
            tvProductCartName = itemView.findViewById(R.id.tvProductCartName);
            tvProductCartPrice = itemView.findViewById(R.id.tvProductCartPrice);
            tvBillingProductQuantity = itemView.findViewById(R.id.tvBillingProductQuantity);
        }

    }

}
