package com.example.musicplayerapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import java.sql.Time
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {



// variables
    var startTime = 0.0
    var finalTime = 0.0
    var forwardTime = 10000
    var backwardTime = 10000
    var oneTimeOnly = 0

// Handler
    var handler: Handler = Handler()

// Media Player
    var mediaPlayer = MediaPlayer()
    lateinit var timeLeft: TextView
    lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val songTitle :TextView = findViewById(R.id.songTitle)
         seekBar  = findViewById(R.id.seekBar)
        timeLeft  = findViewById(R.id.time_left)
        val playBtn :Button = findViewById(R.id.playBtn)
        val pauseBtn :Button = findViewById(R.id.pauseBtn)
        val forwardBtn :Button = findViewById(R.id.forwardBtn)
        val backBtn :Button = findViewById(R.id.backBtn)



//Media Player
         mediaPlayer = MediaPlayer.create(
           this,
           R.raw.astronaut
       )
        seekBar.isClickable = false
// Adding functionalities to the buttons
        playBtn.setOnClickListener(){
            mediaPlayer.start()

             finalTime = mediaPlayer.duration.toDouble()
             startTime = mediaPlayer.currentPosition.toDouble()

            if ( oneTimeOnly ==0){
                seekBar.max= finalTime.toInt()
                 oneTimeOnly =1
            }
            timeLeft.text  =startTime.toString()
            seekBar.setProgress(startTime.toInt())
            handler.postDelayed(UpdateSongTime,100)
        }
//Song title
        songTitle.text=""+resources.getResourceEntryName(R.raw.astronaut)
// Pause button
        pauseBtn.setOnClickListener() {
            mediaPlayer.pause()
        }
//Forward button
        forwardBtn.setOnClickListener(){
            var temp = startTime
            if((temp+forwardTime)<=finalTime){
                startTime += forwardTime
                mediaPlayer.seekTo(startTime.toInt())
            }else{
                Toast.makeText(this,"Oops!",Toast.LENGTH_LONG ).show()
            }
        }

//Back button
            backBtn.setOnClickListener(){
            var temp = startTime
            if((temp-backwardTime)>0){
                startTime -= backwardTime
                mediaPlayer.seekTo(startTime.toInt())
            }else{
                Toast.makeText(this,"Oops!",Toast.LENGTH_SHORT ).show()
            }
        }
    }
//Creating the Runnable
    val UpdateSongTime  :Runnable =object:Runnable{
        override fun run(){
          
            startTime=mediaPlayer.currentPosition.toDouble()
            timeLeft.text= ""+
                    String.format(
                        "%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                        TimeUnit.MILLISECONDS.toSeconds(startTime.toLong())

                    )

            seekBar.progress=startTime.toInt()
            handler.postDelayed(this,100)
        }
    }

}