package pl.droidsonroids.gif;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;

/**
 * 实现可被自动回收的gif
 * 
 * @author zhongyongsheng
 */
public class RecycleGifDrawable extends GifDrawable implements RecycleDrawable {

	static final String LOG_TAG = "RecycleGifDrawable";

	private int mCacheRefCount = 0;
	private int mDisplayRefCount = 0;

	private boolean mHasBeenDisplayed;

	public RecycleGifDrawable(Resources res, int id) throws NotFoundException,
			IOException {
		super(res, id);
	}

	public RecycleGifDrawable(AssetManager assets, String assetName)
			throws IOException {
		super(assets, assetName);
	}

	public RecycleGifDrawable(String filePath) throws IOException {
		super(filePath);
	}

	public RecycleGifDrawable(File file) throws IOException {
		super(file);
	}

	public RecycleGifDrawable(InputStream stream) throws IOException {
		super(stream);
	}

	public RecycleGifDrawable(AssetFileDescriptor afd) throws IOException {
		super(afd);
	}

	public RecycleGifDrawable(FileDescriptor fd) throws IOException {
		super(fd);
	}

	public RecycleGifDrawable(byte[] bytes) throws IOException {
		super(bytes);
	}

	public RecycleGifDrawable(ByteBuffer buffer) throws IOException {
		super(buffer);
	}

	public RecycleGifDrawable(ContentResolver resolver, Uri uri)
			throws IOException {
		super(resolver, uri);
	}

	RecycleGifDrawable(GifInfoHandle gifInfoHandle, long inputSourceLength,
			GifDrawable oldDrawable, ScheduledThreadPoolExecutor executor) {
		super(gifInfoHandle, inputSourceLength, oldDrawable, executor);
	}

	public void setIsDisplayed(boolean isDisplayed) {
		synchronized (this) {
			if (isDisplayed) {
				mDisplayRefCount++;
				mHasBeenDisplayed = true;
			} else {
				mDisplayRefCount--;
			}
			// Check to see if recycle() can be called
			checkState();
		}

	}

	public void setIsCached(boolean isCached) {
		synchronized (this) {
			if (isCached) {
				mCacheRefCount++;
			} else {
				mCacheRefCount--;
			}
			checkState();
		}
	}

	private synchronized void checkState() {
		if (mCacheRefCount <= 0 && mDisplayRefCount <= 0 && mHasBeenDisplayed
				&& hasValid()) {

			recycle();
		}
	}

	private synchronized boolean hasValid() {
		return !isRecycled();
	}
}