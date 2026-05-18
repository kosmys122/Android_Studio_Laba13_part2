package com.shapkin.unscramblegame

import com.shapkin.unscramblegame.ui_model.GameViewModel
import org.junit.Test

class GameViewModelTest {
    private val viewModel= GameViewModel()
    @Test
    fun gameViewModel_CorrectWordGuessed_Score_UpdatedAndErrorFlagUnset(){
        var currentCageUiState=viewModel.uiState.value
    }


    private fun getUnscrambledWord(scrambledWord: String): String{
        return com.shapkin.unscramblegame.data.allWords.firstOrNull(){word->
            scrambledWord.toSet()==word.toSet()
        }?: ""
    }
}