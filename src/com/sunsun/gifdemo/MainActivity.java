package com.sunsun.gifdemo;

import java.io.IOException;

import pl.droidsonroids.gif.RecycleGifDrawable;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends FragmentActivity implements OnClickListener {

	Button mButton;
	ImageView mImageView1;
	ImageView mImageView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mButton = (Button) findViewById(R.id.button);
		mImageView1 = (ImageView) findViewById(R.id.imageView1);
		mImageView2 = (ImageView) findViewById(R.id.imageView2);
		mButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		setGifDrawableParser(mImageView1,R.drawable.a);
		setGifDrawableParser(mImageView2,R.drawable.live_gif);
	}

	protected void setGifDrawableParser(ImageView imageView,int id) {
		RecycleGifDrawable gifDrawable = null;
		try {
			gifDrawable = new RecycleGifDrawable(getResources(), id);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		gifDrawable.start();
		if (gifDrawable != null) {
			imageView.setImageDrawable(gifDrawable);
		}
	}
}
