package com.ipsmeet.uberpath.activity

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityCardDetailsBinding
import com.ipsmeet.uberpath.databinding.DialogCardIsReadyBinding
import com.ipsmeet.uberpath.viewmodel.SelectCountryViewModel

class CardDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCardDetailsBinding

    private lateinit var selectCountryViewModel: SelectCountryViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectCountryViewModel = ViewModelProvider(this)[SelectCountryViewModel::class.java]
        val cardStyle = intent.getStringExtra("cardStyle").toString()

        when(cardStyle) {
            "cardOne" -> {
                Glide.with(this).load(R.drawable.img_card_style1).into(binding.imgVSelectedCardStyle)
            }
            "cardTwo" -> {
                Glide.with(this).load(R.drawable.img_card_style2).into(binding.imgVSelectedCardStyle)
            }
            "cardThree" -> {
                Glide.with(this).load(R.drawable.img_card_style3).into(binding.imgVSelectedCardStyle)
            }
        }

        sharedPreferences = getSharedPreferences("sharedPreference", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        editor.putString("cardStyle", cardStyle)
        editor.apply()

        Glide.with(this)
            .load(sharedPreferences.getInt("countryFlag", R.drawable.flag_uk))
            .into(binding.imgVFlag)
        binding.txtCountryName.text = sharedPreferences.getString("userCountry", "United Kingdom")

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.selectCountry.setOnClickListener {
            selectCountryViewModel.updatedCountry(this, binding)
        }

        binding.btnSave.setOnClickListener {
            readyDialog()
        }
    }

    private fun readyDialog() {
        val dialogBinding = DialogCardIsReadyBinding.inflate(LayoutInflater.from(this))
        val dialog = Dialog(this, R.style.NewDialog)
        dialog.setContentView(dialogBinding.root)
        val layoutParams = dialog.window!!.attributes
        layoutParams.y = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._130sdp)
        dialog.window!!.apply {
            attributes = layoutParams
            attributes.gravity = Gravity.TOP
        }
        dialog.show()

        dialogBinding.imgVImg.elevation = 5F

        //  Corner radius
        val radius = resources.getDimension(com.intuit.sdp.R.dimen._15sdp)

        val bottomBarBackground = dialogBinding.bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, radius)
            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
            .setBottomRightCorner(CornerFamily.ROUNDED, radius)
            .build()

        dialogBinding.btnImReady.setOnClickListener {
            editor.putBoolean("isLogged", true)
            editor.commit()
            dialog.dismiss()

            startActivity(
                Intent(this, HomeActivity::class.java)
            )
            finish()
        }
    }

}