package com.dsg.aliplayer;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.UrlSource;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * @author DSG
 * @Project android
 * @date 2020/10/14
 * @describe
 */
public class RnAliPlayerViewManager extends ViewGroupManager<FrameLayout> {
    AliPlayer aliyunVodPlayer;

    private static final String START = "START";
    private static final String PAUSE = "PAUSE";
    private static final String STOP = "STOP";
    private static final String DESTROY = "DESTROY";

    @Override
    public String getName() {
        return "AliPlayer";
    }

    @Override
    protected FrameLayout createViewInstance(ThemedReactContext reactContext) {
        generateAliPlayer(reactContext);
        SurfaceView surfaceView = new SurfaceView(reactContext);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                aliyunVodPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                aliyunVodPlayer.redraw();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                aliyunVodPlayer.setDisplay(null);
            }
        });
        FrameLayout frameLayout = new FrameLayout(reactContext);
        frameLayout.addView(surfaceView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return frameLayout;
    }

    @ReactProp(name = "url")
    private void url(FrameLayout view, String url) {
        //创建VidSts
        UrlSource source = new UrlSource();
        source.setUri(url);
        //设置播放源
        aliyunVodPlayer.setDataSource(source);
        //准备播放
        aliyunVodPlayer.setLoop(true);
        aliyunVodPlayer.prepare();
    }


    @Override
    public void receiveCommand(@NonNull FrameLayout root, String commandId, @Nullable ReadableArray args) {
        super.receiveCommand(root, commandId, args);
        switch (commandId) {
            case START:
                aliyunVodPlayer.start();
                break;
            case PAUSE:
                aliyunVodPlayer.pause();
                break;
            case STOP:
                aliyunVodPlayer.stop();
                break;
            case DESTROY:
                aliyunVodPlayer.reset();
//释放。释放后播放器将不可再被使用。
                aliyunVodPlayer.release();
                break;
            default:
                break;
        }
    }

    private void generateAliPlayer(ThemedReactContext reactContext) {
        if (aliyunVodPlayer == null) {
            aliyunVodPlayer = AliPlayerFactory.createAliPlayer(reactContext);
        }
        aliyunVodPlayer.setOnCompletionListener(new IPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                //播放完成事件
            }
        });
        aliyunVodPlayer.setOnErrorListener(new IPlayer.OnErrorListener() {
            @Override
            public void onError(ErrorInfo errorInfo) {
                //出错事件
            }
        });
        aliyunVodPlayer.setOnPreparedListener(new IPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                //准备成功事件
            }
        });
        aliyunVodPlayer.setOnVideoSizeChangedListener(new IPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(int width, int height) {
                //视频分辨率变化回调
            }
        });
        aliyunVodPlayer.setOnRenderingStartListener(new IPlayer.OnRenderingStartListener() {
            @Override
            public void onRenderingStart() {
                //首帧渲染显示事件
            }
        });
        aliyunVodPlayer.setOnInfoListener(new IPlayer.OnInfoListener() {
            @Override
            public void onInfo(InfoBean infoBean) {

            }
        });
        aliyunVodPlayer.setOnLoadingStatusListener(new IPlayer.OnLoadingStatusListener() {
            @Override
            public void onLoadingBegin() {
                //缓冲开始。
            }

            @Override
            public void onLoadingProgress(int percent, float kbps) {
                //缓冲进度
            }

            @Override
            public void onLoadingEnd() {
                //缓冲结束
            }
        });
        aliyunVodPlayer.setOnSeekCompleteListener(new IPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete() {
                //拖动结束
            }
        });

        aliyunVodPlayer.setOnTrackChangedListener(new IPlayer.OnTrackChangedListener() {
            @Override
            public void onChangedSuccess(TrackInfo trackInfo) {
                //切换音视频流或者清晰度成功
            }

            @Override
            public void onChangedFail(TrackInfo trackInfo, ErrorInfo errorInfo) {
                //切换音视频流或者清晰度失败
            }
        });
        aliyunVodPlayer.setOnStateChangedListener(new IPlayer.OnStateChangedListener() {
            @Override
            public void onStateChanged(int newState) {
                //播放器状态改变事件
            }
        });
        aliyunVodPlayer.setOnSnapShotListener(new IPlayer.OnSnapShotListener() {
            @Override
            public void onSnapShot(Bitmap bm, int with, int height) {
                //截图事件
            }
        });

    }
}