package com.example.EnglishDictionary

import com.squareup.moshi.Json


data class oxfordJSON(
        @Json(name = "id") val id: String?,
        @Json(name = "metadata") val metadata: Metadata?,
        @Json(name = "results") val results: List<Result>
)

data class Metadata(
        @Json(name = "operation") val operation: String?,
        @Json(name = "provider") val provider: String?,
        @Json(name = "schema") val schema: String?
)

data class Result(
        @Json(name = "id") val id: String?,
        @Json(name = "language") val language: String?,
        @Json(name = "lexicalEntries") val lexicalEntries: List<LexicalEntry?>?
)

data class LexicalEntry(
        @Json(name = "entries") val entries: List<Entry?>?,
        @Json(name = "lexicalCategory") val lexicalCategory : LexicalCategory?
)

data class Entry(
        @Json(name = "etymologies") val etymologies: List<String?>?,
        @Json(name = "grammaticalFeatures") val grammaticalFeatures: List<GrammaticalFeature?>?,
        @Json(name = "notes") val notes: List<Note?>?,
        @Json(name = "pronunciations") val pronunciations: List<Pronunciation?>?,
        @Json(name = "senses") val senses: List<Sense?>?

)

data class GrammaticalFeature(
        @Json(name = "id") val id: String?,
        @Json(name = "text") val text: String?,
        @Json(name = "type") val type: String?
)

data class Note(
        @Json(name = "text") val text: String?,
        @Json(name = "type") val type: String?
)

data class Pronunciation(
        @Json(name = "audioFile") val audioFile: String?,
        @Json(name = "dialects") val dialects: List<String?>?,
        @Json(name = "phoneticNotation") val phoneticNotation: String?,
        @Json(name = "phoneticSpelling") val phoneticSpelling: String?
)

data class Sense(
        @Json(name = "definitions") val definitions: List<String?>?,
        @Json(name = "examples") val examples: List<Example?>?,
        @Json(name = "id") val id: String?,
        @Json(name = "semanticClasses") val semanticClasses: List<SemanticClasse?>?,
        @Json(name = "shortDefinitions") val shortDefinitions: List<String?>?,
        @Json(name = "subsenses") val subsenses: List<Subsense?>?
)

data class Example(
        @Json(name = "text") val text: String?
)

data class SemanticClasse(
        @Json(name = "id") val id: String?,
        @Json(name = "text") val text: String?
)

data class Subsense(
        @Json(name = "definitions") val definitions: List<String?>?,
        @Json(name = "examples") val examples: List<ExampleX?>?,
        @Json(name = "id") val id: String?,
        @Json(name = "semanticClasses") val semanticClasses: List<SemanticClasseX?>?,
        @Json(name = "shortDefinitions") val shortDefinitions: List<String?>?,
        @Json(name = "synonyms") val synonyms: List<Synonym?>?,
        @Json(name = "thesaurusLinks") val thesaurusLinks: List<ThesaurusLink?>?
)

data class ExampleX(
        @Json(name = "text") val text: String?
)

data class SemanticClasseX(
        @Json(name = "id") val id: String?,
        @Json(name = "text") val text: String?
)

data class Synonym(
        @Json(name = "language") val language: String?,
        @Json(name = "text") val text: String?
)

data class ThesaurusLink(
        @Json(name = "entry_id") val entry_id: String?,
        @Json(name = "sense_id") val sense_id: String?
)

data class LexicalCategory(
        @Json(name = "id") val id: String?,
        @Json(name = "text") val text: String?
)