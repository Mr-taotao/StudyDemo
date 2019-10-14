package com.example.chtlei.mydemo.log;

public class LogUtils {

    /**
     * @return 获取调用该函数的最近一级调用者信息
     * 根据调用层级，选择获取的堆栈信息
     */
    public static String getInvokerClassLine() {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
        String fullName = ste.getClassName();
        String className = fullName.substring(fullName.lastIndexOf(".") + 1);
        return className + "$" + ste.getLineNumber();
    }
}
