package com.pxq.appplugin;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Description:
 * Author : pxq
 * Date : 20-1-13 下午10:57
 */
public class App extends Application {

    private static final String TAG = "App";

    private AssetManager mAssetManager;

    @Override
    public void onCreate() {
        super.onCreate();
//        File file = new File(getFilesDir(), "skin.apk");
//        try {
//            initAssetManager(file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void initAssetManager(File apkFile) throws Exception {
        mAssetManager = AssetManager.class.newInstance();
        Method addAssetPathMethod = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
        addAssetPathMethod.setAccessible(true);
        int result = (int) addAssetPathMethod.invoke(mAssetManager, apkFile.getAbsolutePath());
        Log.e(TAG, "initAssetManager: addAssetPath " + result);
        //
//        Method ensureStringBlocksMethod = AssetManager.class.getDeclaredMethod("ensureStringBlocks");
//        ensureStringBlocksMethod.setAccessible(true);
//        ensureStringBlocksMethod.invoke(mAssetManager);

//        mResources = new Resources(mAssetManager, getResources().getDisplayMetrics(), getResources().getConfiguration()) {
//            @NonNull
//            @Override
//            public XmlResourceParser getLayout(int id) throws NotFoundException {
//                Log.e(TAG, "getLayout: " + id);
//                String skinResName = mResources.getResourceEntryName(id);
//                String apkResName = BaseActivity.super.getResources().getResourceEntryName(id);
//                Log.e(TAG, "getLayout: " + skinResName + " " + apkResName);
//                if (!apkResName.equals(skinResName)) {
//                    return BaseActivity.super.getResources().getLayout(id);
//                }
//                return super.getLayout(id);
//            }
//        };
    }


}
