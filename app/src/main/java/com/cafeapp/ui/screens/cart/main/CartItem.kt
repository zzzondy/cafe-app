package com.cafeapp.ui.screens.cart.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.cafeapp.R
import com.cafeapp.core.util.UIText
import com.cafeapp.core.util.toPxWithDensity
import com.cafeapp.domain.models.Food
import com.cafeapp.ui.screens.app.LocalImageLoader
import com.cafeapp.ui.theme.CafeAppTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartItem(
    food: Food?,
    modifier: Modifier = Modifier,
    itemsCount: Int = 1,
    onSelectItem: (Food) -> Unit = {},
    onIncrementItemsCount: () -> Unit = {},
    onDecrementItemsCount: () -> Unit = {},
    onDeleteFoodFromCart: () -> Unit = {}
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val haptic = LocalHapticFeedback.current

    val dismissState = rememberDismissState(
        positionalThreshold = { (screenWidth / 2).toPx() }
    )

    val dismissScope = rememberCoroutineScope()
    LaunchedEffect(key1 = dismissState.currentValue) {
        if (dismissState.currentValue == DismissValue.DismissedToStart) {
            dismissScope.launch {
                dismissState.reset()
            }
            onDeleteFoodFromCart()
        }
    }

    LaunchedEffect(key1 = dismissState.targetValue) {
        if (dismissState.targetValue == DismissValue.DismissedToStart) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

    SwipeToDismiss(
        state = dismissState,
        background = {
            if (dismissState.dismissDirection == null) {
                return@SwipeToDismiss
            }

            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.DismissedToStart -> MaterialTheme.colorScheme.error
                    else -> Color.Gray
                }
            )

            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = stringResource(R.string.delete),
                    modifier = Modifier.scale(scale)
                )
            }
        },
        directions = if (food != null) setOf(DismissDirection.EndToStart) else setOf(),
        dismissContent = {
            CartItemContent(
                food = food,
                itemsCount = itemsCount,
                modifier = modifier,
                onSelectItem = onSelectItem,
                onDecrementItemsCount = onDecrementItemsCount,
                onIncrementItemsCount = onIncrementItemsCount
            )
        }
    )
}

@Composable
private fun CartItemContent(
    food: Food?,
    onSelectItem: (Food) -> Unit,
    modifier: Modifier,
    onDecrementItemsCount: () -> Unit,
    itemsCount: Int,
    onIncrementItemsCount: () -> Unit
) {
    var itemSelected by rememberSaveable { mutableStateOf(false) }

    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        ConstraintLayout(
            constraintSet = ConstraintSet {
                val foodImage = createRefFor(CartItemConstraintTags.foodImage)
                val foodTitle = createRefFor(CartItemConstraintTags.foodTitle)
                val foodPrice = createRefFor(CartItemConstraintTags.foodPrice)
                val checkbox = createRefFor(CartItemConstraintTags.checkbox)
                val increaseItemLayout =
                    createRefFor(CartItemConstraintTags.increaseItemLayout)

                constrain(foodImage) {
                    start.linkTo(checkbox.end, 8.dp)
                    top.linkTo(parent.top, 8.dp)
                }

                constrain(checkbox) {
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(parent.top, 8.dp)
                }

                constrain(foodTitle) {
                    start.linkTo(foodImage.end, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    top.linkTo(parent.top, 8.dp)
                    width = Dimension.fillToConstraints
                }

                constrain(foodPrice) {
                    start.linkTo(foodTitle.start)
                    top.linkTo(foodTitle.bottom, 8.dp)
                }

                constrain(increaseItemLayout) {
                    end.linkTo(parent.end, 8.dp)
                    top.linkTo(foodPrice.bottom, 8.dp)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .padding(horizontal = 16.dp)
                .selectable(selected = itemSelected, enabled = food != null) {
                    itemSelected = !itemSelected
                    onSelectItem(food!!)
                }
        ) {
            CoilImage(
                imageModel = { food?.imageUrl },
                imageOptions = ImageOptions(
                    requestSize = IntSize(
                        width = 40.dp.toPxWithDensity(),
                        height = 40.dp.toPxWithDensity()
                    )
                ),
                modifier = modifier
                    .layoutId(CartItemConstraintTags.foodImage)
                    .clip(MaterialTheme.shapes.medium)
                    .size(80.dp),
                imageLoader = { LocalImageLoader.current },
            )

            Text(
                text = food?.name ?: "",
                modifier = modifier.layoutId(CartItemConstraintTags.foodTitle),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = UIText.StringResource(R.string.rubles, food?.price ?: 0)
                    .asString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier
                    .layoutId(CartItemConstraintTags.foodPrice)
                    .fillMaxWidth(0.5f)
            )

            Checkbox(
                checked = itemSelected,
                onCheckedChange = {
                    itemSelected = !itemSelected
                    onSelectItem(food!!)
                },
                modifier = modifier
                    .layoutId(CartItemConstraintTags.checkbox)
            )

            Row(
                modifier = modifier
                    .layoutId(CartItemConstraintTags.increaseItemLayout)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                IconButton(
                    onClick = {
                        onDecrementItemsCount()
                    },
                    enabled = itemsCount > 1
                ) {
                    Icon(
                        painter = painterResource(R.drawable.round_remove_24),
                        contentDescription = stringResource(R.string.remove_food)
                    )
                }

                Text(
                    text = itemsCount.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxHeight()
                )

                IconButton(onClick = {
                    onIncrementItemsCount()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(R.string.add_food)
                    )
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
private fun CartItemPreview() {
    CafeAppTheme(darkTheme = true) {
        CartItem(
            food = Food(
                1,
                "Адольф Гитлер",
                123,
                "sdfsf",
                "https://www.themealdb.com/images/media/meals/hx335q1619789561.jpg"
            )
        )
    }
}

private object CartItemConstraintTags {
    const val foodImage = "foodImage"
    const val foodTitle = "foodTitle"
    const val foodPrice = "foodPrice"
    const val checkbox = "checkbox"
    const val increaseItemLayout = "increaseItemLayout"
}