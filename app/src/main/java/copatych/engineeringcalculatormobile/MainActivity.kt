package copatych.engineeringcalculatormobile

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.core.view.plusAssign
import com.google.android.flexbox.*


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
        get() = findViewById<TextView>(R.id.expression)

    private fun calculatorButtonFactory(v: String): Button {
        val button = Button(this)
        button.text = v
        button.transformationMethod = null
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

    private fun createDynamicKeyboard(
        viewGroup: ViewGroup,
        keyboard: List<String>,
        nCols: Int = 5
    ) {
        val layout = FlexboxLayout(this)
        layout.flexWrap = FlexWrap.WRAP
        viewGroup.removeAllViews()
        viewGroup += layout
        for (k in keyboard) {
            val button = calculatorButtonFactory(k)
            button.layoutParams = LinearLayout.LayoutParams(
                viewGroup.measuredWidth / nCols - 1,
                viewGroup.measuredWidth / nCols - 1
            )
            layout += button
        }
    }

    private fun buttonF(view: View, isChecked: Boolean) {
        if (isChecked) {
            findViewById<ToggleButton>(R.id.operations).isChecked = false
            createDynamicKeyboard(keyboardLayout, keyboards.functions)
        } else {
            createBaseKeyboard(keyboardLayout)
        }
    }

    private fun buttonOp(view: View, isChecked: Boolean) {
        if (isChecked) {
            findViewById<ToggleButton>(R.id.functions).isChecked = false
            createDynamicKeyboard(keyboardLayout, keyboards.operations)
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
    }

    fun buttonMoveRight(view: View) {
        presenter.moveCursorRight()
    }

    fun buttonClearOne(view: View) {
        presenter.clearOne()
    }

    override fun getExpression(): String {
        return findViewById<TextView>(R.id.expression).text.toString()
    }

    override fun setExpression(expr: String) {
        exprContainer.setText(expr)
    }

    override fun printResult(res: String) {
        findViewById<TextView>(R.id.result).text = res
    }

    override fun printErrorMessage(err: String) {
        findViewById<TextView>(R.id.result).text = err
    }


}