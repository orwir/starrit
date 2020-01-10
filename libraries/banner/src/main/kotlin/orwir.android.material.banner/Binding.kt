package orwir.android.material.banner

import android.view.View
import android.widget.Button
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import kotlinx.android.synthetic.main.view_banner.view.*

//fun Banner.setPrimaryButton(@StringRes textId: Int, listener: View.OnClickListener?) {
//    setPrimaryButton(resources.getText(textId), listener)
//}
//
//@BindingAdapter("primary_text", "primary_onClick")
//fun Banner.setPrimaryButton(text: CharSequence, listener: View.OnClickListener?) {
//    setButton(button1, text, listener)
//}
//
//fun Banner.setSecondaryButton(@StringRes textId: Int, listener: View.OnClickListener?) {
//    setSecondaryButton(resources.getText(textId), listener)
//}
//
//@BindingAdapter("secondary_text", "secondary_onClick")
//fun Banner.setSecondaryButton(text: CharSequence, listener: View.OnClickListener?) {
//    setButton(button2, text, listener)
//}
//
//private fun setButton(button: Button, text: CharSequence, listener: View.OnClickListener?) {
//    button.text = text
//    button.setOnClickListener(listener)
//    button.visibility = if (listener == null) View.GONE else View.VISIBLE
//}