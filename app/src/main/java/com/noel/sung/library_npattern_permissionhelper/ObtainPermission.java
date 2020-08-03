package com.noel.sung.library_npattern_permissionhelper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by noel on 2020/7/31.
 * 請在需要索取權限的function之前加上此 annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ObtainPermission {
    //與 PermissionHelper的啟用錨點相互呼應
    int targetEvent();

    //所需權限 e.g Manifest.permission.ACCESS_FINE_LOCATION...
    String[] permissions();
}
