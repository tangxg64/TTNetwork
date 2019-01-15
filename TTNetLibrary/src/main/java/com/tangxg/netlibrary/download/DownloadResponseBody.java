package com.tangxg.netlibrary.download;

import android.support.annotation.Nullable;
import android.webkit.DownloadListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * 下载响应信息拦截处理进度
 */
public class DownloadResponseBody extends ResponseBody {

    private Response originalResponse;
    private DownLoadCallback downloadListener;
    private long oldPoint = 0;

    public DownloadResponseBody(Response originalResponse, long startsPoint, DownLoadCallback downloadListener) {
        this.originalResponse = originalResponse;
        this.downloadListener = downloadListener;
        this.oldPoint = startsPoint;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return originalResponse.body().contentType();
    }

    @Override
    public long contentLength() {
        return originalResponse.body().contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(originalResponse.body().source()) {
            private long bytesReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                if (downloadListener != null) {
                    downloadListener.onProgress((int) ((bytesReaded + oldPoint) / (1024)));
                }
                return bytesRead;
            }
        });
    }
}
