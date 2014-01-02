package com.colorgame;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OtherActivity extends Activity {

	private static final int RESULT_SETTINGS = 1;
	private static int gameColors = 4;
	private static int colorBoxes = 8;
	RelativeLayout layout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);

		Bundle extras = getIntent().getExtras();
		ColorBox.totalMatches = 4;
		if (extras != null) {
			String value = extras.getString("gameColors");
			gameColors = Integer.parseInt(value);
			colorBoxes = gameColors * 2;
			ColorBox.totalMatches = gameColors;

		}

		Log.d("totalmatches", ColorBox.totalMatches + "");

		TextView t = (TextView) findViewById(R.id.score);

		t.setText("Score : 0");
		ColorBox.AppContext = this;
		ColorBox.setScoreField(t);
		layout = (RelativeLayout) findViewById(R.id.game_layout);

		Integer colorcodes[] = new Integer[8];
		colorcodes[0] = Color.parseColor("#D85632");

		colorcodes[1] = Color.parseColor("#3F7C7D");
		colorcodes[2] = Color.parseColor("#21B636");
		colorcodes[3] = Color.parseColor("#39267b");
		colorcodes[4] = Color.parseColor("#5FADCC");
		colorcodes[5] = Color.parseColor("#FD5478");
		colorcodes[6] = Color.parseColor("#9E1424");
		colorcodes[7] = Color.parseColor("#9DD361");

		shuffleArray(colorcodes);

		int colorLoop = 0;
		ColorBox[] imageColors = new ColorBox[colorBoxes];
		ImageView temp;
		for (int i = 0; i < imageColors.length; i++) {
			temp = new ImageView(getApplicationContext());

			temp.setBackgroundColor(colorcodes[colorLoop]);
			temp.setContentDescription(colorcodes[colorLoop] + "");
			temp.setTag("hidden");
			imageColors[i] = new ColorBox(this, temp);
			colorLoop++;
			if (colorLoop == gameColors)
				colorLoop = 0;

		}

		shuffleArray(imageColors);

		int margin = 100;
		int lastID = 0;
		for (int i = 0; i < imageColors.length; i++) {

			imageColors[i].getBox().setId(i + 1);

			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					120, 120);

			if (i < 4) {
				lp.setMargins(0, margin, 0, 0);
				if (i != 0)
					lp.addRule(RelativeLayout.RIGHT_OF, imageColors[i].getBox()
							.getId() - 1);
			} else {

				if (i % 4 == 0) {
					margin = margin + 130;
				}
				lp.setMargins(0, margin, 0, 0);
				if (i % 4 != 0)
					lp.addRule(RelativeLayout.RIGHT_OF, imageColors[i].getBox()
							.getId() - 1);
			}

			imageColors[i].getBox().setLayoutParams(lp);
			imageColors[i].getBox().setImageResource(R.drawable.front_card);
			layout.addView(imageColors[i].getBox());
			lastID = i;
		}

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		lp.addRule(RelativeLayout.BELOW, lastID);

		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.color_settings_radio);
		radioGroup.setLayoutParams(lp);

	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.radio_pirates:
			if (checked) {
				Intent i = new Intent(getApplicationContext(),
						OtherActivity.class);
				i.putExtra("gameColors", "4");
				startActivity(i);

			}
			// Pirates are the best
			break;
		case R.id.radio_ninjas:
			if (checked) {

				Intent i = new Intent(getApplicationContext(),
						OtherActivity.class);
				i.putExtra("gameColors", "8");
				startActivity(i);

			}
			// Ninjas rule
			break;
		}
	}

	// Implementing Fisher–Yates shuffle
	static void shuffleArray(Object[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			Object a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_settings:
			Intent i = new Intent(this, UserSettingActivity.class);
			startActivityForResult(i, RESULT_SETTINGS);
			break;

		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SETTINGS:
			// showUserSettings();
			break;

		}

	}
}
