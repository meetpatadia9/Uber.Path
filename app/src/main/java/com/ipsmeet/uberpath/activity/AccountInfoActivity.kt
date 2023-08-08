package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ipsmeet.uberpath.databinding.ActivityAccountInfoBinding

class AccountInfoActivity : AppCompatActivity() {

    lateinit var binding: ActivityAccountInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnEdit.setOnClickListener {
            startActivity(
                Intent(this, EditAccountActivity::class.java)
                    .putExtra("name", binding.txtUserName.text.toString())
                    .putExtra("occupation", binding.txtOccupationName.text.toString())
                    .putExtra("employer", binding.txtEmployerName.text.toString())
                    .putExtra("phoneNumber", binding.txtUserContact.text.toString())
                    .putExtra("email", binding.txtTommyEmail.text.toString())
            )
        }
    }

}