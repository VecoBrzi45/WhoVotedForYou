package com.whovotedforyou.app

import android.content.Intent
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val night = Color(0xFF0A0812)
private val surface = Color(0xFF1D1830)
private val violet = Color(0xFF9B72FF)
private val soft = Color(0xFFC5BDD6)

private data class Poll(val question: String, val options: List<String>, val tag: String)
private val polls = listOf(
    Poll("Who is most likely to become famous?", listOf("Me, obviously", "The quiet one", "The funny one", "The mysterious one"), "FRIENDS"),
    Poll("Who would survive longest on a deserted island?", listOf("The planner", "The strongest", "The lucky one", "Nobody 😂"), "CHAOS"),
    Poll("Who takes the longest to get ready?", listOf("The fashion icon", "The perfectionist", "The late one", "Not me!"), "FRIENDS"),
    Poll("Who is secretly the biggest softie?", listOf("The tough one", "The jokester", "The quiet one", "Everyone knows who"), "FRIENDS"),
    Poll("Who would accidentally become a meme?", listOf("The loud one", "The clumsy one", "The serious one", "All of us"), "CHAOS"),
    Poll("Who gives the best advice but never follows it?", listOf("The parent friend", "The romantic", "The overthinker", "Me again"), "REAL TALK"),
    Poll("Who would win a reality show?", listOf("The strategist", "The entertainer", "The drama magnet", "The underdog"), "PARTY"),
    Poll("Who is most likely to reply three days later?", listOf("The busy one", "The ghost", "The forgetful one", "Seen-zoned champion"), "FRIENDS")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}

@Composable
private fun App() {
    val context = LocalContext.current
    var page by remember { mutableStateOf("home") }
    var current by remember { mutableIntStateOf(0) }
    var selected by remember { mutableIntStateOf(-1) }
    var totalVotes by remember { mutableIntStateOf(0) }
    var roomCode by remember { mutableStateOf("VIOLET7") }
    var customName by remember { mutableStateOf("") }
    var customQuestion by remember { mutableStateOf("") }
    var voted by remember { mutableStateOf(false) }

    MaterialTheme(colorScheme = darkColorScheme(background = night, surface = surface, primary = violet)) {
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFF281846), night, Color(0xFF08070D)))).padding(22.dp)) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("WHO VOTED", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Black, letterSpacing = 1.5.sp)
                        Text("FOR YOU?", color = violet, fontSize = 27.sp, fontWeight = FontWeight.Black, letterSpacing = 1.5.sp)
                    }
                    Text("BETA 0.1", color = soft, fontSize = 10.sp, letterSpacing = 1.sp)
                }
                Spacer(Modifier.height(24.dp))
                when (page) {
                    "home" -> {
                        Text("THE GROUP CHAT, BUT MAKE IT A GAME.", color = soft, fontSize = 11.sp, letterSpacing = 1.2.sp, textAlign = TextAlign.Center)
                        Spacer(Modifier.height(24.dp))
                        Panel {
                            Text("👀  FIND OUT WHAT YOUR FRIENDS REALLY THINK", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                            Spacer(Modifier.height(10.dp))
                            Text("Vote anonymously. Reveal the group results. Laugh about it together.", color = soft, textAlign = TextAlign.Center, lineHeight = 22.sp)
                        }
                        Spacer(Modifier.height(18.dp))
                        PrimaryButton("START A GAME") { page = "room" }
                        Spacer(Modifier.height(10.dp))
                        SecondaryButton("QUICK PLAY — DEMO") { current = 0; selected = -1; voted = false; page = "vote" }
                        Spacer(Modifier.weight(1f))
                        Text("NO PUBLIC FEED • NO PERSONAL VOTE REVEALS", color = Color(0xFF81798F), fontSize = 9.sp, textAlign = TextAlign.Center, letterSpacing = 0.7.sp)
                    }
                    "room" -> {
                        Text("CREATE YOUR PRIVATE ROOM", color = soft, fontSize = 11.sp, letterSpacing = 1.5.sp)
                        Spacer(Modifier.height(20.dp))
                        Panel {
                            Text("YOUR ROOM CODE", color = soft, fontSize = 10.sp, letterSpacing = 1.4.sp)
                            Text(roomCode, color = violet, fontSize = 34.sp, fontWeight = FontWeight.Black, letterSpacing = 4.sp)
                            Text("Invite your group using the share button. This preview runs locally; live shared rooms are planned for the online release.", color = soft, textAlign = TextAlign.Center, lineHeight = 21.sp)
                        }
                        Spacer(Modifier.height(16.dp))
                        PrimaryButton("SHARE INVITE") {
                            val send = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, "Join my WHO VOTED FOR YOU? game! Room code: $roomCode")
                            }
                            context.startActivity(Intent.createChooser(send, "Invite your friends"))
                        }
                        Spacer(Modifier.height(10.dp))
                        SecondaryButton("PLAY LOCAL DEMO") { current = 0; selected = -1; voted = false; page = "vote" }
                        Spacer(Modifier.height(10.dp))
                        TextButton(onClick = { page = "home" }) { Text("BACK", color = soft) }
                    }
                    "vote" -> {
                        val poll = polls[current % polls.size]
                        Text("ROUND ${(current % polls.size) + 1}  •  ${poll.tag}", color = violet, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                        Spacer(Modifier.height(18.dp))
                        Panel {
                            Text("WHO IS MOST LIKELY TO…", color = soft, fontSize = 10.sp, letterSpacing = 1.5.sp)
                            Spacer(Modifier.height(12.dp))
                            Text(poll.question, color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, lineHeight = 32.sp)
                            Spacer(Modifier.height(20.dp))
                            poll.options.forEachIndexed { index, option ->
                                val active = selected == index
                                Surface(onClick = { if (!voted) selected = index }, color = if (active) violet.copy(alpha = .32f) else Color(0xFF2A243B), shape = RoundedCornerShape(13.dp), modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                                    Text(option, Modifier.padding(15.dp), color = Color.White, fontWeight = if (active) FontWeight.Bold else FontWeight.Medium)
                                }
                            }
                        }
                        Spacer(Modifier.height(14.dp))
                        if (!voted) PrimaryButton("SUBMIT ANONYMOUS VOTE") { if (selected >= 0) { voted = true; totalVotes++ } }
                        else {
                            Text("YOUR DEMO VOTE IS RECORDED", color = violet, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            PrimaryButton("REVEAL DEMO RESULTS") { page = "results" }
                        }
                        Spacer(Modifier.height(8.dp))
                        SecondaryButton("NEXT QUESTION") { current++; selected = -1; voted = false }
                        Spacer(Modifier.weight(1f))
                        Text("DEMO MODE: VOTES STAY ON THIS DEVICE", color = Color(0xFF81798F), fontSize = 9.sp)
                    }
                    "results" -> {
                        Text("THE GROUP HAS SPOKEN", color = violet, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                        Spacer(Modifier.height(18.dp))
                        Panel {
                            Text("📊  RESULTS PREVIEW", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(12.dp))
                            Text("Your local demo vote was counted. Live group percentages appear once the online room service is connected.", color = soft, textAlign = TextAlign.Center, lineHeight = 22.sp)
                            Spacer(Modifier.height(16.dp))
                            Text("DEMO VOTES: $totalVotes", color = violet, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                        }
                        Spacer(Modifier.height(18.dp))
                        PrimaryButton("PLAY AGAIN") { current++; selected = -1; voted = false; page = "vote" }
                        Spacer(Modifier.height(10.dp))
                        SecondaryButton("BACK TO HOME") { page = "home" }
                    }
                }
            }
        }
    }
}

@Composable private fun Panel(content: @Composable ColumnScope.() -> Unit) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(26.dp), colors = CardDefaults.cardColors(containerColor = surface), elevation = CardDefaults.cardElevation(6.dp)) {
        Column(Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally, content = content)
    }
}
@Composable private fun PrimaryButton(label: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = violet)) {
        Text(label, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
    }
}
@Composable private fun SecondaryButton(label: String, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.outlinedButtonColors(contentColor = soft)) { Text(label, fontWeight = FontWeight.Bold, letterSpacing = .7.sp) }
}
