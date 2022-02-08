package com.noel.sung.library_npattern_permissionhelper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by noel on 2020/7/31.
 */
public class PermissionHelper {

    private OnPermissionStateListener onPermissionStateListener;
    private static PermissionHelper permissionHelper;


    //單例
    public static PermissionHelper getInstance() {
        if (permissionHelper == null) {
            permissionHelper = new PermissionHelper();
        }
        return permissionHelper;
    }

    //-------

    /***
     * 是否已經取得目標權限
     */
    public boolean isPermissionGet(@NonNull Activity activity, String[] permissionInfos) {
        return checkPermissions(activity, permissionInfos) == null;
    }

    //-------

    /**
     * 啟用目標錨點的function並索取相關權限
     *
     * @param activity                  目標錨點所在的activity
     * @param onPermissionStateListener 權限狀態
     */
    public void startWithPermissionCheck(Activity activity, @NonNull OnPermissionStateListener onPermissionStateListener) {
        this.onPermissionStateListener = onPermissionStateListener;

        //需要但未取得的權限
        String[] necessaryPermissions = checkPermissions(activity, onPermissionStateListener.obtainPermissions());
        //如果此function必須的權限已經全數取得
        if (necessaryPermissions == null) {
            onPermissionStateListener.onAcceptPermission();
            this.onPermissionStateListener = null;
        }
        //有必須但使用者選擇曾經拒絕的權限
        else {
            //再次請求所需但使用者選擇曾經拒絕的權限
            ActivityCompat.requestPermissions(activity, necessaryPermissions, 0);
        }
    }

    //-------

    /**
     * 啟用目標錨點的function並索取相關權限
     *
     * @param fragment                  目標錨點所在的fragment
     * @param onPermissionStateListener 權限狀態
     */
    public void startWithPermissionCheck(Fragment fragment, @NonNull OnPermissionStateListener onPermissionStateListener) {
        this.onPermissionStateListener = onPermissionStateListener;
        //需要但未取得的權限
        String[] necessaryPermissions = checkPermissions(Objects.requireNonNull(fragment.getActivity()), onPermissionStateListener.obtainPermissions());
        //如果此function必須的權限已經全數取得
        if (necessaryPermissions == null) {
            onPermissionStateListener.onAcceptPermission();
            this.onPermissionStateListener = null;
        }
        //有必須但使用者選擇曾經拒絕的權限
        else {
            //再次請求所需但使用者選擇曾經拒絕的權限
            fragment.requestPermissions(necessaryPermissions, 0);
        }
    }

    //------------

    /***
     * onRequestPermissionsResult for activity
     * @param activity
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(Activity activity, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (onPermissionStateListener != null) {
            // 授權被拒絕
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //不再提醒
                if (shouldShowRequestPermissionRationale(activity, permissions)) {
                    onPermissionStateListener.onNeverAskAgainPermission();
                }
                //被拒絕
                else {
                    onPermissionStateListener.onRejectPermission();
                }
                onPermissionStateListener = null;
            }
            //授權被允許
            else {
                startWithPermissionCheck(activity, onPermissionStateListener);
            }
        }
    }

    //------------

    /***
     * onRequestPermissionsResult for activity
     * @param fragment
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(Fragment fragment, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (onPermissionStateListener != null) {
            // 授權被拒絕
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //不再提醒
                if (shouldShowRequestPermissionRationale(fragment.getActivity(), permissions)) {
                    onPermissionStateListener.onNeverAskAgainPermission();
                }
                //被拒絕
                else {
                    onPermissionStateListener.onRejectPermission();
                }
                onPermissionStateListener = null;
            }
            //授權被允許
            else {
                startWithPermissionCheck(fragment, onPermissionStateListener);
            }
        }
    }

    //------------

    /***
     * 是否有權限
     * @param permissionInfos
     * @return
     */
    private String[] checkPermissions(@NonNull Activity activity, String[] permissionInfos) {
        if (permissionInfos.length > 0) {
            ArrayList<String> needPermissions = new ArrayList<>();
            for (String permission : permissionInfos) {
                //該權限未授權本App
                if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    needPermissions.add(permission);
                }
            }
            if (needPermissions.size() > 0) {
                return needPermissions.toArray(new String[0]);
            }
        }
        return null;
    }

    //------------

    /***
     * 曾被拒絕過的權限
     * @param permissions
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return false;
            }
        }
        return true;
    }
}
