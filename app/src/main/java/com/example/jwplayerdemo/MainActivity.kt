package com.example.jwplayerdemo

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.cast.framework.CastContext
import com.longtailvideo.jwplayer.JWPlayerView
import com.longtailvideo.jwplayer.configuration.PlayerConfig
import com.longtailvideo.jwplayer.events.FullscreenEvent
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents.OnFullscreenListener
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem

class MainActivity : AppCompatActivity(), OnFullscreenListener {

    private var mJwPlayerView: JWPlayerView? = null
    private var mCastContext: CastContext? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mJwPlayerView = findViewById(R.id.x_jw_player_view)
        mJwPlayerView?.addOnFullscreenListener(this)
        KeepScreenOnHandler(mJwPlayerView, window)

        val pi = PlaylistItem.Builder()
            .title("Physics")
            .file("https://content.jwplatform.com/v2/media/eSj8wAMp/manifest.mpd?policy_id=VBvWkLqX&token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MTg4MTE5NDQsInJlc291cmNlIjoiL3YyL21lZGlhL2VTajh3QU1wL21hbmlmZXN0Lm1wZD9wb2xpY3lfaWQ9VkJ2V2tMcVgifQ.BTuEdvvct4XJehOUfGdIaj_ZToOfgfbKh2cIF0u075U")
            .mediaDrmCallback(WideVineMediaDrmCallback("https://content.jwplatform.com/v2/media/eSj8wAMp/license?drm=widevine&policy_id=VBvWkLqX&token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MTg4MTE5NDQsInJlc291cmNlIjoiL3YyL21lZGlhL2VTajh3QU1wL2xpY2Vuc2U_ZHJtPXdpZGV2aW5lJnBvbGljeV9pZD1WQnZXa0xxWCJ9.YHRlStSK8V1kO9XOKEqSrMFa6Ovi_aBgilxwhkd3Glo"))
            .build()

        val playListItem: ArrayList<PlaylistItem> = ArrayList()
        playListItem.add(pi)

        val playerConfig = PlayerConfig.Builder()
            .playlist(playListItem)
            .build()

        mJwPlayerView?.setup(playerConfig)

        //mCastContext = CastContext.getSharedInstance(this)

    }

    override fun onStart() {
        super.onStart()
        mJwPlayerView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mJwPlayerView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mJwPlayerView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mJwPlayerView?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mJwPlayerView?.onDestroy()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mJwPlayerView?.fullscreen == true) {
                mJwPlayerView?.setFullscreen(false, true)
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onFullscreen(fullscreenEvent: FullscreenEvent?) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            if (fullscreenEvent?.fullscreen == true) {
                actionBar.hide()
            } else {
                actionBar.show()
            }
        }
    }

}