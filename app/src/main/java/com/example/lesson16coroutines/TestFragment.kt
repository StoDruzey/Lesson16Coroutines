package com.example.lesson16coroutines

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lesson16coroutines.databinding.FragmentTestBinding
import kotlinx.coroutines.*

class TestFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val scope = CoroutineScope(Dispatchers.Main)
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTestBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textStopwatch.setOnClickListener {
//            job?.cancel()
            scope.coroutineContext.cancelChildren()
            scope.launch {
                stopwatch()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun stopwatch() {
        var currentSeconds = 0
        while (currentSeconds < COUNT_SECONDS) {
            delay(1000)
            currentSeconds++
            withContext(Dispatchers.Main) {
                binding.textStopwatch.text = DateUtils.formatElapsedTime(currentSeconds.toLong())
            }
        }
    }

    companion object {
        private const val COUNT_SECONDS = 300
    }
}