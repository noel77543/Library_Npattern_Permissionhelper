# Library_Npattern_Permissionhelper
應用映射的設計概念將權限處裡簡化，
詳見[Demo](https://github.com/noel77543/Demo_PermissionHelper)


###引用import
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
