# Library_Npattern_Permissionhelper
以最簡單的調用方式，實現最火的動態權限索取庫。



### 引用import
- build.gradle(Project:YourProject)
  - Add it in your root build.gradle at the end of repositories

        allprojects {
          repositories {
            ...
            maven { url 'https://jitpack.io' }
          }
        }
    
    
- build.gradle(Module:app)
  - Add the dependency
  
        dependencies {
                implementation 'com.github.noel77543:Library_Npattern_Permissionhelper:v1.1.0'
        }

---

### 接口interface

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


### 使用方式use
- Activity
        
        public  class YourActivity extends AnyYourActivity {

                  .
                  .
                  .

                @Override
                public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    PermissionHelper.getInstance().onRequestPermissionsResult(this, permissions, grantResults);
                }

                //-----------

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                      .
                      .
                     yourButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PermissionHelper.getInstance().startWithPermissionCheck(this, new OnPermissionStateListener() {
                              @Override
                              public String[] obtainPermissions() {
                                  //以詳細地理位置,模糊地理位置權限為例
                                  return new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                              }
                             
                              @Override
                              public void onAcceptPermission() {
                                  
                              }

                              @Override
                              public void onRejectPermission() {
                                   
                              }

                              @Override
                              public void onNeverAskAgainPermission() {
                                  
                              }
                          });
                        }
                    });
                }
            }



- Fragment
    
      public  class YourFragment extends AnyYourFragment {

            .
            .
            .
          
          @Override
          public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
              super.onRequestPermissionsResult(requestCode, permissions, grantResults);
              PermissionHelper.getInstance().onRequestPermissionsResult(this, permissions,grantResults);
          }
          
          //-----------
          
          @Override
          public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
              super.onViewCreated(view, savedInstanceState);
              yourButton.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                     PermissionHelper.getInstance().startWithPermissionCheck(this, new OnPermissionStateListener() {
                       @Override
                       public String[] obtainPermissions() {
                           //以詳細地理位置,模糊地理位置權限為例
                           return new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                       }
                      
                       @Override
                       public void onAcceptPermission() {
                           
                       }

                       @Override
                       public void onRejectPermission() {
                            
                       }

                       @Override
                       public void onNeverAskAgainPermission() {
                           
                       }
                  }
              });
          } 

      }



---

### 示例sample

[Demo](https://github.com/noel77543/Demo_PermissionHelper)


---

### 備註remark


