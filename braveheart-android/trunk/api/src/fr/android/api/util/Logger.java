package fr.android.api.util;

import android.util.Log;

public class Logger {
	public static RuntimeException t(String tag, String message, Throwable e) {
		e(tag, message, e);
		return new RuntimeException(message,e);
	}
	public static RuntimeException t(String tag, Throwable e) {
		e(tag, e.getMessage());
		return new RuntimeException(e);
	}
	public static RuntimeException t(String tag, String message) {
		e(tag, message);
		return new RuntimeException(message);
	}
	public static void e(String tag, String message, Throwable e) {
		e(tag, message);
		Log.e(tag, "Exception : " + e.getMessage());
	}
	public static void e(String tag, String message) {
		Log.e(tag, message);
	}
	public static void w(String tag, String message, Throwable e) {
		w(tag, message);
		Log.w(tag, "Exception : " + e.getMessage());
	}
	public static void w(String tag, String message) {
		Log.w(tag, message);
	}
	public static void i(String tag, String message) {
		Log.i(tag, message);
	}
	public static void d(String tag, String message) {
		Log.d(tag, message);
	}
}
