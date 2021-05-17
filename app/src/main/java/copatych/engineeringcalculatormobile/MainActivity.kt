package copatych.engineeringcalculatormobile

import calculator.*
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import com.google.android.flexbox.FlexboxLayout


class MainActivity : AppCompatActivity(), ViewInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initButtons()
        keyboardLayout.apply {
            this.doOnLayout {
                createBaseKeyboard(this)
            }
        }
        presenter = MainPresenter(this)
        keyboards = presenter.keyboards
    }

    private lateinit var presenter: MainPresenter
    private lateinit var keyboards: Keyboards

    private fun initButtons() {
        findViewById<ToggleButton>(R.id.functions).setOnCheckedChangeListener { button, isChecked ->
            buttonF(button, isChecked)
        }
        findViewById<ToggleButton>(R.id.operations).setOnCheckedChangeListener { button, isChecked ->
            buttonOp(button, isChecked)
        }
    }

    private val keyboardLayout
        get() = findViewById<ViewGroup>(R.id.keyboardLayout)

    private val exprContainer
        get() = findViewById<EditText>(R.id.expression)

    private fun calculatorButtonFactory(v: String): Button {
        val button = Button(this)
        button.text = v
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1.0f
        )
        button.setOnClickListener {
            presenter.addSubexpression(v)
        }
        return button
    }

    private fun createBaseKeyboard(viewGroup: ViewGroup) {
        val verticalLayout = LinearLayout(this)
        verticalLayout.orientation = LinearLayout.VERTICAL
        viewGroup.removeAllViews()
        viewGroup += verticalLayout
        val buttonHeight = viewGroup.measuredHeight / 5 - 1
        for (row in keyboards.base) {
            val linearLayout = LinearLayout(this)
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                buttonHeight
            )
//            linearLayout.layoutParams.height = buttonHeight
            for (v in row) {
                val button = calculatorButtonFactory(v)
                linearLayout += button
            }
            verticalLayout += linearLayout
        }
    }

    private fun createDynamicKeyboard(viewGroup: ViewGroup, keyboard: List<String>) {
        val layout = FlexboxLayout(this)
        viewGroup.removeAllViews()
        viewGroup += layout
        for (k in keyboard) {
            layout += calculatorButtonFactory(k)
        }
    }

    private fun buttonF(view: View, isChecked: Boolean) {
        if (isChecked) {
            createDynamicKeyboard(keyboardLayout, keyboards.functions)
        } else {
            createBaseKeyboard(keyboardLayout)
        }
    }

    private fun buttonOp(view: View, isChecked: Boolean) {
        if (isChecked) {
            createDynamicKeyboard(
                keyboardLayout,
                keyboards.operations
            )
        } else {
            createBaseKeyboard(keyboardLayout)
        }
    }

    fun buttonCalculate(view: View) {
        presenter.calculate()
    }

    fun buttonClear(view: View) {
        presenter.clearExpr()
    }

    fun buttonMoveLeft(view: View) {
        presenter.moveCursorLeft()
        exprContainer.setSelection(presenter.getCursorPosition())
    }

    fun buttonMoveRight(view: View) {
        presenter.moveCursorRight()
        exprContainer.setSelection(presenter.getCursorPosition())
    }

    fun buttonClearOne(view: View) {
        presenter.clearOne()
        exprContainer.setSelection(presenter.getCursorPosition())
    }

    override fun getExpression(): String {
        return findViewById<TextView>(R.id.expression).text.toString()
    }

    override fun setExpression(expr: String) {
        exprContainer.setText(expr)
        exprContainer.setSelection(presenter.getCursorPosition())
    }

    override fun printResult(res: String) {
        findViewById<TextView>(R.id.result).text = res
    }

    override fun printErrorMessage(err: String) {
        findViewById<TextView>(R.id.result).text = err
    }

}