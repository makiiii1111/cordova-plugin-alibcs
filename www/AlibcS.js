var exec = require('cordova/exec')
var alibcs = function () {}

alibcs.prototype.showDetail = function (arg, success, error, openType = "taobao") {
    exec(success, error, "AlibcS", "detail", [arg,openType])
}
alibcs.prototype.showPage = function (arg, success, error, openType = "taobao") {
    exec(success, error, "AlibcS", "page", [arg, openType])
}
alibcs.prototype.showShop = function (arg, success, error, openType = "taobao") {
    exec(success, error, "AlibcS", "shop", [arg, openType])
}
var bc = new alibcs()
module.exports = bc
