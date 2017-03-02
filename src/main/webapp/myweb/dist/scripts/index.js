define(function(require) {
    var $ = require('jquery');
    var hoss = require('hoss'),
    URLS = hoss.URLS;
    var layer = require('layer');
    
    var xhr = require('xhr'),
    jsonp = xhr.jsonp,
    jsonpost = xhr.jsonpost,
    doneCallback = xhr.done;
    
    var Vue = require('vue');
    
    var nav = require('scripts/nav');

    $(document).ready(function() {

    	
    	 new Vue({
             el: '#didi-navigator',
             data: {
            	 username:"shaw",
                 tabs: [
                     { text: '巴士',imgurl:"../dist/style/images/s.jpg" },
                     { text: '快车' ,imgurl:"../dist/style/images/s1.jpg"},
                     { text: '专车' ,imgurl:"../dist/style/images/s2.jpg"},
                     { text: '顺风车' ,imgurl:"../dist/style/images/s3.jpg"},
                     { text: '出租车' ,imgurl:"../dist/style/images/s4.jpg"},
                     { text: '代驾' ,imgurl:"../dist/style/images/s5.jpg"}
                 ]
             }
         })
    });
});