package copatych.engineeringcalculatormobile

class MainPresenter(private val view: ViewInterface) {
    private var expr: String = ""
    private var cursor = 0
    private val calculatorModel = CalculatorModel.instance

    fun clearExpr() {
        expr = ""
        view.printResult("")
        view.setExpression("")
        cursor = 0
    }

    fun clearOne() {
        if (cursor == 0) moveCursorRight()
        expr = if (cursor == expr.length) {
            expr.dropLast(1)
        } else {
            expr.substring(0, cursor - 1) + expr.substring(cursor)
        }
        view.setExpression(expr)
        moveCursorLeft()
    }

    fun moveCursorLeft() {
        if (cursor > 0) {
            cursor--
        } else {
            cursor = 0
        }
    }

    fun moveCursorRight() {
        if (cursor < expr.length) {
            cursor++
        } else {
            cursor = expr.length
        }
    }

    fun setCursorPosition(position: Int) {
        cursor = if (position > 0) position else 0
    }

    fun getCursorPosition(): Int {
        return cursor
    }

    fun addSubexpression(subexpression: String) {
        if (expr.length == cursor) {
            expr += subexpression
        } else {
            expr = expr.substring(0, cursor) + subexpression + expr.substring(cursor)
        }
        cursor += subexpression.length
        view.setExpression(expr)
    }

    fun calculate() {
        try {
            calculatorModel?.let { view.printResult(it.process(expr)) }
        } catch (e: Exception) {
            view.printErrorMessage(e.message.toString())
        }
    }

}