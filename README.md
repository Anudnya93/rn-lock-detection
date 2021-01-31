# rn-lock-detection (for Android only)
this code, when you need in your apps for detect sleep mode / active screen you can use this code, but not support IOS.

# How to use ?

  1. you can copy folder **rnlock** first, to your android project.
  ``` YOUR_PROJECT_RN/android/app/src/main/java/com/ ```

  2. edit your **MainApplication.java** file, and add this code
  ``` import com.rnlock.RNLockDetectionPackage; <---- add code on the top line ```
    then you can add class RNLockDetectionPackage in your **MainApplication.java** like this.
    ``` protected List<ReactPackage> getPackages() {
          @SuppressWarnings("UnnecessaryLocalVariable")
          List<ReactPackage> packages = new PackageList(this).getPackages();
          ....... .....
          packages.add(new RNLockDetectionPackage()); // <---- add this code
    ```

  3. you can call this module with **NativeModules** from react native
  ex: ``` import { NativeModules } from 'react-native' ```

    4. ``` 
        componentDidMount(){ 
            const RNLockDetection = NativeModules.RNLockDetection 
            
            RNLockDetection.isLock()
            .then(callback => {
                //calback = true is mode sleep
                if(callbak){
                    console.log("Mode Sleep")
                }else{
                    console.log("Active screen")
                }
            })
        }
        ```

