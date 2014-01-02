package com.colorgame;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements AnimationListener {

	Animation animStart, animEnd, animStartReverse, animEndReverse;
	int currentShown = 0;
	int currentViewId = 0;
	ImageView previousView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		animStart = AnimationUtils.loadAnimation(this, R.anim.from_middle);
		animEnd = AnimationUtils.loadAnimation(this, R.anim.to_middle);
		animStartReverse = AnimationUtils.loadAnimation(this, R.anim.to_middle);
		animEndReverse = AnimationUtils.loadAnimation(this, R.anim.from_middle);
		animStart.setAnimationListener((AnimationListener) this);
		animEnd.setAnimationListener((AnimationListener) this);
		animStartReverse.setAnimationListener(this);
		animEndReverse.setAnimationListener(this);

		animStartReverse.setFillAfter(true);
		animEndReverse.setFillAfter(true);

		// listener = new ColorAnimationListener();

		RelativeLayout layout = (RelativeLayout) findViewById(R.id.game_layout);

		int colorcodes[] = new int[4];
		colorcodes[0] = Color.BLUE;
		colorcodes[1] = Color.GREEN;
		colorcodes[2] = Color.MAGENTA;
		colorcodes[3] = Color.CYAN;

		int colorLoop = 0;
		ImageView[] imageColors = new ImageView[8];
		for (int i = 0; i < imageColors.length; i++) {
			imageColors[i] = new ImageView(getApplicationContext());
			imageColors[i].setId(i + 1);
			imageColors[i].setLayoutParams(new RelativeLayout.LayoutParams(100,
					100));

			imageColors[i].setBackgroundColor(colorcodes[colorLoop]);
			// System.out.println("Color code is" + Color.BLUE);
			imageColors[i].setContentDescription(colorcodes[colorLoop] + "");
			imageColors[i].setTag("hidden");
			colorLoop++;
			if (colorLoop == 4)
				colorLoop = 0;
			// layout.addView(imageColors[i]);

		}

		// shuffleArray(imageColors);
		for (int i = 0; i < imageColors.length; i++) {

			imageColors[i].setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// if (currentShown == 2)
					// return;
					currentShown++;
					ImageView local = (ImageView) v;
					if (currentViewId != 0)
						previousView = (ImageView) findViewById(currentViewId);
					if (local.getTag() == "hidden") {

						currentViewId = local.getId();
						local.startAnimation(animStart);
						// previousView.startAnimation(ani);
						local.setTag("shown");

					} else {

						local.setImageResource(R.drawable.front_card);
						local.setTag("hidden");

					}

				}
			});

			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					120, 120);
			if (i < 4) {
				lp.setMargins(0, 100, 0, 0);
				if (i != 0)
					lp.addRule(RelativeLayout.RIGHT_OF,
							imageColors[i].getId() - 1);
			} else {
				lp.setMargins(0, 230, 0, 0);
				if (i != 4)
					lp.addRule(RelativeLayout.RIGHT_OF,
							imageColors[i].getId() - 1);
			}

			imageColors[i].setLayoutParams(lp);
			imageColors[i].setImageResource(R.drawable.front_card);
			layout.addView(imageColors[i]);
		}

		ImageView[] colors = new ImageView[8];

		colors[0] = imageColors[0];
		colors[1] = imageColors[1];
		colors[2] = imageColors[2];
		colors[3] = imageColors[3];
		colors[4] = imageColors[0];
		colors[5] = imageColors[1];
		colors[6] = imageColors[2];
		colors[7] = imageColors[3];

		for (int i = 0; i < 4; i++) {
			// layout.addView(colors[i]);
		}

		// ImageView testView = new ImageView(this);
		// testView.setLayoutParams(new RelativeLayout.LayoutParams(100, 100));
		// testView.setBackgroundColor(Color.GREEN);

		// layout.addView(testView);

		// imageColors[2].setBackgroundColor(colorcodes[2]);

		/*
		 * System.out.println(viewBoxes.length);
		 * 
		 * ImageView testView = new ImageView(this);
		 * testView.setLayoutParams(new RelativeLayout.LayoutParams(100, 100));
		 * testView.setBackgroundColor(Color.GREEN);
		 */

		// layout.addView(testView);

		// layout.addView(imageColors[2]);

	}

	// Implementing Fisher–Yates shuffle
	static void shuffleArray(ImageView[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			ImageView a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	public void onAnimationStart(Animation animation) {

	}

	public void onAnimationRepeat(Animation animation) {

	}

	public void onAnimationEnd(Animation animation) {

		if (animation == animStartReverse) {

			ImageView local = (ImageView) findViewById(currentViewId);

			local.setImageResource(R.drawable.front_card);

			local.startAnimation(animEndReverse);

		}

		if (animation == animEndReverse) {
			// previousView = null;
			// currentViewId = 0;

		}

		if (animation == animStart) {

			ImageView local = (ImageView) findViewById(currentViewId);
			local.setImageResource(0);
			local.startAnimation(animEnd);

		}

		if (animation == animEnd) {

			ImageView local = (ImageView) findViewById(currentViewId);

			if (currentShown == 2) {
				if (previousView.getContentDescription().toString()
						.equals(local.getContentDescription().toString())) {

					currentShown = 0;

				} else {

				}

				local.startAnimation(animStartReverse);
				local.setTag("hidden");
				previousView.setTag("hidden");

				currentShown = 0;
			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
