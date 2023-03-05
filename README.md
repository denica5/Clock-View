# Clock-View

### Output:
<image src="https://i.imgur.com/sA8Kljc.png" width="45%" height="auto" align="left"/>
<image src="https://i.imgur.com/IeekZih.png" width="45%" height="auto"/>

### Usage:
Layout implementation
  
  ```xml
    <com.example.clockview.ClockView
        android:id="@+id/ClockView_1"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:timeZone="Europe/Moscow"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
  ```
  
### Attribute details:  
| Atrribute | Properties | Type | Default Value̥|
| --------- | ---------- | ---- | -------------|
| timeZone | time zone  | String | Locale.getDefault() |

Don't forget to call <strong>startClock()</strong> in OnStart() and <strong>stopClock()</strong> in OnStop()

```kotlin
   private val mClockView1: ClockView
        get() = findViewById(R.id.clock_view_1)
```	
```kotlin
   override fun onStart() {
        super.onStart()
        mClockView1.startClock()
    }
```
```kotlin
   override fun onStop() {
        super.onStop()
        mClockView1.stopClock()
    }
```
