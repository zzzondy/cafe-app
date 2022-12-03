package com.cafeapp.ui.screens.food_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cafeapp.R
import com.cafeapp.domain.models.Food
import com.cafeapp.ui.theme.CafeAppTheme
import com.cafeapp.ui.util.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItem(food: Food) {
    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(16.dp)
            .size(150.dp, 250.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
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
                    top.linkTo(foodImage.bottom, 8.dp)
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

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(food.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.food_image),
                modifier = Modifier.layoutId(FoodItemConstraintsTags.foodImage)
            )

            Text(
                text = UiText.DynamicText(food.name).asString(),
                style = MaterialTheme.typography.labelLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.layoutId(FoodItemConstraintsTags.name)
                )

            Text(
                text = UiText.StringResource(R.string.rubles, food.price).asString(),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.layoutId(FoodItemConstraintsTags.priceText)
            )

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.layoutId(FoodItemConstraintsTags.addToCartButton)
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
    }
}