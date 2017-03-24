package com.arialyy.listplayerprj;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.Bind;
import com.arialyy.absadapter.common.AbsHolder;
import com.arialyy.absadapter.recycler_view.AbsRVAdapter;
import com.bumptech.glide.Glide;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Aria.Lao on 2017/3/22.
 */

public class ListPlayAdapter extends AbsRVAdapter<Entity, ListPlayAdapter.MyHolder>
    implements TextureView.SurfaceTextureListener {
  private static final String TAG = "VideoAdapter";
  private String mUrl;
  VideoPlay mPlayer;
  private ProgressBar mPb;

  public ListPlayAdapter(Context context, List<Entity> data) {
    super(context, data);
  }

  @Override public int getItemViewType(int position) {

    return mData.get(position).isVideo ? 0 : 1;
  }

  @Override protected MyHolder getViewHolder(View convertView, int viewType) {
    return viewType == 0 ? new VideoHolder(convertView) : new ImgHolder(convertView);
  }

  @Override protected int setLayoutId(int type) {
    return type == 0 ? R.layout.item_video : R.layout.item_img;
  }

  @Override protected void bindData(MyHolder holder, int position, Entity item) {
    if (!item.isVideo) {
      Glide.with(getContext()).load(item.url).into(((ImgHolder) holder).img);
    } else {
      mUrl = item.url;
      VideoHolder helper = (VideoHolder) holder;
      mPb = helper.pb;
      if (mPlayer == null) {
        mPb.setVisibility(View.VISIBLE);
      }
      helper.video.setVisibility(View.VISIBLE);
      ViewGroup.LayoutParams lp = helper.video.getLayoutParams();
      lp.height = 600;
      helper.video.setLayoutParams(lp);
      helper.video.setSurfaceTextureListener(this);
    }
  }

  @Override public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
    mPlayer = new VideoPlay(mUrl, new Surface(surface), mPb);
    new Thread(mPlayer).start();
  }

  @Override public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

  }

  @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
    if (mPlayer != null) {
      mPlayer.stop();
    }
    surface.release();
    mPlayer = null;
    return true;
  }

  @Override public void onSurfaceTextureUpdated(SurfaceTexture surface) {

  }

  static int currentPosition = 0;

  private static class VideoPlay implements Runnable {
    MediaPlayer player;
    String url;
    WeakReference<Surface> surface;
    WeakReference<ProgressBar> pb;

    VideoPlay(String url, Surface surface, ProgressBar pb) {
      this.url = url;
      this.surface = new WeakReference<>(surface);
      this.pb = new WeakReference<>(pb);
    }

    void stop() {
      if (player != null && player.isPlaying()) {
        currentPosition = player.getCurrentPosition();
        player.stop();
        player.release();
        player = null;
      }
    }

    @Override public void run() {
      try {
        player = new MediaPlayer();
        player.setDataSource(url);
        player.setSurface(this.surface.get());
        player.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
          @Override public void onPrepared(MediaPlayer mp) {
            if (currentPosition != 0 && currentPosition != mp.getDuration()) {
              player.seekTo(currentPosition);
            }
            player.start();
            pb.get().setVisibility(View.INVISIBLE);
          }
        });
        player.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
          @Override public void onSeekComplete(MediaPlayer mp) {
            currentPosition = 0;
          }
        });
        player.prepare();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  class MyHolder extends AbsHolder {
    public MyHolder(View itemView) {
      super(itemView);
    }
  }

  class ImgHolder extends MyHolder {
    @Bind(R.id.img) ImageView img;

    public ImgHolder(View itemView) {
      super(itemView);
    }
  }

  class VideoHolder extends MyHolder {
    @Bind(R.id.video) TextureView video;
    @Bind(R.id.pb) ProgressBar pb;

    public VideoHolder(View itemView) {
      super(itemView);
    }
  }
}
