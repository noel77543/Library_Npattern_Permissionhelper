# Library_Npattern_Permissionhelper
應用映射的設計概念將權限處理簡化，



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
                implementation 'com.github.noel77543:Library_Npattern_Permissionhelper:Tag'
        }

---

### 使用方式
- Activity
        
        public  class YourActivity extends AnyActivity {

                  .
                  .
                  .
                private final int EVENT_WHATEVER_YOU_WANT = 123;

                @Override
                public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    boolean isReject = grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED;
                    PermissionHelper.getInstance().onActivityRequestPermissionsResult(this, permissions, isReject, requestCode);
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
                            PermissionHelper.getInstance().startWithPermissionCheck(MainFragment.this,EVENT_WHATEVER_YOU_WANT_2);
                        }
                    });
                }
                
                //-----------

                //事件必須與startWithPermissionCheck中的int一致，permission中添加欲索取的權限，倘若權限已具備則直接執行doSomethingNeedPermission方法反之發起索取
                @ObtainPermission(targetEvent = EVENT_WHATEVER_YOU_WANT, permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA})
                private void doSomethingNeedPermission() {
                    ...
                }
                
                //-----------
                
                //當使用者拒絕你發起的權限索取，事件必須與startWithPermissionCheck中的int一致
                @DeniedPermission(targetEvent = EVENT_WHATEVER_YOU_WANT)
                private void deniedPermission(){
                    ...
                }
                
                //-----------
                
                //當使用者選擇不再提醒並拒絕或者曾如此操做過，事件必須與startWithPermissionCheck中的int一致
                @NeverAskPermission(targetEvent = EVENT_WHATEVER_YOU_WANT)
                private void neverAskAgainPermission(){
                    ...
                }
            }



- Fragment
    
      public  class YourFragment extends Fragment {

            .
            .
            .
          private final int EVENT_WHATEVER_YOU_WANT_2 = 456;
          
          @Override
          public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
              super.onRequestPermissionsResult(requestCode, permissions, grantResults);
               boolean isReject = grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED;
               PermissionHelper.getInstance().onFragmentRequestPermissionsResult(this,permissions,isReject,requestCode);
          }
          
          //-----------
          
          @Override
          public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
              super.onViewCreated(view, savedInstanceState);
              yourButton.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      PermissionHelper.getInstance().startWithPermissionCheck(MainFragment.this,EVENT_WHATEVER_YOU_WANT_2);
                  }
              });
          } 
          
          //-----------
          
          //事件必須與startWithPermissionCheck中的int一致，permission中添加欲索取的權限，倘若權限已具備則直接執行doSomethingNeedPermission方法反之發起索取
          @ObtainPermission(targetEvent = EVENT_WHATEVER_YOU_WANT_2, permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
          private void doSomethingNeedPermission() {
              ...
          }
          
          //-----------
          
          //當使用者拒絕你發起的權限索取，事件必須與startWithPermissionCheck中的int一致
          @DeniedPermission(targetEvent = EVENT_WHATEVER_YOU_WANT_2)
          private void deniedPermission(){
              ...
          }
          
          //-----------

          //當使用者選擇不再提醒並拒絕或者曾如此操做過，事件必須與startWithPermissionCheck中的int一致
          @NeverAskPermission(targetEvent = EVENT_WHATEVER_YOU_WANT_2)
          private void neverAskAgainPermission(){
              ...
          }
      }



---

### 示例sample

[Demo](https://github.com/noel77543/Demo_PermissionHelper)
