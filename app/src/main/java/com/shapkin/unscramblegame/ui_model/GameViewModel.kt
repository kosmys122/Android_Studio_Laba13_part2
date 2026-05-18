package com.shapkin.unscramblegame.ui_model

import androidx.lifecycle.ViewModel
import com.shapkin.unscramblegame.data.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.shapkin.unscramblegame.data.allWords

class GameViewModel: ViewModel() {
    private val _uiState= MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    init {
        resetGame()
    }
    fun resetGame(){
        usedWords.clear()
        _uiState.value= GameUiState(currentScramdledWord = pickRandomWordAndSuffle())
    }
    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()
    private fun shuffleCurrentWord(word: String): String{
        val tempWord=word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord)==word){
            tempWord.shuffle()
        }
        return String(tempWord)
    }
    private fun pickRandomWordAndSuffle(): String{
        currentWord = allWords.random()
        while (usedWords.contains(currentWord)){
            currentWord=allWords.random()
        }
        usedWords.add(currentWord)
        return shuffleCurrentWord(currentWord)
    }
}