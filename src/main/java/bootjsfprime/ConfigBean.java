package bootjsfprime;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
@ApplicationScoped
public class ConfigBean {
	public String getEnvironment() {
		return FacesContext.getCurrentInstance().getExternalContext().getInitParameter("environment");
	}
}
