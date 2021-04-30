package com.schooglink.jwplayerdemo

import android.view.Window
import android.view.WindowManager
import com.longtailvideo.jwplayer.JWPlayerView
import com.longtailvideo.jwplayer.events.CompleteEvent
import com.longtailvideo.jwplayer.events.ErrorEvent
import com.longtailvideo.jwplayer.events.PauseEvent
import com.longtailvideo.jwplayer.events.PlayEvent
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents

class KeepScreenOnHandler(private val mJwPlayerView: JWPlayerView?, private val mWindow: Window) :
    VideoPlayerEvents.OnPlayListener,
    VideoPlayerEvents.OnPauseListener,
    VideoPlayerEvents.OnCompleteListener,
    VideoPlayerEvents.OnErrorListener {


    init {
        mJwPlayerView?.addOnPauseListener(this)
        mJwPlayerView?.addOnPauseListener(this)
        mJwPlayerView?.addOnCompleteListener(this)
        mJwPlayerView?.addOnErrorListener(this)
    }

    private fun updateWakeLock(enable: Boolean) {
        if (enable) {
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            mWindow.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    override fun onPlay(p0: PlayEvent?) {
        updateWakeLock(true)
    }

    override fun onPause(p0: PauseEvent?) {
        updateWakeLock(false)
    }

    override fun onComplete(p0: CompleteEvent?) {
       updateWakeLock(false)
    }

    override fun onError(p0: ErrorEvent?) {
        updateWakeLock(false)
    }
}
