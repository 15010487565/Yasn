package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.model.AddressModel;

import java.util.List;

import static com.yasn.purchase.R.id.tv_AddressSetting;

/**
 * Created by gs on 2017/12/29.
 */

public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<AddressModel> list;
    private OnAddressClickListener onItemClickListener;
    private int addrId;

    public AddressAdapter(Context context) {
        super();
        this.context = context;
    }

    public void setOnItemClickListener(OnAddressClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<AddressModel> list,int addrId) {
        this.list = list;
        this.addrId = addrId;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycleritem_address, parent, false);
        RecyclerView.ViewHolder holder = new AddressViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        AddressModel addressModel = list.get(position);

        AddressViewHolder addressViewHolder = (AddressViewHolder) holder;
        String name = addressModel.getName();
        addressViewHolder.tvName.setText(name == null ? "" : name);
        String mobile = addressModel.getMobile();
        addressViewHolder.tvNumber.setText(mobile == null ? "" : mobile);
        //地址
        String province = addressModel.getProvince();
        String city = addressModel.getCity();
        String region = addressModel.getRegion();
        String addr = addressModel.getAddr();
        if (TextUtils.isEmpty(addr)){
            addressViewHolder.tvMsg.setText("收货地址："+province+"-"+city+"-"+region);
        }else {
            addressViewHolder.tvMsg.setText("收货地址："+province+"-"+city+"-"+region+"-"+addr);
        }
        int defAddr = addressModel.getDefAddr();
        if (defAddr == 1){
            addressViewHolder.ivSelect.setBackgroundResource(R.mipmap.checkbox_checked);
            addressViewHolder.tvSetting.setText("默认地址");
            addressViewHolder.tvDet.setVisibility(View.GONE);
        }else {
            addressViewHolder.ivSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
            addressViewHolder.tvSetting.setText("设为默认地址");
            addressViewHolder.tvDet.setVisibility(View.VISIBLE);
        }
        int addrId = addressModel.getAddrId();
        if (this.addrId == addrId ){
            addressViewHolder.tvDet.setBackgroundResource(R.drawable.shape_oranger_oranger5);
            addressViewHolder.tvUpdata.setBackgroundResource(R.drawable.shape_oranger_oranger5);
            addressViewHolder.llAddressItem.setBackgroundColor(ContextCompat.getColor(context,R.color.orange_arrdess));
        }else {
            addressViewHolder.tvDet.setBackgroundResource(R.drawable.shape_oranger_white5);
            addressViewHolder.tvUpdata.setBackgroundResource(R.drawable.shape_oranger_white5);
            addressViewHolder.llAddressItem.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
        }
        onItemEventClick(addressViewHolder);
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvName,tvNumber,tvMsg,tvSetting,tvDet,tvUpdata;
        LinearLayout llSelect;
        ImageView ivSelect;
        LinearLayout llAddressItem;
        public AddressViewHolder(View itemView) {
            super(itemView);
            llAddressItem = (LinearLayout) itemView.findViewById(R.id.ll_AddressItem);
            llSelect = (LinearLayout) itemView.findViewById(R.id.ll_AddressSelect);
            llSelect.setOnClickListener(this);
            ivSelect = (ImageView) itemView.findViewById(R.id.iv_AddressSelect);
            tvSetting = (TextView) itemView.findViewById(tv_AddressSetting);
            tvSetting.setOnClickListener(this);
            tvDet = (TextView) itemView.findViewById(R.id.tv_AddressDet);
            tvDet.setOnClickListener(this);
            tvUpdata = (TextView) itemView.findViewById(R.id.tv_AddressUpdata);
            tvUpdata.setOnClickListener(this);

            tvName = (TextView) itemView.findViewById(R.id.tv_AddressName);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_AddressNumber);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_AddressMsg);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_AddressSetting://设置默认
                    onItemClickListener.OnItemSettingAddressClick(view,getLayoutPosition());
                    break;
                case R.id.ll_AddressSelect://设置默认
                    onItemClickListener.OnItemSettingAddressClick(view,getLayoutPosition());
                    break;
                case R.id.tv_AddressDet://删除
                    onItemClickListener.OnItemDeleteAddressClick(view,getLayoutPosition());
                    break;
                case R.id.tv_AddressUpdata://编辑
                    onItemClickListener.OnItemUpdataAddressClick(view,getLayoutPosition());
                    break;

            }
        }
    }

    public interface OnAddressClickListener {
        //选择
        void OnItemClick(View view, int position);
        //设置默认
        void OnItemSettingAddressClick(View view, int position);
       //编辑
        void OnItemUpdataAddressClick(View view, int position);
        //删除
        void OnItemDeleteAddressClick(View view, int position);
    }
    private void onItemEventClick(RecyclerView.ViewHolder holder) {
        final int position = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(v, position);
            }
        });

    }
}
