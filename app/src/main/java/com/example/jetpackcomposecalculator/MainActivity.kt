package com.example.jetpackcomposecalculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.jetpackcomposecalculator.ui.theme.JetpackComposeCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            JetpackComposeCalculatorTheme {
                Calculator()
            }
        }
    }
}

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Addition") }
    val options = listOf("Addition", "Subtraction", "Multiplication", "Division")
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var isError1 by remember { mutableStateOf(false) }
    var isError2 by remember { mutableStateOf(false) }
    var result by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Row {
            Text(
                text = "Calculator",
                modifier = Modifier
                    .padding(8.dp)
            )
        }
        TextField(
            value = number1,
            onValueChange = {
                if(it.isDigitsOnly()) {
                    number1 = it
                    isError1 = false
                }
                else {
                    number1 = it
                    isError1 = true
                }
            },
            isError = isError1,
            label = { Text("Enter First Number") },
            placeholder = {Text("Enter Number Here")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        if (isError1){
            Text(
                text = "* Enter valid number",
                modifier = Modifier
                    .padding(8.dp)
            )
        }

        TextField(
            value = number2,
            onValueChange = {
                if(it.isDigitsOnly()) {
                    number2 = it
                    isError2 = false
                }
                else {
                    number2 = it
                    isError2 = true
                }
            },
            isError = isError2,
            label = { Text("Enter Second Number") },
            placeholder = {Text("Enter Number Here")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        if (isError2){
            Text(
                text = "* Enter valid number",
                modifier = Modifier
                    .padding(8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Select Operation: ",
                modifier = Modifier
                    .padding(12.dp)

            )
            Box {
                ElevatedButton(onClick = {
                    expanded = true
                }) {
                    Text(selectedOption)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedOption = option
                                expanded = false
                            }
                        )
                    }
                }
            }

        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            OutlinedButton(
                onClick = {
                    if(number1.isNotEmpty() && number2.isNotEmpty() && !isError1 && !isError2){
                        result = when(selectedOption){
                            "Addition" -> number1.toInt() + number2.toInt()
                            "Subtraction" -> number1.toInt() - number2.toInt()
                            "Multiplication" -> number1.toInt() * number2.toInt()
                            "Division" -> number1.toInt() / number2.toInt()
                            else -> 0
                        }
                        Toast.makeText(context, "Result: $result", Toast.LENGTH_SHORT).show()

                    }
                    else{
                        Toast.makeText(context, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
                    }
                }) {
                Text(text = "Calculate")
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Result: $result",
                modifier = Modifier
                    .padding(8.dp)
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackComposeCalculatorTheme {
        Calculator()
    }
}