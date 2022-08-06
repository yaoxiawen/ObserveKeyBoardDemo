package com.example.observekeyboarddemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        KeyBoardHelper.setListener(this, object : KeyBoardHelper.OnKeyboardChangeListener {
//            override fun keyBoardShow() {
//                Toast.makeText(this@MainActivity, "弹出键盘了", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun keyBoardHide() {
//                Toast.makeText(this@MainActivity, "收起键盘了", Toast.LENGTH_SHORT).show()
//            }
//        })
        observeKeyboardChange {
            if (it) {
                Toast.makeText(this@MainActivity, "弹出键盘了", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "收起键盘了", Toast.LENGTH_SHORT).show()
            }
        }
        //imeOptions actionDone 和 inputType textMultiLine同时生效
        val et = findViewById<EditText>(R.id.et)
        et.imeOptions = EditorInfo.IME_ACTION_DONE
        et.setRawInputType(InputType.TYPE_CLASS_TEXT)
        et.addTextChangedListener {
            Log.d("yxw", it.toString())
        }
        //监听键盘action操作
        et.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Log.d("yxw", "点击了键盘done按钮")
                true
            }
            false
        }
    }
}