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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
            itemsIndexed(workouts) { index, workout ->
                WorkoutItem(
                    index = index,
                    work = workout
                )
            }
        }
    }
}

@Composable
fun WorkoutItem(
    index : Int,
    work : Workout,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.padding(16.dp)
    ){
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
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
                    text = String.format(
                        "Day %d: %s",
                        index + 1,
                        stringResource(id = work.nameRes)),
                    fontWeight = FontWeight.Bold,fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Image(
                painter = painterResource(id = work.imageRes),
                contentDescription = stringResource(id = work.nameRes),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
            if (work.numberOfReps != null) {
                Text(
                    text = String.format(
                        stringResource(R.string.one_set_format),
                        work.numberOfReps
                    ),
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
            }
            if (work.durationInSeconds != null) {
                Text(
                    text = String.format(
                        stringResource(R.string.duration_format),
                        work.durationInSeconds
                    ),
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
            }
            Row {
                Text(
                    text = stringResource(R.string.how_to_do_exercise),
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                WorkoutItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }
            if (expanded) {
                Text(
                    text = stringResource(id = work.instructionRes),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}

@Composable
fun WorkoutItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = stringResource(id = R.string.expand_button_content_description)
        )
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