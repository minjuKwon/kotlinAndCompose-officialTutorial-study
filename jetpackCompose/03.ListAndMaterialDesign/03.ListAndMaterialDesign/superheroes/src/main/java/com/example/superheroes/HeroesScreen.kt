package com.example.superheroes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superheroes.model.Hero
import com.example.superheroes.model.HeroesRepository
import com.example.superheroes.ui.theme.SuperheroesTheme

@Composable
fun HeroList(
    heroList: List<Hero>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier=Modifier
) {
    LazyColumn(contentPadding=contentPadding,modifier=modifier){
        items(heroList){ hero->
            HeroListItem(
                hero = hero,
                modifier=Modifier
                    .padding(
                        dimensionResource(R.dimen.padding_large),
                        dimensionResource(R.dimen.padding_small)
                    )

            )

        }
    }
}

@Composable
fun HeroListItem(hero: Hero, modifier: Modifier= Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation)),
        modifier= modifier
    ) {
        Row(modifier= Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = dimensionResource(R.dimen.imageSize))
            .padding(dimensionResource(R.dimen.padding_large))
        ){
            HeroInformation(
                hero.nameRes,
                hero.descriptionRes,
                modifier=Modifier.weight(1f))
            Spacer(Modifier.width(dimensionResource(R.dimen.padding_large)))
            HeroImage(hero.imageRes)
        }
    }
}

@Composable
fun HeroImage(
    @DrawableRes imageId: Int,
    modifier: Modifier=Modifier
) {
    Image(
        painter=painterResource(imageId),
        contentDescription = null,
        modifier= modifier
            .size(dimensionResource(R.dimen.imageSize))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.radius_small))),
        contentScale = ContentScale.FillWidth,
        alignment = Alignment.TopCenter
    )
}

@Composable
fun HeroInformation(
    @StringRes nameRes: Int,
    @StringRes descriptionRes: Int,
    modifier:Modifier=Modifier
) {
    Column(
        modifier= modifier
    ){
        Text(
            stringResource(nameRes),
            style= MaterialTheme.typography.displaySmall
        )
        Text(
            stringResource(descriptionRes),
            style=MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeroPreview() {
    SuperheroesTheme(darkTheme = false){
        HeroListItem(
            Hero(
                nameRes = R.string.hero2,
                descriptionRes = R.string.description2,
                imageRes = R.drawable.android_superhero2
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeroDarkPreview() {
    SuperheroesTheme(darkTheme=true){
        HeroListItem(
            Hero(
                nameRes = R.string.hero2,
                descriptionRes = R.string.description2,
                imageRes = R.drawable.android_superhero2
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeroListPreview() {
    SuperheroesTheme(darkTheme=false){
        HeroList(HeroesRepository.heroes)
    }
}