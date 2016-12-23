<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="common/header.jsp" %>
    <title>登录</title>
</head>

<body>
<div class="ch-container">
    <div class="row">
        
    <div class="row">
        <div class="col-md-12 center login-header">
            <h2>Welcome to Charisma</h2>
        </div>
        <!--/span-->
    </div><!--/row-->

    <div class="row">
        <div class="well col-md-5 center login-box">
            
            <form class="form-horizontal">
                 <div class="input-group input-group-lg">
                     <span class="input-group-addon"><i class="glyphicon glyphicon-user red"></i></span>
                     <input type="text" name="username" class="form-control" placeholder="用户名">
                 </div>
                 <div class="clearfix"></div><br>

                 <div class="input-group input-group-lg">
                     <span class="input-group-addon"><i class="glyphicon glyphicon-lock red"></i></span>
                     <input type="password" name="password" class="form-control" placeholder="密码">
                 </div>
                 <div class="clearfix"></div><br>
                 

                 <p class="center col-md-5">
                     <button type="submit" class="btn btn-primary">登录</button>
                 </p>
            </form>
        </div>
        <!--/span-->
    </div><!--/row-->
</div><!--/fluid-row-->

</div><!--/.fluid-container-->

<!-- external javascript -->
<%@ include file="common/footer.jsp" %>


</body>
</html>
