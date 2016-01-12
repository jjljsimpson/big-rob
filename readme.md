This is mark IV
The fourth incarnation of my bot code.
Previously I tried to have much of this running on Arduino, but I have shifted to Raspberry Pi.
The reasoning is simple, I'm writing the code in Java. This allows it to run on Raspberry Pi, Android, or a Desktop Computer.
I can now write the client and server in the same language, and they can share code. It does make things simpler, although a littler harder to find
drivers for some hardware.

This code is currently broken into 3 areas.

-- AI --
This is the AI used to control the device. It will run on something more powerful like an android phone or a desktop computer. It may be
connected to the robot in different fashions. For example it could communicate through bluetooth or wifi.
There are different profiles of AI, which may be created on the fly. Basically a device sends a handshake telling the server what
profile the device matches. Then the server determines what kind of AI to instantiate and run.

Future versions of the AI may be able to handle multiple device profiles.

Some AI profiles are very simple, where data is sent to the AI, and a person makes decisions on what should be done. This is more like
remote control. Other AI may be more complicated where it makes decisions on it's own. (This is where I hope to go with this project.)

-- Device --
The device represents the hardware of the robot. It may have sensors, servos, motors, etc. It is responsible for gathering data and performing actions.
It depends on the AI to determine what actions it should take.

Because of the loose coupling between the device and the AI, it is easier to have different logic with the same hardware.


-- Comm --
This is the communications layer. It is responsible for streaming data between the different devices.
A single chunck of data is called a frame. It is genereic and has a variable size payload.
Currently the payload is a form of a command object. After a valid frame has been retreived, the payload is converted
into a command. The commands are different sizes with varying information. This informs the device what to do, or updates information

There is a read queue. It is controlled by another thread. As frames become available they are pushed onto a queue. The main loop
then reads from this queue and performs some action.

There is also a write queue. When a command needs to be sent it is added to the queue and the thread responsible for writing
sends this frame out. Although commands and frames are read the same way on both the AI and device, they are handleded compleltly differently.
For example, a device sends out a handshake command, but the server never does. Also the device sends feedback that a command was received,
but the server doesn't send any feedback to the device.