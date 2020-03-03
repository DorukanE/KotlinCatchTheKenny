package com.dorukaneskiceri.kotlincatchthekenny

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var score = 0
    var highScore = 0
    val imageArray = ArrayList<ImageView>()
    var runnable: Runnable = Runnable {  }
    var handler = Handler()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Storing Score
        sharedPreferences = this.getSharedPreferences("com.dorukaneskiceri.kotlincatchthekenny", Context.MODE_PRIVATE)
        highScore = sharedPreferences.getInt("highScore",0)

        if(highScore == 0){
            highScoreText.text = "High Score: 0"
        }else{
            highScoreText.text = "High Score: $highScore"
        }

        //ImageArray
        imageArray.add(imageView)
        imageArray.add(imageView2)
        imageArray.add(imageView3)
        imageArray.add(imageView4)
        imageArray.add(imageView5)
        imageArray.add(imageView6)
        imageArray.add(imageView7)
        imageArray.add(imageView8)
        imageArray.add(imageView9)

        hideImages()

        //CountDown Timer

        object : CountDownTimer(15000,1000) {
            override fun onFinish() {
                finishGame()
                sharedPreferences.edit().putInt("highScore",highScore).apply()

                //Alert
                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setCancelable(false)
                alert.setTitle("Game Over")
                alert.setMessage("Do you want to play again ?")
                alert.setNegativeButton("No") {dialog, which ->
                    Toast.makeText(this@MainActivity,"Good Luck Next Time..",Toast.LENGTH_LONG).show()
                }
                alert.setPositiveButton("Yes") {dialog, which ->
                    //Restart the game
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                alert.show()
            }

            override fun onTick(millisUntilFinished: Long) {
                timeText.text = "Timer: ${millisUntilFinished/1000}"
            }
        }.start()

    }

    fun increaseScore(view: View){
        if(score >= highScore){
            score++
            highScore++
            highScoreText.text = "High Score: $highScore"
            scoreText.text = "Score: $score"
        }else{
            score++
            highScoreText.text = "High Score: $highScore"
            scoreText.text = "Score: $score"
        }
    }

    fun hideImages(){
        runnable = object: Runnable{
            override fun run() {
                for (images in imageArray){
                    images.visibility = View.INVISIBLE
                }
                val random = Random()
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE
                handler.postDelayed(this,400)
            }
        }
        handler.post(runnable)
    }

    fun finishGame(){
        handler.removeCallbacks(runnable)
        for(image in imageArray){
            image.visibility = View.INVISIBLE
        }
    }
}
