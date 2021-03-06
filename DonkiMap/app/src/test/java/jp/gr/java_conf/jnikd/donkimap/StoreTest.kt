package jp.gr.java_conf.jnikd.donkimap

import jp.gr.java_conf.jnikd.donkimap.entity.StoreList
import org.junit.Assert
import org.junit.Test

class StoreTest {

    private val json = """{"list":[
                {
                "name": "中目黒店",
                "address": "東京都ほげほげ",
                "url": "http://123"
                },
                {
                "name": "川崎店",
                "address": "神奈川県ほげほげ",
                "url": "http://456"
                }
            ]}"""

    @Test
    fun convert_from_json() {
        val storeList: StoreList? = StoreList.adapter().fromJson(json)
        val list = storeList?.list

        val store0 = list?.get(0)
        Assert.assertEquals("中目黒店", store0?.name)
        Assert.assertEquals("東京都ほげほげ", store0?.address)
        Assert.assertEquals("http://123", store0?.url)

        val store1 = list?.get(1)
        Assert.assertEquals("川崎店", store1?.name)
    }
}
