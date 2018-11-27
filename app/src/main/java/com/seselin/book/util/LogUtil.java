package com.seselin.book.util;

import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.seselin.book.tag.EventBusTag;

/**
 * Created by Seselin on 2018/11/27 10:38.
 */
public class LogUtil {

    private static volatile LogUtil instance;

    public static LogUtil getInstance() {
        if (instance == null) {
            synchronized (LogUtil.class) {
                if (instance == null) {
                    instance = new LogUtil();
                }
            }
        }
        return instance;
    }

    public void release() {
        instance = null;
    }

    private TextView textView;

    public LogUtil() {
    }

    public void initText(TextView textView) {
        this.textView = textView;
    }

    public static void d(String text) {
        LogUtil.getInstance().write(text);
    }

    public void write(final String text) {
        if (textView == null) {
            System.out.print(text);
            return;
        }
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.append(text + BookJsoup.NEW_LINE);
                EventBusTag.post(EventBusTag.LOAD_FINISH);
                ViewGroup viewParent = (ViewGroup) textView.getParent();
                if (viewParent instanceof ScrollView) {
                    ScrollView scrollView = (ScrollView) viewParent;
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            }
        });
    }

    public void clear() {
        if (textView == null) {
            return;
        }
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("");
            }
        });
    }

}
