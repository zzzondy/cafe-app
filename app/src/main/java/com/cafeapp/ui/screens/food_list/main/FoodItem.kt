package com.cafeapp.ui.screens.food_list.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.cafeapp.R
import com.cafeapp.domain.models.Food
import com.cafeapp.ui.theme.CafeAppTheme
import com.cafeapp.core.util.UiText
import com.cafeapp.core.util.dpToPx
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItem(
    food: Food?,
    modifier: Modifier = Modifier,
    onAddToCart: () -> Unit = {},
    onCardClicked: () -> Unit = {},
) {
    Card(
        onClick = onCardClicked,
        modifier = Modifier
            .padding(16.dp)
            .size(150.dp, 250.dp),
    ) {
        ConstraintLayout(
            constraintSet = ConstraintSet {
                val foodImage = createRefFor(FoodItemConstraintsTags.foodImage)
                val priceText = createRefFor(FoodItemConstraintsTags.priceText)
                val name = createRefFor(FoodItemConstraintsTags.name)
                val addToCartButton = createRefFor(FoodItemConstraintsTags.addToCartButton)

                constrain(foodImage) {
                    start.linkTo(parent.start, 0.dp)
                    end.linkTo(parent.end, 0.dp)
                    top.linkTo(parent.top, 0.dp)
                }

                constrain(name) {
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    top.linkTo(foodImage.bottom, 8.dp)
                    width = Dimension.fillToConstraints
                }

                constrain(priceText) {
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(name.bottom, 8.dp)
                }

                constrain(addToCartButton) {
                    end.linkTo(parent.end, 8.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                }
            },
            modifier = Modifier.fillMaxSize()
        ) {
            CoilImage(
                imageModel = { food?.imageUrl },
                modifier = modifier
                    .layoutId(FoodItemConstraintsTags.foodImage)
                    .clip(MaterialTheme.shapes.medium)
                    .size(height = 150.dp, width = 250.dp),
                imageOptions = ImageOptions(
                    requestSize = IntSize(
                        width = 150.dp.dpToPx(),
                        height = 250.dp.dpToPx()
                    )
                )
            )

            Text(
                text = UiText.DynamicText(food?.name ?: "").asString(),
                style = MaterialTheme.typography.labelLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .layoutId(FoodItemConstraintsTags.name)
            )

            Text(
                text = UiText.StringResource(R.string.rubles, food?.price ?: 0).asString(),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .layoutId(FoodItemConstraintsTags.priceText)
            )

            IconButton(
                onClick = onAddToCart,
                modifier = modifier.layoutId(FoodItemConstraintsTags.addToCartButton)
            ) {
                Icon(
                    Icons.Rounded.ShoppingCart,
                    contentDescription = stringResource(R.string.cart_desc)
                )
            }
        }
    }
}

private object FoodItemConstraintsTags {
    const val foodImage = "foodImage"
    const val priceText = "priceText"
    const val name = "name"
    const val addToCartButton = "addToCartButton"
}

@Preview
@Composable
fun FoodItemPreview() {
    CafeAppTheme {
        FoodItem(
            food = Food(
                1,
                "Teriyaki Chicken Casserole",
                220,
                "коричневый рис, куриная грудка, соевый соус, вода, чеснок",
                "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg"
            )
        )
//        FoodItem(food = null)
    }
}