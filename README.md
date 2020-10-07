Shows an icon in the tray on Windows to display the Internet connection. Internet connection is done by sending a ping to www.google.com every second.

## Work process for this project

1. Improve/edit the source code (`.java` file).
2. Compile the code :  
`javac TrayIconDemo.java`. This will generate the class files (the ones with $ need to be included too)

3. Create the jar : 

`jar -cfmv0 out/app.jar META-INF/MANIFEST.MF TrayIconDemo.class TrayIconDemo$1.class TrayIconDemo$2.class images`

Links to understand the command above : 
* https://docs.oracle.com/javase/tutorial/deployment/jar/build.html
* https://docs.oracle.com/javase/tutorial/deployment/jar/appman.html