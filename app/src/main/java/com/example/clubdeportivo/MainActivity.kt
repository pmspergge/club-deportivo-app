package com.example.clubdeportivo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClubDeportivoNavHost()
        }
    }
}

@Composable
fun ClubDeportivoNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            ClubDeportivoApp(navController)
        }
        composable("home") {
            HomeScreen()
        }
    }
}

@Composable
fun ClubDeportivoApp(navController: NavController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false) }
    var messageText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (username.isNotBlank() && password.isNotBlank()) {
                SharedPreferencesManager.saveUserData(username, "cliente", context)
                navController.navigate("home") // Navega a la pantalla Home
            } else {
                messageText = "Por favor, ingresa ambos campos."
                showMessage = true
            }
        }) {
            Text("Login")
        }
        if (showMessage) {
            Text(text = messageText, color = MaterialTheme.colors.error)
        }
    }
}

@Composable
fun HomeScreen() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text("Bienvenido al Club Deportivo")
    }
}
