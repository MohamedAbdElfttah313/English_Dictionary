package com.example.EnglishDictionary.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.EnglishDictionary.Data.Local.EnglishWordDB
import com.example.EnglishDictionary.R
import com.example.EnglishDictionary.UI.Adapters.RecentAndFavAdapter
import kotlinx.android.synthetic.main.fragment_recent.*
import kotlinx.coroutines.*


class RecentMenuFragment : Fragment() {

    lateinit var listOfRecentWords: List<String>
    lateinit var coroScope : CoroutineScope

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coroScope = CoroutineScope(Dispatchers.Main)
        val recentWordDAO = EnglishWordDB.getInstance(requireContext()).EnglishWordDAO

        coroScope.launch {
            withContext(Dispatchers.IO) {
                listOfRecentWords = recentWordDAO.getAllWords()
            }

            withContext(Dispatchers.Main) {
                RecentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                RecentRecyclerView.itemAnimator = DefaultItemAnimator()
                RecentRecyclerView.adapter = RecentAndFavAdapter(listOfRecentWords) { someWord ->
                    beginDetailedFragment(someWord)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        coroScope.cancel()
    }

    fun beginDetailedFragment(word: String) {
        val cachedWordFragment = CachedWordDetailFragment()
        val bundle = Bundle()
        bundle.putString("wordDetails", word)
        cachedWordFragment.arguments = bundle

        this.parentFragmentManager.beginTransaction()
            .replace(R.id.SelectedFragment, cachedWordFragment).addToBackStack(null).commit()

    }

}