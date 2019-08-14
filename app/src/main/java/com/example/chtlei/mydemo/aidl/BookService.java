package com.example.chtlei.mydemo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by chtlei on 19-6-5.
 */

public class BookService extends Service {

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();

    //实现了AIDL的抽象函数
    private IBookManager.Stub mBinder = new IBookManager.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            //什么也不做
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            //添加书本
            if (!mBookList.contains(book)) {
                mBookList.add(book);
            }
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"iOS"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
