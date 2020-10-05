package pl.pa3c.agileman.api.info;

import lombok.Data;

@Data


public class InformationSO {
    private String version;
    private Long time;
    private String gitCommitID;
    private String gitCommitBranch;
    private String gitCommitMessage;
}
