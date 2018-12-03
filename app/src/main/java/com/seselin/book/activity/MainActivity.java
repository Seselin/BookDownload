package com.seselin.book.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.seselin.book.R;
import com.seselin.book.bean.BookSetting;
import com.seselin.book.tag.EventBusTag;
import com.seselin.book.util.ApkTools;
import com.seselin.book.util.BookJsoup;
import com.seselin.book.util.InputUtil;
import com.seselin.book.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_book_name)
    EditText etBookName;
    @BindView(R.id.et_url)
    EditText etUrl;
    @BindView(R.id.tv_log)
    TextView tvLog;
    @BindView(R.id.btn_download)
    Button btnDownload;

    private boolean isDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApkTools.setStatusBarColor(this, R.color.colorPrimary);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LogUtil.getInstance().initText(tvLog);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDownload();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Message message) {
        switch (message.what) {
            case EventBusTag.DOWNLOAD_FINISH:
                isDownloading = false;
                btnDownload.setText("开始下载");
                break;
            case EventBusTag.LOAD_FINISH:
                tvLog.requestFocus();
                tvLog.requestLayout();
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.btn_setting, R.id.btn_preview, R.id.btn_download})
    void onClickEvent(View view) {
        InputUtil.hideKeyBoard(this);
        switch (view.getId()) {
            case R.id.btn_setting:
                LogUtil.getInstance().clear();
                break;
            case R.id.btn_preview:
                preview();
                break;
            case R.id.btn_download:
                if (isDownloading) {
                    stopDownload();
                } else {
                    startDownload();
                }
                break;
            default:
                break;
        }
    }

    private void preview() {
        if (InputUtil.checkEditText(etUrl)) {
            return;
        }
        LogUtil.getInstance().clear();
        String url = etUrl.getText().toString().trim();
        BookJsoup.testUrl(url);
    }

    private Thread downloadThread;

    private void startDownload() {
        if (InputUtil.checkEditText(etUrl)) {
            return;
        }
        if (InputUtil.checkEditText(etBookName)) {
            return;
        }
        LogUtil.getInstance().clear();
        String bookName = etBookName.getText().toString().trim();
        BookSetting.getInstance().setBookName(bookName);
        String url = etUrl.getText().toString().trim();
        downloadThread = BookJsoup.getDownloadThread(url);
        downloadThread.start();
        isDownloading = true;
        btnDownload.setText("暂停下载");
    }

    private void stopDownload() {
        if (downloadThread != null) {
            downloadThread.interrupt();
        }
    }

}
