package com.example.EnglishDictionary

import androidx.room.*


@Entity(tableName = "ENGLISH_WORDS")
data class EnglishWord(
        @PrimaryKey(autoGenerate = false)
        var wordPrim : String
        ){

    @ColumnInfo(name = "Category")
    var category : String? = ""

    @ColumnInfo(name = "Etymology")
    var etymology : String? = "None !"

    @ColumnInfo(name = "Normal_Note")
    var normNote : String? = "None !"

    @ColumnInfo(name = "Primary_Definitions")
    var primDefs : String? = "None !"

    @ColumnInfo(name = "Primary_Examples")
    var primExamples : String? = "None !"

    @ColumnInfo(name = "Favourite")
    var Favouritte : Boolean = false

}



@Entity(tableName = "Other_Defs_And_Examples")
data class OtherDefsAndExam(
        @ColumnInfo(name = "Other_Defs")
        var OtherDefs : String? = "None !",

        @ColumnInfo(name = "Other_Examples")
        var Other_Exa :String? = "None !",

        var wordName : String
){
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L
}




data class EnglishWordWithSubSensnes(
        @Embedded
        var EnglishWord : EnglishWord,

        @Relation(
                parentColumn = "wordPrim",
                entityColumn = "wordName"
        )
        var otherDefAndExaS : List<OtherDefsAndExam>

)
