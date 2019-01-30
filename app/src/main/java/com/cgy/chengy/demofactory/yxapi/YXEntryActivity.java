package com.cgy.chengy.demofactory.yxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.cgy.chengy.demofactory.app.App;
import com.cgy.chengy.demofactory.utils.StrNumUtil;
import com.outim.yxopen.api.IYXAPIEventHandler;
import com.outim.yxopen.modelbase.BaseResp;
import com.outim.yxopen.modelbase.SendAuth;
import com.outim.yxopen.modelbase.YXConstants;

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
    public void onResp(BaseResp resp) {
        String scope = resp.scope;
        String result;
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
            result = "成功";
            if (scope.equals(YXConstants.Scope.AUTHOR)) {
                String code = ((SendAuth.Resp) resp).code;
                Log.e("weChatCode", code);
            }
        } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
            result = "取消";
        } else {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    if (!TextUtils.isEmpty(resp.errStr)) {
                        result = resp.errStr;
                    } else {
                        result = "发送被拒绝";
                    }
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
