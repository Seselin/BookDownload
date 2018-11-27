package com.seselin.book.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Seselin on 2018/11/27 13:35.
 */
public class InputUtil {

    public static boolean checkEditText(EditText editText) {
        String url = editText.getText().toString().trim();
        if (TextUtils.isEmpty(url)) {
            editText.requestFocus();
            String hint = editText.getHint().toString();
            if (TextUtils.isEmpty(hint)) {
                hint = "请输入";
            }
            editText.setError(hint);
            return true;
        }
        return false;
    }

    public static void hideKeyBoard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

}
