package com.patryk.gitdemo.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.patryk.gitdemo.model.Organization;
import com.patryk.gitdemo.model.Repository;

@SpringBootTest(classes = GitHubApiClientTest.class)
class GitHubApiClientTest {

	@Test
	@DisplayName("Should test that the method getRepositories returns a non-null list of repositories for a given organization name.")
	void testIfGetRepositoriesReturnsNonNull() {
		GitHubApiClient githubApiClient = new GitHubApiClient();
		String orgName = "collectiveidea";
		List<Repository> repositories = githubApiClient.getRepositories(orgName);
		assertNotNull(repositories);
	}

	@Test
	@DisplayName("Should test that the method getOrganizations returns a non-null list of organizations.")
	void testIfGetOrganizationsReturnsNonNull() {
		GitHubApiClient githubApiClient = new GitHubApiClient();
		List<Organization> organizations = githubApiClient.getOrganizations();
		assertNotNull(organizations);
	}
	
	@Test
	@DisplayName("Should test that the method getBiggestRepositorySize returns the correct size in bytes for the biggest repository in a given organization.")
	void testGetBiggestRepositorySize() {
		GitHubApiClient githubApiClient = new GitHubApiClient();

		String orgName = "collectiveidea";

		// This is the expected size in bytes of the biggest repository in the
		// "collectiveidea" organization.
		int expectedSize = 1466368;

		int actualSize = githubApiClient.getBiggestRepositorySize(orgName);

		assertEquals(expectedSize, actualSize);
	}

}
