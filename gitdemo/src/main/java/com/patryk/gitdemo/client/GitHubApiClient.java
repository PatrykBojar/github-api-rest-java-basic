package com.patryk.gitdemo.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patryk.gitdemo.interfaces.GitHubStrategy;
import com.patryk.gitdemo.model.Organization;
import com.patryk.gitdemo.model.Repository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GitHubApiClient implements GitHubStrategy {
	private static final String API_BASE_URL = "https://api.github.com";
	private static final String GITHUB_TOKEN = System.getenv("GITHUB_TOKEN"); // Your private GitHub Token
	private static final int BYTES_IN_KB = 1024;

	private final OkHttpClient httpClient;
	private final ObjectMapper objectMapper;

	public GitHubApiClient() {
		this.httpClient = new OkHttpClient();
		this.objectMapper = new ObjectMapper();
	}

	/**
	 * Returns a List of all Repositories within an specific Organization on GitHub.
	 * 
	 * @param orgName the name of the GitHub organization
	 * @return a List of all the repositories within an organization
	 * @throws GitHubApiException a exception is thrown when the request fails or
	 *                            it's in the wrong data format
	 */
	@Override
	public List<Repository> getRepositories(String orgName) throws GitHubApiException {
		String apiUrl = String.format("%s/orgs/%s/repos", API_BASE_URL, orgName);
		Request request = new Request.Builder().url(apiUrl).header("Authorization", "Bearer " + GITHUB_TOKEN).build();

		try (Response response = httpClient.newCall(request).execute()) {

			// Checks if the response of the call is successful
			if (!response.isSuccessful()) {
				throw new GitHubApiException(
						String.format("GitHub API request failed: %d %s", response.code(), response.message()));
			}

			// Checks if the root node of JSON is in Array format
			JsonNode rootNode = objectMapper.readTree(response.body().byteStream());
			if (!rootNode.isArray()) {
				throw new GitHubApiException("Unexpected response format: not an array");
			}

			List<Repository> repositories = new ArrayList<>();
			for (JsonNode node : rootNode) {
				Repository repository = objectMapper.treeToValue(node, Repository.class);
				repositories.add(repository);
			}

			return repositories;

		} catch (IOException e) {
			throw new GitHubApiException("API request failed: " + e.getMessage(), e);
		}
	}

	/**
	 * Returns a List of all Organizations on GitHub.
	 * 
	 * @param orgName the name of the GitHub organization
	 * @return a List of all the organizations
	 * @throws GitHubApiException a exception is thrown when the request fails or
	 *                            it's in the wrong data format
	 */
	@Override
	public List<Organization> getOrganizations() throws GitHubApiException {
		String apiUrl = String.format("%s/organizations", API_BASE_URL);
		Request request = new Request.Builder().url(apiUrl).header("Authorization", "Bearer " + GITHUB_TOKEN).build();

		try (Response response = httpClient.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new GitHubApiException(
						String.format("API request failed: %d %s", response.code(), response.message()));
			}

			JsonNode rootNode = objectMapper.readTree(response.body().byteStream());
			if (!rootNode.isArray()) {
				throw new GitHubApiException("Unexpected response format: not an array");
			}

			List<Organization> organizations = new ArrayList<>();

			for (JsonNode node : rootNode) {
				Organization organization = objectMapper.treeToValue(node, Organization.class);
				organizations.add(organization);
			}

			return organizations;

		} catch (IOException e) {
			throw new GitHubApiException("API request failed: " + e.getMessage(), e);
		}
	}

	/**
	 * Returns the size of the biggest Repository within the specific Organization
	 * on GitHub, in Bytes.
	 * 
	 * @param orgName the name of the GitHub Organization
	 * @return size in bytes of the biggest repository within an Organization
	 */
	@Override
	public int getBiggestRepositorySize(String orgName) {

		List<Repository> repositories = getRepositories(orgName);

		// Finds the repository with largest size
		Repository biggestRepo = null;
		for (Repository repository : repositories) {
			if (biggestRepo == null || repository.getSize() > biggestRepo.getSize()) {
				biggestRepo = repository;
			}
		}

		// Returns the size of biggest repository (in bytes)
		if (biggestRepo != null) {
			return biggestRepo.getSize() * BYTES_IN_KB;
		} else {
			return 0;
		}
	}
}