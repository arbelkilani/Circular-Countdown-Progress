## Circular Countdown

- A UI widget that could be used to inform user that he has to await for a specific amount of time, defined while creating the widget.
- Some attributes has been set in order to create a more personal version of the widget. 

### Screenshots : 

Example 1                  |  Example 2                 |  Example 3
:-------------------------:|:-------------------------: |:-------------------------:
![](https://raw.githubusercontent.com/arbelkilani/Circular-Countdown-Progress/master/screenshots/example1.png)  |  ![](https://raw.githubusercontent.com/arbelkilani/Circular-Countdown-Progress/master/screenshots/example2.png) |![](https://raw.githubusercontent.com/arbelkilani/Circular-Countdown-Progress/master/screenshots/example3.png) |

### Attributes

```xml
<edu.arbelkilani.circularcountdown.CircularCountdown
        android:id="@+id/countdown"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_centerHorizontal="true"
        android:layout_margin="40dp"
        app:border_color="@android:color/black"
        app:border_thickness="6dp"
        app:direction="acw"
        app:disk_alpha=".5"
        app:disk_color="@android:color/holo_orange_light"
        app:start_angle="bottom"/>
```

* **border_color** : set the color of the border, default value _Color.BLACK_.
* **border_thickness** : set the width of the stroke for the border, default value _10dp_.
* **direction** : define either animation is **clockwise** or **anti-clockwise* direction, default value _cw_.
* **disk_color** : set the color of disk, default value _Color.GRAY_.
* **disk_alpha** : set an alpha value for the inner circle, the disk. Value should bet between 0f and 1f, default value _1f_.
* **start_angle** : an enumeration that define wether to start the animation. It could be **start**, **top**, **end** or **bottom**, default value _top_.

### Methods

```java
val circularCountdown = findViewById<CircularCountdown>(R.id.countdown)
circularCountdown.setDuration(2000)
//circularCountdown.start()
circularCountdown.startDelay(1000)
```

* **setDuration(value: Long)** : set animation duration, Long value that prevent **seconds** value. 
* **start()** : enable to start animation
* **startDelay(delay: Long)** : enable to start animation with a delay.

### Callbacks

```java
circularCountdown.onAnimationStart = {
    Toast.makeText(applicationContext, "onAnimationStart", Toast.LENGTH_SHORT).show()
}

circularCountdown.onAnimationEnd = {
    Toast.makeText(applicationContext, "onAnimationEnd", Toast.LENGTH_SHORT).show()
}
```

* **onAnimationStart** : called when animation started.
* **onAnimationEnd** : called at the animation end. 


### Setup

Add to your module's build.gradle:

```xml
allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
and to your app build.gradle:

```xml
dependencies {
  implementation 'com.github.arbelkilani:Circular-Countdown-Progress:1.0.0'
}
```



