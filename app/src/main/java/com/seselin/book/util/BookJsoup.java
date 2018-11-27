package com.seselin.book.util;

import com.seselin.book.bean.BookSetting;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BookJsoup {

    public static final String NEW_LINE = "\r\n";
    public static final String PLACEHOLDER = "seselin";

    public static void main(String[] args) {
        String PagerUrl = "http://www.kbiquge.com/7_7134/27240305.html";
        boolean debug = true;// true为测试模式，只打印1章

        if (debug) {
            testUrl(PagerUrl);
        } else {
            startDownload(PagerUrl);
        }
    }

    public static void testUrl(final String pagerUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getNovel(pagerUrl, true);
            }
        }).start();
    }

    public static void startDownload(final String firstUrl) {
        getDownloadThread(firstUrl).start();
    }

    public static Thread getDownloadThread(final String firstUrl) {
        return new DownloadThread(firstUrl);
    }

    public static String getNovel(String PagerUrl, boolean debug) {
        Document doc = getDocument(PagerUrl);
        if (doc == null) {
            LogUtil.d("URL不可用");
            return "fail";
        }
        String title;
        String content;
        String pagerNext;

        title = getTitle(doc);
        pagerNext = getNextUrl(doc);
        content = getBody(doc);

        LogUtil.d(title);
        if (debug) {
            LogUtil.d(NEW_LINE + content);
            LogUtil.d(NEW_LINE + pagerNext);
        } else {
            String savePath = BookSetting.getInstance().getFilePath();
            String fileName = BookSetting.getInstance().getBookName() + ".txt";
            FileUtils.write(savePath, fileName, NEW_LINE + title);
            FileUtils.write(savePath, fileName, NEW_LINE + content);
        }

        return pagerNext;
    }

    private static Document getDocument(String PagerUrl) {
        int counts = 0;
        while (counts < 5) {
            try {
                String url = PagerUrl;
                Document doc = Jsoup.connect(url).get();
                return doc;
            } catch (Exception ex) {
                counts++;
                try {
                    if (counts > 1)
                        Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取文章标题
     *
     * @param doc
     * @return
     */
    private static String getTitle(Document doc) {
        return doc.getElementsByTag("h1").text();
    }

    /**
     * 获取下一章链接地址
     *
     * @param doc
     * @return
     */
    private static String getNextUrl(Document doc) {
        Elements bottom = doc.getElementsContainingOwnText("下一页");
        if (bottom == null || bottom.size() <= 0) {
            bottom = doc.getElementsContainingOwnText("下一章");
        }
        if (bottom == null || bottom.size() <= 0) {
            return "fail";
        }
        Element next = bottom.get(0);
        String url = next.attr("abs:href");
        return url;
    }

    /**
     * 获取文章内容
     *
     * @param doc
     * @return
     */
    private static String getBody(Document doc) {
        String tempHtml = doc.toString().replace("<br>", PLACEHOLDER);
        Document newDoc = Jsoup.parse(tempHtml);
        Elements contentE = newDoc.getElementsContainingOwnText(PLACEHOLDER);
        if (contentE == null || contentE.size() == 0) {
            return "";
        }
        for (int i = 0; i < contentE.size(); i++) {
            String content = contentE.get(i).ownText();
            if (content.length() > 50) {
                content = content.replace(PLACEHOLDER, NEW_LINE);
                return content;
            }
        }
        return "";
    }

}
