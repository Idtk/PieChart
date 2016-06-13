package com.idtk.smallchart;

import android.util.Log;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class LogUtil {

    /*private int prfFlag = -1;

    public enum LogFlag{
        Verbose(0),Debug(1),Info(2),Warn(3),Error(4),Assert(5);

        // 定义私有变量
        private int nCode;

        // 构造函数，枚举类型只能为私有
        private LogFlag(int _nCode) {
            this.nCode = _nCode;
        }
    }

    public void prf(LogFlag level, String tag, String msg){
        if (level.nCode>prfFlag)
        switch (level){
            case Verbose:
                Log.v(tag,msg);
                break;
            case Debug:
                Log.d(tag,msg);
                break;
            case Info:
                Log.i(tag,msg);
                break;
            case Warn:
                Log.w(tag,msg);
                break;
            case Error:
                Log.e(tag,msg);
                break;
        }
    }*/

    private static int flag = 0;

    public static void v(String tag, String msg){
        if (Log.VERBOSE>flag){
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if (Log.DEBUG>flag){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if (Log.INFO>flag){
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if (Log.WARN>flag){
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if (Log.ERROR>flag){
            Log.e(tag, msg);
        }
    }
}
