package com.seselin.book.tag;

import android.os.Message;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Seselin on 2016/6/20.
 * EventBus的标志
 */
public class EventBusTag {

    public static final int DOWNLOAD_FINISH = 0x01;//下载线程停止
    public static final int LOAD_FINISH = 0x02;//加载完毕

    public static void post(int tag) {
        Message message = Message.obtain();
        message.what = tag;
        EventBus.getDefault().post(message);
    }

}
