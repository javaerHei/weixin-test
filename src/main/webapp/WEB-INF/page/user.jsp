<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
     <%@ include file="common/header.jsp" %>
     <title>空页面</title>
</head>

<body>
<!-- 头部导航 -->
<%@ include file="common/topbar.jsp" %>
<div class="ch-container">
    <div class="row">
        <!-- left menu starts -->
       <%@ include file="common/left-menu.jsp" %>
        <!-- left menu ends -->

        <noscript>
            <div class="alert alert-block col-md-12">
                <h4 class="alert-heading">Warning!</h4>

                <p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a>
                    enabled to use this site.</p>
            </div>
        </noscript>

        <div id="content" class="col-lg-10 col-sm-10">
            <!-- content starts -->
            <div>
			    <ul class="breadcrumb">
			        <li>
			            <a href="/index">Home</a>
			        </li>
			        <li>
			            <a href="/user">User</a>
			        </li>
			    </ul>
			</div>
			<div class="row">
			    <div class="box col-md-12">
			        <div class="box-inner">
			            <div class="box-header well">
			                <h2><i class="glyphicon glyphicon-info-sign"></i> 用户</h2>
			
			                <div class="box-icon">
			                    <a href="#" class="btn btn-setting btn-round btn-default"><i
			                            class="glyphicon glyphicon-cog"></i></a>
			                    <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                            class="glyphicon glyphicon-chevron-up"></i></a>
			                    <a href="#" class="btn btn-close btn-round btn-default"><i
			                            class="glyphicon glyphicon-remove"></i></a>
			                </div>
			            </div>
			            <div class="box-content row">
			                <div class="col-lg-7 col-md-12">
			                     <h2>权限列表</h2>
								    <shiro:authenticated>用户已经登录显示此内容<br/></shiro:authenticated><br/>
								    <shiro:hasRole name="manager">manager角色登录显示此内容<br/></shiro:hasRole>
								    <shiro:hasRole name="admin">admin角色登录显示此内容<br/></shiro:hasRole>
								    <shiro:hasRole name="normal">normal角色登录显示此内容<br/></shiro:hasRole><br/>
								
								    <shiro:hasAnyRoles name="manager,admin">manager or admin 角色用户登录显示此内容<br/></shiro:hasAnyRoles><br/>
								    <shiro:principal property="username"/>-显示当前登录用户名<br/><br/>
								    <shiro:hasPermission name="add">add权限用户显示此内容<br/></shiro:hasPermission>
								    <shiro:hasPermission name="user:query">user:query权限用户显示此内容<br/></shiro:hasPermission>
								    <shiro:lacksPermission name="user:query">不具有user:query权限的用户显示此内容 <br/></shiro:lacksPermission>
								
								    <br/>所有用户列表：<br/>
								    <ul>
								        <c:forEach items="${userList }" var="user">
								            <li>用户名：${user.username }----密码：${user.password }----<a href="${pageContext.request.contextPath }/user/edit/${user.id}">修改用户（测试根据不同用户可访问权限不同，本例tom无权限，jack有权限）</a></li>
								        </c:forEach>
								    </ul>
			                </div>
			
			            </div>
			        </div>
			    </div>
			</div><!--/row-->
    		<!-- content ends -->
    	</div><!--/#content.col-md-0-->
	</div><!--/fluid-row-->
    
</div><!--/.fluid-container-->

<!-- external javascript -->
<%@ include file="common/footer.jsp" %>

</body>
</html>
