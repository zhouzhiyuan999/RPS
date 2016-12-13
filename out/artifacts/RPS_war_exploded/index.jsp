<%--
  Created by IntelliJ IDEA.
  User: ThinkPad
  Date: 2016/12/7
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>

  <div class="form-bottom">
    <form role="form" action="" method="post" class="login-form">
      <div class="form-group">
        <label class="sr-only" for="username">Username</label>
        <input type="text" name="username" placeholder="Username..." class="form-username form-control" id="username">
      </div>
      <div class="form-group">
        <label class="sr-only" for="password">Password</label>
        <input type="password" name="password" placeholder="Password..." class="form-password form-control" id="password">
      </div>
      <button type="submit" class="btn">Sign in!</button>
    </form>
  </div>

  <script src="js/jquery.js"></script>
  <!-- 包括所有已编译的插件 -->
  <script src="js/bootstrap.min.js"></script>
  </body>
</html>
