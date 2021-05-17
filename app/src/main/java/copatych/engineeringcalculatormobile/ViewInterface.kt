package copatych.engineeringcalculatormobile

interface ViewInterface {
    fun getExpression(): String
    fun setExpression(expr: String)
    fun printResult(res: String)
    fun printErrorMessage(err: String)
}