package jp.gr.java_conf.jnikd.donkimap.entity

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

data class StoreList(
        val list: List<Store>
) {
    companion object {
        fun adapter(): JsonAdapter<StoreList> {
            return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    .adapter(StoreList::class.java)
        }
    }
}

data class Store(
        val name: String,
        val address: String,
        val url: String
)