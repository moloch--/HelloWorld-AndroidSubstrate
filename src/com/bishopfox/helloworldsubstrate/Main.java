package com.bishopfox.helloworldsubstrate;

import java.lang.reflect.Constructor;

import android.util.Log;

import com.saurik.substrate.MS;

public class Main {
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    static void initialize() throws Throwable {
        
        Log.d("Substrate", "Initializing ...");

        // Get the class we want to hook
        Class _class = Class.forName("java.net.InetSocketAddress");

        // Get the method we want to hook, in this case it's the constructor
        Constructor method = _class.getConstructor(String.class, Integer.TYPE);

        // Place our hook 
        MS.hookMethod(_class, method, new MS.MethodAlteration() {
            
            // Our modified version of the hooked method
            public Object invoked(Object _this, Object... args) throws Throwable
            {
                String host = (String) args[0];
                int port = (Integer) args[1];

                Log.d("Substrate", "Socket connection to -> " + host.toString());

                return invoke(_this, host, port);
            }
        });
    }
}