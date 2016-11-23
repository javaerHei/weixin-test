package com.example.web.exception.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.common.BusinessException;
import com.example.common.Constants;
import com.example.common.JsonUtils;
import com.example.common.NoPermissionsException;
import com.example.common.SessionTimeoutException;

@Component
public class SystemExceptionHandler implements HandlerExceptionResolver {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg2,
			Exception ex) {
		final boolean isAjaxRequest = this.isAjaxRequest(request);
		final boolean isBusinessException = ex instanceof BusinessException;
		String errorMessage = isBusinessException ? ex.getMessage() : ExceptionUtils.getStackTrace(ex);
		logger.error(String.format("触发请求:[%s]时系统出现异常，异常类型：%s，异常信息：%s", request.getRequestURI(),
				isBusinessException ? "业务异常" : "系统异常", errorMessage));

		// 登录超时异常处理
		if (ex instanceof SessionTimeoutException) {
			if (isAjaxRequest) {
				throw (SessionTimeoutException) ex;
			}
			return new ModelAndView(new RedirectView(""));
		} else if (ex instanceof NoPermissionsException) {
			return new ModelAndView(new RedirectView("no_permissions_error"));
		} else {
			if (!isBusinessException) {
				ex.printStackTrace();
			}
			logger.info(ExceptionUtils.getMessage(ex));

			String errorMsg = isBusinessException ? ex.getMessage() : Constants.SYSTEM_ERROR_MSG;
			String errorCode = isBusinessException ? ((BusinessException) ex).getErrorCode() : null;
			String result = JsonUtils.getFailure(errorMsg, errorCode);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("exception", result);
			if (isAjaxRequest) {
				return new ModelAndView("error/ajax_error", model);
			}
			return new ModelAndView("redirect:/error/500");
		}

	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		return request.getHeader("x-requested-with") != null;
	}
}
