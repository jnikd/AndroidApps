#!/bin/bash

# 前回のファイルを削除
`rm app/src/main/assets/tenpo.json`

while read line
do
  # 店名
  if echo $line | grep '店' | grep 'h4' ; then
    name=`echo $line | sed "s/<\/h4>//" | sed "s/ //"`
    echo '[\n  "name" : "'$name'",' >> app/src/main/assets/tenpo.json
  fi
  # 住所
  # TODO 住所2つめのスペース以後を削除
  if echo $line | grep '〒' ; then
    address=`echo $line | sed "s/<dd>//" | sed "s/<\/dd>//"`
    echo '  "address" : "'$address'",' >> app/src/main/assets/tenpo.json
  fi
  # URL
  if echo $line | grep 'menu_detail' ; then
    url=`echo $line | sed "s/<li><a href=\"//" | sed "s/\" class=\"btn\">//" | sed "s/amp;//g"`
    echo '  "url" : "http://www.donki.com/store/'$url'"\n],\n' >> app/src/main/assets/tenpo.json
  fi
done < tenpo.html
