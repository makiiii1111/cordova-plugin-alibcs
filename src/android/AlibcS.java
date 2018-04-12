package topo.alibcs;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.webkit.WebSettings;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.android.trade.page.AlibcShopPage;
import com.alibaba.baichuan.trade.biz.AlibcConstants;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This class echoes a string called from JavaScript.
 */
public class AlibcS extends CordovaPlugin {
    //mm_131040549_43524337_344058314 24831514
    private  String pid = "";
    private String adzoneid = "";
    private String appKey = "";
    private final static String TB = "taobao";
    private final static String TM = "tianmao";
    private final static String H5 = "h5";
    private Map<String, String> exParams;//yhhpass参数
    private AlibcShowParams alibcShowParams;
    private AlibcTaokeParams alibcTaokeParams = null;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView){
        super.initialize(cordova, webView);
        if(!cordova.hasPermission(Manifest.permission.READ_PHONE_STATE)){
            this.cordova.requestPermission(this, 10086, Manifest.permission.READ_PHONE_STATE);
        }
        AlibcTradeSDK.asyncInit(this.cordova.getActivity().getApplication() ,new AlibcTradeInitCallback(){
            @Override
            public void onSuccess(){
                alibcTaokeParams = new AlibcTaokeParams();
                pid = preferences.getString("PID","");
                adzoneid = pid.split("_")[3];
                appKey = preferences.getString("APPKEY","");
                alibcTaokeParams.setPid(pid);
                alibcTaokeParams.setAdzoneid(adzoneid);
                alibcTaokeParams.setSubPid(pid);
                alibcTaokeParams.extraParams = new HashMap<String, String>();
                alibcTaokeParams.extraParams.put("taokeAppkey", appKey);
                exParams = new HashMap<String, String>();
                exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
            }

            @Override
            public void  onFailure(int code, String msg){
                System.out.printf(msg);
            }
        });
    }
    @Override
    public boolean execute(String action,JSONArray args, CallbackContext callbackContext) throws JSONException {
        String type = args.getString(1);
        if(type==TB){
            alibcShowParams = new AlibcShowParams(OpenType.Native, false);
            alibcShowParams.setClientType("taobao_scheme");
        }else if(type==TM){
            alibcShowParams = new AlibcShowParams(OpenType.Native, false);
            alibcShowParams.setClientType("tmall_scheme");
        }else if(type==H5){
            alibcShowParams = new AlibcShowParams(OpenType.H5, false);
        }else{
            alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        }
        if (action.equals("detail")) {
            String message = args.getString(0);
            this.showDetail(message, callbackContext);
            return true;
        } else if (action.equals("page")) {
            String message = args.getString(0);
            this.showPage(message, callbackContext);
            return true;
        } else if (action.equals("shop")){
            String message = args.getString(0);
            this.showShop(message, callbackContext);
            return true;
        }
        return false;
    }

    private void showDetail(String message, CallbackContext callbackContext){
        if (message != null && message.length()>0){
            AlibcBasePage detailPage = new AlibcDetailPage(message);
            AlibcTrade.show(this.cordova.getActivity(), detailPage, alibcShowParams, null, exParams, new AlibcTradeCallback() {
                @Override
                public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                    System.out.print(alibcTradeResult.toString());
                }

                @Override
                public void onFailure(int i, String s) {
                    System.out.print(s);
                }
            });

        }else {
            callbackContext.error("error");
        }
    }

    private void showPage(String message, CallbackContext callbackContext) {
        if (message !=null && message.length()>0){
            AlibcBasePage page = new AlibcPage(message);
            AlibcTrade.show(this.cordova.getActivity(), page, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback(){
                @Override
                public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                    System.out.printf(alibcTradeResult.toString());
                }

                @Override
                public void onFailure(int code, String msg){
                    System.out.printf(msg);
                }
            });
        }
    }

    private void showShop(String message, CallbackContext callbackContext){
        if (message != null && message.length()>0){
            AlibcBasePage shopPage = new AlibcShopPage(message);
            AlibcTrade.show(this.cordova.getActivity(), shopPage, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
                @Override
                public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                    System.out.print(alibcTradeResult.toString());
                }

                @Override
                public void onFailure(int i, String s) {
                    System.out.print(s);
                }
            });

        }else {
            callbackContext.error("error");
        }
    }

    @Override
    public void onDestroy(){
        AlibcTradeSDK.destory();
        super.onDestroy();
    }
}
