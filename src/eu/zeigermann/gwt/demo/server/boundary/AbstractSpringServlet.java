package eu.zeigermann.gwt.demo.server.boundary;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings("serial")
public abstract class AbstractSpringServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		super.init();
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

		applicationContext.getAutowireCapableBeanFactory()
				.autowireBeanProperties(this,
						AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
	}

}
