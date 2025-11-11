package com.crunchry.animusicplayer

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Animated splash screen:
 * - Text logo (horizontal) slides in from left, decelerates, performs squash & stretch on stop.
 * - Icon logo (eye only) spins and performs bouncing motion while moving horizontally, then
 *   does a final "collision" bounce against the text logo and settles ~10dp away.
 * Both align on a horizontal center line.
 */
@Composable
fun SplashScreen(
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
    splashDurationMillis: Int = 2600,
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

    val backgroundRes = if (isLandscape) {
        R.drawable.splash_screen_landscape
    } else {
        R.drawable.splash_screen
    }

    // Animatables for text logo
    val textOffsetX = remember { Animatable(-screenWidthDp.value) } // start far left
    val textScaleX = remember { Animatable(1f) }
    val textScaleY = remember { Animatable(1f) }

    // Animatables for icon logo
    val iconOffsetX = remember { Animatable(-screenWidthDp.value * 1.2f) }
    val iconVerticalBounce = remember { Animatable(0f) } // dp offset (negative = up)
    val iconScaleX = remember { Animatable(1f) }
    val iconScaleY = remember { Animatable(1f) }
    val iconRotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Text logo slide in
        launch {
            textOffsetX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 1600, easing = FastOutSlowInEasing)
            )
            // Squash & stretch after arrival
            textScaleX.animateTo(0.85f, tween(120))
            textScaleY.animateTo(1.08f, tween(120))
            textScaleX.animateTo(1.08f, tween(140))
            textScaleY.animateTo(0.95f, tween(140))
            textScaleX.animateTo(1f, tween(180))
            textScaleY.animateTo(1f, tween(180))
        }
        // Icon horizontal travel with bounce & spin
        launch {
            // Horizontal travel ends slightly left of text logo (account for spacing 10dp and icon size ~60dp)
            val finalIconOffset = 0f // We'll position via Row spacing; offset animatable reaches 0
            iconOffsetX.animateTo(
                targetValue = finalIconOffset,
                animationSpec = tween(durationMillis = 1400, easing = FastOutSlowInEasing)
            )
            // Final collision bounce sequence (overshoot + settle)
            iconOffsetX.animateTo(8f, tween(110, easing = FastOutSlowInEasing)) // slight push into text
            iconOffsetX.animateTo(0f, tween(260, easing = FastOutSlowInEasing))
        }
        // Icon rotation
        launch {
            iconRotation.animateTo(1080f, tween(1400, easing = LinearEasing)) // 3 spins
            iconRotation.animateTo(0f, tween(400, easing = FastOutSlowInEasing)) // return to original orientation
        }
        // Icon bouncing vertically & squash/stretch cycles while moving
        launch {
            // Use keyframes to emulate damped bouncing
            iconVerticalBounce.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = 1600
                    // Upward bounces (negative values) diminishing amplitude
                    (-24f) at 200
                    0f at 350
                    (-18f) at 500
                    0f at 650
                    (-12f) at 780
                    0f at 920
                    (-8f) at 1050
                    0f at 1180
                    (-16f) at 1320 // final bigger bounce on collision
                    0f at 1600
                }
            )
        }
        // Icon squash & stretch synchronized to vertical bounce using a loop
        launch {
            val bounceKeyTimes = listOf(200, 350, 500, 650, 780, 920, 1050, 1180, 1320, 1600)
            bounceKeyTimes.windowed(2, 1).forEach { (start, end) ->
                val segment = end - start
                // At bounce apex (start) squash (scaleY < 1, scaleX > 1), then restore.
                iconScaleX.animateTo(1.25f, tween(segment / 2))
                iconScaleY.animateTo(0.75f, tween(segment / 2))
                iconScaleX.animateTo(1f, tween(segment / 2))
                iconScaleY.animateTo(1f, tween(segment / 2))
            }
        }
        // Wait total splash duration and then finish
        launch {
            kotlinx.coroutines.delay(splashDurationMillis.toLong())
            onFinished()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Icon logo
                val iconYOffset: Dp = with(density) { iconVerticalBounce.value.dp }
                Image(
                    painter = painterResource(id = R.drawable.cr_logo_eye_only),
                    contentDescription = "App Icon Logo",
                    modifier = Modifier
                        .size(60.dp)
                        .offset(x = iconOffsetX.value.dp, y = iconYOffset)
                        .scale(iconScaleX.value, iconScaleY.value)
                        .rotate(iconRotation.value)
                )
                Spacer(modifier = Modifier.width(10.dp))
                // Text logo
                Image(
                    painter = painterResource(id = R.drawable.cr_logo_horizontal),
                    contentDescription = "App Text Logo",
                    modifier = Modifier
                        .offset(x = textOffsetX.value.dp)
                        .scale(textScaleX.value, textScaleY.value)
                )
            }
        }
    }
}
