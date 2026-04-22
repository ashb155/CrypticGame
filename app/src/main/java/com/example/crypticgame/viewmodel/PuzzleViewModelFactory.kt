import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crypticgame.data.db.ProgressDao
import com.example.crypticgame.viewmodel.PuzzleViewModel

class PuzzleViewModelFactory(
    private val puzzleId: Int,
    private val progressDao: ProgressDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PuzzleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PuzzleViewModel(puzzleId = puzzleId, progressDao = progressDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

