package fr.game.display.utils;

import java.util.ArrayList;
import java.util.Collection;

import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class FlingDetector extends SimpleOnGestureListener implements OnTouchListener {
	public interface Fling {
		void leftToRight();
		void rightToLeft();
	}

	private static final int SWIPE_MIN_DISTANCE = 5;
	private static final int SWIPE_THRESHOLD_VELOCITY = 300;

	private GestureDetector detector = new GestureDetector(this);
	private Collection<Fling> flings = new ArrayList<Fling>();

	public void addFling(Fling fling) {
		flings.add(fling);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		Log.d("Fling", String.format("e1 : %f, e2 : %f, velocity : %f",e1.getX(),e2.getX(),velocityX));
		if (Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
				synchronized (flings) {
					for(Fling fling : flings) {
						fling.rightToLeft();
					}
				}
				return true;
			}
			if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
				synchronized (flings) {
					for(Fling fling : flings) {
						fling.leftToRight();
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (detector.onTouchEvent(event)) {
			return true;
		}
		return false;
	}
}
