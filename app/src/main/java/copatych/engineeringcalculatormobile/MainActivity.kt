package copatych.engineeringcalculatormobile

import calculator.*
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import com.google.android.flexbox.FlexboxLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        initCalculatorApp(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initButtons()
        keyboardLayout.apply {
            this.doOnLayout {
                createBaseKeyboard(this)
            }
        }
    }

    private var expr: String = ""
    private lateinit var calculatorApp: CalculatorApp
    private fun initCalculatorApp(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            calculatorApp = CalculatorApp()
        } else {
            // TODO serialisation for functionsAdder
            calculatorApp = CalculatorApp()
        }
    }

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

    private fun updateExprOnTextView() {
        val textView = findViewById<View>(R.id.expression) as TextView
        textView.text = expr
    }

    private val baseKeyboard: List<List<String>> = listOf(
        listOf("sin", "cos", "tan", "pi", "abs"),
        listOf("round", "(", ")", "e"),
        listOf("7", "8", "9", "/", "ln"),
        listOf("4", "5", "6", "*", "^"),
        listOf("1", "2", "3", "-", "max"),
        listOf("!", "0", ".", "+", "min"),
        listOf("asin", "acos", "atan", "if"),
    )

    private fun calculatorButtonFactory(v: String): Button {
        val button = Button(this)
        button.text = v
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1.0f
        )
        button.setOnClickListener {
            expr += v
            updateExprOnTextView()
        }
        return button
    }

    private fun createBaseKeyboard(viewGroup: ViewGroup) {
        val verticalLayout = LinearLayout(this)
        verticalLayout.orientation = LinearLayout.VERTICAL
        viewGroup.removeAllViews()
        viewGroup += verticalLayout
        val buttonHeight = viewGroup.measuredHeight / 5 - 1
        for (row in baseKeyboard) {
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
            createDynamicKeyboard(keyboardLayout, calculatorApp.allFunctionsNames)
        } else {
            createBaseKeyboard(keyboardLayout)
        }
    }

    private fun buttonOp(view: View, isChecked: Boolean) {
        if (isChecked) {
            createDynamicKeyboard(
                keyboardLayout,
                calculatorApp.operationsDirector.operationsNames()
            )
        } else {
            createBaseKeyboard(keyboardLayout)
        }
    }

    fun buttonCalculate(view: View) {
        val res = try {
            calculatorApp.process(expr)
        } catch (e : Exception) {
            e.message.toString()
        }
        findViewById<TextView>(R.id.result).text = res
    }

    fun buttonClear(view: View) {
        expr = ""
        findViewById<TextView>(R.id.expression).text = ""
        findViewById<TextView>(R.id.result).text = ""
    }

}