package copatych.engineeringcalculatormobile

class Keyboards(private val calculatorApp: CalculatorApp = CalculatorApp()) {

    // TODO Dynamically change keyboard
    val base: List<List<String>> = listOf(
        listOf(" round ", " ( ", " ) ", " ; ", "e"),
        listOf("7", "8", "9", " / ", " ln "),
        listOf("4", "5", "6", " * ", " ^ "),
        listOf("1", "2", "3", "-", " max "),
        listOf(" ! ", "0", ".", " + ", " min "),
        listOf(" sin ", " cos ", " tan ", " pi ", " abs "),
        listOf(" asin ", " acos ", " atan ", " if "),
    )

    val functions: List<String> = calculatorApp.allFunctionsNames
        .map { if ((it != "e") and (it != "E")) " $it " else it }

    val operations: List<String> = calculatorApp.operationsDirector.operationsNames()
        .map { if (it != "-") " $it " else it }
}