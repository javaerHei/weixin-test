<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="common/header.jsp" %>
    <title>demo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <style type="text/css">
    #ossfile b {
    	padding-left: 10px;
    }
    </style>
</head>

<body>
<div class="ch-container">
    <div class="row">
    	<a href="/oss/index">普通方式上传</a><br>
        <a href="/oss/js" >JavaScript客户端签名直传</a><br>
        <a href="/oss/server">服务端签名后直传</a>
    <div class="row">
        <div class="col-md-12 center login-header">
            <h2>OSS web直传demo（JavaScript客户端签名直传）</h2>
        </div>
        <!--/span-->
    </div><!--/row-->

    <div class="row">
        <div class="well col-md-5 center login-box">
            <form class="form-horizontal">
                <h4>您所选择的文件列表：</h4>
				<div id="ossfile">你的浏览器不支持flash,Silverlight或者HTML5！</div>
				<br/>
                 <div id="container" class="center col-md-5">
                     <a id="selectfiles" href="javascript:void(0);" class='btn btn-default'>选择文件</a>
					 <a id="postfiles" href="javascript:void(0);" class='btn btn-primary'>开始上传</a>
                 </div>
                  <br>
                 <pre id="console"></pre>

				 <p>&nbsp;</p>
            </form>
        </div>
        <!--/span-->
    </div><!--/row-->
</div><!--/fluid-row-->

</div><!--/.fluid-container-->

<!-- external javascript -->
<%@ include file="common/footer.jsp" %>
</body>
<script type="text/javascript" src="/js/crypto1/crypto/crypto.js"></script>
<script type="text/javascript" src="/js/crypto1/hmac/hmac.js"></script>
<script type="text/javascript" src="/js/crypto1/sha1/sha1.js"></script>
<script type="text/javascript" src="/js/base64.js"></script>
<script type="text/javascript" src="/js/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="/js/oss-upload1.js"></script>
</html>
