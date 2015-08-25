package com.mopote.ios.meta.controller;

import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-mvc.xml",
		"classpath:spring/spring-core.xml" })
public class TestActionBase {
	@Autowired
	public RequestMappingHandlerAdapter handlerAdapter;

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	HandlerMethod handlerMethod = null;

	@Before
	public void before() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		response = new MockHttpServletResponse();
	}

	@SuppressWarnings("unused")
	private void example() {
		request.setRequestURI("/test");
		request.setMethod("get");
		// invoke(appCategoryController, "test");
	}

	public void invoke(Object bean, String methodName,
			Class<?>... parameterTypes) {
		try {
			handlerMethod = new HandlerMethod(bean, methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

	}

	@After
	public void after() throws UnsupportedEncodingException {
		try {
			handlerAdapter.handle(request, response, handlerMethod);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = response.getContentAsString();
		System.out.println(result);
	}

}
