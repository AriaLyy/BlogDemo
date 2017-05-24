package com.lyy.ariatest;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;

public class MainActivity extends AppCompatActivity {
  private static final String DOWNLOAD_URL =
      "http://static.gaoshouyou.com/d/d4/4f/d6d48db3794fb9ecf47e83c346570881.apk";

  @Bind(R.id.progressBar) HorizontalProgressBarWithNumber mPb;
  //@Bind(R.id.start) Button mStart;
  //@Bind(R.id.stop) Button mStop;
  //@Bind(R.id.cancel) Button mCancel;
  @Bind(R.id.size) TextView mSize;
  @Bind(R.id.speed) TextView mSpeed;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  @OnClick({ R.id.start, R.id.stop, R.id.cancel }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.start:
        Aria.download(this)
            .load(DOWNLOAD_URL)
            .setDownloadPath(Environment.getExternalStorageDirectory().getPath() + "/aria_test.apk")
            .start();
        break;
      case R.id.stop:
        Aria.download(this).load(DOWNLOAD_URL).pause();
        break;
      case R.id.cancel:
        Aria.download(this).load(DOWNLOAD_URL).cancel();
        break;
    }
  }

  @Override protected void onResume() {
    super.onResume();
    Aria.download(this).addSchedulerListener(new MySchedulerListener());
  }

  private class MySchedulerListener extends Aria.DownloadSchedulerListener {

    @Override public void onTaskStart(DownloadTask task) {
      mSize.setText(task.getConvertFileSize());
    }

    @Override public void onTaskStop(DownloadTask task) {
      Toast.makeText(MainActivity.this, "停止下载", Toast.LENGTH_SHORT).show();
    }

    @Override public void onTaskCancel(DownloadTask task) {
      Toast.makeText(MainActivity.this, "取消下载", Toast.LENGTH_SHORT).show();
      mSize.setText("0m");
      mSpeed.setText("");
      mPb.setProgress(0);
    }

    @Override public void onTaskFail(DownloadTask task) {
      Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
    }

    @Override public void onTaskComplete(DownloadTask task) {
      Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
    }

    @Override public void onTaskRunning(DownloadTask task) {
      //使用转换单位后的速度，需要在aria_config.xml配置文件中将单位转换开关打开
      //https://github.com/AriaLyy/Aria#配置文件设置参数
      mSpeed.setText(task.getConvertSpeed());
      mPb.setProgress(task.getPercent());
    }
  }
}
