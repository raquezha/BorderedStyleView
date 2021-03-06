package com.github.edwnmrtz.borderedstyleedittext

import android.content.Context
import android.content.res.TypedArray
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.textfield.TextInputLayout

class PasswordBorderedStyleEditText (context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var tvFieldLabelTitle: AppCompatTextView
    private var tvAssistiveText: AppCompatTextView
    private var etField: AppCompatEditText
    private var tilPassword: TextInputLayout
    private var flField: FrameLayout

    private var titleTextColor : Int       = ContextCompat.getColor(context,R.color.greyish)
    private var assistiveTextColor: Int    = ContextCompat.getColor(context,R.color.greyish)
    private var assistiveText : String?    = ""

    private var isError = false

    init {
        View.inflate(context, R.layout.bordered_edittext_password, this)
        tvFieldLabelTitle               = findViewById(R.id.tvFieldLabelTitle)
        tvAssistiveText                 = findViewById(R.id.tvAssistiveText)
        etField                         = findViewById(R.id.etField)
        tilPassword                     = findViewById(R.id.tilPassword)
        flField                         = findViewById(R.id.flField)

        val attributes: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.PasswordBorderedStyleEditText, 0, 0)
        val count = attributes.indexCount
        for (i in 0 until count) {
            when (val attr = attributes.getIndex(i)) {
                R.styleable.PasswordBorderedStyleEditText_endIconDrawable -> {
                    val passwordDrawable = attributes.getDrawable(attr)
                    if(passwordDrawable != null)
                        tilPassword.endIconDrawable = passwordDrawable
                }
                R.styleable.PasswordBorderedStyleEditText_startIconDrawable -> {
                    val passwordDrawable = attributes.getDrawable(attr)
                    if(passwordDrawable != null)
                        tilPassword.startIconDrawable = passwordDrawable
                }
                R.styleable.PasswordBorderedStyleEditText_endIconMode-> {
                    tilPassword.endIconMode =  attributes.getInt(attr, TextInputLayout.END_ICON_NONE)
                }
                R.styleable.PasswordBorderedStyleEditText_android_imeOptions -> {
                    etField.imeOptions =  attributes.getInt(attr, 0)
                }
                R.styleable.PasswordBorderedStyleEditText_android_maxLines -> {
                    etField.maxLines = attributes.getInt(attr, 1000)
                }
                R.styleable.PasswordBorderedStyleEditText_android_maxLength -> {
                    etField.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(attributes.getInt(attr, 1000)))
                }
                R.styleable.PasswordBorderedStyleEditText_assistiveText-> {
                    assistiveText = attributes.getString(R.styleable.PasswordBorderedStyleEditText_assistiveText)
                    tvAssistiveText.text = assistiveText
                    tvAssistiveText.visibility = View.VISIBLE
                }
                R.styleable.PasswordBorderedStyleEditText_fieldLabel-> {
                    tvFieldLabelTitle.text = attributes.getString(R.styleable.PasswordBorderedStyleEditText_fieldLabel)
//                    etField.hint = attributes.getString(R.styleable.PasswordBorderedStyleEditText_fieldLabel)
                    tvFieldLabelTitle.visibility = View.VISIBLE
                }
                R.styleable.PasswordBorderedStyleEditText_assistiveTextColor -> {
                    assistiveTextColor = attributes.getColor(R.styleable.PasswordBorderedStyleEditText_assistiveTextColor, assistiveTextColor)
                    tvAssistiveText.setTextColor(assistiveTextColor)
                }
                R.styleable.PasswordBorderedStyleEditText_fieldLabelTextColor -> {
                    titleTextColor = attributes.getColor(R.styleable.PasswordBorderedStyleEditText_fieldLabelTextColor, titleTextColor)
                    tvFieldLabelTitle.setTextColor(titleTextColor)
                }
                R.styleable.PasswordBorderedStyleEditText_android_hint -> {
                    etField.hint = attributes.getString(R.styleable.PasswordBorderedStyleEditText_android_hint)
                }
                R.styleable.PasswordBorderedStyleEditText_android_textAppearance -> {
                    TextViewCompat.setTextAppearance(etField, attributes.getResourceId(attr, 0))
                }
            }
        }
        attributes.recycle()

        textChangeListener()
    }

    private fun textChangeListener() {
        etField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(isError) {
                    removeError()
                    isError = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Ignore
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Ignore
            }

        })
    }

    fun getText() : String {
        return etField.text.toString()
    }

    fun getEditText(): AppCompatEditText {
        return etField
    }

    fun setText(text : String) {
        etField.setText(text)
    }

    fun setFieldLabel(label : String) {
        tvFieldLabelTitle.text = label
        tvFieldLabelTitle.visibility = View.VISIBLE
    }

    fun setError(errorMessage: String) {
        isError = true
        tvFieldLabelTitle.setTextColor(ContextCompat.getColor(context, R.color.reddish_pink))
        tvAssistiveText.visibility = View.VISIBLE
        tvAssistiveText.setTextColor(ContextCompat.getColor(context, R.color.reddish_pink))
        flField.setBackgroundResource(R.drawable.bordered_roundbox_error)
        tvAssistiveText.text = errorMessage
    }

    fun removeError() {
        isError = false
        tvFieldLabelTitle.setTextColor(titleTextColor)
        flField.setBackgroundResource(R.drawable.bordered_roundbox_active)
        tvAssistiveText.setTextColor(assistiveTextColor)

        if(assistiveText != null) {
            tvAssistiveText.visibility = View.VISIBLE
            tvAssistiveText.text = assistiveText
        } else {
            tvAssistiveText.visibility = View.GONE
        }
    }

    fun enable() {
        etField.isEnabled = true
        flField.setBackgroundResource(R.drawable.bordered_roundbox_active)
    }

    fun disable() {
        etField.isEnabled = false
        flField.setBackgroundResource(R.drawable.bordered_roundbox_disabled)
    }
}