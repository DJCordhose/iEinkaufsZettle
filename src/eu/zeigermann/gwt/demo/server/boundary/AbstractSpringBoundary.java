package eu.zeigermann.gwt.demo.server.boundary;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public abstract class AbstractSpringBoundary extends RemoteServiceServlet {

	@Override
	protected void onBeforeRequestDeserialized(String serializedRequest) {
		super.onBeforeRequestDeserialized(serializedRequest);
		inject();
	}
	
	protected <TBean> TBean getBean(Class<TBean> pClass) {
		WebApplicationContext applicationContext = getApplicationContext();
		return applicationContext.getBean(pClass);
	}

	protected WebApplicationContext getApplicationContext() {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		return applicationContext;
	}

	protected void inject() {
		WebApplicationContext applicationContext = getApplicationContext();

		applicationContext.getAutowireCapableBeanFactory().autowireBeanProperties(this,
				AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
	}

}
