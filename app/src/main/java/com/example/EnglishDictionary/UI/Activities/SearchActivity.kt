package com.example.EnglishDictionary.UI.Activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.EnglishDictionary.*
import com.example.EnglishDictionary.Data.Local.EnglishWordDB
import com.example.EnglishDictionary.UI.Adapters.DictionaryRecViewAdapter
import com.example.EnglishDictionary.UI.Dialogs.InfoDialogueFragment
import com.example.EnglishDictionary.Util.CustomSpeechRecognizer
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var previousWords : Stack<String>
    lateinit var coroScope : CoroutineScope
    lateinit var im : InputMethodManager
    var speechReco : SpeechRecognizer? = null
    var ADD_FAV : Boolean = false
    var ready : Boolean = true

    val searchViewModel : SearchActivityViewModel by viewModels()

    @Inject
    lateinit var RecentWord : EnglishWordDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myNavigationView.itemIconTintList = null
        myNavigationView.setNavigationItemSelectedListener(this)
        coroScope = CoroutineScope(Dispatchers.Main)
        searchViewModel.initialize()
        previousWords = Stack()


        im = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        speechReco = SpeechRecognizer.createSpeechRecognizer(this)
        val recoListenerCallback = object : CustomSpeechRecognizer(){
            override fun onResults(p0: Bundle?) {
                if (p0!=null) {
                    val data = p0.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    searchWordView.setText(data!!.get(0))
                    previousWords.push(searchWordView.text.toString().trim().toLowerCase())
                    networkCall()
                }
            }
        }
        speechReco!!.setRecognitionListener(recoListenerCallback)
        val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL , RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE , Locale.getDefault())

        //Voice Search Button
        with(voiceSearch){
            setOnCheckedChangeListener { compoundButton, b ->
                if(ContextCompat.checkSelfPermission(applicationContext , android.Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED){
                    voiceSearch.isChecked=false
                    grantPermission()
                }else{
                    if (b){
                        speechReco!!.startListening(speechIntent)
                        clearlayout()
                    }else{
                        speechReco!!.stopListening()
                    }
                }
            }

            setOnLongClickListener {
                val frag = InfoDialogueFragment()
                frag.show(supportFragmentManager , "diaFrag")
                return@setOnLongClickListener true
            }
        }

        previousWord.setOnClickListener {
            if (previousWords.size>=2){
                previousWords.pop()
                clearlayout(previousWords.peek())
                networkCall()
            }
        }

        with(searchWordView){
            setOnFocusChangeListener { view, b ->
                if (favouriteToggle.isEnabled) {
                    ADD_FAV = false
                    favouriteToggle.isEnabled = false
                    favouriteToggle.isChecked = false
                }
            }

            setOnEditorActionListener { textView, i, keyEvent ->
                searchWordView.clearFocus()
                im.hideSoftInputFromWindow(searchWordView.windowToken, 0)
                var retType: Boolean = false
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    previousWords.push(searchWordView.text.toString().trim().toLowerCase())
                    networkCall()
                    retType = true
                }
                retType
            }
        }

        favouriteToggle.setOnCheckedChangeListener { compoundButton, b ->
            coroScope.launch {
                if (ADD_FAV){
                    withContext(Dispatchers.IO) {
                        val res = RecentWord.EnglishWordDAO.getWordToUpdate(searchWordView.text.toString().trim().toLowerCase())
                        res.Favouritte = b
                        RecentWord.EnglishWordDAO.updateWord(res)
                    }
                }
            }

        }

        searchAlso.setOnClickListener{
            previousWords.push(searchWordView.text.toString().trim().toLowerCase())
            networkCall()
            im.hideSoftInputFromWindow(searchWordView.windowToken , 0)
        }

        deleteWord.setOnClickListener{
            clearlayout()
            setMainVisibility(View.GONE)
        }

        toggleButton.setOnClickListener {
            mainDrawer.openDrawer(GravityCompat.START)
        }


        searchViewModel.detailedWordLiveData.observe(this){ englishWord->

            if (englishWord!=null) {
                coroScope.launch(Dispatchers.IO) {
                    try {
                        favouriteToggle.isChecked = RecentWord.EnglishWordDAO.isFav(englishWord.wordPrim)
                    }catch (e : Exception){}
                }

                favouriteToggle.isEnabled = true
                ADD_FAV = true
                with(englishWord){
                    wordType.text = category
                    historicalNote.text = etymology
                    normNoteView.text = normNote
                    primDefsView.text = primDefs
                    primExa.text = primExamples
                }
                setMainVisibility(View.VISIBLE)
                searchViewModel.saveWordToDb(englishWord)
                ready = true
            }else{
                Toast.makeText(this@SearchActivity , "Couldn't find this word!",Toast.LENGTH_SHORT).show()
                ready = true
            }
        }

        searchViewModel.otherDefinitionsAndExamples.observe(this){
            val wordString = searchWordView.text.toString().trim().toLowerCase()
            myRecView.layoutManager = LinearLayoutManager(applicationContext)
            myRecView.itemAnimator = DefaultItemAnimator()
            myRecView.adapter = DictionaryRecViewAdapter(it)
            searchViewModel.saveOthersToDb(it,wordString)
        }

    }

    override fun onDestroy() {
        speechReco!!.destroy()
        coroScope.cancel()
        super.onDestroy()
    }

    override fun onBackPressed() {
        ADD_FAV=false
        super.onBackPressed()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val toIntent = Intent(this , MainActivity2::class.java)
        when (item.itemId) {
            R.id.navrecent -> toIntent.putExtra("fragmentRecent", "RecFrag")
            R.id.navfavourite ->toIntent.putExtra("fragmentRecent", "FavFrag")
        }

        startActivity(toIntent)
        mainDrawer.closeDrawer(GravityCompat.START)
        return true
    }


    private fun networkCall(){
        setMainVisibility(View.GONE)
        val word = searchWordView.text.toString().trim().toLowerCase()
        if (word.isNotEmpty() and ready ) {
            ready = false
            searchViewModel.getDetailedWord(word)
        }else{
            Toast.makeText(this , "Empty Field" , Toast.LENGTH_SHORT).show()
        }
    }

    private fun setMainVisibility(flag : Int){
        typeAndNote.visibility = flag
        hardCodedDefs.visibility = flag
        primDefsLay.visibility = flag
        hardCodedPrimaDefs.visibility = flag
        myRecView.visibility = flag
    }

    private fun clearlayout(text : String = ""){
        ADD_FAV=false
        searchWordView.setText(text)
        favouriteToggle.isEnabled = false
        favouriteToggle.isChecked = false
        searchWordView.clearFocus()
        im.hideSoftInputFromWindow(searchWordView.windowToken , 0)
    }




    private fun grantPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO) , 1212)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==1212 && grantResults.isNotEmpty()){
            if (grantResults.first()==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this , "Permission Granted" , Toast.LENGTH_SHORT).show()
                val dialogueFragment = InfoDialogueFragment()
                dialogueFragment.show(supportFragmentManager , "")
            }

        }
    }

}