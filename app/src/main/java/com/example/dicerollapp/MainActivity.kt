package com.example.dicerollapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.dicerollapp.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var score = 0
    private var diceValue = 0

    private val random = Random(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.roll.setOnClickListener {
            rollDice()
        }

        binding.add.setOnClickListener {
            addScore()
        }

        binding.subtract.setOnClickListener {
            subtractScore()
        }

        binding.reset.setOnClickListener {
            resetScore()
        }

        savedInstanceState?.let {
            score = it.getInt(KEY_SCORE, score)
            updateScore()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SCORE, score)
    }
    private fun rollDice() {
        binding.roll.backgroundTintList = ContextCompat.getColorStateList(this, R.color.unavailable)
        binding.add.backgroundTintList = ContextCompat.getColorStateList(this, R.color.available)
        binding.subtract.backgroundTintList = ContextCompat.getColorStateList(this, R.color.available)

        diceValue = random.nextInt(6) + 1
        Log.d("LOGapp", diceValue.toString())
        // Update the dice image
        val diceImageResource = when (diceValue) {
            1 -> R.drawable.dice1
            2 -> R.drawable.dice2
            3 -> R.drawable.dice3
            4 -> R.drawable.dice4
            5 -> R.drawable.dice5
            else -> R.drawable.dice6
        }
        binding.dice.setImageResource(diceImageResource)
        binding.roll.isEnabled = false
        binding.add.isEnabled = true
        binding.subtract.isEnabled = true
    }

    private fun addScore() {
        binding.roll.backgroundTintList = ContextCompat.getColorStateList(this, R.color.available)
        binding.add.backgroundTintList = ContextCompat.getColorStateList(this, R.color.unavailable)
        binding.subtract.backgroundTintList = ContextCompat.getColorStateList(this, R.color.unavailable)
        score += diceValue
        Log.d("LOGapp","new score after adding : $score")

        updateScore()
    }

    private fun subtractScore() {
        binding.roll.backgroundTintList = ContextCompat.getColorStateList(this, R.color.available)
        binding.add.backgroundTintList = ContextCompat.getColorStateList(this, R.color.unavailable)
        binding.subtract.backgroundTintList = ContextCompat.getColorStateList(this, R.color.unavailable)
        score -= diceValue
        Log.d("LOGapp","new score after subtracting : $score")
        if (score < 0) score = 0
        updateScore()
    }

    private fun resetScore() {
        binding.roll.backgroundTintList = ContextCompat.getColorStateList(this, R.color.available)
        binding.add.backgroundTintList = ContextCompat.getColorStateList(this, R.color.unavailable)
        binding.subtract.backgroundTintList = ContextCompat.getColorStateList(this, R.color.unavailable)
        score = 0
        binding.score.text = "-"
        updateScore()
        binding.roll.isEnabled = true
        binding.add.isEnabled = false
        binding.subtract.isEnabled = false
    }

    private fun updateScore() {
        binding.score.text = score.toString()
        binding.roll.isEnabled = true
        binding.add.isEnabled = false
        binding.subtract.isEnabled = false
        when {
            score < 19 -> binding.score.setTextColor(ContextCompat.getColor(this, R.color.black))
            score > 20 -> binding.score.setTextColor(ContextCompat.getColor(this, R.color.red))
            score == 20 -> binding.score.setTextColor(ContextCompat.getColor(this, R.color.green))
        }
    }

    companion object {
        private const val KEY_SCORE = "key_score"
    }
}