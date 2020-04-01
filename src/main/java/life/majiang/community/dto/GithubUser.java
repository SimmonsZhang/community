package life.majiang.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String login;
    private String name;
    private String bio;
    private String id;
    private String avatar_url;
}
