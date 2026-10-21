package com.movefirst.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val Ink = Color(0xFF18251F)
private val Green = Color(0xFFB9F36A)
private val Cream = Color(0xFFF7F7F0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("movefirst", MODE_PRIVATE)
        setContent {
            var completedToday by remember { mutableStateOf(prefs.getBoolean("completedToday", false)) }
            var secondsLeft by remember { mutableIntStateOf(300) }
            var running by remember { mutableStateOf(false) }
            var totalSessions by remember { mutableIntStateOf(prefs.getInt("totalSessions", 0)) }

            LaunchedEffect(running, secondsLeft) {
                if (running && secondsLeft > 0) {
                    delay(1000)
                    secondsLeft -= 1
                }
                if (running && secondsLeft == 0) running = false
            }

            MaterialTheme(colorScheme = lightColorScheme(
                primary = Ink, onPrimary = Color.White, background = Cream, surface = Color.White
            )) {
                Surface(modifier = Modifier.fillMaxSize(), color = Cream) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 28.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(42.dp).background(Green, RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                                Text("M", fontSize = 24.sp, fontWeight = FontWeight.Black, color = Ink)
                            }
                            Spacer(Modifier.width(10.dp))
                            Text("MoveFirst", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Ink)
                            Spacer(Modifier.weight(1f))
                            Text("TU RITMO", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text("Primero te mueves.\nDespués, scrolleas.", fontSize = 34.sp, lineHeight = 39.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                        Text("Un pequeño movimiento antes de entrar a tus redes puede cambiar tu día.", fontSize = 16.sp, lineHeight = 23.sp, color = Color(0xFF59645D))
                        Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                            Column(Modifier.fillMaxWidth().padding(22.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                                Text(if (completedToday) "¡OBJETIVO CUMPLIDO!" else "TU PAUSA ACTIVA", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF54752D))
                                Text(if (completedToday) "Ya hiciste algo bueno por ti hoy." else "5 minutos para ti", fontSize = 23.sp, fontWeight = FontWeight.Bold, color = Ink)
                                Text(if (completedToday) "Mañana puedes volver a empezar." else "Camina, estira, baila o haz sentadillas. Elige lo que te haga bien.", fontSize = 15.sp, lineHeight = 22.sp, color = Color(0xFF59645D))
                                Box(Modifier.fillMaxWidth().background(Cream, RoundedCornerShape(18.dp)).padding(22.dp), contentAlignment = Alignment.Center) {
                                    Text("%02d:%02d".format(secondsLeft / 60, secondsLeft % 60), fontSize = 44.sp, fontWeight = FontWeight.Bold, color = Ink)
                                }
                                if (!completedToday) {
                                    Button(
                                        onClick = {
                                            if (secondsLeft == 0) {
                                                completedToday = true
                                                totalSessions += 1
                                                prefs.edit().putBoolean("completedToday", true).putInt("totalSessions", totalSessions).apply()
                                            } else {
                                                running = !running
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth().height(54.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Ink),
                                        shape = RoundedCornerShape(16.dp)
                                    ) {
                                        Text(
                                            when {
                                                secondsLeft == 0 -> "Marcar como completado"
                                                running -> "Pausar movimiento"
                                                secondsLeft < 300 -> "Continuar movimiento"
                                                else -> "Empezar mis 5 minutos"
                                            },
                                            fontSize = 16.sp, fontWeight = FontWeight.Bold
                                        )
                                    }
                                    if (secondsLeft < 300 && secondsLeft > 0) {
                                        TextButton(onClick = { running = false; secondsLeft = 300 }) {
                                            Text("Reiniciar temporizador", color = Color.Gray)
                                        }
                                    }
                                } else {
                                    Button(onClick = { completedToday = false; secondsLeft = 300; prefs.edit().putBoolean("completedToday", false).apply() },
                                        modifier = Modifier.fillMaxWidth().height(54.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Ink),
                                        shape = RoundedCornerShape(16.dp)) {
                                        Text("Volver a la pantalla inicial")
                                    }
                                }
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = Green), shape = RoundedCornerShape(20.dp)) {
                                Column(Modifier.padding(18.dp)) {
                                    Text("SESIONES", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Ink)
                                    Text("$totalSessions", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                                    Text("en total", fontSize = 13.sp, color = Ink)
                                }
                            }
                            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(20.dp)) {
                                Column(Modifier.padding(18.dp)) {
                                    Text("RECUERDA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Ink)
                                    Text("Poco es mejor que nada.", fontSize = 17.sp, lineHeight = 21.sp, fontWeight = FontWeight.Bold, color = Ink)
                                }
                            }
                        }
                        Spacer(Modifier.weight(1f))
                        Text("VERSIÓN INICIAL · SOLO EN TU CELULAR", modifier = Modifier.fillMaxWidth(), fontSize = 10.sp, color = Color.Gray, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}
