package fr.android.api;

import android.app.Activity;
import android.os.Bundle;

public class ApiActivity extends Activity  {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Api.start();
	}
}