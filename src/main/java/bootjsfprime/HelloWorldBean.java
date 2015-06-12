package bootjsfprime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class HelloWorldBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldBean.class);

	private String hello;

	@Autowired
	public HelloService helloService;

	@PostConstruct
	public void init() {
		hello = helloService.fetchHello();
		LOGGER.debug("init(), hello={}", hello);
	}

	public String getTitle() {
		return "Spring Boot with JSF 2 and PrimeFaces!";
	}

	public String getWorld() {
		return "World!";
	}

	public String getHello() {
		return hello;
	}
	public void setHello(String hello) {
		this.hello = hello;
	}
}
