define(function(require) {
    var $ = require('jquery');
    var hoss = require('hoss'),
    URLS = hoss.URLS;
    var layer = require('layer');
    var xhr = require('xhr'),
    jsonp = xhr.jsonp,
    jsonpost = xhr.jsonpost,
    doneCallback = xhr.done;
    var sha1 = require('scripts/sha1');

    $(document).ready(function() {
        var regAccount = /^.{1,}$/;
        var regPassword = /^.{6,}$/;
        var regCaptcha = /^.{4}$/;
        var $loginForm = $('#loginForm');
        var $username = $('#username');
        var $remmberMe = $('#remmberMe');
        var $updateCaptcha = $('#updateCaptcha');
        var $password = $('#password');
        var $captcha = $('#captcha');
        var $submit = $loginForm.find('[type=submit]');
        var $errorMsg = $('.error-message');
        $captchaImg = $updateCaptcha.find('img'),
        $captcha = $('#captcha');
        var invisible = {
            visibility: 'hidden'
        };
        var visible = {
            visibility: 'visible'
        };
        var showMsg = function(msg) {
            $errorMsg.html(msg).css(visible);
        };
        $captchaImg.attr('src', URLS.common.C1001 + "?t=" + new Date().getTime()).css(visible);
        $updateCaptcha.on('click',
        function() {
            $captchaImg.attr('src', $captchaImg.attr('src').split('?').shift() + '?' + $.now());
            $captcha.val('').addClass('error').focus();
            return false;
        });

        // 修改后正确后删除提醒
        $username.on('input',
        function() {
            if (regAccount.test($.trim($username.val()))) {
                $username.removeClass('error').focus();
                $errorMsg.css(invisible);
            }
        });

        $password.on('input',
        function() {
            if (regPassword.test($.trim($password.val()))) {
                $password.removeClass('error').focus();
                $errorMsg.css(invisible);
            }
        });

        $captcha.on('input',
        function() {
            if (regCaptcha.test($.trim($captcha.val()))) {
                $captcha.removeClass('error').focus();
                $errorMsg.css(invisible);
            }
        });

        function getCookie(name) {
            var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
            if (arr = document.cookie.match(reg)) {
                return arr[2];
            } else {
                return null;
            }
        }
        //超过5次显示验证码
        function captchaShow() {
            var captchaTime = getCookie('_capt');
            if (captchaTime > 5) {
                $captcha.parent().show();
            }
        }
        checkRemmberMe();
        function checkRemmberMe(){
        	var memberName = getCookie('_mp');
        	if(memberName!=null){
        		$remmberMe.attr('checked','checked');
        		$username.val(memberName);
        	}
        }

        function setCookie(name, value) {
            var Days = 30;
            var exp = new Date();
            exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
            var domain = webHost.indexOf("com") < 0 ? 'localhost' : 'shopcmd.com';
            document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=/;domain=" + domain;
        }

        // 提交前验证
        $loginForm.submit(function(e) {
            var $context = $(this),
            $disabled = $context.find('[disabled]'),
            $submit = $("#submit");
            if ($submit.hasClass('disabled')) {
                return false;
            }

            $disabled.removeAttr('disabled');
            var pwd = $password.val();
            $password.val(sha1.hex_sha1(pwd));
            var serializeString = $(this).serialize();
            if ($remmberMe.is(':checked')) {
                serializeString += '&rememberMe=on';
            }
            $password.val(pwd);
            $.ajax($.extend({
                url: URLS.passport.P1001,
                data: serializeString,
                beforeSend: function() {
                    $submit.val('登录中...').addClass('disabled');
                }
            },
            jsonpost)).done(function(data) {
                function useful(data) {
                	console.log(data);
                	var results = data.data || {};
                    setCookie('ckUserName', results.memberName);
                    localStorage.setItem("member", results);
                }
                function useless(data) {
                    //判断验证码是否显示
                    captchaShow();
                    //showMsg(data.detail);
                }
                doneCallback.call(this, data, useful, useless);
            }).fail(function(data) {
                showMsg('网络繁忙，请稍后重试！');
            }).always(function() {
                $updateCaptcha.click();
                $submit.val('登  录').removeClass('disabled');
            });
            return false;
        });
        

        $("#submit").on('click',
        function(e) {
            e.preventDefault();
            if (!regAccount.test($.trim($username.val()))) {
                showMsg('请输入您的账号');
                $username.addClass('error').focus();
                return false;
            }
            if (!regPassword.test($.trim($password.val()))) {
                showMsg('密码不能小于六位且不能为空格');
                $password.addClass('error').focus();
                return false;
            }
            $loginForm.submit();
        })
    });
});