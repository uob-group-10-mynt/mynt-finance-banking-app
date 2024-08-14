# frontendNotes

Test && play around with React, Capacitor and Electron to compile very basic place holder react web app && to take this app and compile it into a desktop application and mobile app. 

## Getting Started

First Time Setup `npm install`  

### <u>Development Mode</u>  
**React Web App**  
`npm run devReact`  
  
**Electron Desktop App**  
`npm run devDesktop`  

### <u>Compile or Package</u> 
**React Web App**  
`npm run packageReact`  
Executable location: '/webApp' Dir  

**IOS**  
`npm run packageMobile`  
`npx cap run ios` or open Ios folder within XCode and click and compile and run there. Package location: '/ios' Dir  

**Android**  
`npm run packageMobile`  
`npx cap run android` or open android folder within Android Studio and click the play button. Package location: '/android' Dir  

**Desktop Application**  
  
*You can only build the RPM target on Linux machines with the rpm or rpm-build packages installed.  
On **Fedora** you can do something like this:
`sudo dnf install rpm-build`  
While on **Debian or Ubuntu** you'll need to do this:
`sudo apt-get install rpm`*  
  
`npm run compileDesktop`  
executable location: '/out' Dir  
  
## Notes 
https://capacitorjs.com/
