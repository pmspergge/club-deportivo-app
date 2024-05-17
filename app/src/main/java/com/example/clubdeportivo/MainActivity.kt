package com.example.clubdeportivo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    lateinit var clubDeportivo: sqlHelper;
    private lateinit var sqlHelper: sqlHelper;
    private lateinit var nameEditText: EditText;
    private lateinit var passwordEditText: EditText;
    private lateinit var addButton: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContent {
            ClubDeportivoNavHost()
        }
        setContentView(R.layout.activity_main);
        clubDeportivo = sqlHelper(this);
        nameEditText = findViewById(R.id.name);
        passwordEditText = findViewById(R.id.password);
        addButton = findViewById(R.id.button_login);
        addButton.setOnClickListener{
            val name = nameEditText.text.toString();
            val password = passwordEditText.text.toString();

            clubDeportivo.InsertData(name, password);

            Toast.makeText(this, "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
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
        composable("register") {
            RegisterScreen(navController)
        }
        composable("home") {
            HomeScreen()
        }
    }
}

@Composable
fun CustomButton(onClick: () -> Unit, text: String) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        modifier = Modifier
            .height(50.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text)
    }
}

@Composable
fun LogoImage() {
    Image(
        painter = painterResource(id = R.drawable.loginlogo),
        contentDescription = "Login Logo",
        modifier = Modifier
            .width(167.dp)
            .height(214.dp)
    )
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
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("register") }) {
            Text("Registrarse")
        }
    }
}

@Composable
fun HomeScreen() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text("Bienvenido al Club Deportivo")
    }
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false) }
    var messageText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                if (isValidEmail(email)) {
                    SharedPreferencesManager.saveUserData(email, "cliente", context)
                    navController.navigate("home") // Navega a la pantalla Home tras el registro
                } else {
                    messageText = "Email no válido."
                    showMessage = true
                }
            } else {
                messageText = "Todos los campos son obligatorios."
                showMessage = true
            }
        }) {
            Text("Registrar")
        }
        if (showMessage) {
            Text(text = messageText, color = MaterialTheme.colors.error)
        }
    }
}