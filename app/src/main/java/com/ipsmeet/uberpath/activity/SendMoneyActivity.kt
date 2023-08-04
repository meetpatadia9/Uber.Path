package com.ipsmeet.uberpath.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivitySendMoneyBinding
import com.ipsmeet.uberpath.databinding.DialogTransferConfirmationBinding

class SendMoneyActivity : AppCompatActivity() {

    lateinit var binding: ActivitySendMoneyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtxtAmt.addTextChangedListener(textWatcher)

        binding.btnSendMoney.setOnClickListener {
            confirmDialog()
        }
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        override fun afterTextChanged(s: Editable?) {
            if (binding.edtxtAmt.text.toString().trim() != "") {
                binding.edtxtAmt.setTypeface(null, Typeface.BOLD)
            } else {
                binding.edtxtAmt.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    private fun confirmDialog() {
        val dialogBinding = DialogTransferConfirmationBinding.inflate(LayoutInflater.from(this))
        val dialog = Dialog(this, R.style.NewDialog)
        dialog.setContentView(dialogBinding.root)
        val layoutParams = dialog.window!!.attributes
        layoutParams.y = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._100sdp)
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

        dialogBinding.btnSend.setOnClickListener {
            dialog.dismiss()
            transferConfirm()
        }
    }

    private fun transferConfirm() {
        binding.layoutSendMoney.visibility = View.GONE
        binding.includedLayout.layoutTransferConfirm.visibility = View.VISIBLE

        binding.includedLayout.btnHome.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }
    }

}