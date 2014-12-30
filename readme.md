This is my project for creating a robot.
The code for this robot is in 3 main parts (And three main directories).

Overview
The robot I’m currently building is controlled mainly from an arduino micro controller. This controls the movements of the robot, and reads the sensors. Right now it is very simple with two motors and bluetooth for communication. The next version will include some ultrasonic sensors and a servo to detect distance values around the bot.

Although the arduino controls the low level functions of the bot, all high level functions are going to be controlled elsewhere. The arduino is great for connecting to servos and sensors, but it lacks memory to have lots of high level AI. My plan to address this issue is to have all high level logic done off board. With this iteration the logic is done on an android device. The android device sends commands to the arduino, and the robot does what it is told. Eventually, I would like to replace this with some sophisticated AI, on a computer. Possibly with the phone acting as a bridge.



Android
This first version is fairly simple. The Android phone has two sliders each representing the speed for two motors. The framework that is given here is generic so that I can add functionality fairly easily. There is a bluetooth protocol I created to send commands to the robot, and to receive data from the robot. Every command has a receipt, and when the robot reads the command it sends back the receipt. When the phone gets this receipt back it sends the next command. If the arduino can’t read the command then it asks to resend the command. This was built because of data which was lost in transmissions. This happened much more than what I expected.

The Android folder contains an android project (written with ADT). It should be easy to import and build.



Arduino
The arduino project comes in two directories. The arduino_engine directory contains the code for the main logic flow. It contains the loop that the robot constantly goes through. This loop doesn’t end until the bot is shut off.

Most of the actual processing happens in classes used by the main loop. These classes are used as libraries. This is the purpose for the libraries directory. Add these to the library directory used by the arduino IDE. These do the processing and communication with bluetooth.