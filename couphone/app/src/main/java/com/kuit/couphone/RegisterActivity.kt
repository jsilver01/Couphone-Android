package com.kuit.couphone

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuit.couphone.data.User
import com.kuit.couphone.databinding.ActivityRegisterBinding
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    val auth = Firebase.auth
    var verificationId = ""
    var isverified =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyPasswordToggle(binding.passwordEt)
        applyPasswordToggle(binding.passwordCheckEt)

        binding.verifyButton.setOnClickListener {
            verifyPhonenum(binding.phonenumberEt.text)
        }
        binding.verifyCodeButton.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(verificationId,
                binding.verificationCodeEt.text.toString()
            )
            Log.d("test12345",verificationId)
            Log.d("test12345",binding.verificationCodeEt.text.toString())
            signInWithPhoneAuthCredential(credential)
        }
        binding.registerButton.setOnClickListener {
            if(binding.usernameEt.text.toString() != null && isverified && binding.passwordEt.text.toString() != null && binding.passwordCheckEt.text.toString() != null) {
                registerUser()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
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
            Toast.makeText(this, " tjdrhd", Toast.LENGTH_LONG).show()
            Log.d("test12345","success")

        } else {
            Toast.makeText(this, "비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함하여 최소 8자 이상이어야 합니다.",Toast.LENGTH_LONG).show()
            Log.d("test12345","fail")
        }
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern = Regex("^[0-9]{6}$")
        return pattern.matches(password)
    }
    private fun verifyPhonenum(phonenum: Editable?){
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) { }
            override fun onVerificationFailed(e: FirebaseException) {
            }
            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                this@RegisterActivity.verificationId = verificationId
            }
        }

        val optionsCompat =  PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber82(phonenum.toString()))
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(optionsCompat)
        auth.setLanguageCode("kr")
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    isverified = true
                    binding.verifyCodeButton.text = "인증완료"
                }
                else {
                    Log.d("test12345","fail")
                }
            }
    }
    fun phoneNumber82(msg : String) : String{
        val firstNumber : String = msg.substring(0,3)
        var phoneEdit = msg.substring(3)

        when(firstNumber){
            "010" -> phoneEdit = "+8210$phoneEdit"
            "011" -> phoneEdit = "+8211$phoneEdit"
            "016" -> phoneEdit = "+8216$phoneEdit"
            "017" -> phoneEdit = "+8217$phoneEdit"
            "018" -> phoneEdit = "+8218$phoneEdit"
            "019" -> phoneEdit = "+8219$phoneEdit"
            "106" -> phoneEdit = "+82106$phoneEdit"
        }
        Log.d("국가코드로 변경된 번호 ",phoneEdit)
        return phoneEdit
    }
}
