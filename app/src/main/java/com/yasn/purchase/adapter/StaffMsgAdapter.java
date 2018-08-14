package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.model.StaffMessageModel;

import java.util.List;

/**
 * Created by gs on 2017/12/29.
 */

public class StaffMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private  List<StaffMessageModel.DataBean> list;

    public StaffMsgAdapter(Context context) {
        super();
        this.context = context;
    }


    public void setData( List<StaffMessageModel.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleritem_staffmsg, parent, false);
        RecyclerView.ViewHolder holder = new StatisViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        StatisViewHolder viewHolder = (StatisViewHolder) holder;
        StaffMessageModel.DataBean dataBean = list.get(position);
        //1:采购 ,2:财务 1,2采购+财务
        String employeeAuth = dataBean.getEmployeeAuth();
        if ("1".equals(employeeAuth)){
            viewHolder.tvEmployeeAuth.setText("采购");
            viewHolder.tvEmployeeAuth.setTextColor(ContextCompat.getColor(context,R.color.black_66));
        }else if ("2".equals(employeeAuth)){
            viewHolder.tvEmployeeAuth.setText("财务");
            viewHolder.tvEmployeeAuth.setTextColor(ContextCompat.getColor(context,R.color.black_66));
        }else if ("1,2".equals(employeeAuth)){
            viewHolder.tvEmployeeAuth.setText("采购+财务");
            viewHolder.tvEmployeeAuth.setTextColor(ContextCompat.getColor(context,R.color.black_66));
        }else if ("-1".equals(employeeAuth)){
            viewHolder.tvEmployeeAuth.setText("部门");
            viewHolder.tvEmployeeAuth.setTextColor(ContextCompat.getColor(context,R.color.black_33));
        }
        String phone = dataBean.getPhone();
        if ("手机号".equals(phone)){
            viewHolder.tvEmployeeAuth.setTextColor(ContextCompat.getColor(context,R.color.black_33));
        }else {
            viewHolder.tvEmployeeAuth.setTextColor(ContextCompat.getColor(context,R.color.black_66));
        }
        viewHolder.tvNumber.setText(phone == null ? "" : phone );
        //状态 (1为待确认，2可以修改，删除)
        int inviteStatus = dataBean.getInviteStatus();
        if (inviteStatus == 1){

            viewHolder.tvDel.setVisibility(View.GONE);
            viewHolder.tvUpdata.setVisibility(View.GONE);
            viewHolder.tvInviteStatus.setVisibility(View.VISIBLE);
            viewHolder.tvEmployeeAuth.setTextColor(ContextCompat.getColor(context,R.color.black_66));
        }else if (inviteStatus == 2){
            viewHolder.tvDel.setVisibility(View.VISIBLE);
            viewHolder.tvUpdata.setVisibility(View.VISIBLE);
            viewHolder.tvInviteStatus.setVisibility(View.GONE);
            viewHolder.tvEmployeeAuth.setTextColor(ContextCompat.getColor(context,R.color.black_66));
        }else if (inviteStatus == -1){

            viewHolder.tvDel.setVisibility(View.GONE);
            viewHolder.tvUpdata.setVisibility(View.GONE);
            viewHolder.tvInviteStatus.setVisibility(View.VISIBLE);
            viewHolder.tvInviteStatus.setText("操作");
            viewHolder.tvInviteStatus.setTextColor(ContextCompat.getColor(context,R.color.black_33));
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class StatisViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvEmployeeAuth;
        private TextView tvNumber;
        private TextView tvUpdata,tvDel,tvInviteStatus;

        public StatisViewHolder(View view) {
            super(view);
            tvEmployeeAuth = (TextView)itemView.findViewById(R.id.tv_StaffEmployeeAuth);
            tvNumber = (TextView)itemView.findViewById(R.id.tv_StaffNumber);
            tvInviteStatus = (TextView)itemView.findViewById(R.id.tv_StaffInviteStatus);
            tvDel = (TextView)itemView.findViewById(R.id.tv_Staffdel);
            tvDel.setOnClickListener(this);
            tvUpdata = (TextView)itemView.findViewById(R.id.tv_StaffUpdata);
            tvUpdata.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_StaffUpdata:
                    onListener.OnUpdataClick(view,getLayoutPosition());
                    break;
                case R.id.tv_Staffdel:
                    onListener.OnDelClick(view, getLayoutPosition());
                    break;
            }
        }
    }
    private OnInviteStatusListener onListener;

    public void setOnUpdataClickListener(OnInviteStatusListener onListener) {
        this.onListener = onListener;
    }

    public interface OnInviteStatusListener {
        //修改
        void OnUpdataClick(View view, int position);
        //删除
        void OnDelClick(View view, int position);
    }
}
