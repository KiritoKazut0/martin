package com.example.tareas.src.core.hardware.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.example.tareas.src.core.hardware.domain.ShakeSensorRepository
import kotlin.math.sqrt

class ShakeDetectorManager(private val context: Context) : ShakeSensorRepository {

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var listener: SensorEventListener? = null

    private val shakeThreshold = 12f

    override fun startListening(onShake: () -> Unit) {
        if (accelerometer == null) {
            Log.e("ShakeDetectorManager", "Error: El dispositivo no cuenta con sensor acelerómetro")
            return
        }

        listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val x = it.values[0]
                    val y = it.values[1]
                    val z = it.values[2]

                    val acceleration = sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH

                    if (acceleration > shakeThreshold) {
                        onShake()
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(
            listener,
            accelerometer,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    override fun stopListening() {
        listener?.let {
            sensorManager.unregisterListener(it)
            listener = null
        }
    }
}
