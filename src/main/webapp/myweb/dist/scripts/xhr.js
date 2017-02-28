define(function (require) {
    var hoss = require('hoss'),
        global = hoss.global,
        localStorage = hoss.localStorage,

        // data.status
        // '0'      失败--表示请求完成，但没有可用的数据
        // '1'      成功--表示请求完成，有可用的数据
        // '-99'    未登录--表示请求完成，但没有登录
        isFail = '0',
        isDone = '1',
        isNotLogin = '-99',

        jsonpost = {
            type: 'POST',
            dataType: 'json'
        },

        jsonp = {
            type: 'GET',
            dataType: 'jsonp'
        };

    var $ = require('jquery');

    function removeURLParameter(url, parameter) {
        //prefer to use l.search if you have a location/link object
        var urlParts = url.split('?');
        if (urlParts.length >= 2) {

            var prefix = encodeURIComponent(parameter) + '=';
            var pars = urlParts[1].split(/[&;]/g);

            //reverse iteration as may be destructive
            for (var i = pars.length; i-- > 0;) {
                //idiom for string.startsWith
                if (pars[i].lastIndexOf(prefix, 0) !== -1) {
                    pars.splice(i, 1);
                }
            }

            url = urlParts[0] + (pars.length > 0 ? '?' + pars.join('&') : "");
            return url;
        } else {
            return url;
        }
    }
    /**
     * 统一处理请求成功时接口返回的消息
     *    '-99'，未登录，转到登录页
     *    '1'，接口返回可用结果时，调用doneFn回调函数
     *    '0'，其它，调用failFn回调函数，并在控制台显示错误信息
     * @param {Object} data
     * @param {Function} doneFn 可选
     * @param {Function} failFn 可选
     */
    function done(data, doneFn, failFn) {
        if ($.isEmptyObject(data = data || {})) {
            fail.call(this, data, '返回的信息为空对象');
            return;
        }

        switch (data.status) {
            case isNotLogin:
                localStorage.clear();
                break;
            case isDone:
                $.isFunction(doneFn) && doneFn(data);
                break;
            // 无可用数据时，优选调用 failFn
            // 如果没有传 failFn 则调用 doneFn
            // 在无可用数据时，在控制台报错
            case isFail:
                if ($.isFunction(failFn)) {
                    failFn(data);
                } else {
                    if (currentPageUrl !== loginPageUrl) {
                        try {
                            $.isFunction(doneFn) && doneFn(data);
                        } catch (e) {
                        }
                    }
                }
                fail.call(this, data);
                break;
            default:
                fail.call(this, data);
                break;
        }

    }

    /**
     * 统一处理请求失败与请求成功后不可用的错误消息
     * @param {Object} jqXHR
     * @param {String} errorMsg 可选
     */
    function fail(jqXHR, errorMsg) {
        var context = this,
            newline = '\n',
            indentation = new Array(10).join(' '),
            property = [
                'message',
                'result',
                'statusText',
                'status',
                'detail',
                'url'
            ],
            errorInfo = [];

        if (errorMsg) {
            jqXHR[property[0]] = errorMsg;
            if (global.console) {
                global.console.error(errorMsg)
            }
        }

        $.each(property, function (i, n) {
            if (jqXHR[n] || context[n]) {
                errorInfo[errorInfo.length] =
                    newline + n + ' :' + newline +
                    indentation + (jqXHR[n] || context[n]);
            }
        });

        if (global.console) {
            global.console.log(errorInfo.join(''));
        }
    }

    function always() {
    }

    /**
     * 提交表单时，将无值的表单项过滤掉
     * @param $form $('#formId')
     * @returns {String}
     */
    function clearEmptyValue($form) {
        var dataArray = [];
        $.each($form.serializeArray(), function (i, n) {
            if (n.value !== '') {
                n.value = $.trim(n.value);
                dataArray.push(n);
            }
        });
        return $.param(dataArray);
    }


    /**
     * Url参数取值
     * @param {string} name 字段名
     * @param {string} url 网址
     * @returns {String} 字段值
     * @author wze_151215
     */
    function getQueryString(name, url) {
        var str = url || document.location.search || document.location.hash,
            result = null;

        if (!name || str === '') {
            return result;
        }
        result = str.match(
            new RegExp('(^|&|[\?#])' + name + '=([^&]*)(&|$)')
        );
        return result === null ? result : decodeURIComponent(result[2]);
    }


    /**
     * 对象序列化对象字符串
     * @param Obj 对象
     * @returns {string} 字符串对象
     * @author wze_151215
     */
    function objectToObjectStr(Obj) {
        var objStr = '';

        if (!$.isEmptyObject(Obj)) {
            objStr = JSON.stringify(Obj).replace(/:/g, '=')
        }
        return objStr;
    }

    function getStringLength(str) {
        if (!str) {
            return 0;
        }
        var bytesCount = 0;
        for (var i = 0; i < str.length; i++) {
            var c = str.charAt(i);
            if (/^[\u0000-\u00ff]$/.test(c)) {
                bytesCount += 1;
            }
            else {
                bytesCount += 2;
            }
        }
        return bytesCount;
    }


    return {
        isNotLogin: isNotLogin,
        jsonp: jsonp,
        jsonpost: jsonpost,

        done: done,
        fail: fail,
        always: always,

        clearEmptyValue: clearEmptyValue,
        getQueryString: getQueryString,
        objectToObjectStr: objectToObjectStr,
        getStringLength: getStringLength,
    };


});