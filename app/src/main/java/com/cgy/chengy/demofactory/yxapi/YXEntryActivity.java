package com.cgy.chengy.demofactory.yxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.cgy.chengy.demofactory.app.App;
import com.outim.yxopen.api.IYXAPIEventHandler;
import com.outim.yxopen.modelbase.BaseResp;

/**
 * Created by ChenGY on 2019-01-21.
 */
public class YXEntryActivity extends Activity implements IYXAPIEventHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getYxapi().handleIntent(getIntent(), this);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        String result;
        if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
            result = "成功";
            try {
                String code = baseResp.data;
                Log.e("weChatCode", code);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (baseResp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
            result = "取消";
        } else {
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "发送被拒绝";
                    break;
                case BaseResp.ErrCode.ERR_UNSUPPORT:
                    result = "不支持错误";
                    break;
                default:
                    result = "发送返回";
                    break;
            }
        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        finish();
    }
}
