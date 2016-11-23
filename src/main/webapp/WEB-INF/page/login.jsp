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
        	
            <div class="alert alert-${empty(messageType) ? 'info' : messageType }">
            	<c:if test="${empty(messageType)}">请输入用户名和密码.</c:if>
            	<c:if test="${not empty(messageType)}"> ${message }</c:if>
            </div>
            
            <form class="form-horizontal" action="${pageContext.request.contextPath }/login" method="post">
                <fieldset>
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
					
					<div class="input-group input-group-lg col-sm-8">
						<span class="input-group-addon"><i class="glyphicon glyphicon-lock red"></i></span>
                        <input type="text" name="validateCode" class="form-control" placeholder="验证码">
                    </div>
                    <span class="input-group input-group-lg col-sm-4" style="float: right;margin-top: -54px">
						 <img alt="验证码" src="/login-validateCode" class="validateCodeImg" onclick="$('.validateCodeRefresh').click();">&nbsp;
						 <a href="javascript:" class="validateCodeRefresh" onclick="$('.validateCodeImg').attr('src','/login-validateCode?'+new Date().getTime());">看不清</a>
                    </span>
                    
                    <div class="clearfix"></div>
                    
                    <div class="input-prepend">
                        <label class="remember" for="remember"><input type="checkbox" id="remember"> 记住密码</label>
                    </div>
                    
                    <div class="clearfix"></div>

                    <p class="center col-md-5">
                        <button type="submit" class="btn btn-primary">登录</button>
                    </p>
                </fieldset>
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
