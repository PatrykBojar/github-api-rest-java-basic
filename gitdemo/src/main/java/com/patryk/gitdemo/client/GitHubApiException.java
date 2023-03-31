package com.patryk.gitdemo.client;

import java.io.IOException;

public class GitHubApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GitHubApiException(String message, IOException cause) {
		super(message, cause);
	}

	public GitHubApiException(String message) {
		super(message);
	}
}
