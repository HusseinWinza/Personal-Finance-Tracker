package www.mc.com.utils

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import www.mc.com.R
import www.mc.com.databinding.LayoutButtonViewBinding

/**
 * Custom [MaterialButton] that displays a progress indicator
 */

class ButtonView(context: Context, attrs: AttributeSet) :
    MaterialCardView(context, attrs) {

    var binding: LayoutButtonViewBinding =
        LayoutButtonViewBinding.bind(inflate(R.layout.layout_button_view, attachToRoot = true))

    private val attributes: TypedArray =
        context.obtainStyledAttributes(attrs, R.styleable.ButtonView)

    init {

        initView()
    }

    private fun initView() {
        with(attributes) {
            strokeColor = getColor(
                R.styleable.ButtonView_viewStrokeColor,
                ContextCompat.getColor(context, R.color.colorPrimary)
            )
        }
        binding.buttonTitle.apply {
            text = attributes.getString(R.styleable.ButtonView_text)
            setTextColor(
                attributes.getColor(
                    R.styleable.ButtonView_titleTextColor,
                    ContextCompat.getColor(context, R.color.white)
                )
            )
        }
        cardElevation = 5f
        radius = 8f
        setCardBackgroundColor(
            attributes.getColor(
                R.styleable.ButtonView_backgroundColor,
                ContextCompat.getColor(context, R.color.colorPrimary)
            )
        )


    }

    fun setButtonTitle(text: String) {
        binding.buttonTitle.text = text
    }

    fun hideLoadingIndicator() {
        cardElevation = 5f
        isEnabled = true
        binding.progressBar.makeGone()
        binding.buttonTitle.makeVisible()
        setCardBackgroundColor(
            attributes.getColor(
                R.styleable.ButtonView_backgroundColor,
                ContextCompat.getColor(context, R.color.colorPrimary)
            )
        )

    }

    fun showLoadingIndicator() {
        cardElevation = 0f
        isEnabled = false
        binding.buttonTitle.makeGone()
        binding.progressBar.makeVisible()
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.buttonLoadingState))
    }


}