package com.patryk.gitdemo.application;

import com.patryk.gitdemo.client.GitHubApiClient;

public class GitHubApplication {

	public static void main(String[] args) throws Exception {
		GitHubApiClient github = new GitHubApiClient();

		// Given an organization return the number of repositories.
		String orgName = "collectiveidea";

		int numOfRepos = github.getRepositories(orgName).size();
		System.out.println("Number of repositories in organization " + orgName + ": " + numOfRepos);

		// Given an organization return the biggest repository (in bytes).
		int biggestRepoSize = github.getBiggestRepositorySize(orgName);
		System.out.println(
				"Size of the biggest repository in organization " + orgName + ": " + biggestRepoSize + " bytes");

		// Return the number of organizations that are currently on Github.
		int numOfOrgs = github.getOrganizations().size();
		System.out.println("Number of organizations on Github: " + numOfOrgs);
	}
}
