package ozaitseva.experiments.junit5.vanila;

import lombok.Data;

import java.util.List;

@Data
class RepoSearchResult {
    private Integer totalCount;
    private List<Repo> items;

    @Data
    static class Repo {
        private String id;
        private String name;
        private String fullName;
        private String language;
        private Owner owner;

        @Data
        static class Owner {
            private String login;
            private String id;
            private String type;
        }
    }
}
