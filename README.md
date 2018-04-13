# cordova-plugin-alibcs
a plugin integrated AlibcSDK
# Useage
1. 从阿里百川官网获取自己app的安全图片，pid，appkey
2. Add plugin to project <br/>
```cordova plugin add https://github.com/makiiii1111/cordova-plugin-alibcs --variable PID=m_xxx_xxx_xxx --variable APPKEY = xxxxxx```
3. 打开url
```window.alibcS.showPage(url,successCb,errorCb)```
4. 打开商品详情
```window.alibcS.showDetail(num_iid,cb,cb)```
5. 打开店铺
```window.alibcS.showShop(shop_id,cb,cb)```
