package com.example.canvassemi_circularringcomponent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.example.canvassemi_circularringcomponent.ui.theme.CanvasSemicircularRingComponentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvasSemicircularRingComponentTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(80.dp)
                ){
                    SemiCircularRing(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}
