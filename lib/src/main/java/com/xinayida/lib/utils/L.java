package com.xinayida.lib.utils;

import android.util.Log;


/**
 * Log统一管理
 *
 * @author way
 */
public class L {

    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;//BuildConfig.DEBUG;
    public static int maxLogSize = 2500;

    public static final String TAG = "kindergarten";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    if (i == 0) {
                        Log.i(TAG, msg.substring(start, end));
                    } else {
                        Log.i(TAG, "\r\n" + msg.substring(start, end));
                    }
                }
            } else
                Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    if (i == 0) {
                        Log.d(TAG, msg.substring(start, end));
                    } else {
                        Log.d(TAG, "\r\n" + msg.substring(start, end));
                    }
                }
            } else
                Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    if (i == 0) {
                        Log.e(TAG, msg.substring(start, end));
                    } else Log.e(TAG, "\r\n" + msg.substring(start, end));
                }
            } else Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    if (i == 0) {
                        Log.v(TAG, msg.substring(start, end));
                    } else {
                        Log.v(TAG, "\r\n" + msg.substring(start, end));
                    }
                }
            } else
                Log.v(TAG, msg);
    }

    // 下面是传入自定义tag
    public static void i(String tag, String msg) {
        if (isDebug)
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    if (i == 0) {
                        Log.i(tag, msg.substring(start, end));
                    } else {
                        Log.i(tag, "\r\n" + msg.substring(start, end));
                    }
                }
            } else
                Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            if (msg != null) {
                if (msg.length() > maxLogSize) {
                    int start, end;
                    for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                        start = i * maxLogSize;
                        end = (i + 1) * maxLogSize;
                        end = end > msg.length() ? msg.length() : end;
                        if (i == 0) {
                            Log.d(tag, msg.substring(start, end));
                        } else {
                            Log.d(tag, "\r\n" + msg.substring(start, end));
                        }
                    }
                } else {
                    Log.d(tag, msg);
                }
            }
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    //
                    if (i == 0) {
                        Log.e(tag, msg.substring(start, end));
                    } else {
                        Log.e(tag, "\r\n" + msg.substring(start, end));
                    }
                }
            } else
                Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    //
                    if (i == 0) {
                        Log.v(tag, msg.substring(start, end));
                    } else {
                        Log.v(tag, "\r\n" + msg.substring(start, end));
                    }
                }
            } else
                Log.v(tag, msg);
    }

    // 下面四个是默认tag的函数
    public static void i(boolean showFrom, String msg) {
        if (isDebug) {
            String logHead = showFrom ? "From:\"" + getParentClassName() + "." + getParentMethodName() + "\" " : "";
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    if (i == 0) {
                        Log.i(TAG, logHead + msg.substring(start, end));
                    } else {
                        Log.i(TAG, "\r\n" + logHead + msg.substring(start, end));
                    }
                }
            } else {
                Log.i(TAG, logHead + msg);
            }
        }
    }

    public static void d(boolean showFrom, String msg) {
        if (isDebug) {
            String logHead = showFrom ? "From:\"" + getParentClassName() + "." + getParentMethodName() + "\" " : "";
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    if (i == 0) {
                        Log.d(TAG, logHead + msg.substring(start, end));
                    } else {
                        Log.d(TAG, "\r\n" + logHead + msg.substring(start, end));
                    }
                }
            } else {
                Log.d(TAG, logHead + msg);
            }
        }
    }

    /**
     * Description: 带调用者的错误日志.<br/><br/>
     * Author: Create by Yu.Yao on 2016/4/13.<br/><br/>
     */
    public static void e(boolean showFrom, String msg) {
        if (isDebug) {
            String logHead = showFrom ? "From:\"" + getParentClassName() + "." + getParentMethodName() + "\" " : "";
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    if (i == 0) {
                        Log.e(TAG, logHead + msg.substring(start, end));
                    } else {
                        Log.e(TAG, "\r\n" + logHead + msg.substring(start, end));
                    }
                }
            } else {
                Log.e(TAG, logHead + msg);
            }
        }
    }

    public static void v(boolean showFrom, String msg) {
        if (isDebug) {
            String logHead = showFrom ? "From:\"" + getParentClassName() + "." + getParentMethodName() + "\" " : "";
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    if (i == 0) {
                        Log.v(TAG, logHead + msg.substring(start, end));
                    } else {
                        Log.v(TAG, "\r\n" + logHead + msg.substring(start, end));
                    }
                }
            } else {
                Log.v(TAG, logHead + msg);
            }
        }
    }

    // 下面是传入自定义tag
    public static void i(boolean showFrom, String tag, String msg) {
        if (isDebug) {
            String logHead = showFrom ? "From:\"" + getParentClassName() + "." + getParentMethodName() + "\" " : "";
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    if (i == 0) {
                        Log.i(tag, logHead + msg.substring(start, end));
                    } else {
                        Log.i(tag, "\r\n" + logHead + msg.substring(start, end));
                    }
                }
            } else
                Log.i(tag, logHead + msg);
        }
    }

    public static void d(boolean showFrom, String tag, String msg) {
        if (isDebug) {
            String logHead = showFrom ? "From:\"" + getParentClassName() + "." + getParentMethodName() + "\" " : "";
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    if (i == 0) {
                        Log.d(tag, logHead + msg.substring(start, end));
                    } else {
                        Log.d(tag, "\r\n" + logHead + msg.substring(start, end));
                    }
                }
            } else
                Log.d(tag, logHead + msg);
        }
    }

    public static void e(boolean showFrom, String tag, String msg) {
        if (isDebug) {
            String logHead = showFrom ? "From:\"" + getParentClassName() + "." + getParentMethodName() + "\" " : "";
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    if (i == 0) {
                        Log.e(tag, logHead + msg.substring(start, end));
                    } else {
                        Log.e(tag, "\r\n" + logHead + msg.substring(start, end));
                    }
                }
            } else
                Log.e(tag, logHead + msg);
        }
    }

    public static void v(boolean showFrom, String tag, String msg) {
        if (isDebug) {
            String logHead = showFrom ? "From:\"" + getParentClassName() + "." + getParentMethodName() + "\" " : "";
            if (msg != null && msg.length() > maxLogSize) {
                int start, end;
                for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = end > msg.length() ? msg.length() : end;
                    if (i == 0) {
                        Log.v(tag, logHead + msg.substring(start, end));
                    } else {
                        Log.v(tag, "\r\n" + logHead + msg.substring(start, end));
                    }
                }
            } else
                Log.v(tag, logHead + msg);
        }
    }

    // 自定义格式
    public static void fi(String tag, String format, Object... msg) {
        if (isDebug) {
            Log.i(tag, String.format(format, msg));
        }
    }

    // 自定义格式
    public static void fd(String tag, String format, Object... msg) {
        if (isDebug) {
            Log.d(tag, String.format(format, msg));
        }
    }

    // 自定义格式
    public static void fe(String tag, String format, Object... msg) {
        if (isDebug) {
            Log.e(tag, String.format(format, msg));
        }
    }

    // 自定义格式
    public static void fv(String tag, String format, Object... msg) {
        if (isDebug) {
            Log.v(tag, String.format(format, msg));
        }
    }

    private static void segmentationLog(String tag, String msg, LogType type) {
        if (msg != null && msg.length() > maxLogSize) {
            int start, end;
            for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                start = i * maxLogSize;
                end = (i + 1) * maxLogSize;
                end = end > msg.length() ? msg.length() : end;
                String logMsg = i == 0 ? msg.substring(start, end) : "\n========分割线========" + msg.substring(start, end);
                Log.d(tag, logMsg);
                switch (type) {
                    case d:
                        Log.d(tag, logMsg);
                        break;
                    case e:
                        Log.e(tag, logMsg);
                        break;
                    case i:
                        Log.i(tag, logMsg);
                        break;
                    case v:
                        Log.v(tag, logMsg);
                        break;
                    case w:
                        Log.w(tag, logMsg);
                        break;
                }
            }
        } else
            switch (type) {
                case d:
                    Log.d(tag, msg);
                    break;
                case e:
                    Log.e(tag, msg);
                    break;
                case i:
                    Log.i(tag, msg);
                    break;
                case v:
                    Log.v(tag, msg);
                    break;
                case w:
                    Log.w(tag, msg);
                    break;
            }
    }

    private static void outputLog(String tag, String msg, LogType type) {
        switch (type) {
            case d:
                Log.d(tag, msg);
                break;
            case e:
                Log.e(tag, msg);
                break;
            case i:
                Log.i(tag, msg);
                break;
            case v:
                Log.v(tag, msg);
                break;
            case w:
                Log.w(tag, msg);
                break;
        }
    }

    enum LogType {
        d,
        i,
        e,
        w,
        v
    }

    /**
     * Description: 获取调用者的类名.<br/><br/>
     * Author: Create by Yu.Yao on 2016/4/7.<br/><br/>
     */
    public static String getParentClassName() {
        String className = "";
        if (Thread.currentThread().getStackTrace().length > 4) {
            className = Thread.currentThread().getStackTrace()[4].getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);
        }
        return className;
    }

    /**
     * Description: 获取当前类名.<br/><br/>
     * Author: Create by Yu.Yao on 2016/4/7.<br/><br/>
     */
    public static String getMyClassName() {
        String className = "";
        if (Thread.currentThread().getStackTrace().length > 3) {
            className = Thread.currentThread().getStackTrace()[3].getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);
        }
        return className;
    }

    /**
     * Description: 获取调用者方法名.<br/><br/>
     * Author: Create by Yu.Yao on 2016/4/7.<br/><br/>
     */
    public static String getParentMethodName() {
        String methodName = "";
        if (Thread.currentThread().getStackTrace().length > 4) {
            methodName = Thread.currentThread().getStackTrace()[4].getMethodName();
            methodName = methodName.substring(methodName.lastIndexOf(".") + 1);
        }
        return methodName;
    }

    /**
     * Description: 获取当前方法名.<br/><br/>
     * Author: Create by Yu.Yao on 2016/4/7.<br/><br/>
     */
    public static String getMyMethodName() {
        String methodName = "";
        if (Thread.currentThread().getStackTrace().length > 3) {
            methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            methodName = methodName.substring(methodName.lastIndexOf(".") + 1);
        }
        return methodName;
    }
}