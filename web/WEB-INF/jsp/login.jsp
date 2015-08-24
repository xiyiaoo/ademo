<%--
  User: zz
  Date: 15-1-10 下午4:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <meta charset="utf-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

      <title>登录</title>
<%--
      <link href='http://fonts.useso.com/css?family=Source+Sans+Pro:400,700|Open+Sans:300italic,400,300,700' rel='stylesheet' type='text/css'>
--%>
      <link rel="stylesheet" type="text/css" href="static/semantic/semantic.css">
      <style type="text/css">
          #login {
              margin: 0;
              padding: 0;
              height: 100%;
              background: url(static/app/background.jpg) 50% 50% / cover no-repeat fixed;
          }
          #login > .container {
              position: absolute;
              top: 50%;
              left: 50%;
              transform: translate(-50%, -50%);
              -webkit-transform: translate(-50%, -50%);
              -moz-transform: translate(-50%, -50%);
              min-width: 600px;
          }
          #login > .container > .ui {
              border: 0;
          }
      </style>
  </head>
  <body id="login">
  <div class="container">
      <div class="ui top attached segment">
          <a class="ui top black large attached label">请登录</a>
          <div class="ui two column middle aligned relaxed fitted stackable grid">
              <div class="column">
                  <form class="ui ${hasError ? "error" : ""} form segment" method="post">
                      <div class="ui error message">
                          <div class="header">登录失败</div>
                          <p>用户名或密码错误</p>
                      </div>
                      <div class="field">
                          <label for="username">用户</label>
                          <div class="ui left icon input">
                              <input id="username" name="username" type="text" placeholder="Username" required>
                              <i class="user icon"></i>
                          </div>
                      </div>
                      <div class="field">
                          <label for="password">密码</label>
                          <div class="ui left icon input">
                              <input id="password" name="password" type="password" required>
                              <i class="lock icon"></i>
                          </div>
                      </div>
                      <div class="field inline">
                          <div class="ui toggle checkbox">
                              <input id="rememberMe" name="rememberMe" type="checkbox">
                              <label for="rememberMe">记住我</label>
                          </div>
                      </div>
                      <button class="ui block blue fluid button" type="submit">
                          <i class="sign in icon"></i> 登录
                      </button>
                  </form>
              </div>
              <div class="ui vertical divider">
                  Or
              </div>
              <div class="center aligned column">
                  <div class="huge green ui labeled icon button">
                      <i class="signup icon"></i> 注册
                  </div>
              </div>
          </div>
      </div>
  </div>
  </body>
</html>