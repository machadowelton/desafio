package br.com.machadowelton.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ErroInternoException extends RuntimeException {

	private static final long serialVersionUID = -5554899028537336673L;
	
	public ErroInternoException() {
		super();
	}
	
	public ErroInternoException(String msg) {
		super(msg);
	}
	
	public ErroInternoException(Throwable thr) {
		super(thr);
	}
	
	public ErroInternoException(String msg, Throwable thr) {
		super(msg, thr);
	}

}
