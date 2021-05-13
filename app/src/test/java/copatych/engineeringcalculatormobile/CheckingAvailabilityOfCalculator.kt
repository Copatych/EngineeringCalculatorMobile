package copatych.engineeringcalculatormobile
import calculator.*
import org.junit.Assert.assertEquals
import org.junit.Test

internal class CheckingAvailabilityOfCalculator {
    @Test
    fun check() {
        val calculatorEngine = CalculatorEngine(
            FunctionsDirectorInstance().funcDirector,
            OperationsDirectorInstance().opDirector
        )
        assertEquals(4.0, calculatorEngine.calculate(Lexer("1+3").tokens)!!, 1e-7)
        assertEquals(0.0, calculatorEngine.calculate(Lexer("sin(pi)").tokens)!!, 1e-7)

    }
}