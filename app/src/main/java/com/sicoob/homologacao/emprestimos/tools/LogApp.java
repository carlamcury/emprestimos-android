package com.sicoob.homologacao.emprestimos.tools;

import android.util.Log;

public class LogApp {
    public LogApp() {
    }

    public static void debug(String msg) {
        if (msg != null) {
            Log.i("DEBUG", msg);
        }

    }
}
