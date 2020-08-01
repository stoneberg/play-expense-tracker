package com.expense.tracker.play.common.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Autowired
	private HttpServletRequest request;

	@Override
	public Optional<String> getCurrentAuditor() {
		HttpSession httpSession = request.getSession(false);
		String creator = "SYSTEM";
		if (httpSession != null) {
			creator = (String) httpSession.getAttribute("email");
		}
		return Optional.ofNullable(creator);
	}

}
