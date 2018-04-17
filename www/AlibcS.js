cordova.define("cordova-plugin-alibcs.AlibcS", function(require, exports, module) {
var exec = require('cordova/exec')
var alibcS = function () {}

alibcS.prototype.showDetail = function (arg, success, error, options) {
    var obj = Object.assign({openType:"taobao"},options)
    exec(success, error, "AlibcS", "detail", [arg,obj])
}
alibcS.prototype.showPage = function (arg, success, error, options) {
    var obj = Object.assign({ openType: "taobao" }, options)
    exec(success, error, "AlibcS", "page", [arg, obj])
}
alibcS.prototype.showShop = function (arg, success, error, options) {
    var obj = Object.assign({ openType: "taobao" }, options)
    exec(success, error, "AlibcS", "shop", [arg, obj])
}
module.exports = new alibcS()

});
