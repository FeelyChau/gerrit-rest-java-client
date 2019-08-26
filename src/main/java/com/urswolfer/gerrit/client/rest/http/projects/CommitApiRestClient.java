package com.urswolfer.gerrit.client.rest.http.projects;

import com.google.common.collect.Iterables;
import com.google.gerrit.extensions.api.projects.CommitApi;
import com.google.gerrit.extensions.common.CommitInfo;
import com.google.gerrit.extensions.restapi.RestApiException;
import com.google.gson.JsonElement;
import com.urswolfer.gerrit.client.rest.http.GerritRestClient;
import com.urswolfer.gerrit.client.rest.http.changes.CommitInfoParser;

public class CommitApiRestClient extends CommitApi.NotImplemented implements CommitApi {
    private final GerritRestClient gerritRestClient;
    private final CommitInfoParser commitInfoParser;
    private final ProjectApiRestClient projectApiRestClient;
    private final String name;

    public CommitApiRestClient(GerritRestClient gerritRestClient,
                               CommitInfoParser commitInfoParser,
                               ProjectApiRestClient projectApiRestClient,
                               String name) {
        this.gerritRestClient = gerritRestClient;
        this.commitInfoParser = commitInfoParser;
        this.projectApiRestClient = projectApiRestClient;
        this.name = name;
    }
    public CommitInfo get() throws RestApiException {
        JsonElement jsonElement = gerritRestClient.getRequest(commitUrl());
        return Iterables.getOnlyElement(commitInfoParser.parseCommitInfos(jsonElement));
    }
    protected String commitUrl() {
        return projectApiRestClient.projectsUrl() + "/commits/" + name;
    }
}
