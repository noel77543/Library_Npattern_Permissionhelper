package com.noel.sung.library_npattern_permissionhelper;

public interface OnPermissionStateListener {
    //所需權限
    String[] obtainPermissions();
    //成功授權
    void onAcceptPermission();
    //拒絕授權
    void onRejectPermission();
    //不再提醒
    void onNeverAskAgainPermission();
}
