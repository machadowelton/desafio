package br.com.machadowelton.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidacaoException extends RuntimeException {
	
	private static final long serialVersionUID = 3115784230374704523L;
	
	public ValidacaoException() {
		super();
	}
	
	public ValidacaoException(String msg) {
		super(msg);
	}
	
	public ValidacaoException(Throwable thr) {
		super(thr);
	}
	
	public ValidacaoException(String msg, Throwable thr) {
		super(msg, thr);
	}

}
