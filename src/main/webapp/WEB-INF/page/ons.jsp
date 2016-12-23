<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="common/header.jsp" %>
    <title>demo</title>
    <style type="text/css">
    .send-type input {margin-right: 10px}
    </style>
</head>

<body>
<div class="ch-container">
    <div class="row">
        
    <div class="row">
        <div class="col-md-12 center login-header">
            <h2>ONS 消息demo</h2>
        </div>
        <!--/span-->
    </div><!--/row-->

    <div class="row">
        <div class="well col-md-5 center login-box">
            <form class="form-horizontal">
                 <div class="input-group input-group-lg">
                     <span class="input-group-addon"><i class="glyphicon glyphicon-comment red"></i></span>
                     <input type="text" id="messageContent" name="message" class="form-control" placeholder="请输入消息内容">
                 </div>
                 <div class="clearfix"></div><br>
 				 <div class="send-type">
                     <label>发送方式：</label>
                     <input type="radio" name="sendType" id="sync" value="1" checked="checked"><label for="sync">同步发送</label>
                     <input type="radio" name="sendType" id="async" value="2"><label for="async">异步发送</label>
                     <input type="radio" name="sendType" id="oneway" value="3"><label for="oneway">单向发送</label>
                 </div>
                 
                 <div class="clearfix"></div><br>
                 
                 <div>
                 	<label>延迟时间(秒)：</label>
	                 <select id="delayTime" class="" >
	                    <c:forEach begin="0" end="30" step="2" var="time">
	                    	<option value="${time}">${time}</option>
	                    </c:forEach>
	                 </select>
                 </div>
                 
                 <div class="clearfix"></div><br>
                 <p class="center col-md-5">
                     <button type="button" id="sendMsgBtn" class="btn btn-primary">发送消息</button>
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
<script type="text/javascript">
	$(function() {
		$("#sendMsgBtn").click(function() {
			var messageContent = $("#messageContent").val();
			if($.trim(messageContent) == "") {
				alert("请输入发送的消息内容");
				return;
			}
			var delayTime = $("#delayTime").val();
			var url = "/message/send";
			if(delayTime > 0) {
				url = "/message/send-delay";
			}
			$.ajax({
				url : url,
				type : "post",
				data : {
					message : messageContent,
					sendType : $(":radio[name='sendType']:checked").val(),
					delayTimeSeconds : delayTime
				},
				dataType : "json",
				success : function(result) {
					if(result.success) {
						alert("发送成功！");
					}else {
						alert(result.errorMsg);
					}
				},
				error : function() {
					alert("系统异常！");
				}
			});
		});
	})
</script>
</html>
