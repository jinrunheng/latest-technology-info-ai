package com.github.article_crawler;

import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/10 下午12:21
 * @Version 1.0
 * @Desc 爬取阮一峰科技爱好者周刊文章（每周五发布）；link：https://github.com/ruanyf/weekly
 */
@Slf4j
public class RyfArticleCrawler {
    private static final String repoName = "ruanyf/weekly";

    public static void crawler(String repo) throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();
        GHRepository repository = gitHub.getRepository(repo);
        final PagedIterable<GHCommit> commits = repository.listCommits();
        final List<GHCommit> ghCommits = commits.asList();
        // 按照日期进行排序
        ghCommits.stream().sorted((c1, c2) -> {
            try {
                return c1.getCommitDate().compareTo(c2.getCommitDate());
            } catch (IOException exception) {
                log.error("error", exception);
            }
            return 0;
        });
        GHCommit recentArticleCommit = null;
        // 获取最新的文章提交 commit
        for (GHCommit commit : ghCommits) {
            if (commit.getCommitShortInfo().getMessage().startsWith("docs: release issue")) {
                recentArticleCommit = commit;
                break;
            }
        }
        log.info("recentArticleCommit", recentArticleCommit);
        recentArticleCommit.getFiles().forEach(file -> {
            final URL blobUrl = file.getBlobUrl();
            log.info("blobUrl", blobUrl);
        });
    }

    public static void main(String[] args) {
        try {
            crawler(repoName);
        } catch (IOException exception) {
            log.error("error", exception);
        }
    }
}
