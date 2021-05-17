package copatych.engineeringcalculatormobile

object Keyboards {
    private val calculatorModel: CalculatorModel? = CalculatorModel.instance

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

    val functions: List<String> = calculatorModel?.allFunctionsNames
        ?.map { if ((it != "e") and (it != "E")) " $it " else it } ?: listOf()

    val operations: List<String> = calculatorModel?.operationsNames
        ?.map { if (it != "-") " $it " else it } ?: listOf()
}