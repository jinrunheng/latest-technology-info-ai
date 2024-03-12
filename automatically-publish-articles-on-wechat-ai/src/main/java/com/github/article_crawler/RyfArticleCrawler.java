package com.github.article_crawler;

import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @Author Dooby Kim
 * @Date 2024/3/10 下午12:21
 * @Version 1.0
 * @Desc 爬取阮一峰科技爱好者周刊文章（每周五发布）；link：https://github.com/ruanyf/weekly
 */
@Slf4j
public class RyfArticleCrawler {
    private static String login;
    private static String token;
    private static String keyPath = "src/test/resources/github.properties";

    private static void initKey() {
        Properties prop = new Properties();

        try (FileInputStream input = new FileInputStream(keyPath)) {
            // 从输入流加载属性列表
            prop.load(input);
            // 获取属性
            login = prop.getProperty("login");
            token = prop.getProperty("token");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static final String repoName = "ruanyf/weekly";

    public static void crawler(String repo) throws IOException {
        initKey();
        final GitHub gitHub = GitHub.connect(login, token);

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
            if (file.getFileName().startsWith("docs/issue")) {
                final String patch = file.getPatch();
                // 去除掉所有的 "+" 字符
                final String originalText = patch.replace("+", "").trim();
                StringBuilder oneLineBuilder = new StringBuilder();
                for (char c : originalText.toCharArray()) {
                    if (c != '\n') { // 如果不是换行符，则追加到StringBuilder中
                        oneLineBuilder.append(c);
                    }
                }

                String oneLineString = oneLineBuilder.toString();
                String fileName = "output.md";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    writer.write(oneLineString);
                    log.info("Markdown text has been written to " + fileName);
                } catch (IOException e) {
                    log.error("error", e);
                }
            }
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
