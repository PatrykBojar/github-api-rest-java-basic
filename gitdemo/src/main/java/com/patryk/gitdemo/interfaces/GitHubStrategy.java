package com.patryk.gitdemo.interfaces;

import java.util.List;

import com.patryk.gitdemo.model.Organization;
import com.patryk.gitdemo.model.Repository;

public interface GitHubStrategy {
	List<Repository> getRepositories(String orgName);

	List<Organization> getOrganizations();

	int getBiggestRepositorySize(String orgName);
}
