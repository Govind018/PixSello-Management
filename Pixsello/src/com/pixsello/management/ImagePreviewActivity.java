package com.pixsello.management;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.pixsello.management.util.Uttilities;

public class ImagePreviewActivity extends Activity {

	ImageView imagePreview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_preview);

		imagePreview = (ImageView) findViewById(R.id.image_preview);

		Intent intent = getIntent();
		byte[] image = intent.getByteArrayExtra("image");

		DisplayMetrics display = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(display);
		int screenWidth = display.widthPixels;
		int screenHeight = display.heightPixels;

		Bitmap m = convert2Bitmap(image);
		if(m == null){
			this.finish();
			return;
		}
		Bitmap resizedImage = Uttilities.resizeImage(screenWidth, screenHeight,
				m);

		imagePreview.setScaleType(ImageView.ScaleType.FIT_XY);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		m.compress(Bitmap.CompressFormat.PNG, 100, stream);

		imagePreview.setImageBitmap(resizedImage);

	}

	public void goBack(View v) {

		finish();
	}

	public Bitmap convert2Bitmap(byte[] image) {

		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}
}
