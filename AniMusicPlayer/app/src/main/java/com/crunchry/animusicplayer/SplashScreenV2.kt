package com.crunchry.animusicplayer

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * SplashScreenV2 changes:
 * - Eye icon and horizontal text logo start off-screen on opposite sides and move toward each other.
 * - Movement speed slowed ~25% compared to v1 (longer durations).
 * - On collision both perform a mutual squash (horizontal compression / vertical stretch), then overshoot and settle.
 * - Eye icon vertical bounce amplitude is dynamic: ~15% of screen height maximum.
 */
@Composable
fun SplashScreenV2(
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
    splashDurationMillis: Int = 3250, // slightly longer total to account for slower travel & collision settle
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp

    // Orientation-aware background selection (avoids ResourceNotFound in portrait)
    val backgroundRes = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> R.drawable.splash_screen_landscape
        else -> R.drawable.splash_screen_portrait_mobile // generic portrait version present in /drawable
    }

    // Dynamic maximum bounce height (negative value = upward) ~15% of screen height
    val maxBounceUpDp = -screenHeightDp.value * 0.15f

    // Horizontal animatables (both start off-screen: icon from left, text from right)
    val iconOffsetX = remember { Animatable(-screenWidthDp.value * 1.2f) }
    val textOffsetX = remember { Animatable(screenWidthDp.value * 1.2f) }

    // Vertical bounce for icon
    val iconVerticalBounce = remember { Animatable(0f) }

    // Scale & rotation animatables
    val iconScaleX = remember { Animatable(1f) }
    val iconScaleY = remember { Animatable(1f) }
    val textScaleX = remember { Animatable(1f) }
    val textScaleY = remember { Animatable(1f) }
    val iconRotation = remember { Animatable(0f) }

    // Durations (slowed by ~25%)
    val travelDuration = 2000 // v1 ~1600
    val bounceDuration = 2000 // vertical bounce sequence aligned with travel+collision

    LaunchedEffect(Unit) {
        // Parallel travel toward center
        launch {
            iconOffsetX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = travelDuration, easing = FastOutSlowInEasing)
            )
        }
        launch {
            textOffsetX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = travelDuration, easing = FastOutSlowInEasing)
            )
        }

        // Icon rotation slower & then quick settle
        launch {
            iconRotation.animateTo(1080f, tween(travelDuration, easing = LinearEasing)) // three spins during travel
            iconRotation.animateTo(0f, tween(450, easing = FastOutSlowInEasing)) // decelerate & align
        }

        // Vertical bounce (keyframes scaled to bounceDuration) using dynamic amplitude fractions
        launch {
            iconVerticalBounce.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = bounceDuration
                    // Fractions based on original timeline, scaled.
                    val times = listOf(250, 440, 625, 800, 960, 1120, 1280, 1440, 1600, bounceDuration)
                    // Amplitudes diminishing; last bigger bounce on collision (~ just before travel ends)
                    (maxBounceUpDp) at times[0]
                    0f at times[1]
                    (maxBounceUpDp * 0.75f) at times[2]
                    0f at times[3]
                    (maxBounceUpDp * 0.5f) at times[4]
                    0f at times[5]
                    (maxBounceUpDp * 0.35f) at times[6]
                    0f at times[7]
                    (maxBounceUpDp * 0.65f) at times[8] // slightly larger on collision
                    0f at times[9]
                }
            )
        }

        // Collision sequence after both reach center: overshoot inward, squash, recoil, settle.
        launch {
            // Wait until travel is complete
            kotlinx.coroutines.delay(travelDuration.toLong())

            val collisionPush = 15f
            val collisionDuration = 150
            val recoilOvershootDuration = 200
            val settleDuration = 300

            // 1. Collision and Squash
            launch {
                // Horizontal overshoot
                iconOffsetX.animateTo(collisionPush, tween(collisionDuration, easing = FastOutSlowInEasing))
                textOffsetX.animateTo(-collisionPush, tween(collisionDuration, easing = FastOutSlowInEasing))
            }
            launch {
                // Squash deformation
                iconScaleX.animateTo(0.75f, tween(collisionDuration, easing = FastOutSlowInEasing))
                iconScaleY.animateTo(1.25f, tween(collisionDuration, easing = FastOutSlowInEasing))
                textScaleX.animateTo(0.8f, tween(collisionDuration, easing = FastOutSlowInEasing))
                textScaleY.animateTo(1.2f, tween(collisionDuration, easing = FastOutSlowInEasing))
            }

            // 2. Recoil and Overshoot
            launch {
                // Recoil past neutral
                iconOffsetX.animateTo(-collisionPush / 2, tween(recoilOvershootDuration, easing = FastOutSlowInEasing))
                textOffsetX.animateTo(collisionPush / 2, tween(recoilOvershootDuration, easing = FastOutSlowInEasing))
            }
            launch {
                // Stretch deformation (overshoot)
                iconScaleX.animateTo(1.1f, tween(recoilOvershootDuration, easing = FastOutSlowInEasing))
                iconScaleY.animateTo(0.9f, tween(recoilOvershootDuration, easing = FastOutSlowInEasing))
                textScaleX.animateTo(1.08f, tween(recoilOvershootDuration, easing = FastOutSlowInEasing))
                textScaleY.animateTo(0.92f, tween(recoilOvershootDuration, easing = FastOutSlowInEasing))
            }

            // 3. Settle back to final state
            launch {
                // Settle position
                iconOffsetX.animateTo(0f, tween(settleDuration, easing = FastOutSlowInEasing))
                textOffsetX.animateTo(0f, tween(settleDuration, easing = FastOutSlowInEasing))
            }
            launch {
                // Settle scale
                iconScaleX.animateTo(1f, tween(settleDuration, easing = FastOutSlowInEasing))
                iconScaleY.animateTo(1f, tween(settleDuration, easing = FastOutSlowInEasing))
                textScaleX.animateTo(1f, tween(settleDuration, easing = FastOutSlowInEasing))
                textScaleY.animateTo(1f, tween(settleDuration, easing = FastOutSlowInEasing))
            }
        }

        // Finish after total splash duration
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
                // Icon
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
