package com.schooglink.jwplayerdemo

import android.text.TextUtils
import android.util.Log
import com.google.android.exoplayer2.drm.ExoMediaDrm
import com.google.android.exoplayer2.util.Util
import com.longtailvideo.jwplayer.media.drm.MediaDrmCallback
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class WideVineMediaDrmCallback(private val wideVineUrl: String?) : MediaDrmCallback {

    private var defaultUri: String? = null

    init {
        defaultUri = wideVineUrl
    }

    override fun executeProvisionRequest(p0: UUID?, p1: ExoMediaDrm.ProvisionRequest?): ByteArray {
        val url = p1?.defaultUrl + "&signedRequest=" + p1?.data.toString()
        return executePost(url, null, null)
    }

    override fun executeKeyRequest(p0: UUID?, p1: ExoMediaDrm.KeyRequest?): ByteArray {
        var url = p1?.licenseServerUrl
        if (TextUtils.isEmpty(url)) {
            url = defaultUri
        }
        return executePost(url, null, null)
    }

    @Throws(IOException::class)
    fun executePost(
        url: String?,
        data: ByteArray?,
        requestProperties: Map<String?, String?>?
    ): ByteArray {
        var urlConnection: HttpURLConnection? = null
        return try {
            urlConnection = URL(url).openConnection() as HttpURLConnection
            urlConnection.requestMethod = "POST"
            urlConnection.doOutput = data != null
            urlConnection.doInput = true
            if (requestProperties != null) {
                for ((key, value) in requestProperties) {
                    urlConnection.setRequestProperty(key, value)
                }
            }
            // Write the request body, if there is one.
            if (data != null) {
                val out: OutputStream = urlConnection.outputStream
                try {
                    out.write(data)
                } finally {
                    out.close()
                }
            }
            // Read and return the response body.
            val inputStream: InputStream = urlConnection.inputStream
            try {
                Util.toByteArray(inputStream)
            } finally {
                inputStream.close()
            }
        } finally {
            urlConnection?.disconnect()
        }
    }
}