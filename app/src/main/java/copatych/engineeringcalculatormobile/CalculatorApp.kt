package copatych.engineeringcalculatormobile

import calculator.*

// TODO use getFunctionsNames from package in newer versions of calculator package
fun FunctionsDirector.functionsNames(): List<String> {
    return this.getRegisteredFunctions().split('\n')
        .map { it.split(" - ").first() }
        .filterNot { it.isEmpty() }
}

// TODO use operationsNames from package in newer versions of calculator package
fun OperationsDirector.operationsNames(): List<String> {
    return this.getRegisteredOperations().split('\n')
        .map { it.split("\t- ").first() }
        .filterNot { it.isEmpty() }
}

// TODO implement in calculator package and do common code for this class and StringCalculatorApp
class CalculatorApp(
    val functionsAdder: FunctionsAdder = FunctionsAdder(),
    val tokensPreprocessors: MutableList<TokenPreprocessor> = mutableListOf(),
    val functionsDirector: FunctionsDirector = FunctionsDirectorInstance().funcDirector,
    val operationsDirector: OperationsDirector = OperationsDirectorInstance().opDirector
) {
    private val calculatorEngine = CalculatorEngine(functionsDirector, operationsDirector)

    init {
        tokensPreprocessors.add(functionsAdder)
    }


    // TODO rewrite with class inheritance
    fun process(expr: String): String {
        val lexer = Lexer(expr)
        // TODO My Exceptions or rewrite lexer for checking on correctness immediately
        if (!lexer.isCorrect()) {
            // TODO My Exceptions
            throw Exception("Error for symbol '${expr[lexer.erroredIndex!!]}' in position ${lexer.erroredIndex}")
        }
        var tokens: List<Token> = lexer.tokens
        for (tp in tokensPreprocessors) {
            tokens = tp.doProcessing(tokens)
        }
        // TODO My Exceptions
        return (calculatorEngine.calculate(tokens) ?: throw Exception("Returned null")).toString()
    }


}