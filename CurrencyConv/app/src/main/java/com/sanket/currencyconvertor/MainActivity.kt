package com.sanket.currencyconvertor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    var baseCurrency = "EUR"
    var convertedCurrency = "USD"
    var rate = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }


    private fun getApiResult() {
        if (et_first_conversion != null && et_first_conversion.text.isNotEmpty() && et_first_conversion.text.isNotBlank()) {

            var API =
                "https://api.ratesapi.io/api/latest?base=$baseCurrency&symbols=$convertedCurrency"

            if (baseCurrency == convertedCurrency) {
                Toast.makeText(
                    applicationContext,
                    "Please pick a currency to convert",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                GlobalScope.launch(Dispatchers.IO) {

                    try {

                        val apiResult = URL(API).readText()
                        val jsonObject = JSONObject(apiResult)
                        rate =
                            jsonObject.getJSONObject("rates").getString(convertedCurrency)
                                .toFloat()

                        Log.d("Main", "$rate")
                        Log.d("Main", apiResult)

                        withContext(Dispatchers.Main) {
                            val text =
                                ((et_first_conversion.text.toString()
                                    .toFloat()) * rate).toString()
                            et_secondConversion?.setText(text)

                        }

                    } catch (e: Exception) {
                        Log.e("Main", "$e")
                    }
                }
            }
        }
    }


}