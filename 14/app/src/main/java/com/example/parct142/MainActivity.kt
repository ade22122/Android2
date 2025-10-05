package com.example.parct142

import android.app.AlertDialog
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.example.parct142.ui.theme.Parct142Theme
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Parct142Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    XMLWithLogicContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun XMLWithLogicContent(modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            val view = android.view.LayoutInflater.from(context).inflate(R.layout.main, null)
            val textView = view.findViewById<TextView>(R.id.textView)
            val button = view.findViewById<Button>(R.id.button)
            val textView2 = view.findViewById<TextView>(R.id.textView2)
            val textView3 = view.findViewById<TextView>(R.id.textView3)
            val textView4 = view.findViewById<TextView>(R.id.textView4)
            val imageButton1 = view.findViewById<ImageButton>(R.id.imageButton)
            val radioGroup = view.findViewById<RadioGroup>(R.id.RadioGroup)
            textView4.text = "0"
            textView.text = "Hello from XML!"
            textView2.text = "Waiting for click..."
            textView3.text = "0"


            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val message = when (checkedId) {
                    R.id.radioButton -> "ГЕЙ - это неправильный ответ!\nСВЕГ означает что-то другое"
                    R.id.radioButton2 -> "ИВАН ЗОЛО верно ты победил!"
                    R.id.radioButton3 -> "СЕКАС - нет, это не правильное определение"
                    else -> ""
                }

                if (message.isNotEmpty()) {
                    AlertDialog.Builder(context)
                        .setTitle("Результат выбора")
                        .setMessage(message)
                        .setPositiveButton("OK") { dialog, which ->
                            dialog.dismiss()
                        }
                        .setCancelable(true)
                        .show()
                }
            }
            Thread {
                val handler = android.os.Handler(context.mainLooper)
                var text = 0

                for (i in 1..10) {
                    Thread.sleep(3000) 
                    text = i

                    handler.post {
                        textView3.text = "$text"
                    }
                }
            }.start() 
            var click2 = 0;
            imageButton1.setOnClickListener{
                click2++
                textView4.text = "Clicked $click2 times"
                when (click2 % 3) {
                    0 -> textView4.setTextColor(android.graphics.Color.BLACK)
                    1 -> textView4.setTextColor(android.graphics.Color.RED)
                    2 -> textView4.setTextColor(android.graphics.Color.BLUE)
                }
            }
            // Логика для кнопки
            var clickCount = 0
            button.setOnClickListener {
                clickCount++
                textView.text = "Button clicked!"
                textView2.text = "Clicked $clickCount times"
                Toast.makeText(context, "Click count: $clickCount", Toast.LENGTH_SHORT).show()
                when (clickCount % 3) {
                    0 -> textView.setTextColor(android.graphics.Color.BLACK)
                    1 -> textView.setTextColor(android.graphics.Color.RED)
                    2 -> textView.setTextColor(android.graphics.Color.BLUE)
                }
            }
            button.setOnLongClickListener {
                textView.text = "Long press detected!"
                textView2.text = "Reset counter"
                clickCount = 0
                true
            }

            view
        },
        modifier = modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun XMLWithLogicPreview() {
    Parct142Theme {
        XMLWithLogicContent()
    }
}