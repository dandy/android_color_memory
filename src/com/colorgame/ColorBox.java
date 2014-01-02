package com.colorgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class ColorBox extends ImageView implements AnimationListener {

	private ImageView box;
	public static ImageView currentView, lastView;
	public static TextView score;
	public static Context AppContext;
	private static int currentMatches;
	public static int totalMatches;
	private static boolean isAnimating = false;
	public static int CurrentShown, gameScore;
	private Animation animStart, animEnd, animStartReverse, animEndReverse;
	{
		gameScore = 0;
		CurrentShown = 0;
		currentMatches = 0;
	}

	public ColorBox(Context context, ImageView v) {
		super(context);
		setBox(v);
		box.setOnClickListener(theCommonListener);
		animStart = AnimationUtils
				.loadAnimation(getContext(), R.anim.to_middle);
		animEnd = AnimationUtils
				.loadAnimation(getContext(), R.anim.from_middle);
		animStartReverse = AnimationUtils.loadAnimation(getContext(),
				R.anim.to_middle);
		animEndReverse = AnimationUtils.loadAnimation(getContext(),
				R.anim.from_middle);
		animStart.setAnimationListener(this);
		animEnd.setAnimationListener(this);
		animStartReverse.setAnimationListener(this);
		animEndReverse.setAnimationListener(this);

	}

	public ColorBox(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ColorBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void onAnimationEnd(Animation animation) {
		super.onAnimationEnd();

		if (animation == animStart) {

			currentView.setImageResource(0);
			currentView.startAnimation(animEnd);
		}

		if (animation == animEnd) {

			if (CurrentShown == 2) {
				lastView.setTag("hidden");
				currentView.setTag("hidden");
				if (currentView.getContentDescription().toString()
						.equals(lastView.getContentDescription().toString())) {
					// match found
					gameScore = gameScore + 5;
					CurrentShown = 0;
					lastView.setTag("Shown");
					currentView.setTag("Shown");
					lastView = null;
					currentMatches++;

					score.setText("Score : " + gameScore);

					if (currentMatches == totalMatches) {
						score.setText("Score : " + gameScore
								+ "\nGame Finished");

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								getContext());

						// set title
						alertDialogBuilder.setTitle("Game ended");

						// set dialog message
						alertDialogBuilder
								.setMessage("Would you like to play again?")
								.setCancelable(false)
								.setPositiveButton("Yes",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												// if this button is clicked,
												// close
												// current activity
												// Intent act = new
												// Intent(getContext(),
												// OtherActivity.class);

												((Activity) AppContext)
														.recreate();

											}
										})
								.setNegativeButton("No",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												// if this button is clicked,
												// just close
												// the dialog box and do nothing
												dialog.cancel();
											}
										});

						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();

						// show it
						alertDialog.show();
					}

					// Todo: Use textswitcher here(maybe?)
					// score = (TextView) findViewById(R.id.score);
					isAnimating = false;
				} else {
					gameScore = gameScore - 1;
					score.setText("Score : " + gameScore);

					currentView.startAnimation(animStartReverse);
					lastView.startAnimation(animStartReverse);
					// lastView = null;
				}
			} else {

				lastView = currentView;
				isAnimating = false;
			}

		}

		if (animation == animStartReverse) {

			if (CurrentShown == 2) {
				lastView.setImageResource(R.drawable.front_card);
				lastView.startAnimation(animEndReverse);
				currentView.setImageResource(R.drawable.front_card);
				currentView.startAnimation(animEndReverse);

			} else {
				currentView.setImageResource(R.drawable.front_card);
				currentView.startAnimation(animEndReverse);
			}
		}

		if (animation == animEndReverse) {
			if (CurrentShown == 2) {
				CurrentShown = 0;
				isAnimating = false;
			}
		}

	}

	public void onAnimationRepeat(Animation animation) {

	}

	public void onAnimationStart(Animation animation) {

	}

	/**
	 * @return the box
	 */
	public ImageView getBox() {
		return box;
	}

	/**
	 * @param box
	 *            the box to set
	 */
	public void setBox(ImageView box) {
		this.box = box;
	}

	private OnClickListener theCommonListener = new OnClickListener() {
		public void onClick(View v) {

			if (CurrentShown == 2 || isAnimating == true)
				return;

			currentView = (ImageView) v;
			if (currentView.getTag() == "hidden" && isAnimating == false) {
				CurrentShown++;
				isAnimating = true;
				currentView.setTag("Shown");
				currentView.startAnimation(animStart);
			}

		}
	};

	public static void setScoreField(TextView t) {
		score = t;
	}

}
