<%--
  User: zz
  Date: 15-1-10 下午4:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Standard Meta -->
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

    <!-- Site Properities -->
    <title>Example - Semantic</title>
    <script src="static/avalon/avalon.modern.js" data-main="static/main.js"></script>
    <style type="text/css">
        #top-menu > .container > .left.menu {
            margin-top: 11px;
        }
        #top-menu .logo {
            margin: 0;
        }
        #top-menu .ui.item > .dropdown.icon {
            margin-top: 11px;
            margin-left: 5px;
        }
        #feed > .pusher > .ui {
            margin-top: 65px;
            box-shadow: none;
        }
        #feed > .pusher {
            height: initial;
        }

        #feed > .pusher > .footer {
            clear: both;
        }
        #feed > .pusher > .footer {
            margin: 0;
            border-radius: 0;
            padding: 4em 0 5em;
        }

    </style>
</head>
<body id="feed" ms-controller="app">
<!-- 左侧菜单 -->
<div class="ui left vertical labeled icon sidebar menu">
    <a ms-repeat="menu" ms-click="to(el, $event)" ms-href="'#!'+el.url" class="item" ms-class="active: active==el.id"><i ms-class="{{el.iconClass}} icon"></i> {{el.name}} </a>
</div>
<!-- 顶部菜单 -->
<div id="top-menu" class="ui large top fixed inverted menu">
    <div class="container">
        <a class="left logo item" href="#!/">
            <img src="static/app/logo.png" height="40px">
        </a>
        <div class="left menu">
            <a class="item launch button">
                <i class="content icon"></i> 菜单
            </a>
            <a class="item">
                <i class="mail icon"></i> Messages
            </a>
        </div>
        <div class="right menu">
            <div class="ui pointing dropdown link item">
                <i class="dropdown icon"></i>
                <img class="ui avatar image" src="static/app/logo.png" height="45px">
                <div class="menu">
                    <div class="header">个人设置</div>
                    <a class="item" href="/logout">
                        <i class="user icon"></i> 个人信息
                    </a>
                    <div class="divider"></div>
                    <div class="header">会话</div>
                    <a class="item" href="/logout">
                        <i class="sign out icon"></i> 退出
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 正文 -->
<div class="pusher">
    <div class="ui segment">
        <div class="container" ms-view="container"></div>
    </div>
</div>
</body>
</html>