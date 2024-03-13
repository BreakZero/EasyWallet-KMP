package com.easy.wallet.design.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import kotlinx.coroutines.launch

internal enum class ActionsState {
    SHOW, HIDE
}

@Composable
fun SwipeToActions(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    actions: @Composable () -> Unit
) {
    var actionWidth by remember {
        mutableIntStateOf(200)
    }
    val coroutineScope = rememberCoroutineScope()
    var actionState by remember {
        mutableStateOf(ActionsState.SHOW)
    }
    val translationX = remember {
        Animatable(0f)
    }

    val draggableState = rememberDraggableState(onDelta = { dragAmount ->
        coroutineScope.launch {
            translationX.snapTo(translationX.value + dragAmount)
        }
    })
    val decay = rememberSplineBasedDecay<Float>()

    val contentBox: @Composable (() -> Unit) = @Composable {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    this.translationX = translationX.value
                }
                .draggable(
                    draggableState, Orientation.Horizontal,
                    onDragStarted = {},
                    onDragStopped = { velocity ->
                        val targetOffsetX = decay.calculateTargetValue(
                            translationX.value,
                            velocity
                        )
                        coroutineScope.launch {
                            val actualTargetX = if (targetOffsetX < -actionWidth * 0.5) {
                                -actionWidth.toFloat()
                            } else {
                                0f
                            }
                            // checking if the difference between the target and actual is + or -
                            val targetDifference = (actualTargetX - targetOffsetX)
                            val canReachTargetWithDecay =
                                (targetOffsetX > actualTargetX && velocity > 0f &&
                                    targetDifference > 0f) ||
                                    (targetOffsetX < actualTargetX && velocity < 0 &&
                                        targetDifference < 0f)
                            if (canReachTargetWithDecay) {
                                translationX.animateDecay(
                                    initialVelocity = velocity,
                                    animationSpec = decay
                                )
                            } else {
                                translationX.animateTo(actualTargetX, initialVelocity = velocity)
                            }
                            actionState = if (actualTargetX == -actionWidth.toFloat()) {
                                ActionsState.SHOW
                            } else {
                                ActionsState.HIDE
                            }
                        }
                    }
                )
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
    val actionBox: @Composable (() -> Unit) = @Composable {
        Box(modifier = Modifier
            .wrapContentWidth()
            .onSizeChanged { actionWidth = it.width }) {
            actions()
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        actionBox()
        contentBox()
    }
}
