package copatych.engineeringcalculatormobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class AppCalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CalculatorModel.initialize(this)
    }
}