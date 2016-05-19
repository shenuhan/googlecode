package fr.game.display.view;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import fr.android.api.Api;
import fr.android.api.display.SpriteFactory;
import fr.android.api.display.sprite.Sprite.Fitting;
import fr.android.api.display.sprite.drawable.DrawableSprite;
import fr.game.R;
import fr.game.display.config.Descriptors.Type;
import fr.game.display.utils.FlingDetector;
import fr.game.display.utils.FlingDetector.Fling;
import fr.game.engine.fighter.Army;
import fr.game.engine.fighter.Fighter;

public class FighterMenuView extends HorizontalScrollView {
	private List<Fighter> mItems = null;
	private int mActiveFeature = 0;

	public FighterMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FighterMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FighterMenuView(Context context) {
		super(context);
	}

	public void setFeatureItems(Army army, int width){
		mItems = new ArrayList<Fighter>();

		LinearLayout internalWrapper = new LinearLayout(getContext());
		internalWrapper.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));
		internalWrapper.setOrientation(LinearLayout.HORIZONTAL);
		addView(internalWrapper);
		for(Fighter fighter : army.getFighters()){
			ScrollView featureLayout = (ScrollView) View.inflate(this.getContext(),R.layout.fighter,null);
			featureLayout.setLayoutParams(new LayoutParams(width, android.view.ViewGroup.LayoutParams.FILL_PARENT));
			featureLayout.setOnTouchListener(detector);
			internalWrapper.addView(featureLayout);

			final LinearLayout identity = (LinearLayout) featureLayout.findViewById(R.id.properties);
			identity.setOnTouchListener(detector);
			final DrawableSprite menu = Api.singleton(SpriteFactory.class).getDrawableSprite(Type.FighterMenu);
			identity.setBackgroundDrawable(menu);
			identity.post(new Runnable() {
				@Override
				public void run() {
					menu.show(identity.getWidth(),identity.getHeight(), Fitting.Scale);
					Log.d("Identity", ""+identity.getWidth() + " " + identity.getHeight());
				}
			});

			FighterPictureView pictureView = (FighterPictureView) featureLayout.findViewById(R.id.picture);
			pictureView.setOnTouchListener(detector);
			pictureView.setFighter(fighter);
			mItems.add(fighter);
		}
	}

	private FlingDetector detector;
	public void setOnTouchListener(final FlingDetector detector) {
		super.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (detector.onTouch(v, event)) {
					return true;
				} else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ){
					int scrollX = getScrollX();
					int featureWidth = v.getMeasuredWidth();
					mActiveFeature = ((scrollX + (featureWidth/2))/featureWidth);
					int scrollTo = mActiveFeature*featureWidth;
					smoothScrollTo(scrollTo, 0);
					return true;
				}
				return false;
			}
		});
		this.detector = detector;
		detector.addFling(new Fling() {
			@Override
			public void rightToLeft() {
				int featureWidth = getMeasuredWidth();
				mActiveFeature = (mActiveFeature < (mItems.size() - 1))? mActiveFeature + 1:mItems.size() -1;
				smoothScrollTo(mActiveFeature*featureWidth, 0);
			}
			@Override
			public void leftToRight() {
				int featureWidth = getMeasuredWidth();
				mActiveFeature = (mActiveFeature > 0)? mActiveFeature - 1:0;
				smoothScrollTo(mActiveFeature*featureWidth, 0);
			}
		});
	}
}