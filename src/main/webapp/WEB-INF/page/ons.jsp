<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
     <%@ include file="common/header.jsp" %>
     <title>ONS demo</title>
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
			            <a href="/">Page</a>
			        </li>
			    </ul>
			</div>
			

		<!--/row-->
    		<!-- content ends -->
    	</div><!--/#content.col-md-0-->
	</div><!--/fluid-row-->

    <hr>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3>Settings</h3>
                </div>
                <div class="modal-body">
                    <p>Here settings can be configured...</p>
                </div>
                <div class="modal-footer">
                    <a href="#" class="btn btn-default" data-dismiss="modal">Close</a>
                    <a href="#" class="btn btn-primary" data-dismiss="modal">Save changes</a>
                </div>
            </div>
        </div>
    </div>
</div><!--/.fluid-container-->

<!-- external javascript -->
<%@ include file="common/footer.jsp" %>

</body>
</html>
