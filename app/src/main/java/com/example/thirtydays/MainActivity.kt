package com.example.thirtydays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thirtydays.model.Workout
import com.example.thirtydays.model.WorkoutRepo.workouts
import com.example.thirtydays.ui.theme.ThirtyDaysTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThirtyDaysTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WorkoutPreview()
                }
            }
        }
    }
}

@Composable
fun WorkoutsApp(modifier : Modifier = Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        WorkoutTopBar()
        LazyColumn(){
            items(workouts){
                WorkoutItem(
                    work = it
                )
            }
        }
    }
}

@Composable
fun WorkoutItem(
    work : Workout,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.padding(8.dp),
    ){
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = work.nameRes),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                ElevatedButton(
                    onClick = { expanded = !expanded }
                ) {
                    Text(if (expanded) "Show less" else "Show more")
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            if(!expanded)
                Image(
                    painter = painterResource(id = work.imageRes),
                    contentDescription = stringResource(id = work.nameRes),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp),
                )
            else{
                Image(
                    painter = painterResource(id = work.imageRes),
                    contentDescription = stringResource(id = work.nameRes),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = stringResource(id = work.instructionRes))
            }
        }
    }
}

@Composable
fun WorkoutTopBar(modifier: Modifier = Modifier){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
            ){
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun WorkoutPreview() {
    ThirtyDaysTheme {
        WorkoutsApp()
    }
}