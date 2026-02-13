package com.example.platformman

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ImageButton>(R.id.imageButton)
            .setOnClickListener {
                levelOneLogic()
            }
    }

    private fun moveLeft(v : View) {
        v.x -= 7
    }

    private fun moveRight(v : View) {
        v.x += 7
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun levelOneLogic() {
            setContentView(R.layout.level1)
            val levelOneLayout = findViewById<ConstraintLayout>(R.id.linearLayout)
            val c = findViewById<View>(R.id.character)
            val leftArrow = findViewById<ImageButton>(R.id.leftArrow)
            val mainHandler = Handler(Looper.getMainLooper())
            leftArrow.setOnTouchListener { v, event ->
                val leftR = object : Runnable {
                    override fun run() {
                        if (event?.action == MotionEvent.ACTION_DOWN) {
                            moveLeft(c)
                            mainHandler.postDelayed(this, 5)
                        }
                    }
                }
                mainHandler.removeCallbacks(leftR)
                mainHandler.post(leftR)
                v?.onTouchEvent(event) ?: true
            }
        val rightArrow = findViewById<ImageButton>(R.id.rightArrow)
        rightArrow.setOnTouchListener { v, event ->
                val rightR = object : Runnable {
                    override fun run() {
                        if (event?.action == MotionEvent.ACTION_DOWN) {
                            moveRight(c)
                            mainHandler.postDelayed(this, 5)
                        }
                    }
                }
                mainHandler.removeCallbacks(rightR)
                mainHandler.post(rightR)
                v?.onTouchEvent(event) ?: true
            }
        var xRand = 300f;
        val imgView = ImageView(this)
            imgView.layoutParams = ConstraintLayout.LayoutParams(225, 225)
            imgView.setImageResource(R.drawable.spike)
            imgView.x = xRand
            imgView.y = -50f
            levelOneLayout?.addView(imgView)
            imgView.visibility = View.VISIBLE

        val counter = TextView(this)
        counter.x = 50f
        counter.y = 30f
        levelOneLayout?.addView(counter)
        counter.textSize = 70f
        counter.setTextColor(Color.RED)
        counter.visibility = View.VISIBLE


            fun isColliding() : Boolean {
                var xDim = c.x > imgView.x - 225f && c.x < imgView.x + 225f
                var yDim = c.y > imgView.y - 225f && c.y < imgView.y + 225f
                if (xDim && yDim) {
                    return true
                }
                return false
            }
            fun randomizeVal() : Float {
                xRand = ((Math.random() * 955) + 122.5).toFloat()
                return xRand
            }

            var currentY = 5.0
            var score = 0

            mainHandler.post(object : Runnable {
                override fun run() {
                    if (!isColliding()) {
                        if (imgView.y < 816f) {
                            imgView.y += currentY.toFloat()
                        }
                        else {
                            imgView.x = randomizeVal()
                            imgView.y = -50f
                            currentY *= 1.1
                            score++
                            counter.text = score.toString()
                        }
                    }
                    else
                    {
                        mainHandler.removeCallbacksAndMessages(null)
                        setContentView(R.layout.gameoverscreen)
                        findViewById<TextView>(R.id.textView3).text = "YOUR SCORE WAS " + score
                    }
                    mainHandler.postDelayed(this, 5)
                }
            })
        }
}