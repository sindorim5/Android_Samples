package com.example.carouselrecyclerviewtest

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.carouselrecyclerviewtest.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private const val TAG = "MainActivity_sdr"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var dateList = arrayListOf<String>()
    private var hourList = arrayListOf<ArrayList<String>>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDateHourList()

        Log.d(TAG, "onCreate: $dateList")
        Log.d(TAG, "onCreate: $hourList")

        binding.dialogButton.setOnClickListener {
            Toast.makeText(this, "dialog", Toast.LENGTH_SHORT).show()
            var dialog = CustomDialog(this@MainActivity, dateList, hourList)
            dialog.show()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDateHourList() {
        var now = LocalDateTime.now()
        var fullHours = arrayListOf<String>(
            "00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
            "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
            "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
            "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"
        )
        var startTime = LocalDateTime.of(now.year, now.month, now.dayOfMonth, now.hour, 0)
        var endTime = startTime.plusHours(71L)
        

        var i = 0L
        while (!startTime.plusDays(i).isEqual(endTime.plusHours(1L))) {
            var tempLocalDateTime = startTime.plusDays(i)
            var date = LocalDate.of(
                tempLocalDateTime.year,
                tempLocalDateTime.month,
                tempLocalDateTime.dayOfMonth
            ).format(DateTimeFormatter.ofPattern("yy/MM/dd"))
            dateList.add(date)
            i += 1L
        }
        dateList.add(
            LocalDate.of(endTime.year, endTime.month, endTime.dayOfMonth).format(
                DateTimeFormatter.ofPattern("yy/MM/dd")
            )
        )

        for (idx in 0 until dateList.size) {
            var tempTimeList = arrayListOf<String>()
            when (idx) {
                0 -> for (h in startTime.hour until 24) {
                    if (h < 10) {
                        tempTimeList.add("0$h:00")
                    } else {
                        tempTimeList.add("$h:00")
                    }
                }
                dateList.size - 1 -> for (h in 0 until startTime.hour) {
                    if (h < 10) {
                        tempTimeList.add("0$h:00")
                    } else {
                        tempTimeList.add("$h:00")
                    }
                }
                else -> tempTimeList = fullHours
            }
            hourList.add(tempTimeList)
        }
    }


}