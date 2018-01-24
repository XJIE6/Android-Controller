# Android-Controller

This project allows you to controll PC keyboard through laptop. You are offered some screens to choose. Each contains the element you can controll some keys through.
To set which keys need to be pushed when you interact with element(e.g. when you push button) you need to use next code:

1) Write key code from [here](https://docs.oracle.com/javase/7/docs/api/constant-values.html#java.awt.event.KeyEvent.CHAR_UNDEFINED) to press and release the key. Use '+' before key code to press the key and '-' to release. ("65" means the same to "+65 -65" and means to press and release key 'a')
2) You can use "[ ... ] n" to repeat inner sequence of commands n times
3) We have some syntax sugar to use most common keys without remembering their kodes. You can use letters and digits to press them ("+a" to press 'a'). Besides, there are several capital letters used for most common keys. Here they are :
        'A' -> Alt,
        'B' -> Backspace,
        'C' -> Control,
        'D' -> Down,
        'E' -> Enter,
        'K' -> CapsLock,
        'L' -> Left,
        'P' -> Space,
        'R' -> Right,
        'S' -> Shift,
        'T' -> Tab,
        'U' -> Up,
        'W' -> Windows,
        'X' -> Escape,
        'Z' -> Delete
