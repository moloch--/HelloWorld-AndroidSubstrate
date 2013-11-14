package com.bishopfox.helloworldsubstrate;

import java.lang.reflect.Method;
import com.saurik.substrate.MS;

public class Main {
	static void initialize() {
		MS.ClassLoadHook colorHook = new MS.ClassLoadHook() {

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void classLoaded(Class<?> resources) {

				Method getColor;  // Find the original function
				try {
					getColor = resources.getMethod("getColor", Integer.TYPE);
				} catch (NoSuchMethodException e) {
					getColor = null;
				}

				if (getColor != null) {
					final MS.MethodPointer old = new MS.MethodPointer();
					MS.hookMethod(resources, getColor, new MS.MethodHook() {
						// Our new implementation
						public Object invoked(Object resources, Object... args) throws Throwable {
							int color = (Integer) old.invoke(resources, args);
							return color & ~0x0000ffff | 0x00ff00ff;
						}
					}, old);
				}
			}
		};
		MS.hookClassLoad("android.content.res.Resources", colorHook);
	}
}