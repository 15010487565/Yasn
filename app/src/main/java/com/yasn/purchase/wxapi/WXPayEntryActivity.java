package com.yasn.purchase.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yasn.purchase.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import static com.yasn.purchase.pay.PayConfig.WEIXIN_APP_ID;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case 0://成功
                    EventBus.getDefault().post("1");//1成功,其他未支付成功,-3未安装微信
                    break;
                case -1://错误
                    EventBus.getDefault().post("-1");
                    break;
                case -2://用户取消
                    EventBus.getDefault().post("-2");
                    break;
                default:
                    ToastUtil.showToast("未安装威信！");
                    setResult(RESULT_OK);
                    break;
            }
            this.finish();
        }
    }

}
