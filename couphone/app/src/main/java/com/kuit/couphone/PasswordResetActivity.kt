package com.kuit.couphone

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.kuit.couphone.data.User
import com.kuit.couphone.databinding.ActivityPasswordResetBinding

class PasswordResetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordResetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordResetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyPasswordToggle(binding.changePasswordEt, binding.changePasswordLayout)
        applyPasswordToggle(binding.changePasswordCheckEt, binding.changePasswordCheckLayout)

        binding.registerResetButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun applyPasswordToggle(passwordEditText: EditText, passwordLayout: TextInputLayout) {
        var isPasswordVisible = false
        val drawableLock: Drawable? = ContextCompat.getDrawable(this, R.drawable.lock_square)
        val drawableUnlock: Drawable? = ContextCompat.getDrawable(this, R.drawable.unlock_square_green)

        passwordEditText.setOnTouchListener { _, event ->
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
        val userpw = binding.changePasswordEt.text.toString()

        if (isValidPassword(userpw)) {
            val user = User(username, phonenum, userpw)
        } else {
            binding.changePasswordLayout.error = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함하여 최소 8자 이상이어야 합니다."
        }
    }
    private fun isValidPassword(password: String): Boolean {
        val pattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
        return pattern.matches(password)
    }
}
