package bootjsfprime;

import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
	@Override
	public String fetchHello() {
		return "Hello";
	}
}
