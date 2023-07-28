package com.kuit.couphone

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kuit.couphone.data.User
import com.kuit.couphone.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyPasswordToggle(binding.passwordEt)
        applyPasswordToggle(binding.passwordCheckEt)

        binding.registerButton.setOnClickListener {
            registerUser()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun applyPasswordToggle(passwordEditText: EditText) {
        var isPasswordVisible = false
        val drawableLock: Drawable? = ContextCompat.getDrawable(this, R.drawable.lock_square)
        val drawableUnlock: Drawable? = ContextCompat.getDrawable(this, R.drawable.unlock_square_green)

        passwordEditText.setOnTouchListener { v, event ->
            val drawableEnd: Drawable? = passwordEditText.compoundDrawables[2]

            if (event.action == MotionEvent.ACTION_UP && event.rawX >= (passwordEditText.right - (drawableEnd?.bounds?.width() ?: 0))) {
                isPasswordVisible = !isPasswordVisible

                if (isPasswordVisible) {
                    passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    passwordEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableUnlock, null)
                } else {
                    passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    passwordEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLock, null)
                }
                return@setOnTouchListener true
            }
            false
        }
    }

    private fun registerUser() {
        val username = binding.usernameEt.text.toString()
        val phonenum = binding.phonenumberEt.text.toString()
        val userpw = binding.passwordEt.text.toString()

        if (isValidPassword(userpw)) {
            val user = User(username, phonenum, userpw)
        } else {
            android.widget.Toast.makeText(this, "비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함하여 최소 8자 이상이어야 합니다.", android.widget.Toast.LENGTH_LONG).show()
        }
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
        return pattern.matches(password)
    }
}
