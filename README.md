Shows an icon in the tray on Windows to display the Internet connection. Internet connection is done by sending a ping to some server (www.google.com or other) every second.

## Work process for this project

1. Improve/edit the source code (`.java` file).
2. Compile all the classes of the code :  
`javac TrayIconDemo.java JLogFrame.java`. This will generate the class files (the ones with $ need to be included too)

3. Create the jar, containing all compiled classes (`.class` files), images, the manifest : 

`jar -cfmv0 out/app.jar META-INF/MANIFEST.MF TrayIconDemo.class JLogFrame.class images`

Links to understand the command above : 
* https://docs.oracle.com/javase/tutorial/deployment/jar/build.html
* https://docs.oracle.com/javase/tutorial/deployment/jar/appman.html



## Demo 

When internet connection active (while ping received) :

![demogreen](https://raw.githubusercontent.com/Max-le/GooglePingTray/master/images/demogreen.gif)

When no internet connection (no ping from server) :

![demored](https://raw.githubusercontent.com/Max-le/GooglePingTray/master/images/demored.gif)

