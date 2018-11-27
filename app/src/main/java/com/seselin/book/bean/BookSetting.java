package com.seselin.book.bean;

import android.os.Environment;

/**
 * Created by Seselin on 2018/11/27 10:36.
 */
public class BookSetting {

    private static volatile BookSetting instance;

    public static BookSetting getInstance() {
        if (instance == null) {
            synchronized (BookSetting.class) {
                if (instance == null) {
                    instance = new BookSetting();
                }
            }
        }
        return instance;
    }

    public void release() {
        instance = null;
    }

    private String bookName = "书名";
    private String filePath = Environment.getExternalStorageDirectory() + "/book/download/";

    public BookSetting() {
    }

    public String getBookName() {
        return bookName;
    }

    public BookSetting setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public BookSetting setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getSavePath() {
        String bookName = BookSetting.getInstance().getBookName() + ".txt";
        String savePath = BookSetting.getInstance().getFilePath() + bookName;
        return savePath;
    }

}
