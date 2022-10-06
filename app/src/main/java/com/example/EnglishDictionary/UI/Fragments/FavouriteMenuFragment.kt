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
import com.example.EnglishDictionary.UI.Activities.MainActivity2
import com.example.EnglishDictionary.UI.Adapters.RecentAndFavAdapter
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.coroutines.*


class FavouriteMenuFragment : Fragment() {

    lateinit var listOfFavouriteWords: List<String>
    lateinit var coroScope : CoroutineScope

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coroScope = CoroutineScope(Dispatchers.Main)
        val recentWordDAO = EnglishWordDB.getInstance(requireContext()).EnglishWordDAO

        coroScope.launch {

            withContext(Dispatchers.IO) {
                listOfFavouriteWords = recentWordDAO.getAllFav(true)
            }

            withContext(Dispatchers.Main) {
                FavouriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                FavouriteRecyclerView.itemAnimator = DefaultItemAnimator()
                FavouriteRecyclerView.adapter = RecentAndFavAdapter(listOfFavouriteWords) { someWord ->
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