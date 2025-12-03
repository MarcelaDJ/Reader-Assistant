package com.hoshiengineering.readerassistant.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hoshiengineering.readerassistant.databinding.FragmentHomeBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
   /*     homeViewModel.setEditTextContent("\"warning: in the working copy of 'app/src/main/java/com/hoshiengineering/readerassistant/MainActivity.kt', LF will be replaced by CRLF the next time Git touches it\\n\" +\n" +
                "                \"warning: in the working copy of 'app/src/main/java/com/hoshiengineering/readerassistant/ui/home/HomeFragment.kt', LF will be replaced by CRLF the next time Git touches it\\n\" +\n" +
                "                \"warning: in the working copy of 'app/src/main/java/com/hoshiengineering/")*/
        var typingJob: Job? = null // Para cancelar el trabajo anterior

        binding.mainEdittxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // 1. Cancelar cualquier tarea de guardado anterior
                typingJob?.cancel()

                // 2. Iniciar una nueva Coroutine
                typingJob = viewLifecycleOwner.lifecycleScope.launch {
                    // 3. Esperar 500 ms (el tiempo de pausa)
                    delay(500)

                    // 4. Si el usuario no escribió nada más durante 500ms, actualizar el ViewModel
                    val text = s?.toString() ?: ""
                    homeViewModel.setEditTextContent(text)
                }
            }
        })
    }
}