package com.graph.atfrinds;

import android.content.Context;
import android.util.AttributeSet;

import com.graph.atfrinds.CloudEditText;

/**
 * 作者： long on 2017/3/17 11:06
 */

public class ContactCloudEditTextImpl extends CloudEditText {

    public ContactCloudEditTextImpl(Context context) {
        super(context);
    }

    public ContactCloudEditTextImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactCloudEditTextImpl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean checkInputSpan(String showText, String returnText) {
        if(returnText.contains("@")){
            boolean result = true/*CheckUtils.checkEmail(returnText)*/;
            if(!result){
//                ToastUtils.showErrorToast(getContext(),"请输入一个邮箱",0);
            }
            return result;
        }else{
            if(returnText.contains("+")){
//                ToastUtils.showErrorToast(getContext(),"手机号前请不要加区号",0);
                return false;
            }else{
                boolean result = true;
                if(!result){
//                    ToastUtils.showErrorToast(getContext(),"请输入一个手机号",0);
                }
                return result;
            }
        }
    }
}
