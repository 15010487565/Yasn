package com.yasn.purchase.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.OrderDetailsActivity;
import com.yasn.purchase.activity.PayActivity;
import com.yasn.purchase.activityold.WebViewH5Activity;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.order.OrderMainPayInfoModel;
import com.yasn.purchase.model.order.OrderSonPayInfoModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gs on 2018/2/3.
 */

public abstract class OrderFragment extends SimpleTopbarFragment {

    protected LinearLayout llError;
    protected ImageView ivError;
    protected TextView tvErrorHint;
    @Override
    protected void initView(LayoutInflater inflater, View view) {
        llError = (LinearLayout)view.findViewById(R.id.ll_Error);
        llError.setVisibility(View.GONE);
        ivError = (ImageView) view.findViewById(R.id.iv_Error);
        tvErrorHint = (TextView) view.findViewById(R.id.tv_ErrorHint);
    }


    protected void startOrderDetailsActivity(int orderId, int isMainOrder){
        startOrderDetailsActivity(orderId,isMainOrder,false);
    }
    protected void startOrderDetailsActivity(int orderId,int isMainOrder,boolean isNeedPay){
        Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
        intent.putExtra("orderId",orderId);
        intent.putExtra("isMainOrder",isMainOrder);
        intent.putExtra("isNeedPay",isNeedPay);
        startActivityForResult(intent,10001);
    }
    protected void startOrderPay(Object o){
        if (o instanceof OrderMainPayInfoModel) {
            OrderMainPayInfoModel infoModel = (OrderMainPayInfoModel) o;
            if (Config.isWebViewPay) {
                int orderid = infoModel.getOrderId();
                Intent intent = new Intent(getActivity(), WebViewH5Activity.class);
                intent.putExtra("webViewUrl", Config.ORDERPAY + orderid);
                startActivity(intent);
            } else {
                //订单号
                String sn = infoModel.getSn();
                Log.e("TAG_立即支付", "sn=" + sn);
                //支付金额
                String needPayMoney = infoModel.getNeedPayMoney();
                //订单创建时间
                long createTime = infoModel.getCreateTime();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                Log.e("TAG_时间", "createTime=" + df.format(new Date(createTime)));
                String format = df.format(new Date(createTime + 2 * 60 * 60 * 1000));
                Intent intent = new Intent(getActivity(), PayActivity.class);
                intent.putExtra("sn", sn);
                intent.putExtra("needPayMoney", needPayMoney);
                intent.putExtra("payTime", format);
                startActivity(intent);
            }
        }else if (o instanceof OrderSonPayInfoModel){

        }
    }
}
