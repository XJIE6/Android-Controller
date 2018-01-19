# Android-Controller

This project allows you to controll PC keyboard through laptop. You are offered some screens to choose. Each contains the element you can controll some keys through.
To set which keys need to be pushed when you interact with element(e.g. when you push button) you need to use next code:

1) Letters are coded to letters. For example, if you want to push `w` on PC when ypu push button you need to set code to be equal to `w`.
2) Arrows are coded through `\left`, `\right`, `\up`, `\down`
3) You can specidy a sequence by divided codes through `+`. For example, to write word `cat` when pushing button you need the code `c+a+t`
