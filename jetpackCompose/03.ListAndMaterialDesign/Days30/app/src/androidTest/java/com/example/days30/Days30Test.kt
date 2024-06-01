package com.example.days30

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.days30.data.Datasource
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class Days30Test {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val index= LocalDate.now().dayOfMonth

    @Test
    fun list_CorrectScrollToItem(){
        composeTestRule.setContent { Days30App() }
        //UI가 렌더링 될 때까지 대기
        composeTestRule.waitForIdle()
        //아이템 데이터 제대로 표시 되는지 확인
        composeTestRule.onNodeWithText("$index").assertIsDisplayed()
    }

    @Test
    fun list_ClickIconButton_ShowDetails(){
        composeTestRule.setContent { Days30App() }
        //UI가 렌더링 될 때까지 대기
        composeTestRule.waitForIdle()
        //아이템 버튼 클릭
        composeTestRule.onNodeWithContentDescription(
            "$index index"+composeTestRule.activity.getString(R.string.icon_button)
        ).performClick()
        //아이템 버튼 클릭할 때만 보이는 텍스트 표시 확인
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                Datasource.allMusic[index-1].title
            )
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                Datasource.allMusic[index-1].artist
            )
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                Datasource.allMusic[index-1].genre
            )
        ).assertIsDisplayed()
        //아이템 버튼 다시 클릭하여 보여지는 텍스트 없애기
        composeTestRule.onNodeWithContentDescription(
            "$index index"+composeTestRule.activity.getString(R.string.icon_button)
        ).performClick()
        //아이템 버튼 클릭 후 텍스트가 제대로 사라졌는지 확인
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                Datasource.allMusic[index-1].title
            )
        ).assertDoesNotExist()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                Datasource.allMusic[index-1].artist
            )
        ).assertDoesNotExist()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                Datasource.allMusic[index-1].genre
            )
        ).assertDoesNotExist()
    }

}

