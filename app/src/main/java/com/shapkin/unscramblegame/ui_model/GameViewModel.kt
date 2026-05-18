package com.shapkin.unscramblegame.ui_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapkin.unscramblegame.data.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.shapkin.unscramblegame.data.allWords
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.shapkin.unscramblegame.data.MAX_NO_OF_WORDS
import com.shapkin.unscramblegame.data.SCORE_INCREASE
import kotlinx.coroutines.flow.update

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
    var userGuess by mutableStateOf("")
        private set
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
    fun updateUserGuess(guessedWord: String){
        userGuess=guessedWord
    }
    private fun updateGameState(updatedScore: Int){
        if (usedWords.size== MAX_NO_OF_WORDS){
            _uiState.update { currentState->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            _uiState.update { currentState->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScramdledWord = pickRandomWordAndSuffle(),
                    score = updatedScore,
                    currentWordCount = currentState.currentWordCount+1
                )
            }
        }
    }
    fun checkUserGuess(){
        if(userGuess.equals(currentWord , ignoreCase = true)){
            val updateScope = _uiState.value.score + SCORE_INCREASE
            updateGameState(updateScope)
        }else{
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        updateUserGuess("")
    }
    fun skipWord(){
        updateGameState(_uiState.value.score)
        updateUserGuess("")
    }
}