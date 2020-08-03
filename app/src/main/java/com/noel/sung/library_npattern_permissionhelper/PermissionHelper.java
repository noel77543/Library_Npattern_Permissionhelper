package com.noel.sung.library_npattern_permissionhelper;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by noel on 2020/7/31.
 */
public class PermissionHelper {


    private static PermissionHelper _permissionHelper;

    //單例
    public static PermissionHelper getInstance() {
        if (_permissionHelper == null) {
            _permissionHelper = new PermissionHelper();
        }
        return _permissionHelper;
    }

    //-------

    /**
     * 啟用目標錨點的function並索取相關權限
     *
     * @param activity    目標錨點所在的activity
     * @param targetEvent 目標錨點 與RequirePermission 的 targetEvent 呼應
     */
    public void startWithPermissionCheck(Activity activity, int targetEvent) {
        //此class中所有function
        Method[] methods = activity.getClass().getDeclaredMethods();
        for (Method method : methods) {
            //如果function具備指定的annotation
            if (method.isAnnotationPresent(ObtainPermission.class)) {
                //取得annotation
                ObtainPermission obtainPermissionAnnotation = method.getAnnotation(ObtainPermission.class);
                if (obtainPermissionAnnotation != null) {
                    //annotation的targetTag
                    int annotationTargetEvent = obtainPermissionAnnotation.targetEvent();
                    //找到啟用目標的function
                    if (annotationTargetEvent == targetEvent) {
                        //需要但未取得的權限
                        String[] necessaryPermissions = checkPermissions(activity, obtainPermissionAnnotation.permissions());
                        //如果此function必須的權限已經全數取得
                        if (necessaryPermissions == null) {
                            try {
                                method.setAccessible(true);
                                //執行該function
                                method.invoke(activity);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        //有必須但使用者選擇曾經拒絕的權限
                        else {
                            //再次請求所需但使用者選擇曾經拒絕的權限
                            ActivityCompat.requestPermissions(activity, necessaryPermissions, targetEvent);
                        }
                        //找到目標function 即可跳脫迴圈
                        break;
                    }
                }
            }
        }
    }

    //-------

    /**
     * 啟用目標錨點的function並索取相關權限
     *
     * @param fragment    目標錨點所在的fragment
     * @param targetEvent 目標錨點 與RequirePermission 的 targetEvent 呼應
     */
    public void startWithPermissionCheck(Fragment fragment, int targetEvent) {
        //此class中所有function
        Method[] methods = fragment.getClass().getDeclaredMethods();
        for (Method method : methods) {
            //如果function具備指定的annotation
            if (method.isAnnotationPresent(ObtainPermission.class)) {
                //取得annotation
                ObtainPermission obtainPermissionAnnotation = method.getAnnotation(ObtainPermission.class);
                if (obtainPermissionAnnotation != null) {
                    //annotation的targetTag
                    int annotationTargetEvent = obtainPermissionAnnotation.targetEvent();
                    //找到啟用目標的function
                    if (annotationTargetEvent == targetEvent) {
                        //需要但未取得的權限
                        String[] necessaryPermissions = checkPermissions(Objects.requireNonNull(fragment.getActivity()), obtainPermissionAnnotation.permissions());
                        //如果此function必須的權限已經全數取得
                        if (necessaryPermissions == null) {
                            try {
                                method.setAccessible(true);
                                //執行該function
                                method.invoke(fragment);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        //有必須但使用者選擇曾經拒絕的權限
                        else {
                            //再次請求所需但使用者選擇曾經拒絕的權限
                            fragment.requestPermissions(necessaryPermissions, targetEvent);
                        }
                        //找到目標function 即可跳脫迴圈
                        break;
                    }
                }
            }
        }
    }

    //------------

    /***
     * 必須但使用者選擇曾經拒絕的權限 for activity
     */
    private void startDeniedAnnotation(Activity activity, int targetEvent) {
        //此class中所有function
        Method[] methods = activity.getClass().getDeclaredMethods();
        for (Method method : methods) {
            //如果function具備指定的annotation
            if (method.isAnnotationPresent(DeniedPermission.class)) {
                //取得annotation
                DeniedPermission deniedPermission = method.getAnnotation(DeniedPermission.class);
                if (deniedPermission != null) {
                    if (deniedPermission.targetEvent() == targetEvent) {
                        try {
                            method.setAccessible(true);
                            //執行該function
                            method.invoke(activity);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        //找到目標function 即可跳脫迴圈
                        break;
                    }
                }
            }
        }
    }

    //------------

    /***
     * 必須但使用者曾選擇不再詢問的權限 for activity
     */
    private void startNeverAskAgainAnnotation(Activity activity, int targetEvent) {
        //此class中所有function
        Method[] methods = activity.getClass().getDeclaredMethods();
        for (Method method : methods) {
            //如果function具備指定的annotation
            if (method.isAnnotationPresent(NeverAskPermission.class)) {
                //取得annotation
                NeverAskPermission neverAskPermission = method.getAnnotation(NeverAskPermission.class);
                if (neverAskPermission != null) {
                    if (neverAskPermission.targetEvent() == targetEvent) {
                        try {
                            method.setAccessible(true);
                            //執行該function
                            method.invoke(activity);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        //找到目標function 即可跳脫迴圈
                        break;
                    }
                }
            }
        }
    }

    //------------

    /***
     * 必須但使用者選擇曾經拒絕的權限 for fragment
     */
    private void startDeniedAnnotation(Fragment fragment, int targetEvent) {
        //此class中所有function
        Method[] methods = fragment.getClass().getDeclaredMethods();
        for (Method method : methods) {
            //如果function具備指定的annotation
            if (method.isAnnotationPresent(DeniedPermission.class)) {
                //取得annotation
                DeniedPermission deniedPermission = method.getAnnotation(DeniedPermission.class);
                if (deniedPermission != null) {
                    if (deniedPermission.targetEvent() == targetEvent) {
                        try {
                            method.setAccessible(true);
                            //執行該function
                            method.invoke(fragment);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        //找到目標function 即可跳脫迴圈
                        break;
                    }
                }
            }
        }
    }


    //------------

    /***
     * 必須但使用者曾選擇不再詢問的權限 for fragment
     */
    private void startNeverAskAgainAnnotation(Fragment fragment, int targetEvent) {
        //此class中所有function
        Method[] methods = fragment.getClass().getDeclaredMethods();
        for (Method method : methods) {
            //如果function具備指定的annotation
            if (method.isAnnotationPresent(NeverAskPermission.class)) {
                //取得annotation
                NeverAskPermission neverAskPermission = method.getAnnotation(NeverAskPermission.class);
                if (neverAskPermission != null) {
                    if (neverAskPermission.targetEvent() == targetEvent) {
                        try {
                            method.setAccessible(true);
                            //執行該function
                            method.invoke(fragment);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        //找到目標function 即可跳脫迴圈
                        break;
                    }
                }
            }
        }
    }


    //------------

    /***
     * onRequestPermissionsResult for activity
     * @param activity
     * @param permissions
     * @param isReject
     * @param targetEvent
     */
    public void onActivityRequestPermissionsResult(Activity activity, String[] permissions, boolean isReject, int targetEvent) {
        // 授權被拒絕
        if (isReject) {
            //不再提醒
            if (shouldShowRequestPermissionRationale(activity, permissions)) {
                startNeverAskAgainAnnotation(activity, targetEvent);
            }
            //被拒絕
            else {
                startDeniedAnnotation(activity, targetEvent);
            }
        }
        //授權被允許
        else {
            startWithPermissionCheck(activity, targetEvent);
        }
    }

    //------------

    /***
     * onRequestPermissionsResult for activity
     * @param fragment
     * @param permissions
     * @param isReject
     * @param targetEvent
     */
    public void onFragmentRequestPermissionsResult(Fragment fragment, String[] permissions, boolean isReject, int targetEvent) {
        // 授權被拒絕
        if (isReject) {
            //不再提醒
            if (shouldShowRequestPermissionRationale(fragment.getActivity(), permissions)) {
                startNeverAskAgainAnnotation(fragment, targetEvent);
            }
            //被拒絕
            else {
                startDeniedAnnotation(fragment, targetEvent);
            }
        }
        //授權被允許
        else {
            startWithPermissionCheck(fragment, targetEvent);
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
            for (String permissionInfo : permissionInfos) {
                //該權限未授權本App
                if (ActivityCompat.checkSelfPermission(activity, permissionInfo) != PackageManager.PERMISSION_GRANTED) {
                    needPermissions.add(permissionInfo);
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
