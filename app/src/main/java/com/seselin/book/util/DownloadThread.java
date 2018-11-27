package com.seselin.book.util;

import com.seselin.book.tag.EventBusTag;

/**
 * Created by Seselin on 2018/11/27 15:12.
 */
public class DownloadThread extends Thread {

    private String firstUrl;

    public DownloadThread(String firstUrl) {
        this.firstUrl = firstUrl;
    }

    @Override
    public void run() {
        String LastUrl = "";
        String pagerUrl = BookJsoup.getNovel(firstUrl, false);
        while (!this.isInterrupted()
                && pagerUrl.contains("html")
                && !"fail".equals(pagerUrl)
                && !LastUrl.equals(pagerUrl)) {
            LastUrl = pagerUrl;
            pagerUrl = BookJsoup.getNovel(pagerUrl, false);
        }
        LogUtil.d("完成");
        EventBusTag.post(EventBusTag.DOWNLOAD_FINISH);
    }

}
