# Performance-Estimation-Tool-Java

A tool reading ground truth data and detected object data provides visualization way to estimate position accuracy.

The input data needs:
1. Ground Truth Data: [frame_name left_top_x left_top_y right_bottom_x right_bottom_y]
2. Detected Object Data: [frame_name left_top_x left_top_y right_bottom_x right_bottom_y]
3. Frames Folder

You can find the Matlab edition [here](https://github.com/imprld01/Performance-Estimation-Tool-Matlab).

# GUI DEMO

* The testing data comes from [NOL](http://people.cs.nctu.edu.tw/~yi/).

1. Prepare the needed files first.  
![GUI](/res/gui.JPG)

2. Press 'Setup' Button to show the result.  
![GUI](/res/gui2.JPG)

# Update Log

* 2017/09/28
  * fix the logic of trouble key collection
* 2017/09/29
  * update trouble key list after ratio changing event

# Development Environment

* NetBeans
* JavaFX Scene Builder
* under windows 10 64-bit OS
* Java Development Kit (ver. 8u144)

If you want to use Eclipse to develop JavaFx application, you can install [plugin]() from '' on Eclipse.

# Execution Environment

* Java Runtime Environment is needed (developed under jre1.8.0)

# Executable Jar

You can find the executable jar file under this [folder](https://github.com/imprld01/Performance-Estimation-Tool-Java/tree/master/dist) or find it [here](https://github.com/imprld01/Performance-Estimation-Tool-Java/blob/master/dist/Performance%20Estimation%20Tool.jar) directly.

# License
[![Creative Commons License](https://i.creativecommons.org/l/by-sa/4.0/88x31.png)](http://creativecommons.org/licenses/by-sa/4.0/)  
This project is licensed under a [Creative Commons Attribution-ShareAlike 4.0 International License](http://creativecommons.org/licenses/by-sa/4.0/) and the [MIT License](LICENSE.md).
