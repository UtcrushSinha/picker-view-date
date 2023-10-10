package com.example.assignment_four

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    //private val countries = arrayOf("India","Pakistan","Nepal","Bangladesh","Sri Lanka")
    private val dateFormats = arrayOf("dd-MM-yyyy", "dd/MM/yy", "dd MMMM, yyyy 'with' h:mm:ss a zzz","dd MMM, yyyy")

    private val countries = arrayOf(
        mapOf(
            "id" to 1,
            "country" to "India",
            "gmt_offset" to "+5:30"
        ),
        mapOf(
            "id" to 2,
            "country" to "Pakistan",
            "gmt_offset" to "+5:00"
        ),
        mapOf(
            "id" to 3,
            "country" to "Nepal",
            "gmt_offset" to "+5:45"
        ),
        mapOf(
            "id" to 4,
            "country" to "Bangladesh",
            "gmt_offset" to "+6:00"
        ),
        mapOf(
            "id" to 5,
            "country" to "Sri Lanka",
            "gmt_offset" to "+5:30"
        )
    )

     private lateinit var gmtOffset: String
     private lateinit var selectedFormatPattern: String

    private val calendar = Calendar.getInstance()
   // private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countryPicker = findViewById<NumberPicker>(R.id.countryPicker)
        val dateFormatPicker = findViewById<NumberPicker>(R.id.dateFormatPicker)
        val currentTime = findViewById<TextView>(R.id.currentTime)

       val country = countries.map { it["country"] as String }.toTypedArray()









       countryPicker.minValue = 0
        countryPicker.maxValue = countries.size - 1
        countryPicker.displayedValues = country

        dateFormatPicker.minValue = 0
        dateFormatPicker.maxValue = dateFormats.size - 1
        dateFormatPicker.displayedValues = dateFormats

       selectedFormatPattern = dateFormats[0]
       gmtOffset = countries[0]["gmt_offset"] as String

       calculateTimeWithGMTOffset(gmtOffset,selectedFormatPattern)

        countryPicker.setOnValueChangedListener { _, _, newVal ->

            //updateDateFormatPicker(dateFormatPicker)

            gmtOffset = countries[newVal]["gmt_offset"] as String

//            Log.d(TAG, gmtOffset)
//            Log.d(TAG,gmtOffset.javaClass.simpleName)

            currentTime.text = calculateTimeWithGMTOffset(gmtOffset,selectedFormatPattern)
        }

        dateFormatPicker.setOnValueChangedListener { _, _, newVal ->
            // Handle the selected date format here
            selectedFormatPattern = dateFormats[newVal]

           // Log.d(TAG, selectedFormatPattern)

            //Log.d(TAG,selectedFormatPattern.javaClass.simpleName)

            //Log.d(TAG,gmtOffset.javaClass.simpleName)



        currentTime.text = calculateTimeWithGMTOffset(gmtOffset,selectedFormatPattern)



        }




    }

    private fun calculateTimeWithGMTOffset(gmtOffset: String, pattern: String): String {

        val gmtOffsetParts = gmtOffset.split(":")
        val hours = gmtOffsetParts[0].toInt()
        val minutes = gmtOffsetParts[1].toInt()
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())


        val customTimeZone = TimeZone.getTimeZone("GMT${if (hours >= 0) "+" else "-"}${abs(hours)}:${String.format("%02d", minutes)}")

        dateFormat.timeZone = customTimeZone
        val currentTime = Date()
        return dateFormat.format(currentTime)
    }



}