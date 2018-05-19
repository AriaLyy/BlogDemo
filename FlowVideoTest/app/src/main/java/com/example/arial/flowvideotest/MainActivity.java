package com.example.arial.flowvideotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import java.io.IOException;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

  static {
    IjkMediaPlayer.loadLibrariesOnce(null);
    IjkMediaPlayer.native_profileBegin("libijkplayer.so");
  }

  SurfaceView mSf;

  private IjkMediaPlayer mMediaPlayer;
  private long mStartLocation = 0;
  private boolean isLive = true;
  private String mUrl;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_main);
    mSf = findViewById(R.id.sf);
    mSf.getHolder().addCallback(this);
    mUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
  }

  /**
   * 初始化播放器
   */
  private void initMediaPlay(SurfaceHolder sf, String url) {
    mMediaPlayer = new IjkMediaPlayer();
    mMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_INFO);
    //硬解码：1、打开，0、关闭
    mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
    //软解码：1、打开，0、关闭
    //mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "videotoolbox", 0);

    //rtsp设置 https://ffmpeg.org/ffmpeg-protocols.html#rtsp
    //mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");
    //mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_flags", "prefer_tcp");

    //udp
    mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "timeout", 10000);
    mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "buffer_size", 1316);
    //播放前的探测时间
    mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzeduration", 50000);
    //播放前的探测Size，默认是1M, 改小一点会出画面更快
    mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probsize", 4096);

    if (isLive) {
      // 最大缓存大小是3秒，可以依据自己的需求修改
      mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "max_cached_duration", 3000);
      mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "infbuf", 1);  // 无限读
      mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "packet-buffering", 0);//  关闭播放器缓冲
    } else {
      mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "max_cached_duration", 0);
      mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "infbuf", 0);
      mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "packet-buffering", 1);
    }

    mMediaPlayer.setOnPreparedListener(new IjkMediaPlayer.OnPreparedListener() {

      @Override public void onPrepared(IMediaPlayer iMediaPlayer) {
        if (mStartLocation > 0) {
          iMediaPlayer.seekTo(mStartLocation);
        } else {
          iMediaPlayer.start();
        }
      }
    });
    mMediaPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
      @Override public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
      }
    });
    mMediaPlayer.setOnSeekCompleteListener(new IMediaPlayer.OnSeekCompleteListener() {
      @Override public void onSeekComplete(IMediaPlayer iMediaPlayer) {
        iMediaPlayer.start();
      }
    });
    mMediaPlayer.setOnBufferingUpdateListener(new IMediaPlayer.OnBufferingUpdateListener() {
      @Override public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
      }
    });
    mMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
      @Override public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
      }
    });
    try {
      mMediaPlayer.setDataSource(url);
    } catch (IOException e) {
      e.printStackTrace();
    }
    mMediaPlayer.setDisplay(sf);
    mMediaPlayer.prepareAsync();
  }

  @Override public void surfaceCreated(SurfaceHolder holder) {
    initMediaPlay(holder, mUrl);
  }

  @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

  }

  @Override public void surfaceDestroyed(SurfaceHolder holder) {
  }
}
