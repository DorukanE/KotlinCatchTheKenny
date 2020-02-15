package com.dorukaneskiceri.kotlincatchthekenny

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //CountDown Timer

        object : CountDownTimer(15000,1000) {
            override fun onFinish() {
                timeText.text = "Timer: 0"

                //Alert
                val alert = AlertDialog.Builder(this@MainActivity)
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
        score++
        scoreText.text = "Score: $score"
    }
}
