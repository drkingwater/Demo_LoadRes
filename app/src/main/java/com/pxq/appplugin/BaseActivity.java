package com.pxq.appplugin;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Description:
 * Author : pxq
 * Date : 20-1-13 下午11:27
 */
public abstract class BaseActivity extends Activity {

    private static final String TAG = "BaseActivity";

    private AssetManager mAssetManager;
    private Resources mResources;

    private boolean open = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (isOpen()) {
            File file = new File(getFilesDir(), "skin.apk");
            try {
                initAssetManager(file);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "onCreate: ", e);
            }
        }
        super.onCreate(savedInstanceState);
//        mAssetManager = getApplication().getAssets();
//        mResources = new Resources(mAssetManager, getResources().getDisplayMetrics(), getResources().getConfiguration()) {
//            @NonNull
//            @Override
//            public XmlResourceParser getLayout(int id) throws NotFoundException {
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

        mResources = new Resources(mAssetManager, getResources().getDisplayMetrics(), getResources().getConfiguration()) {
            @NonNull
            @Override
            public XmlResourceParser getLayout(int id) throws NotFoundException {
                Log.e(TAG, "getLayout: " + id);
                String skinResName = mResources.getResourceEntryName(id);
                String apkResName = BaseActivity.super.getResources().getResourceEntryName(id);
                Log.e(TAG, "getLayout: " + skinResName + " " + apkResName);
                if (!apkResName.equals(skinResName)) {
                    return BaseActivity.super.getResources().getLayout(id);
                }
                return super.getLayout(id);
            }
        };
    }

    @Override
    public AssetManager getAssets() {
        return mAssetManager != null && isOpen() ? mAssetManager : super.getAssets();
    }

    @Override
    public Resources getResources() {
        return mResources != null && isOpen() ? mResources : super.getResources();
    }


    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(getIdentifier(id));
    }

    protected boolean isOpen(){
        return open;
    }

    public int getIdentifier(int id){
        try {
            String resourceEntryName = BaseActivity.super.getResources().getResourceEntryName(id);
            String resourceTypeName = BaseActivity.super.getResources().getResourceTypeName(id);
            Log.e(TAG, "getIdentifier: " + resourceEntryName + " " + resourceTypeName);
            int identifier = mResources.getIdentifier(resourceEntryName, resourceTypeName, "com.pxq.skin");
            Log.e(TAG, "getIdentifier: " + identifier);
            return identifier > 0 ? identifier : id;
        } catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }
}
