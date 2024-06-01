package com.example.mycity

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.mycity.data.SpotDataProvider
import com.example.mycity.ui.MyCityApp
import org.junit.Rule
import org.junit.Test
class MyCityAppStateRestorationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @TestCompactWidth
    fun compactDevice_selectedSpotSpotRetained_afterConfigChange(){
        //화면 크기 설정
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {MyCityApp(windowSize= WindowWidthSizeClass.Compact)}
        //특정 장소의 이름이 제대로 표시 되는지 확인
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                SpotDataProvider.allSpots[0].name
            )
        ).assertIsDisplayed()
        //특정 장소를 클릭하여 상세 화면으로 이동
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                SpotDataProvider.allSpots[0].name
            )
        ).performClick()
        //뒤로 가기 버튼 존재 확인하여 제대로 상세 화면으로 이동되었는지 확인
        composeTestRule.onNodeWithContentDescriptionForStringId(
            R.string.navigation_back
        ).assertExists()
        //상세 화면에서 텍스트가 제대로 표시하는지 확인
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                SpotDataProvider.allSpots[0].description
            )
        ).assertExists()
        //화면 회전
        stateRestorationTester.emulateSavedInstanceStateRestore()
        //회전 전과 같은 데이터 유지 하는지 확인
        composeTestRule.onNodeWithContentDescriptionForStringId(
            R.string.navigation_back
        ).assertExists()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                SpotDataProvider.allSpots[0].description
            )
        ).assertExists()
    }

    @Test
    @TestExpandedWidth
    fun expandedDevice_selectedSpotSpotRetained_afterConfigChange(){
        //화면 크기 설정
        val stateRestorationTest = StateRestorationTester(composeTestRule)
        stateRestorationTest.setContent{MyCityApp(windowSize=WindowWidthSizeClass.Expanded)}
        //특정 장소의 이름이 제대로 표시 되는지 확인
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                SpotDataProvider.allSpots[1].name
            )
        ).assertIsDisplayed()
        //특정 장소를 클릭하여 상세 화면으로 이동
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                SpotDataProvider.allSpots[1].name
            )
        ).performClick()
        //상세 화면에서 텍스트가 제대로 표시하는지 확인
        composeTestRule.onNodeWithTagForStringId(R.string.details_screen).onChildren()
            .assertAny(hasAnyDescendant(hasText(
                composeTestRule.activity.getString(SpotDataProvider.allSpots[1].description)))
            )
        //화면 회전
        stateRestorationTest.emulateSavedInstanceStateRestore()
        //회전 전과 같은 데이터 유지 하는지 확인
        composeTestRule.onNodeWithTagForStringId(R.string.details_screen).onChildren()
            .assertAny(hasAnyDescendant(hasText(
                composeTestRule.activity.getString(SpotDataProvider.allSpots[1].description)))
            )
    }

}

