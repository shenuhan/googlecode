package fr.android.api.display.animation;


public class AnimationDescriptor {
	public final int[] numFrame;
	public final long[] timeFrame;
	public final int loopCount;

	public AnimationDescriptor(final int[] numFrame,final long[] timeFrame, int loopCount) {
		this.numFrame = numFrame;
		this.timeFrame = timeFrame;
		this.loopCount = loopCount;
	}

	public AnimationDescriptor(final long[] timeFrame, int loopCount) {
		this(null,timeFrame,loopCount);
	}

	public AnimationDescriptor(final int[] numFrame, int loopCount) {
		this(numFrame,null,loopCount);
	}

	public AnimationDescriptor(final int[] numFrame,final long[] timeFrame) {
		this(numFrame,timeFrame,1);
	}

	public AnimationDescriptor(final long[] timeFrame) {
		this(null,timeFrame,1);
	}

	public AnimationDescriptor(final int[] numFrame) {
		this(numFrame,null,1);
	}

	public AnimationDescriptor(int loopCount) {
		this(null,null,loopCount);
	}

	public AnimationDescriptor() {
		this(null,null,1);
	}
}
