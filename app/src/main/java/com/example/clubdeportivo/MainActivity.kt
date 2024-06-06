package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate ejecutado")
        val dbHelper = SqlHelper(this)
        initializeDatabase(dbHelper)

        // Verificar que los datos se han insertado correctamente
        val users = dbHelper.getAllUsers()
        for (user in users) {
            Log.d("MainActivity", "Usuario: ${user["nombre"]} ${user["apellido"]}, Usuario: ${user["usuario"]}")
        }
        setContent {
            ClubDeportivoNavHost()
        }
    }

    private fun initializeDatabase(dbHelper: SqlHelper) {
        // Insertar un administrador
        dbHelper.insertPersona("Admin", "ApellidoAdmin", "DireccionAdmin", "12345678A", "1980-01-01", 1, 1, 1, "adminUser", "adminPassword")

        // Insertar un cliente
        dbHelper.insertPersona("Cliente", "ApellidoCliente", "DireccionCliente", "87654321C", "1995-05-15", 1, 1, 0, "clienteUser", "clientePassword")

        // Insertar un cliente que no sea socio
        dbHelper.insertPersona("ClienteNoSocio", "ApellidoClienteNoSocio", "DireccionClienteNoSocio", "87654321N", "1998-07-22", 1, 0, 0, "clienteNoSocioUser", "clienteNoSocioPassword")

        // Insertar cuotas para los clientes
        dbHelper.insertCuota(2, "Enero", "mensual", "2024-01-05", "2024-01", 1, 100.00, "2024-01-31")
        dbHelper.insertCuota(2, "Febrero", "mensual", "2024-02-05", "2024-02", 2, 100.00, "2024-02-28")
        dbHelper.insertCuota(3, "Enero", "mensual", "2024-01-10", "2024-01", 1, 150.00, "2024-01-31")
        dbHelper.insertCuota(3, "Febrero", "mensual", "2024-02-10", "2024-02", 2, 150.00, "2024-02-28")
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
    val dbHelper = SqlHelper(context)
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
                if (dbHelper.getUser(username, password)) {
                    navController.navigate("home") // Navega a la pantalla Home
                } else {
                    messageText = "Usuario o contraseña incorrectos."
                    showMessage = true
                }
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

@Preview("Home screen")
@Composable
fun HomeScreen() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text("Bienvenido al Club Deportivo")
    }
}
