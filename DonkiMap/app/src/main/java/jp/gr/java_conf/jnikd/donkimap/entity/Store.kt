package jp.gr.java_conf.jnikd.donkimap.entity

data class StoreList(
        val list: List<Store>
)

data class Store(
        val name: String,
        val address: String,
        val url: String
)